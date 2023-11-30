package com.umitytsr.pokebook.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.data.service.PokeBookApiService
import com.umitytsr.pokebook.data.source.PokeBookPagingSource
import com.umitytsr.pokebook.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokeBookApiService: PokeBookApiService) {

    fun getfetchPokemons() : Flow<PagingData<PokeBookModel>> {
        return Pager(
            initialKey = 1,
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = 3
            ),
            pagingSourceFactory = {PokeBookPagingSource(pokeBookApiService)}
        ).flow.map { pagingData ->
            pagingData.map { entry ->
                val number = extractPokemonNumber(entry.url)
                val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                PokeBookModel(number,entry.name.capitalize(Locale.ROOT),url)
            }
        }
    }

    private fun extractPokemonNumber(url: String): Int {
        return if (url.endsWith("/")) {
            url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()
        } else {
            url.takeLastWhile { it.isDigit() }.toInt()
        }
    }
}