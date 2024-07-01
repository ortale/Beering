package com.example.beering.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beering.data.domain.Beer
import com.example.beering.data.usecases.GetBeerByIdUseCase
import com.example.beering.data.usecases.GetBeersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val getBeersUseCase: GetBeersUseCase,
    private val getBeerByIdUseCase: GetBeerByIdUseCase
) : ViewModel() {

    private val _beerListState = mutableStateOf<BeerListState>(BeerListState.Loading)
    val beerListState: State<BeerListState> = _beerListState

    private val _beerDetailState = mutableStateOf<BeerDetailState>(BeerDetailState.Loading)
    val beerDetailState: State<BeerDetailState> = _beerDetailState

    init {
        fetchBeers()
    }

    private fun fetchBeers() {
        viewModelScope.launch {
            getBeersUseCase.execute().collect { result ->
                _beerListState.value = when (result) {
                    is Result.Success -> BeerListState.Success(result.data)
                    is Result.Failure -> BeerListState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }
    }

    fun getBeerById(id: String) {
        viewModelScope.launch {
            getBeerByIdUseCase.execute(id).collect { result ->
                _beerDetailState.value = when (result) {
                    is Result.Success -> BeerDetailState.Success(result.data)
                    is Result.Failure -> BeerDetailState.Error(result.exception.message ?: "Unknown error")
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
    object Loading : BeerListState()
    data class Success(val beers: List<Beer>) : BeerListState()
    data class Error(val message: String) : BeerListState()
}

sealed class BeerDetailState {
    object Loading : BeerDetailState()
    data class Success(val beer: Beer) : BeerDetailState()
    data class Error(val message: String) : BeerDetailState()
}