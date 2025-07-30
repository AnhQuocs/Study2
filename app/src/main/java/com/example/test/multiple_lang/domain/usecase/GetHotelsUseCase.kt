package com.example.test.multiple_lang.domain.usecase

import com.example.test.multiple_lang.Hotel
import com.example.test.multiple_lang.data.repository.HotelRepository

class GetHotelsUseCase(private val repository: HotelRepository) {
    suspend operator fun invoke(): List<Hotel> {
        return repository.getHotels()
    }
}