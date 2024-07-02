package com.example.beering.domain.repository

import com.example.beering.data.datasource.BeerDataSource
import com.example.beering.data.network.BeerApiService
import com.example.beering.data.repository.BeerRepositoryImpl
import com.example.beering.domain.usecases.GetBeerByIdUseCase
import com.example.beering.domain.usecases.GetBeerByIdUseCaseImpl
import com.example.beering.domain.usecases.GetBeersUseCase
import com.example.beering.domain.usecases.GetBeersUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideBeerDataSource(beerApiService: BeerApiService): BeerDataSource {
        return BeerDataSource(beerApiService)
    }

    @Provides
    fun provideBeerRepository(beerDataSource: BeerDataSource): BeerRepository {
        return BeerRepositoryImpl(beerDataSource)
    }

    @Provides
    fun provideGetBeersUseCase(beerRepository: BeerRepository): GetBeersUseCase {
        return GetBeersUseCaseImpl(beerRepository)
    }

    @Provides
    fun provideGetBeerByIdUseCase(beerRepository: BeerRepository): GetBeerByIdUseCase {
        return GetBeerByIdUseCaseImpl(beerRepository)
    }
}
