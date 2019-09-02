package com.andremion.bikes.data.remote

import com.andremion.bikes.data.remote.api.CityBikeService

class BikesRemoteDataSource(
    private val service: CityBikeService
) {

    suspend fun findNetworks() = service.findNetworks().networks

    suspend fun getNetworkById(id: String) = service.getNetworkById(id).network
}