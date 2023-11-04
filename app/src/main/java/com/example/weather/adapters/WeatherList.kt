package com.example.weather.adapters


import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherList(
    val list: ArrayList<Weather>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    val dt_txt: String,
    val main: Main,
    val clouds: Clouds,
    val wind: Wind
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Main(
    val temp: Int, val humidity: Double, val temp_max: Int, val temp_min: Int, val pressure: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Wind(
    val speed: Double
)

data class Clouds(
    val all: Double
)