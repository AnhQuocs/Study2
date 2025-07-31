package com.example.test.weather.domain.usecase

import com.example.test.weather.domain.repository.WeatherRepository

class GetWeatherByLocationUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getWeather(lat, lon)
}