package com.andremion.bikes.home.presentation

import com.andremion.bikes.data.entity.Network

object BikesContract {

    sealed class Action {
        object FindNetworks : Action()
    }

    sealed class Result {
        object SetLoading : Result()
        data class SetNetworks(val networks: List<Network>) : Result()
        data class SetError(val error: String?) : Result()
    }

    sealed class ViewEffect {
        data class ShowError(val error: String) : ViewEffect()
    }

    data class ViewState(
        val loading: Boolean,
        val networks: List<Network>,
        val error: String?
    ) {
        companion object {
            val INITIAL = ViewState(
                loading = false,
                networks = emptyList(),
                error = null
            )
        }
    }
}