package com.example.test.weather.data.repository

import com.example.test.weather.data.remote.api.IApiService
import com.example.test.weather.domain.model.WeatherResult
import com.example.test.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: IApiService
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherResult {
        return api.getWeather(lat, lon)
    }
}