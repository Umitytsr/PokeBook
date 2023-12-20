package com.umitytsr.pokebook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.umitytsr.pokebook.ui.screen.detailer.DetailerScreen
import com.umitytsr.pokebook.ui.screen.home.HomeScreen
import com.umitytsr.pokebook.ui.theme.PokeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeBookTheme {
                NavGraph()
            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navController)
        }
        composable(
            "detailer_screen/{pokemonName}",
            arguments = listOf(navArgument("pokemonName"){type = NavType.StringType})
        ) {
            val pokeName = it.arguments?.getString("pokemonName")
            DetailerScreen(pokeName = pokeName.toString().toLowerCase(), navController = navController)
        }
    }
}