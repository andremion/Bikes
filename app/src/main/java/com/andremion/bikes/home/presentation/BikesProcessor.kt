package com.andremion.bikes.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.andremion.bikes.data.BikesRepository
import com.andremion.bikes.home.presentation.BikesContract.Action
import com.andremion.bikes.home.presentation.BikesContract.Result
import com.andremion.bikes.home.presentation.BikesContract.ViewEffect
import com.andremion.bikes.udf.UdfProcessor

class BikesProcessor(
    private val bikesRepository: BikesRepository
) : UdfProcessor<Action, Result, ViewEffect>() {

    override fun invoke(action: Action): LiveData<Result> =
        when (action) {
            is Action.FindNetworks -> findNetworks()
            is Action.GetNetworkById -> getNetworkById(action.id)
        }

    private fun findNetworks(): LiveData<Result> = liveData {
        emit(Result.SetLoading)
        try {
            val networks = bikesRepository.findNetworks()
            emit(Result.SetNetworks(networks))
        } catch (e: Exception) {
            trigger(ViewEffect.ShowError(e.message ?: ""))
        }
    }

    private fun getNetworkById(id: String): LiveData<Result> = liveData {
        emit(Result.SetLoading)
        try {
            val network = bikesRepository.getNetworkById(id)
            emit(Result.SetStations(network.stations))
        } catch (e: Exception) {
            trigger(ViewEffect.ShowError(e.message ?: ""))
        }
    }
}
