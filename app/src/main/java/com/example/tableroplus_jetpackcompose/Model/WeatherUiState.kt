package com.example.tableroplus_jetpackcompose.Model

data class WeatherUiState(
    val isLoading: Boolean = false,
    val temperature: String? = null,
    val windSpeed: String? = null,
    val errorMessage: String? = null
)
