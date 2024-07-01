package com.example.beering.data.usecases

import com.example.beering.data.domain.Beer
import com.example.beering.data.repository.beer.BeerRepository
import com.example.beering.viewmodel.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetBeersUseCase {
    suspend fun execute(): Flow<Result<List<Beer>>>
}

interface GetBeerByIdUseCase {
    suspend fun execute(id: String): Flow<Result<Beer>>
}

class GetBeersUseCaseImpl @Inject constructor(private val beerRepository: BeerRepository) : GetBeersUseCase {
    override suspend fun execute(): Flow<Result<List<Beer>>> {
        return beerRepository.getBeers()
    }
}

class GetBeerByIdUseCaseImpl @Inject constructor(private val beerRepository: BeerRepository) : GetBeerByIdUseCase {
    override suspend fun execute(id: String): Flow<Result<Beer>> {
        return beerRepository.getBeerById(id)
    }
}