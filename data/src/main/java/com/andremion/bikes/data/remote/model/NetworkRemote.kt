package com.andremion.bikes.data.remote.model

data class NetworkRemote(
    val id: String,
    val name: String,
    val href: String,
    val location: LocationRemote
)

