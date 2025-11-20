package com.example.tableroplus_jetpackcompose.Remote

import com.example.tableroplus_jetpackcompose.Model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    // Llamada a:
    // https://api.open-meteo.com/v1/forecast?latitude=-33.45&longitude=-70.67&current_weather=true
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double = -33.45,
        @Query("longitude") longitude: Double = -70.67,
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}