package com.umitytsr.pokebook.util

import androidx.compose.ui.graphics.Color
import com.umitytsr.pokebook.data.model.remote.Stat
import com.umitytsr.pokebook.data.model.remote.Type
import com.umitytsr.pokebook.ui.theme.AtkColor
import com.umitytsr.pokebook.ui.theme.DefColor
import com.umitytsr.pokebook.ui.theme.HPColor
import com.umitytsr.pokebook.ui.theme.SpAtkColor
import com.umitytsr.pokebook.ui.theme.SpDefColor
import com.umitytsr.pokebook.ui.theme.SpdColor
import com.umitytsr.pokebook.ui.theme.TypeBug
import com.umitytsr.pokebook.ui.theme.TypeDark
import com.umitytsr.pokebook.ui.theme.TypeDragon
import com.umitytsr.pokebook.ui.theme.TypeElectric
import com.umitytsr.pokebook.ui.theme.TypeFairy
import com.umitytsr.pokebook.ui.theme.TypeFighting
import com.umitytsr.pokebook.ui.theme.TypeFire
import com.umitytsr.pokebook.ui.theme.TypeFlying
import com.umitytsr.pokebook.ui.theme.TypeGhost
import com.umitytsr.pokebook.ui.theme.TypeGrass
import com.umitytsr.pokebook.ui.theme.TypeGround
import com.umitytsr.pokebook.ui.theme.TypeIce
import com.umitytsr.pokebook.ui.theme.TypeNormal
import com.umitytsr.pokebook.ui.theme.TypePoison
import com.umitytsr.pokebook.ui.theme.TypePsychic
import com.umitytsr.pokebook.ui.theme.TypeRock
import com.umitytsr.pokebook.ui.theme.TypeSteel
import com.umitytsr.pokebook.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when(type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}