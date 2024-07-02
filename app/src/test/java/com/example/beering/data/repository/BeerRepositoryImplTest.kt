package com.example.beering.data.repository

import com.example.beering.data.datasource.BeerDataSource
import com.example.beering.data.dto.BeerDto
import com.example.beering.data.dto.toDomainModel
import com.example.beering.domain.repository.BeerRepository
import com.example.beering.presentation.viewmodel.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class BeerRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val beerDataSource: BeerDataSource = mockk()
    private lateinit var beerRepository: BeerRepository

    private val testBeerDtoList = listOf(
        BeerDto(1, "Beer 1", "Brewery 1", "Type 1"),
        BeerDto(2, "Beer 2", "Brewery 2", "Type 2")
    )
    private val testBeerDto = BeerDto(1, "Beer 1", "Brewery 1", "Type 1")

    private val testBeerList = testBeerDtoList.map { it.toDomainModel() }
    private val testBeer = testBeerDto.toDomainModel()
    private val testBeerId = 1

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        beerRepository = BeerRepositoryImpl(beerDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getBeers returns success result`() = testScope.runTest {
        // Prepare
        coEvery { beerDataSource.getAllBeers() } returns testBeerDtoList

        // Execute
        val result = beerRepository.getBeers().first()

        // Verify
        assertTrue(result is Result.Success)
        assertEquals(testBeerList, (result as Result.Success).data)
    }

    @Test
    fun `getBeers returns failure result`() = testScope.runTest {
        // Prepare
        val errorMessage = "Error fetching beers"
        coEvery { beerDataSource.getAllBeers() } throws Exception(errorMessage)

        // Execute
        val result = beerRepository.getBeers().first()

        // Verify
        assertTrue(result is Result.Failure)
        assertEquals(errorMessage, (result as Result.Failure).exception.message)
    }

    @Test
    fun `getBeerById returns success result`() = testScope.runTest {
        // Prepare
        coEvery { beerDataSource.getBeerById(testBeerId) } returns testBeerDto

        // Execute
        val result = beerRepository.getBeerById(testBeerId).first()

        // Verify
        assertTrue(result is Result.Success)
        assertEquals(testBeer, (result as Result.Success).data)
    }

    @Test
    fun `getBeerById returns failure result`() = testScope.runTest {
        // Prepare
        val errorMessage = "Error fetching beer"
        coEvery { beerDataSource.getBeerById(testBeerId) } throws Exception(errorMessage)

        // Execute
        val result = beerRepository.getBeerById(testBeerId).first()

        // Verify
        assertTrue(result is Result.Failure)
        assertEquals(errorMessage, (result as Result.Failure).exception.message)
    }
}
