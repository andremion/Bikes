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
