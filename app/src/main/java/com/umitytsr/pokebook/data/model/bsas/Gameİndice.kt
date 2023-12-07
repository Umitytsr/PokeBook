package com.umitytsr.pokebook.data.model.bsas


import com.google.gson.annotations.SerializedName

data class Gameİndice(
    @SerializedName("game_index")
    val gameİndex: Int,
    @SerializedName("version")
    val version: Version
)