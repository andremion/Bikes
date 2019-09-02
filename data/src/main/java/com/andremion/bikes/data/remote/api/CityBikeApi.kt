package com.andremion.bikes.data.remote.api

import com.andremion.bikes.data.BuildConfig
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityBikeApi(baseUrl: String) : CityBikeService {

    private val service: CityBikeService

    init {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) BODY else BASIC

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

        val client = httpClient.build()

        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        service = retrofit.create(CityBikeService::class.java)
    }

    override suspend fun findNetworks() = service.findNetworks()

    override suspend fun getNetworkById(id: String) = service.getNetworkById(id)
}
