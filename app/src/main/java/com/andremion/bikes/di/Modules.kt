package com.andremion.bikes.di

import com.andremion.bikes.data.BikesRepository
import com.andremion.bikes.data.BuildConfig
import com.andremion.bikes.data.local.BikesLocalDataSource
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
    factory { BikesRepository(get(), get()) }
    factory { BikesRemoteDataSource(get()) }
    factory<CityBikeService> { CityBikeApi(BuildConfig.API_URL) }
    factory { BikesLocalDataSource() }
}
