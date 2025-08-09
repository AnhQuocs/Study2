package com.example.test.flights.domain.usecase

import com.example.test.flights.domain.model.Flight
import com.example.test.flights.domain.repository.FlightRepository

class GetAllFlightsUseCase(
    private val repository: FlightRepository
) {
    suspend operator fun invoke(): List<Flight> {
        return repository.getAllFlights()
    }
}