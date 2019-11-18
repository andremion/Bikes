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

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.andremion.bikes.R
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.CompositePermissionListener
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }
}

fun Activity.checkLocationPermission(
    rootView: View?,
    onPermissionGranted: () -> Unit
) = Dexter.withActivity(this)
    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    .withListener(
        CompositePermissionListener(
            SnackbarOnDeniedPermissionListener.Builder.with(
                rootView ?: window.decorView,
                R.string.bikes_map_location_permission_request
            )
                .withDuration(Snackbar.LENGTH_INDEFINITE)
                .withOpenSettingsButton(R.string.action_settings)
                .build(),
            object : BasePermissionListener() {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    onPermissionGranted()
                }
            }
        ))
    .check()

@SuppressLint("MissingPermission")
fun Activity.getDeviceLocation(
    onSuccess: (Location) -> Unit,
    onError: (Exception) -> Unit
) {
    val locationResult = LocationServices.getFusedLocationProviderClient(this).lastLocation
    locationResult.addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            task.result?.let { onSuccess(it) }
        } else {
            task.exception?.let { onError(it) }
        }
    }
}
