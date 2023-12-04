package com.umitytsr.pokebook.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.data.repo.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private val _getAllPoke = MutableStateFlow<PagingData<PokeBookModel>>(PagingData.empty())
    val getAllPoke = _getAllPoke.asStateFlow()

    private val _searchPokeList = MutableStateFlow<List<PokeBookModel>>(emptyList())
    val searchPokeList = _searchPokeList.asStateFlow()

    init {
        allPoke()
        searchPoke(query = "")
    }

    private fun allPoke(){
        viewModelScope.launch {
            pokemonRepository.getfetchPokemons().collectLatest {
                _getAllPoke.emit(it)
            }
        }
    }

    fun searchPoke(query: String) {
        viewModelScope.launch {
            pokemonRepository.getFetchSeach().collectLatest { list ->
                if (query != "") {
                    val results = list.filter {
                        it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                                it.number.toString() == query.trim()
                    }
                    _searchPokeList.emit(results)
                }else{
                    _searchPokeList.emit(list)
                }
            }
        }
    }
}