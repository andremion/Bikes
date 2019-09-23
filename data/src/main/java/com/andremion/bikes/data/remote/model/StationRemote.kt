package com.andremion.bikes.data.remote.model

import com.google.gson.annotations.SerializedName

data class StationRemote(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("empty_slots") val slots: Int,
    @SerializedName("free_bikes") val bikes: Int
)
