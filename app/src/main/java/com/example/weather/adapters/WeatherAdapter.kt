package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class WeatherAdapter(weatherList: WeatherList) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    private var newWeatherList = weatherList.list.filter { it.dt_txt.contains("12:00:00") }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val currentTemp: TextView = itemView.findViewById(R.id.tvTemp)
        private val tempMaxMin: TextView = itemView.findViewById(R.id.tempMaxMin)
        fun bindView(item: Weather) {
            this.tvDate.text = item.dt_txt
            this.currentTemp.text = item.main.temp.toString()
            this.tempMaxMin.text = "${item.main.temp_max}°C/${item.main.temp_min}°C"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )

    override fun getItemCount(): Int {
        return newWeatherList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(newWeatherList[position])
    }
}