package com.example.tableroplus_jetpackcompose.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tableroplus_jetpackcompose.Model.WeatherUiState
import com.example.tableroplus_jetpackcompose.Repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    // Estado que la UI va a observar
    var weatherUiState by mutableStateOf(WeatherUiState())
        private set

    fun loadWeather() {
        // Lanzamos una corrutina en el alcance del ViewModel
        viewModelScope.launch {
            // 1. Mostrar que estamos cargando
            weatherUiState = weatherUiState.copy(
                isLoading = true,
                errorMessage = null
            )

            // 2. Llamar al repositorio
            val result = repository.getCurrentWeatherSantiago()

            // 3. Actualizar estado según éxito o error
            result
                .onSuccess { currentWeather ->
                    val tempText = "${currentWeather.temperature} °C"
                    val windText = "${currentWeather.windspeed} km/h"

                    weatherUiState = weatherUiState.copy(
                        isLoading = false,
                        temperature = tempText,
                        windSpeed = windText,
                        errorMessage = null
                    )
                }
                .onFailure { e ->
                    weatherUiState = weatherUiState.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error al cargar el clima"
                    )
                }
        }
    }
}