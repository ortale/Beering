package com.example.beering.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.115:5002")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideBeerApiService(retrofit: Retrofit): BeerApiService {
        return retrofit.create(BeerApiService::class.java)
    }
}