package com.example.tableroplus_jetpackcompose.Model

data class CurrentWeather(
    val time: String,
    val interval: Int,
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Int,
    val is_day: Int,
    val weathercode: Int
)
