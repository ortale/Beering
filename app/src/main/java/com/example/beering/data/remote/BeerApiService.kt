package com.example.beering.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface BeerApiService {
    @GET("/api/v1/beer/")
    suspend fun getAllBeers(): List<BeerDto>

    @GET("/api/v1/beer/{id}")
    suspend fun getBeerById(@Path("id") id: String): BeerDto
}