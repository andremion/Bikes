package com.andremion.bikes.data.remote

import com.andremion.bikes.data.remote.api.CityBikeService
import com.andremion.bikes.data.remote.model.NetworkRemote
import kotlinx.coroutines.delay

class BikesRemoteDataSource(
    private val service: CityBikeService
) {

    suspend fun findNetworks(): List<NetworkRemote> {
        // TODO Temporary simulation of network latency
        delay(2000)
        return service.findNetworks().networks
    }

    suspend fun getNetworkById(id: String) = service.getNetworkById(id).network
}