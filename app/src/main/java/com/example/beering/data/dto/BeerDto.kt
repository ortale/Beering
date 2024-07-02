package com.example.beering.data.dto

import com.example.beering.domain.model.Beer

data class BeerDto(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

fun BeerDto.toDomainModel(): Beer {
    return Beer(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )
}