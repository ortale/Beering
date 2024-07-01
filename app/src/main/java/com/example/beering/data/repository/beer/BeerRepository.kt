package com.example.beering.data.repository.beer

import com.example.beering.data.domain.Beer
import kotlinx.coroutines.flow.Flow
import com.example.beering.viewmodel.Result

interface BeerRepository {
    suspend fun getBeers(): Flow<Result<List<Beer>>>
    suspend fun getBeerById(id: String): Flow<Result<Beer>>
}