package com.example.beering.data.repository.beer

import com.example.beering.data.datasource.BeerDataSource
import com.example.beering.data.domain.Beer
import com.example.beering.data.dto.toDomainModel
import com.example.beering.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(private val beerDataSource: BeerDataSource) : BeerRepository {
    override suspend fun getBeers(): Flow<Result<List<Beer>>> {
        return try {
            val beers = beerDataSource.getAllBeers().map { it.toDomainModel() }
            flow {
                emit(Result.Success(beers))
            }
        } catch (e: Exception) {
            flow {
                emit(Result.Failure(e))
            }
        }
    }

    override suspend fun getBeerById(id: String): Flow<Result<Beer>> {
        return try {
            val beer = beerDataSource.getBeerById(id).toDomainModel()
            flow {
                emit(Result.Success(beer))
            }
        } catch (e: Exception) {
            flow {
                emit(Result.Failure(e))
            }
        }
    }
}