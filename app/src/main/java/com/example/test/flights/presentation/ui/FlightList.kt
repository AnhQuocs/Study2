package com.example.test.flights.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.test.flights.domain.model.Flight
import com.example.test.flights.presentation.viewmodel.FlightViewModel
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun FlightList(
    flightViewModel: FlightViewModel = hiltViewModel()
) {
    val flights = flightViewModel.flights

    val isLoading = flightViewModel.isFlightLoading.value

    LaunchedEffect(Unit) {
        flightViewModel.loadFlights()
    }

    if(isLoading || flights.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                flights,
                key = { it.id }
            ) {flight ->
                FlightItem(flight)
            }
        }
    }

}

@Composable
fun FlightItem(flight: Flight) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(flight.airlineLogoUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED) // lưu xuống disk
                        .memoryCachePolicy(CachePolicy.ENABLED) // cache RAM
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(200.dp, 50.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = "Airline: ${flight.airline}"
            )

            val itemsStops = flight.stops

            Row {
                Text(
                    text = "Journey: ${flight.departureAirportCode} -> ${flight.arrivalAirportCode}"
                )

                if (itemsStops.isNotEmpty()) {
                    itemsStops.forEachIndexed { _, flightStops ->
                        Text(
                            text = " (via ${flightStops.airportCode})"
                        )
                    }
                }
            }

            Text(
                text = "Address: ${flight.departureShortAddress} -> ${flight.arrivalShortAddress}"
            )

            Text(
                text = "Flights/day: ${flight.numberOfFlightsInDay}"
            )

            flight.schedules.forEachIndexed{ index, schedule ->
                Text(text = "Schedule #${index + 1}")
                Text(
                    text = "Time: ${
                        convertToVietnamTime(schedule.departureTime)
                    } -> ${
                        convertToVietnamTime(schedule.arrivalTime)
                    }"
                )
                Text(text = "Available Seats Economy: ${schedule.availableSeatsEconomy}")
                Text(text = "Available Seats Business: ${schedule.availableSeatsBusiness}")
                Text(text = "Duration: ${schedule.durationMinutes} minutes")
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = "Price Economy: ${flight.priceEconomy}"
            )

            Text(
                text = "Price Business: ${flight.priceBusiness}"
            )
        }
    }
}

fun convertToVietnamTime(timeString: String, outputPattern: String = "HH:mm"): String {
    return try {
        // Parse chuỗi dạng "HH:mm:ss±HH:mm" thành OffsetTime
        val offsetTime = OffsetTime.parse(timeString, DateTimeFormatter.ISO_OFFSET_TIME)
        // Chuyển sang múi giờ +07:00 (Vietnam)
        val vietnamOffset = ZoneOffset.of("+07:00")
        val vietnamTime = offsetTime.withOffsetSameInstant(vietnamOffset)
        // Format lại thời gian theo định dạng đầu ra
        vietnamTime.format(DateTimeFormatter.ofPattern(outputPattern))
    } catch (e: Exception) {
        // Nếu lỗi parse trả về nguyên bản
        timeString
    }
}