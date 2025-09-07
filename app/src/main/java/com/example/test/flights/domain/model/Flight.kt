package com.example.test.flights.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Flight(
    val id: String,
    val airline: String,
    val flightNumber: String,
    val airlineLogoUrl: String,
    val departureAirportCode: String,
    val departureAirportName: String,
    val departureShortAddress: String,
    val arrivalAirportCode: String,
    val arrivalAirportName: String,
    val arrivalShortAddress: String,
    val numberOfFlightsInDay: Int,
    val schedules: List<FlightSchedules>,
    val priceEconomy: Int,
    val priceBusiness: Int,
    val numberOfStops: Int,
    val stops: List<FlightStops>
) : Parcelable

@Parcelize
data class FlightSchedules (
    val departureTime: String,
    val arrivalTime: String,
    val durationMinutes: Int,
    val availableSeatsEconomy: Int,
    val availableSeatsBusiness: Int
) : Parcelable

@Parcelize
data class FlightStops (
    val airportCode: String,
    val airportName: String,
    val stopDurationMinutes: Int,
    val stopShortAddress: String
) : Parcelable