package com.andremion.bikes.home.presentation

import com.andremion.bikes.home.presentation.BikesContract.Action
import com.andremion.bikes.home.presentation.BikesContract.Result
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.udf.UdfDispatcher
import com.andremion.bikes.udf.UdfViewModel

typealias BikesUdfDispatcher = UdfDispatcher<Action, ViewState, ViewEffect>

class BikesViewModel(
    dispatcher: BikesUdfDispatcher
) : UdfViewModel<Action, Result, ViewState, ViewEffect>(dispatcher) {

    fun findNetworks() {
        submit(Action.FindNetworks)
    }

    fun getNetworkById(id: String) {
        submit(Action.GetNetworkById(id))
    }
}
