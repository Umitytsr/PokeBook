package com.umitytsr.pokebook.ui.screen.detailer

import androidx.lifecycle.ViewModel
import com.umitytsr.pokebook.data.model.remote.Pokemon
import com.umitytsr.pokebook.data.repo.PokemonRepository
import com.umitytsr.pokebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailerScreenViewModel @Inject constructor(private val pokemonRepository : PokemonRepository) : ViewModel() {

    suspend fun getPokeInfo(name : String) : Resource<Pokemon> = pokemonRepository.getPokeInfo(name)

}