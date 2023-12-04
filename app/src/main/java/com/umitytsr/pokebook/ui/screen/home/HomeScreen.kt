package com.umitytsr.pokebook.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.umitytsr.pokebook.R
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.util.itemsPaging

@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val pokemonList = viewModel.getAllPoke.collectAsLazyPagingItems()
    val pokemonSearchList = viewModel.searchPokeList.collectAsState()
    val search = remember {
        mutableStateOf("")
    }
    val startingSearch = remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFDC0A2D)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                if (!startingSearch.value){
                    notPokeSearch(startingSearch = startingSearch)
                }else{
                    LaunchedEffect(Unit){
                        focusRequester.requestFocus()
                    }
                    pokeSearch(search = search, startingSearch = startingSearch,focusRequester=focusRequester)
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    if (startingSearch.value){
                        viewModel.searchPoke(search.value)
                        items(pokemonSearchList.value){
                            PokeCard(
                                pokemonResult = it,
                                navController = navController
                            )
                        }
                    }else{
                        itemsPaging(items = pokemonList) { pokemonResult: PokeBookModel? ->
                            PokeCard(pokemonResult = pokemonResult, navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun notPokeSearch(startingSearch : MutableState<Boolean>){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokeball_icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = Color.White)
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "PokeBook",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Icon(
        painter = painterResource(
            id = R.drawable.search
        ),
        contentDescription = "",
        modifier = Modifier
            .size(24.dp)
            .clickable {
                startingSearch.value = true
            },
        Color.White,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pokeSearch(search : MutableState<String>,startingSearch : MutableState<Boolean>, focusRequester: FocusRequester) {
    OutlinedTextField(
        value = search.value,
        onValueChange = {
            search.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.White,
            textColor = Color.Black,
            cursorColor = Color(0xFFDC0A2D),
            focusedBorderColor = Color(0xFFDC0A2D)
        ),
        shape = RoundedCornerShape(24.dp),
        maxLines = 1,
        placeholder = { Text(text = "Search")},
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        startingSearch.value = false
                    },
                Color(0xFFDC0A2D)
            )
        },
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PokeCard(pokemonResult: PokeBookModel?, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 12.dp, bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),


        ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("detailer_screen/${pokemonResult?.pokemonName}")
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Text(
                    text = "#${pokemonResult?.number}",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )
                GlideImage(
                    model = pokemonResult?.imageUrl,
                    contentDescription = "",
                    modifier = Modifier.size(96.dp)
                )
                Text(
                    text = "${pokemonResult?.pokemonName?.capitalize()}",
                    maxLines = 1,
                )
            }
        }
    }
}