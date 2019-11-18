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
