package com.andremion.bikes.home.presentation

import com.andremion.bikes.home.presentation.BikesContract.Result
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.udf.UdfReducer

class BikesReducer : UdfReducer<ViewState, Result> {

    override fun invoke(currentState: ViewState, result: Result) =
        when (result) {
            is Result.SetLoading -> currentState.copy(
                loading = true
            )
            is Result.SetNetworks -> currentState.copy(
                loading = false,
                networks = result.networks
            )
            is Result.SetStations -> currentState.copy(
                loading = false,
                stations = result.stations
            )
        }
}
