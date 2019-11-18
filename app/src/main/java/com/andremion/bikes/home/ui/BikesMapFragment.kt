package com.andremion.bikes.home.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import com.andremion.bikes.R
import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.data.entity.Station
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect.ShowError
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.home.presentation.BikesViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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
        viewModel.states.observe(this, Observer(::render))
        viewModel.effects.observe(this, Observer(::trigger))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap.withInitialSetup().apply {
            onCameraMoveToZoomLevel(
                zoomLevel = MapZoomLevel.STREETS,
                onMoveIn = { zoomLevel -> Log.d("GoogleMap", "Moved in: $zoomLevel") },
                onMoveOut = { zoomLevel -> Log.d("GoogleMap", "Moved out: $zoomLevel") }
            )
            setOnMarkerClickListener { marker ->
                (marker.tag as? Network)?.let { network ->
                    viewModel.getNetworkById(network.id)
                    animateCameraTo(marker.position)
                }
                false
            }
        }

        viewModel.findNetworks()

        activity?.checkLocationPermission(view) {
            googleMap.enableMyLocation()

            activity?.getDeviceLocation(
                onSuccess = { myLocation -> googleMap animateCameraTo myLocation },
                onError = {
                    // TODO Add Crashlytics
                    Log.e("GoogleMap", "Exception while getting location. Using defaults: %s", it)
                }
            )
        }
    }

    private fun render(viewState: ViewState) {
        when {
            viewState.loading -> renderLoading()
            viewState.stations.isNotEmpty() -> renderStations(viewState.stations)
            else -> renderNetworks(viewState.networks)
        }
    }

    private fun renderLoading() {
        view?.let {
            Snackbar.make(it, "Loading...", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun renderStations(stations: List<Station>) {
        googleMap?.apply {
            stations.forEach { station ->
                addMarker(
                    MarkerOptions()
                        .title(station.name)
                        .snippet(station.buildMarkerSnippet())
                        .icon(station.buildMarkerIcon())
                        .position(LatLng(station.latitude, station.longitude))
                )
            }
        }
    }

    private fun renderNetworks(networks: List<Network>) {
        googleMap?.apply {
            clear()
            networks.forEach { network ->
                addMarker(
                    MarkerOptions()
                        .title(network.name)
                        .snippet(network.location.city)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(LatLng(network.location.latitude, network.location.longitude))
                ).apply {
                    tag = network
                }
            }
        }
    }

    private fun trigger(effect: ViewEffect) {
        when (effect) {
            is ShowError -> triggerError(effect.error)
        }
    }

    private fun triggerError(error: String) = view?.let {
        Snackbar.make(it, error, Snackbar.LENGTH_LONG).show()
    }

    private fun Station.buildMarkerSnippet() = resources.getQuantityString(
        R.plurals.bikes_map_station_snippet_text_bikes,
        bikes, bikes
    ) + " " + resources.getQuantityString(
        R.plurals.bikes_map_station_snippet_text_slots,
        slots, slots
    )

    private fun Station.buildMarkerIcon(): BitmapDescriptor {
        val color = when (bikes) {
            0 -> android.R.color.holo_red_dark
            in 1..slots / 2 -> android.R.color.holo_orange_light
            else -> android.R.color.holo_green_dark
        }
        return requireContext().buildBitmapDescriptorFromVectorDrawable(
            R.drawable.ic_directions_bike_black_24dp, color
        )
    }
}

private fun Context.buildBitmapDescriptorFromVectorDrawable(
    @DrawableRes vectorDrawableResId: Int, @ColorRes tintColorResId: Int
): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this, vectorDrawableResId)
    val tintColor = ContextCompat.getColor(this, tintColorResId)
    return requireNotNull(vectorDrawable).let { drawable ->
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        DrawableCompat.setTint(drawable, tintColor)
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ).run {
            Canvas(this).also { canvas ->
                drawable.draw(canvas)
            }
            BitmapDescriptorFactory.fromBitmap(this)
        }
    }
}
