package com.example.tableroplus_jetpackcompose.Repository

import com.example.tableroplus_jetpackcompose.Model.CurrentWeather
import com.example.tableroplus_jetpackcompose.Remote.RetrofitInstance

class WeatherRepository {

    /**
     * Llama a la API de Open-Meteo para obtener el clima actual de Santiago.
     *
     * Devuelve:
     * - Result.success(CurrentWeather) si todo sale bien.
     * - Result.failure(Exception) si hay algún error (sin internet, timeout, etc.).
     */
    suspend fun getCurrentWeatherSantiago(): Result<CurrentWeather> {
        return try {
            val response = RetrofitInstance.api.getCurrentWeather()
            Result.success(response.currentWeather)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}