package com.example.beering.domain.repository

import com.example.beering.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import com.example.beering.presentation.viewmodel.Result

interface BeerRepository {
    suspend fun getBeers(): Flow<Result<List<Beer>>>
    suspend fun getBeerById(id: String): Flow<Result<Beer>>
}