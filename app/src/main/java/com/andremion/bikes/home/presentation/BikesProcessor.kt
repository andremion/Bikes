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
