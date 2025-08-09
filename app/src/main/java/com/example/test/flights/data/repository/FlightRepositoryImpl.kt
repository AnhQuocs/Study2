package com.example.test.flights.data.repository

import com.example.test.flights.data.mapper.toFlight
import com.example.test.flights.data.source.FirebaseFlightDataSource
import com.example.test.flights.domain.model.Flight
import com.example.test.flights.domain.repository.FlightRepository

class FlightRepositoryImpl(
    private val dataSource: FirebaseFlightDataSource
): FlightRepository {
    override suspend fun getAllFlights(): List<Flight> {
        return dataSource.fetchAllFlight().map { (id, dto) ->
            dto.toFlight(id)
        }
    }
}