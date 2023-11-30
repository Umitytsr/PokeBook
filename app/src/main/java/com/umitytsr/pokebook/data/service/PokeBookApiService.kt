package com.umitytsr.pokebook.data.service

import com.umitytsr.pokebook.data.model.remote.Pokemon
import com.umitytsr.pokebook.data.model.remote.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeBookApiService {
    @GET("pokemon")
    suspend fun getAllPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonInfo(@Path("pokemonName") pokemonName: String): Pokemon
}