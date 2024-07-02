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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.beering.R
import com.example.beering.domain.model.Beer
import com.example.beering.presentation.viewmodel.BeerListState
import com.example.beering.presentation.viewmodel.BeerViewModel

@Composable
fun BeerListScreen(navController: NavController, viewModel: BeerViewModel = hiltViewModel()) {
    val state by viewModel.beerListState

    Surface(
        color = Color.LightGray,
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is BeerListState.Loading -> CircularProgressIndicator()
            is BeerListState.Success -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
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
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = beer.imageUrl,
            contentDescription = null,
            error = painterResource(R.drawable.ic_launcher_foreground),
            modifier = Modifier.size(64.dp)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = beer.name)
        }
    }
}
