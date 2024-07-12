package com.example.beering.data.repository

import com.example.beering.data.datasource.BeerDataSource
import com.example.beering.data.dto.BeerDto
import com.example.beering.data.dto.toDomainModel
import com.example.beering.dispatcher_rules.MainDispatcherRule
import com.example.beering.domain.repository.BeerRepository
import com.example.beering.presentation.viewmodel.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BeerRepositoryImplTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val beerDataSource: BeerDataSource = mockk()
    private lateinit var beerRepository: BeerRepository

    @Before
    fun setUp() {
        beerRepository = BeerRepositoryImpl(beerDataSource)
    }

    @Test
    fun `Given beer data source returns a list of beers, When getBeers is called, Then it returns a success result with the list of beers`() = runTest {
        // Given
        coEvery { beerDataSource.getAllBeers() } returns testBeerDtoList

        // When
        val result = beerRepository.getBeers().first()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(testBeerList, (result as Result.Success).data)
    }

    @Test
    fun `Given beer data source throws an exception, When getBeers is called, Then it returns a failure result with the error message`() = runTest {
        // Prepare
        coEvery { beerDataSource.getAllBeers() } throws Exception(errorMessage)

        // Execute
        val result = beerRepository.getBeers().first()

        // Verify
        assertTrue(result is Result.Failure)
        assertEquals(errorMessage, (result as Result.Failure).exception.message)
    }

    @Test
    fun `Given beer data source returns a beer, When getBeerById is called with a valid ID, Then it returns a success result with the beer`() = runTest {
        // Prepare
        coEvery { beerDataSource.getBeerById(testBeerId) } returns testBeerDto

        // Execute
        val result = beerRepository.getBeerById(testBeerId).first()

        // Verify
        assertTrue(result is Result.Success)
        assertEquals(testBeer, (result as Result.Success).data)
    }

    @Test
    fun `Given beer data source throws an exception, When getBeerById is called with a valid ID, Then it returns a failure result with the error message`() = runTest {
        // Prepare
        coEvery { beerDataSource.getBeerById(testBeerId) } throws Exception(errorMessage)

        // Execute
        val result = beerRepository.getBeerById(testBeerId).first()

        // Verify
        assertTrue(result is Result.Failure)
        assertEquals(errorMessage, (result as Result.Failure).exception.message)
    }

    companion object {
        private val testBeerDtoList = listOf(
            BeerDto(1, "Beer 1", "Brewery 1", "Type 1"),
            BeerDto(2, "Beer 2", "Brewery 2", "Type 2")
        )
        private val testBeerDto = BeerDto(1, "Beer 1", "Brewery 1", "Type 1")

        private val testBeerList = testBeerDtoList.map { it.toDomainModel() }
        private val testBeer = testBeerDto.toDomainModel()
        private const val testBeerId = 1
        private const val errorMessage = "Error fetching beer"
    }
}
