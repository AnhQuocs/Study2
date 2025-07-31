package com.example.test.weather.utils

object Utils {
    fun buildIcon(icon: String): String {
        return "https://openweathermap.org/img/wn/$icon@4x.png"
    }
}