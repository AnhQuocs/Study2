package com.example.test.flights.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.test.MyApplication
import com.example.test.flights.domain.model.Flight
import com.example.test.flights.domain.usecase.GetAllFlightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val getAllFlightsUseCase: GetAllFlightsUseCase,
    private val imageLoader: ImageLoader
): ViewModel() {

    private val _isFlightLoading = mutableStateOf(true)
    val isFlightLoading: State<Boolean> = _isFlightLoading

    var flights by mutableStateOf<List<Flight>>(emptyList())
        private set

    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
            _isFlightLoading.value = true
            flights = getAllFlightsUseCase()
            _isFlightLoading.value = false
        }
    }

    fun preloadImage(url: String) {
        viewModelScope.launch {
            imageLoader.enqueue(
                ImageRequest.Builder(context = MyApplication.instance)
                    .data(url)
                    .build()
            )
        }
    }
}