package com.example.beering.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.beering.R
import com.example.beering.presentation.ui.LocalImageSize
import com.example.beering.presentation.ui.screenBackground
import com.example.beering.presentation.ui.spacing
import com.example.beering.presentation.viewmodel.BeerDetailState
import com.example.beering.presentation.viewmodel.BeerViewModel

@Composable
fun BeerDetailScreen(beerId: Int, viewModel: BeerViewModel = hiltViewModel()) {
    LaunchedEffect(beerId) {
        viewModel.getBeerById(beerId)
    }

    val state by viewModel.beerDetailState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.screenBackground.color
    ) {
        when (state) {
            is BeerDetailState.Loading -> CircularProgressIndicator()
            is BeerDetailState.Success -> {
                val beer = (state as BeerDetailState.Success).beer
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                ) {
                    AsyncImage(
                        model = beer.imageUrl,
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_launcher_foreground),
                        modifier = Modifier.size(LocalImageSize.current.large)
                    )
                    Text(
                        text = beer.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                    )
                    Text(
                        text = beer.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                    )
                }
            }
            is BeerDetailState.Error -> Text(text = (state as BeerDetailState.Error).message)
        }
    }
}
