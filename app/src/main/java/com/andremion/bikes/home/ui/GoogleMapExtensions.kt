package com.andremion.bikes.home.ui

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

internal fun GoogleMap.withInitialSetup() = apply {
    uiSettings.isZoomControlsEnabled = true
    uiSettings.isZoomGesturesEnabled = true
    uiSettings.isMyLocationButtonEnabled = false
    isMyLocationEnabled = false
}

internal fun GoogleMap.onCameraMoveToZoomLevel(
    zoomLevel: MapZoomLevel,
    onMoveIn: (zoomLevel: Float) -> Unit,
    onMoveOut: (zoomLevel: Float) -> Unit
) {
    setOnCameraMoveListener(object : GoogleMap.OnCameraMoveListener {

        private var isInRange = false

        override fun onCameraMove() {
            if (zoomLevel.inRange(cameraPosition.zoom)) {
                if (!isInRange) {
                    isInRange = true
                    onMoveIn(cameraPosition.zoom)
                }
            } else if (isInRange) {
                isInRange = false
                onMoveOut(cameraPosition.zoom)
            }
        }
    })
}

internal fun GoogleMap.enableMyLocation() {
    uiSettings.isMyLocationButtonEnabled = true
    isMyLocationEnabled = true
}

internal infix fun GoogleMap.animateCameraTo(location: Location) {
    animateCameraTo(LatLng(location.latitude, location.longitude))
}

internal infix fun GoogleMap.animateCameraTo(latLng: LatLng) {
    animateCamera(
        buildCameraUpdate(latLng)
    )
}

internal fun GoogleMap.buildCameraUpdate(latLng: LatLng) = CameraUpdateFactory.newLatLngZoom(
    latLng,
    MapZoomLevel.CITY.inRange(
        minZoomLevel,
        maxZoomLevel
    )
)
