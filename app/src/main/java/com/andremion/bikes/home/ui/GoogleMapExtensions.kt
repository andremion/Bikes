/*
 * Copyright (c) 2019. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
