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
