package com.andremion.bikes.data.remote.api

import com.andremion.bikes.data.remote.model.FindNetworksResponse
import com.andremion.bikes.data.remote.model.GetNetworkByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CityBikeService {

    @GET("v2/networks?fields=id,name,href,location")
    suspend fun findNetworks(): FindNetworksResponse

    @GET("v2/networks/{id}")
    suspend fun getNetworkById(@Path("id") id: String): GetNetworkByIdResponse
}
