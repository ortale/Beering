package com.example.beering.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.beering.presentation.screens.BeerDetailScreen
import com.example.beering.presentation.screens.BeerListScreen
import com.example.beering.presentation.ui.theme.BeeringTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeeringTheme {
                NavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavGraph(startDestination: String = "list") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("list") {
            BeerListScreen(navController = navController)
        }
        composable("detail/{beerId}") { backStackEntry ->
            val beerId = backStackEntry.arguments?.getString("beerId")?.toInt()
            beerId?.let { BeerDetailScreen(beerId = it) }
        }
    }
}