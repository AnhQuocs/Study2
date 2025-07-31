package com.example.test.weather.data.remote.api

import com.example.test.weather.const.Const.openWeatherMapApiKey
import com.example.test.weather.domain.model.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey
    ): WeatherResult
}