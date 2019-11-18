/*
 * Copyright (c) 2019. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
