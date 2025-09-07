package com.example.test.flights.presentation.share_test

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.test.flights.domain.model.Flight

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShareTestMainScreen(
    flightList: List<Flight>,
    onSelect: (Flight) -> Unit,
    transitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(flightList) { flight ->
            val painter = rememberAsyncImagePainter(model = flight.airlineLogoUrl) // Cache painter

            with(transitionScope) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = flight.id),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .clickable { onSelect(flight) }
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Column {
                        Text(
                            text = flight.airline,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = flight.flightNumber,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShareTestDetailScreen(
    onBack: () -> Unit,
    selectedFlight: Flight,
    transitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    BackHandler { onBack() }

    val painter = rememberAsyncImagePainter(model = selectedFlight.airlineLogoUrl) // Cache

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onBack() },
        contentAlignment = Alignment.Center
    ) {
        with(transitionScope) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = selectedFlight.id),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(16.dp)), // Shape giống Main
                    contentScale = ContentScale.Crop // Scale giống Main
                )

                Text(
                    text = selectedFlight.airline,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "${selectedFlight.departureAirportCode} -> ${selectedFlight.arrivalAirportCode}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}