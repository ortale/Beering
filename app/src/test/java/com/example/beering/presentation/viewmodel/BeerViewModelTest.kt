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

    // Sample data for testing
    private val testBeerList = listOf(
        Beer(1, "Beer 1", "Brewery 1", "Type 1"),
        Beer(2, "Beer 2", "Brewery 2", "Type 2")
    )
    private val testBeerId = 1
    private val testBeer = Beer(1, "Beer 1", "Brewery 1", "Type 1")

    @Before
    fun setUp() {
        beerViewModel = BeerViewModel(getBeersUseCase, getBeerByIdUseCase)
    }

    @Test
    fun `fetchBeers updates beerListState on success`() = runTest {
        // Prepare
        `when`(getBeersUseCase.execute()).thenReturn(flowOf(Result.Success(testBeerList)))

        // Execute
        beerViewModel.fetchBeers()

        // Verify
        assertEquals(BeerListState.Success(testBeerList), beerViewModel.beerListState.value)
    }

    @Test
    fun `fetchBeers updates beerListState on failure`() = runTest {
        // Prepare
        val errorMessage = "Error fetching beers"
        `when`(getBeersUseCase.execute()).thenReturn(flowOf(Result.Failure(Exception(errorMessage))))

        // Execute
        beerViewModel.fetchBeers()

        // Verify
        assertEquals(BeerListState.Error(errorMessage), beerViewModel.beerListState.value)
    }

    @Test
    fun `getBeerById updates beerDetailState on success`() = runTest {
        // Prepare
        `when`(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flowOf(Result.Success(testBeer)))

        // Execute
        beerViewModel.getBeerById(testBeerId)

        // Verify
        assertEquals(BeerDetailState.Success(testBeer), beerViewModel.beerDetailState.value)
    }

    @Test
    fun `getBeerById updates beerDetailState on failure`() = runTest {
        // Prepare
        val errorMessage = "Error fetching beer"
        `when`(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flowOf(Result.Failure(Exception(errorMessage))))

        // Execute
        beerViewModel.getBeerById(testBeerId)

        // Verify
        assertEquals(BeerDetailState.Error(errorMessage), beerViewModel.beerDetailState.value)
    }
}
