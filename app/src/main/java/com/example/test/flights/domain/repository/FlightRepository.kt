package com.example.test.flights.domain.repository

import com.example.test.flights.domain.model.Flight

interface FlightRepository {
    suspend fun getAllFlights(): List<Flight>
}