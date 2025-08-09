package com.example.test.location.domain.repository

import com.google.android.gms.maps.model.LatLng

interface LocationRepository {
    suspend fun getCurrentLocation(): LatLng?
}