package com.example.test.flights.data.model

data class FlightDto(
    val airline: String? = null,
    val flightNumber: String? = null,
    val airlineLogoUrl: String? = null,
    val departureAirportCode: String? = null,
    val departureAirportName: Map<String, String>? = null,
    val departureShortAddress: Map<String, String>? = null,
    val arrivalAirportCode: String? = null,
    val arrivalAirportName: Map<String, String>? = null,
    val arrivalShortAddress: Map<String, String>? = null,
    val numberOfFlightsInDay: Int? = null,
    val schedules: List<FlightSchedulesDto> = emptyList(),
    val priceEconomy: Int? = null,
    val priceBusiness: Int? = null,
    val numberOfStops: Int? = null,
    val stops: List<FlightStopsDto> = emptyList()
)

data class FlightSchedulesDto (
    val departureTime: String? = null,
    val arrivalTime: String? = null,
    val durationMinutes: Int? = null,
    val availableSeatsEconomy: Int? = null,
    val availableSeatsBusiness: Int? = null
)

data class FlightStopsDto (
    val airportCode: String? = null,
    val airportName: Map<String, String>? = null,
    val stopDurationMinutes: Int? = null,
    val stopShortAddress: Map<String, String>? = null
)