package com.example.weather

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.adapters.WeatherAdapter
import com.example.weather.adapters.WeatherList
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val WEATHER_SERVER_URL = "https://api.openweathermap.org"

class MainActivityViewModel : ViewModel(), LifecycleObserver {

    var latitude: String = ""
    var longitude: String = ""
    private var data: MutableLiveData<WeatherList>? = null
    private var weatherAdapter: WeatherAdapter? = null
    fun getData(): LiveData<WeatherList>? {
        if (data == null) {
            data = MutableLiveData<WeatherList>()
        }
        return data
    }

    fun getDataFromJson() {
        GlobalScope.launch(Dispatchers.IO) {
            val dataFromJson: WeatherList

            val client = HttpClient()

            val text: String =
                client.get("${WEATHER_SERVER_URL}/data/2.5/forecast?lat=${latitude}&lon=${longitude}&&appid=03c323788a61a8022d2c36cf46d0ce47&units=metric")

            val mapper = jacksonObjectMapper()
            dataFromJson = mapper.readValue(text)

            data?.postValue(dataFromJson)
        }
    }

    fun getAdapter(
        context: Context,
        data: WeatherList?,
        recyclerView: RecyclerView
    ): WeatherAdapter? {
        if (weatherAdapter == null) {
            weatherAdapter = WeatherAdapter(
                data!!
            )
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = weatherAdapter
        }
        return weatherAdapter
    }
}

