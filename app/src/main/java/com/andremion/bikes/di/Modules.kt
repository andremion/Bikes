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

package com.andremion.bikes.di

import com.andremion.bikes.data.BikesRepository
import com.andremion.bikes.data.BuildConfig
import com.andremion.bikes.data.remote.BikesRemoteDataSource
import com.andremion.bikes.data.remote.api.CityBikeApi
import com.andremion.bikes.data.remote.api.CityBikeService
import com.andremion.bikes.home.presentation.BikesContract.ViewState
import com.andremion.bikes.home.presentation.BikesProcessor
import com.andremion.bikes.home.presentation.BikesReducer
import com.andremion.bikes.home.presentation.BikesUdfDispatcher
import com.andremion.bikes.home.presentation.BikesViewModel
import com.andremion.bikes.udf.UdfDispatcherImpl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { BikesViewModel(get()) }

    factory<BikesUdfDispatcher> {
        UdfDispatcherImpl(
            get<BikesProcessor>(),
            get<BikesReducer>(),
            ViewState.INITIAL
        )
    }
    factory { BikesProcessor(get()) }
    factory { BikesReducer() }
}

val dataModule = module {
    factory { BikesRepository(get()) }
    factory { BikesRemoteDataSource(get()) }
    factory<CityBikeService> { CityBikeApi(BuildConfig.API_URL) }
}
