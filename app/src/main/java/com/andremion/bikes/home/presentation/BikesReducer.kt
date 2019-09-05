package com.andremion.bikes.home.presentation

import com.andremion.bikes.home.presentation.BikesContract.Result
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.udf.UdfReducer

class BikesReducer : UdfReducer<ViewState, Result> {

    override fun invoke(currentState: ViewState, result: Result) =
        when (result) {
            is Result.SetLoading -> currentState.copy(
                loading = true,
                networks = emptyList(),
                error = null
            )
            is Result.SetNetworks -> currentState.copy(
                loading = false,
                networks = result.networks,
                error = null
            )
            is Result.SetError -> currentState.copy(
                loading = false,
                networks = emptyList(),
                error = result.error
            )
        }
}
