package com.andremion.bikes.data.remote.model

data class NetworkRemote(
    val id: String,
    val name: String,
    val href: String,
    val location: Location,
    val stations: List<StationRemote>? = emptyList()
) {

    data class Location(
        val city: String,
        val latitude: Double,
        val longitude: Double
    )
}
