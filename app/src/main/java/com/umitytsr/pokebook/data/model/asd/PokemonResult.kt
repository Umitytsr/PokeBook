package com.umitytsr.pokebook.data.model.asd


import com.google.gson.annotations.SerializedName

data class PokemonResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<Result>
)