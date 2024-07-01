package com.example.beering.data.repository.beer

import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(): Flow<Result<List<Beer>>>
    suspend fun getBeerById(id: String): Flow<Result<Beer>>
}