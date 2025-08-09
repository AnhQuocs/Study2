package com.example.test.flights.data.mapper

import com.example.test.flights.data.model.FlightDto
import com.example.test.flights.data.model.FlightSchedulesDto
import com.example.test.flights.data.model.FlightStopsDto
import com.example.test.flights.domain.model.Flight
import com.example.test.flights.domain.model.FlightSchedules
import com.example.test.flights.domain.model.FlightStops
import com.example.test.language.utils.LangUtils

fun FlightDto.toFlight(id: String): Flight {
    return Flight(
        id = id,
        airline = airline.orEmpty(),
        flightNumber = flightNumber.orEmpty(),
        airlineLogoUrl = airlineLogoUrl.orEmpty(),
        departureAirportCode = departureAirportCode.orEmpty(),
        departureAirportName = LangUtils.getLocalizedText(departureAirportName),
        departureShortAddress = LangUtils.getLocalizedText(departureShortAddress),
        arrivalAirportCode = arrivalAirportCode.orEmpty(),
        arrivalAirportName = LangUtils.getLocalizedText(arrivalAirportName),
        arrivalShortAddress = LangUtils.getLocalizedText(arrivalShortAddress),
        numberOfFlightsInDay = numberOfFlightsInDay ?: 0,
        schedules = schedules.map { it.toFlightSchedules() },
        priceEconomy = priceEconomy ?: 0,
        priceBusiness = priceBusiness ?: 0,
        numberOfStops = numberOfStops ?: 0,
        stops = stops.map { it.toFlightStops() }
    )
}

fun FlightSchedulesDto.toFlightSchedules(): FlightSchedules {
    return FlightSchedules(
        departureTime = departureTime.orEmpty(),
        arrivalTime = arrivalTime.orEmpty(),
        durationMinutes = durationMinutes ?: 0,
        availableSeatsEconomy = availableSeatsEconomy ?: 0,
        availableSeatsBusiness = availableSeatsBusiness ?: 0
    )
}

fun FlightStopsDto.toFlightStops(): FlightStops {
    return FlightStops(
        airportCode = airportCode.orEmpty(),
        airportName = LangUtils.getLocalizedText(airportName),
        stopDurationMinutes = stopDurationMinutes ?: 0,
        stopShortAddress = LangUtils.getLocalizedText(stopShortAddress)
    )
}