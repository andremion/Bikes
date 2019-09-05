package com.andremion.bikes.home.ui

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect.ShowError
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.home.presentation.BikesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class BikesMapFragment : SupportMapFragment(), OnMapReadyCallback {

    private val viewModel: BikesViewModel by viewModel()

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            states.observe(this@BikesMapFragment, Observer { viewState ->
                render(viewState)
            })
            effects.observe(this@BikesMapFragment, Observer { effect ->
                trigger(effect)
            })
        }
    }

    private fun render(viewState: ViewState) {
        when {
            viewState.loading -> renderLoading()
            viewState.error != null -> renderLoadingError(viewState.error)
            else -> renderNetworks(viewState.networks)
        }
    }

    private fun renderLoading() {
        view?.let {
            Snackbar.make(it, "Loading...", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun renderLoadingError(error: String) {
        //TODO Render loading error
    }

    private fun renderNetworks(networks: List<Network>) {
        networks.forEach {
            val position = LatLng(it.location.latitude, it.location.longitude)
            googleMap?.apply {
                clear()
                addMarker(
                    MarkerOptions()
                        .title(it.name)
                        .snippet(it.location.city)
                        .position(position)
                )
            }
        }
    }

    private fun trigger(effect: ViewEffect) {
        when (effect) {
            is ShowError -> view?.let {
                Snackbar.make(it, effect.error, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.withInitialSetup()

        viewModel.findNetworks()

        activity?.checkLocationPermission(view) {
            googleMap.enableMyLocation()

            activity?.getDeviceLocation(
                onSuccess = { myLocation -> googleMap moveCameraTo myLocation },
                onError = {
                    // TODO Add Crashlytics
                    Log.e("GoogleMap", "Exception while getting location. Using defaults: %s", it)
                }
            )
        }
    }
}

private fun GoogleMap.withInitialSetup() = apply {
    uiSettings.isZoomControlsEnabled = true
    uiSettings.isZoomGesturesEnabled = true
    uiSettings.isMyLocationButtonEnabled = false
    isMyLocationEnabled = false
}

private fun GoogleMap.enableMyLocation() {
    uiSettings.isMyLocationButtonEnabled = true
    isMyLocationEnabled = true
}

private infix fun GoogleMap.moveCameraTo(location: Location) {
    moveCamera(
        CameraUpdateFactory.newLatLngZoom(
            LatLng(location.latitude, location.longitude),
            MapZoomLevel.CITY.inRange(
                minZoomLevel,
                maxZoomLevel
            )
        )
    )
}