package com.umitytsr.pokebook.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.umitytsr.pokebook.data.model.local.PokeBookModel
import com.umitytsr.pokebook.data.model.remote.Pokemon
import com.umitytsr.pokebook.data.service.PokeBookApiService
import com.umitytsr.pokebook.data.source.PokeBookPagingSource
import com.umitytsr.pokebook.domain.toPokeBookModel
import com.umitytsr.pokebook.domain.toPokeBookModelPaging
import com.umitytsr.pokebook.util.Constants
import com.umitytsr.pokebook.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
                entry.toPokeBookModelPaging()
            }
        }
    }

    suspend fun getFetchSeach(): Flow<List<PokeBookModel>> = flow {
        val result = pokeBookApiService.getSearchPokemon().results.toPokeBookModel()
        emit(result)
    }

    suspend fun getPokeInfo(poke : String) : Resource<Pokemon> {
        val response = try {
            pokeBookApiService.getPokemonInfo(poke)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }
}