package com.example.beering.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.beering.R
import com.example.beering.domain.model.Beer
import com.example.beering.presentation.ui.LocalImageSize
import com.example.beering.presentation.ui.screenBackground
import com.example.beering.presentation.ui.spacing
import com.example.beering.presentation.viewmodel.BeerListState
import com.example.beering.presentation.viewmodel.BeerViewModel

@Composable
fun BeerListScreen(navController: NavController, viewModel: BeerViewModel = hiltViewModel()) {
    val state by viewModel.beerListState.collectAsState()

    viewModel.fetchBeers()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.screenBackground.color
    ) {
        when (state) {
            is BeerListState.Loading -> CircularProgressIndicator()
            is BeerListState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(MaterialTheme.spacing.medium)
                ) {
                    items((state as BeerListState.Success).beers) { beer ->
                        BeerListItem(beer) {
                            navController.navigate("detail/${beer.id}")
                        }
                    }
                }
            }
            is BeerListState.Error -> Text(text = (state as BeerListState.Error).message)
        }
    }
}

@Composable
fun BeerListItem(beer: Beer, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = MaterialTheme.spacing.small, horizontal = MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = beer.imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.size(LocalImageSize.current.small)
        )
        Column(
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium)
        ) {
            Text(text = beer.name)
        }
    }
}
