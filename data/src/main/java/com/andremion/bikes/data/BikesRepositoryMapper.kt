package com.andremion.bikes.data

import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.data.entity.Station
import com.andremion.bikes.data.remote.model.NetworkRemote
import com.andremion.bikes.data.remote.model.StationRemote

internal fun List<NetworkRemote>.toNetworkEntityList() = map { it.toEntity() }

internal fun NetworkRemote.toEntity() =
    Network(id, name, href, location.toEntity(), stations.toStationEntityList())

private fun NetworkRemote.Location.toEntity() = Network.Location(city, latitude, longitude)

private fun List<StationRemote>?.toStationEntityList() = this?.map { it.toEntity() } ?: emptyList()

private fun StationRemote.toEntity() = Station(id, name, latitude, longitude, slots, bikes)
