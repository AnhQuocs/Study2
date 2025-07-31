package com.example.test.weather.domain.repository

import com.example.test.weather.domain.model.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResult
}