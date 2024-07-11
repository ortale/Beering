package com.example.beering.domain.usecases

import com.example.beering.domain.model.Beer
import com.example.beering.domain.repository.BeerRepository
import com.example.beering.presentation.viewmodel.Result
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class GetBeersUseCaseImplTest {

    // Mock dependencies
    private val beerRepository: BeerRepository = mock()

    private lateinit var getBeersUseCase: GetBeersUseCase

    @Before
    fun setUp() {
        getBeersUseCase = GetBeersUseCaseImpl(beerRepository)
    }

    // Test cases for GetBeersUseCaseImpl
    @Test
    fun `execute should return Flow of Result containing list of beers`() = runBlocking {
        // Prepare
        val expectedBeers = listOf(testBeer)
        `when`(beerRepository.getBeers()).thenReturn(flowOf(Result.Success(expectedBeers)))

        // Execute
        val resultFlow = getBeersUseCase.execute()

        // Verify
        val result = resultFlow.first() // Blocking call to get the first emitted value
        assertTrue(result is Result.Success)
        assertEquals(expectedBeers, (result as Result.Success).data)
    }

    @Test
    fun `execute should return Flow of Result containing error`() = runBlocking {
        // Prepare
        `when`(beerRepository.getBeers()).thenReturn(flowOf(Result.Failure(Throwable(errorMessage))))

        // Execute
        val resultFlow = getBeersUseCase.execute()

        // Verify
        val result = resultFlow.first() // Blocking call to get the first emitted value
        assertTrue(result is Result.Failure)
        assertEquals(errorMessage, (result as Result.Failure).exception.message)
    }

    companion object {
        private val testBeer = Beer(
            1,
            "Test Beer",
            "Test Brewery",
            "IPA"
        )
        private const val errorMessage = "Error fetching beers"
    }
}