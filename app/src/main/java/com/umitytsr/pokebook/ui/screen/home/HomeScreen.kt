package com.umitytsr.pokebook.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.util.itemsPaging

@OptIn(ExperimentalGlideComposeApi::class)
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    LaunchedEffect(key1 = null) {

    }
    val pokemonList = viewModel.getAllPokemon.collectAsLazyPagingItems()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(3)){
           itemsPaging(items = pokemonList){pokemonResult : PokeBookModel? ->
               Text(text = "${pokemonResult?.pokemonName}", modifier = Modifier.clickable {

               })
               GlideImage(
                   model = pokemonResult?.imageUrl,
                   contentDescription = "",
                   modifier = Modifier.size(96.dp)
                   )
           }
        }
    }
}

