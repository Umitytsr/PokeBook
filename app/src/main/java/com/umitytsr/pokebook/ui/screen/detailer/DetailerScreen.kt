package com.umitytsr.pokebook.ui.screen.detailer

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.umitytsr.pokebook.R
import com.umitytsr.pokebook.data.model.remote.Pokemon
import com.umitytsr.pokebook.util.Resource
import com.umitytsr.pokebook.util.parseStatToAbbr
import com.umitytsr.pokebook.util.parseStatToColor
import com.umitytsr.pokebook.util.parseTypeToColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailerScreen(
    pokeName: String,
    navController: NavController
) {
    val viewModel: DetailerScreenViewModel = hiltViewModel()
    val pokeInfo = remember { mutableStateOf<Resource<Pokemon>>(Resource.Loading()) }
    val detailerColor = remember {
        mutableStateOf(Color.Transparent)
    }
    LaunchedEffect(true) {
        pokeInfo.value = viewModel.getPokeInfo(pokeName)
        pokeInfo.value.data?.types?.let { types ->
            detailerColor.value = parseTypeToColor(types[0])
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = detailerColor.value
    ) {
        Box(
            modifier = Modifier.padding(4.dp)
        ) {
            LargePokeball()
            PokeWrapper(
                pokeInfo = pokeInfo.value,
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
            PokeImage(pokeInfo = pokeInfo.value, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun LargePokeball() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, end = 5.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(id = R.drawable.pokeball_large),
            contentDescription = "",
            modifier = Modifier.size(206.dp, 208.dp),
            tint = Color(0x6FFFFFFF)
        )
    }
}

@Composable
fun PokeWrapper(
    pokeInfo: Resource<Pokemon>,
    navController: NavController,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        PokeTopBar(
            pokeInfo = pokeInfo,
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 24.dp),
            navController = navController
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .weight(11f)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 56.dp, end = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PokeType(pokeInfo = pokeInfo)
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "About", color = Color(0xFFB8B8B8), fontSize = 20.sp)
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PokeAbout(
                        pokeInfo = pokeInfo.data?.weight,
                        id = R.drawable.weight,
                        unit = "kg",
                        unit2 = "Weight"
                    )
                    Spacer(
                        modifier = Modifier
                            .size(width = 2.dp, height = 50.dp)
                            .background(color = Color(0xFFE0E0E0))
                    )
                    PokeAbout(
                        pokeInfo = pokeInfo.data?.height,
                        id = R.drawable.height,
                        unit = "M",
                        unit2 = "Height"
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Base State", color = Color(0xFFB8B8B8), fontSize = 20.sp)
                Spacer(modifier = Modifier.size(16.dp))
                pokeInfo.data?.let {
                    PokemonBaseStats(pokemonInfo = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokeImage(
    pokeInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        pokeInfo.data?.sprites?.let {
            GlideImage(
                model = it.frontDefault,
                contentDescription = "",
                modifier = Modifier
                    .size(250.dp)
                    .offset(y = 60.dp)
            )
        }
    }
}

@Composable
fun PokeTopBar(
    pokeInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                Color.White
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "${pokeInfo.data?.name?.capitalize(Locale.current)}",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
        Text(
            text = "#${pokeInfo.data?.id}",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun PokeType(pokeInfo: Resource<Pokemon>){
    pokeInfo.data?.types?.let { types ->
        LazyRow {
            items(types.size) { index ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = parseTypeToColor(types[index])
                    )
                ) {
                    Text(
                        text = "${types[index].type.name.capitalize(Locale.current)}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PokeAbout(pokeInfo: Int?, id: Int, unit: String, unit2: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "${pokeInfo} ${unit}")
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "$unit2",
            color = Color(0xFF666666),
            fontSize = 14.sp
        )
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    val animationPlayed = remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed.value) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        ), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed.value = true
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = statName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(2f),
        )
        Text(
            text = (curPercent.value * statMaxValue).toInt().toString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        LinearProgressIndicator(
            progress = curPercent.value,
            color = statColor,
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .clip(CircleShape)
                .weight(8f)
        )
    }
}