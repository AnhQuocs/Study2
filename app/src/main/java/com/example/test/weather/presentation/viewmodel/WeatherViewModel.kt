package com.example.test.weather.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.weather.domain.model.WeatherResult
import com.example.test.weather.domain.usecase.GetWeatherByLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class STATE {
    LOADING,
    SUCCESS,
    FAILED
}

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherByLocationUseCase
) : ViewModel() {
    var state by mutableStateOf(STATE.LOADING)
    var weather by mutableStateOf<WeatherResult?>(null)
    var errorMessage by mutableStateOf("")

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            state = STATE.LOADING
            try {
                weather = getWeatherUseCase(lat, lon)
                state = STATE.SUCCESS
            } catch (e: Exception) {
                errorMessage = e.message ?: "Lỗi không xác định"
                state = STATE.FAILED
            }
        }
    }
}