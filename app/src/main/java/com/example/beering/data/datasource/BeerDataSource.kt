package com.example.beering.data.datasource

import com.example.beering.data.dto.BeerDto
import com.example.beering.data.network.BeerApiService

class BeerDataSource(private val beerApiService: BeerApiService) {
    suspend fun getAllBeers(): List<BeerDto> = beerApiService.getAllBeers()
    suspend fun getBeerById(id: String): BeerDto = beerApiService.getBeerById(id)
}