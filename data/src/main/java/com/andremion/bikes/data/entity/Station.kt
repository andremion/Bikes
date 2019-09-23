package com.andremion.bikes.data.entity

data class Station(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val slots: Int,
    val bikes: Int
)
