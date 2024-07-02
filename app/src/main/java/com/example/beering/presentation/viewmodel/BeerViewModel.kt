package com.example.beering.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beering.domain.model.Beer
import com.example.beering.domain.usecases.GetBeerByIdUseCase
import com.example.beering.domain.usecases.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val getBeerByIdUseCase: GetBeerByIdUseCase
) : ViewModel() {

    private val _beerListState = MutableStateFlow<BeerListState>(BeerListState.Loading)
    val beerListState: StateFlow<BeerListState> = _beerListState.asStateFlow()

    private val _beerDetailState = MutableStateFlow<BeerDetailState>(BeerDetailState.Loading)
    val beerDetailState: StateFlow<BeerDetailState> = _beerDetailState.asStateFlow()

    init {
        fetchBeers()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun fetchBeers() {
        viewModelScope.launch {
            getBeersUseCase.execute().collect { result ->
                _beerListState.value = when (result) {
                    is Result.Success -> BeerListState.Success(result.data)
                    is Result.Failure -> BeerListState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun getBeerById(id: Int) {
        viewModelScope.launch {
            getBeerByIdUseCase.execute(id).collect { result ->
                _beerDetailState.value = when (result) {
                    is Result.Success -> BeerDetailState.Success(result.data)
                    is Result.Failure -> BeerDetailState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}

sealed class BeerListState {
    data object Loading : BeerListState()
    data class Success(val beers: List<Beer>) : BeerListState()
    data class Error(val message: String) : BeerListState()
}

sealed class BeerDetailState {
    data object Loading : BeerDetailState()
    data class Success(val beer: Beer) : BeerDetailState()
    data class Error(val message: String) : BeerDetailState()
}
