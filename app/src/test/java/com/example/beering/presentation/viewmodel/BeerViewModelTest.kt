package com.example.beering.presentation.viewmodel

import com.example.beering.domain.model.Beer
import com.example.beering.domain.usecases.GetBeerByIdUseCase
import com.example.beering.domain.usecases.GetBeersUseCase
import com.example.beering.dispatcher_rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import org.junit.Assert.assertEquals
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mock dependencies
    private val getBeersUseCase: GetBeersUseCase = mock()
    private val getBeerByIdUseCase: GetBeerByIdUseCase = mock()

    // Subject under test
    private lateinit var beerViewModel: BeerViewModel

    @Before
    fun setUp() {
        beerViewModel = BeerViewModel(getBeersUseCase, getBeerByIdUseCase)
    }

    @Test
    fun `Given getBeersUseCase returns a list of beers, When fetchBeers is called, Then beerListState is updated to Success with the list of beers`() = runTest {
        // Prepare
        `when`(getBeersUseCase.execute()).thenReturn(flowOf(Result.Success(testBeerList)))

        // Execute
        beerViewModel.fetchBeers()

        // Verify
        assertEquals(BeerListState.Success(testBeerList), beerViewModel.beerListState.value)
    }

    @Test
    fun `Given getBeersUseCase returns an error, When fetchBeers is called, Then beerListState is updated to Error with the error message`() = runTest {
        // Prepare
        `when`(getBeersUseCase.execute()).thenReturn(flowOf(Result.Failure(Exception(errorMessage))))

        // Execute
        beerViewModel.fetchBeers()

        // Verify
        assertEquals(BeerListState.Error(errorMessage), beerViewModel.beerListState.value)
    }

    @Test
    fun `Given getBeerByIdUseCase returns a beer, When getBeerById is called with a valid ID, Then beerDetailState is updated to Success with the beer`() = runTest {
        // Prepare
        `when`(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flowOf(Result.Success(testBeer)))

        // Execute
        beerViewModel.getBeerById(testBeerId)

        // Verify
        assertEquals(BeerDetailState.Success(testBeer), beerViewModel.beerDetailState.value)
    }

    @Test
    fun `Given getBeerByIdUseCase returns an error, When getBeerById is called with a valid ID, Then beerDetailState is updated to Error with the error message`() = runTest {
        // Prepare
        `when`(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flowOf(Result.Failure(Exception(errorMessage))))

        // Execute
        beerViewModel.getBeerById(testBeerId)

        // Verify
        assertEquals(BeerDetailState.Error(errorMessage), beerViewModel.beerDetailState.value)
    }

    companion object {
        // Sample data for testing
        private val testBeerList = listOf(
            Beer(1, "Beer 1", "Brewery 1", "Type 1"),
            Beer(2, "Beer 2", "Brewery 2", "Type 2")
        )
        private const val testBeerId = 1
        private val testBeer = Beer(1, "Beer 1", "Brewery 1", "Type 1")
        private const val errorMessage = "Error fetching beers"
    }
}
