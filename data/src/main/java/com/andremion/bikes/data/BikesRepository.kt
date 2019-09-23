package com.andremion.bikes.data

import com.andremion.bikes.data.remote.BikesRemoteDataSource

class BikesRepository(private val remoteDataSource: BikesRemoteDataSource) {

    suspend fun findNetworks() = remoteDataSource.findNetworks().toNetworkEntityList()

    suspend fun getNetworkById(id: String) = remoteDataSource.getNetworkById(id).toEntity()
}
