package com.codinomello.aqua.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) {
    suspend fun getWeather(): Result<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getWeather(
                    lat = -23.4628, // Guarulhos latitude
                    lon = -46.5331, // Guarulhos longitude
                    apiKey = "YOUR_API_KEY" // Replace with your OpenWeatherMap API key
                )
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}