package com.example.weather


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.fragments.FourthFragment
import com.example.weather.fragments.MainFragment
import com.example.weather.fragments.SecondFragment
import com.example.weather.fragments.ThirdFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bNav.selectedItemId = R.id.menuFirst

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance()).commit()

        binding.bNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuFirst -> {
                    val fragment = MainFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.menuSecond -> {
                    val fragment = SecondFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.menuThurd -> {
                    val fragment = ThirdFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                R.id.menuFourth -> {
                    val fragment = FourthFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    true
                }
                else -> false
            }

        }
    }
}

