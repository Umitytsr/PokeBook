package com.umitytsr.pokebook.data.model.remote


import com.google.gson.annotations.SerializedName

data class GenerationVii(
    @SerializedName("icons")
    val icons: İcons,
    @SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoon
)