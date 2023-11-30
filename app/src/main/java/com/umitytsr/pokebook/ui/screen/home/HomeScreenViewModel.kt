package com.umitytsr.pokebook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.data.repo.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val pokemonRepository: PokemonRepository): ViewModel(){

    val getAllPokemon: Flow<PagingData<PokeBookModel>> =
        pokemonRepository.getfetchPokemons().cachedIn(viewModelScope)
}