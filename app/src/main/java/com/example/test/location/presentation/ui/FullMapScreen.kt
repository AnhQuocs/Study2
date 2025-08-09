package com.example.test.location.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun FullMapScreen(latLng: LatLng) {
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    ) {
        mapView.getMapAsync { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            googleMap.addMarker(MarkerOptions().position(latLng).title("You are here"))

            // Bật tương tác
            googleMap.uiSettings.apply {
                isZoomControlsEnabled = true
                isScrollGesturesEnabled = true
                isZoomGesturesEnabled = true
                isMapToolbarEnabled = true
            }
        }
    }
}
