package com.codinomello.aqua.data

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val rain: Rain?
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Weather(
    val description: String
)

data class Rain(
    val oneHour: Float? // 1-hour precipitation in mm
)