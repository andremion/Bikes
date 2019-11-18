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

package com.andremion.bikes.home.presentation

import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.data.entity.Station

object BikesContract {

    sealed class Action {
        object FindNetworks : Action()
        data class GetNetworkById(val id: String) : Action()
    }

    sealed class Result {
        object SetLoading : Result()
        data class SetNetworks(val networks: List<Network>) : Result()
        data class SetStations(val stations: List<Station>) : Result()
    }

    sealed class ViewEffect {
        data class ShowError(val error: String) : ViewEffect()
    }

    data class ViewState(
        val loading: Boolean,
        val networks: List<Network>,
        val stations: List<Station>
    ) {
        companion object {
            val INITIAL = ViewState(
                loading = false,
                networks = emptyList(),
                stations = emptyList()
            )
        }
    }
}
