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
