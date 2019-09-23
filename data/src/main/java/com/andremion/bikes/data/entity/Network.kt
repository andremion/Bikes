package com.andremion.bikes.data.entity

data class Network(
    val id: String,
    val name: String,
    val href: String,
    val location: Location,
    val stations: List<Station> = emptyList()
) {

    data class Location(
        val city: String,
        val latitude: Double,
        val longitude: Double
    )
}
