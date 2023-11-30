package com.umitytsr.pokebook.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.umitytsr.pokebook.data.model.remote.Result
import com.umitytsr.pokebook.data.service.PokeBookApiService
import com.umitytsr.pokebook.util.Constants

class PokeBookPagingSource(private val pokeBookApiService: PokeBookApiService): PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            val response = pokeBookApiService.getAllPokemon(Constants.NETWORK_PAGE_SIZE,(position-1)*Constants.NETWORK_PAGE_SIZE)
            LoadResult.Page(data = response.results, prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}