package com.andremion.bikes.data.remote

import com.andremion.bikes.data.remote.api.CityBikeService
import com.andremion.bikes.data.remote.model.FindNetworksResponse
import com.andremion.bikes.data.remote.model.GetNetworkByIdResponse
import com.andremion.bikes.data.remote.model.NetworkRemote
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BikesRemoteDataSourceTest {

    private val service: CityBikeService = mockk()
    private val dataSource = BikesRemoteDataSource(service)

    @Test
    fun `when find networks with success, should get network list properly`() = runBlocking {
        // Given
        coEvery { service.findNetworks() } returns Preconditions.findNetworkResponse
        // When
        val result = dataSource.findNetworks()
        // Then
        assertThat(result).isEqualTo(Expectations.networks)
    }

    @Test
    fun `when find networks with error, should throw exception`() {
        runBlocking {
            // Given
            coEvery { service.findNetworks() } throws Preconditions.error
            try {
                // When
                dataSource.findNetworks()
            } catch (e: Exception) {
                // Then
                assertThat(e).isEqualTo(Expectations.error)
            }
        }
    }

    @Test
    fun `when get network by id with success, should get network properly`() = runBlocking {
        // Given
        val anId = "id"
        coEvery { service.getNetworkById(anId) } returns Preconditions.getNetworkByIdResponse
        // When
        val result = dataSource.getNetworkById(anId)
        // Then
        assertThat(result).isEqualTo(Expectations.network)
    }

    @Test
    fun `when get network by id with error, should thrown exception`() {
        runBlocking {
            // Given
            coEvery { service.findNetworks() } throws Preconditions.error
            try {
                // When
                dataSource.findNetworks()
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
    val findNetworkResponse = FindNetworksResponse(
        networks = listOf(network)
    )
    val getNetworkByIdResponse = GetNetworkByIdResponse(
        network = network
    )
    val error = SomeException("An error")
}

private object Expectations {
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

private data class SomeException(override val message: String) : Exception(message)
