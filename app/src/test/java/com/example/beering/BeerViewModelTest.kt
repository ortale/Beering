package com.example.beering

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.beering.domain.model.Beer
import com.example.beering.domain.usecases.GetBeerByIdUseCase
import com.example.beering.domain.usecases.GetBeersUseCase
import com.example.beering.presentation.viewmodel.BeerDetailState
import com.example.beering.presentation.viewmodel.BeerListState
import com.example.beering.presentation.viewmodel.BeerViewModel
import com.example.beering.presentation.viewmodel.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class BeerViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

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
        Dispatchers.setMain(testDispatcher)
        beerViewModel = BeerViewModel(getBeersUseCase, getBeerByIdUseCase)
    }

    @Test
    fun `fetchBeers updates beerListState on success`() = testScope.runTest {
        // Prepare
        whenever(getBeersUseCase.execute()).thenReturn(flow {
            emit(Result.Success(testBeerList))
        })

        try {
            // Execute
            beerViewModel.fetchBeers()

            // Verify
            assertEquals(BeerListState.Success(testBeerList), beerViewModel.beerListState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `fetchBeers updates beerListState on failure`() = testScope.runTest {
        // Prepare
        val errorMessage = "Error fetching beers"
        whenever(getBeersUseCase.execute()).thenReturn(flow {
            emit(Result.Failure(Exception(errorMessage)))
        })

        try {
            // Execute
            beerViewModel.fetchBeers()

            // Verify
            assertEquals(BeerListState.Error(errorMessage), beerViewModel.beerListState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `getBeerById updates beerDetailState on success`() = testScope.runTest {
        // Prepare
        whenever(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flow {
            emit(Result.Success(testBeer))
        })

        try {
            // Execute
            beerViewModel.getBeerById(testBeerId)

            // Verify
            assertEquals(BeerDetailState.Success(testBeer), beerViewModel.beerDetailState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `getBeerById updates beerDetailState on failure`() = testScope.runTest {
        // Prepare
        val errorMessage = "Error fetching beer"
        whenever(getBeerByIdUseCase.execute(testBeerId)).thenReturn(flow {
            emit(Result.Failure(Exception(errorMessage)))
        })

        try {
            // Execute
            beerViewModel.getBeerById(testBeerId)

            // Verify
            assertEquals(BeerDetailState.Error(errorMessage), beerViewModel.beerDetailState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
