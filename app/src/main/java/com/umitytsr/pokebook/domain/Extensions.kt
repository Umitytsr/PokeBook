package com.umitytsr.pokebook.domain

import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.data.model.remote.Result

fun List<Result>.toPokeBookModel() : List<PokeBookModel>{
    return this.map {
        PokeBookModel(
            pokemonName = it.name,
            number = extractPokemonNumber(it.url),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${extractPokemonNumber(it.url)}.png"
        )
    }
}

fun Result.toPokeBookModelPaging() : PokeBookModel{
    return PokeBookModel(
        pokemonName = this.name,
        number = extractPokemonNumber(this.url),
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${extractPokemonNumber(this.url)}.png"
    )
}

private fun extractPokemonNumber(url: String): Int {
    return if (url.endsWith("/")) {
        url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
    } else {
        url.takeLastWhile { it.isDigit() }.toInt()
    }
}