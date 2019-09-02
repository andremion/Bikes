package com.andremion.bikes.data

import com.andremion.bikes.data.entity.Location
import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.data.local.BikesLocalDataSource
import com.andremion.bikes.data.remote.BikesRemoteDataSource
import com.andremion.bikes.data.remote.model.LocationRemote
import com.andremion.bikes.data.remote.model.NetworkRemote
import kotlinx.coroutines.delay

class BikesRepository(
    private val remoteDataSource: BikesRemoteDataSource,
    private val localDataSource: BikesLocalDataSource
) {

    suspend fun findNetworks(): List<Network> {
        // TODO Temporary simulation of network latency
        delay(2000)
        return remoteDataSource.findNetworks().toEntityList()
    }

    suspend fun getNetworkById(id: String) = remoteDataSource.getNetworkById(id).toEntity()
}

private fun List<NetworkRemote>.toEntityList() = map { it.toEntity() }

private fun NetworkRemote.toEntity() = Network(id, name, href, location.toEntity())

private fun LocationRemote.toEntity() = Location(city, latitude, longitude)