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

package com.andremion.bikes.data

import com.andremion.bikes.data.entity.Network
import com.andremion.bikes.data.remote.BikesRemoteDataSource
import com.andremion.bikes.data.remote.model.NetworkRemote
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BikesRepositoryTest {

    private val dataSource: BikesRemoteDataSource = mockk()
    private val repository = BikesRepository(dataSource)

    @Test
    fun `when find networks with success, should get network list properly`() = runBlocking {
        // Given
        coEvery { dataSource.findNetworks() } returns Preconditions.networks
        // When
        val result = repository.findNetworks()
        // Then
        assertThat(result).isEqualTo(Expectations.networks)
    }

    @Test
    fun `when find networks with error, should throw exception`() {
        runBlocking {
            // Given
            coEvery { dataSource.findNetworks() } throws Preconditions.error
            try {
                // When
                repository.findNetworks()
            } catch (e: Exception) {
                // Then
                assertThat(e).isEqualTo(Expectations.error)
            }
        }
    }

    @Test
    fun `when get network by id with success, should get network properly`() = runBlocking {
        // Given
        val id = "id"
        coEvery { dataSource.getNetworkById(id) } returns Preconditions.network
        // When
        val result = repository.getNetworkById(id)
        // Then
        assertThat(result).isEqualTo(Expectations.network)
    }

    @Test
    fun `when get network by id with error, should thrown exception`() {
        runBlocking {
            // Given
            coEvery { dataSource.findNetworks() } throws Preconditions.error
            try {
                // When
                repository.findNetworks()
            } catch (e: Exception) {
                // Then
                assertThat(e).isEqualTo(Expectations.error)
            }
        }
    }
}

private object Preconditions {
    val network = NetworkRemote(
        id = "id",
        name = "name",
        href = "href",
        location = NetworkRemote.Location(
            city = "city",
            latitude = 123.321,
            longitude = 321.123
        )
    )
    val networks = listOf(network)
    val error = SomeException("An error")
}

private object Expectations {
    val network = Network(
        id = "id",
        name = "name",
        href = "href",
        location = Network.Location(
            city = "city",
            latitude = 123.321,
            longitude = 321.123
        )
    )
    val networks = listOf(network)
    val error = SomeException("An error")
}

private data class SomeException(override val message: String) : Exception(message)
