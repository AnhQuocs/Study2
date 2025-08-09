package com.example.test.location.domain.usecase

import com.example.test.location.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetUserLocationUseCase (
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LatLng? = locationRepository.getCurrentLocation()
}