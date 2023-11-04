package com.example.weather.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.MainActivityViewModel
import com.example.weather.adapters.WeatherAdapter
import com.example.weather.adapters.WeatherList
import com.example.weather.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private lateinit var launcher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private var weatherAdapter: WeatherAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model: MainActivityViewModel =
            ViewModelProvider(this)[MainActivityViewModel::class.java]

        fLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation(model)

        var data: LiveData<WeatherList>? = null
        data = model.getData()

        data?.observe(this, Observer {
            if (data != null) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.VISIBLE
                }
                weatherAdapter =
                    context?.let { it1 -> model.getAdapter(it1, data.value, binding.recyclerView) }
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun permissionListener() {
        launcher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            it
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation(model: MainActivityViewModel) {
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener {
                var lat = "${it.result.latitude}"
                model.latitude = lat.replace(",", ".")
                var long = "${it.result.longitude}"
                model.longitude = long.replace(",", ".")
                model.getDataFromJson()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
