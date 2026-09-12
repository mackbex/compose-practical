package com.mackbex.rickdex.data.character.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mackbex.rickdex.data.character.remote.mapper.toDomain
import com.mackbex.rickdex.domain.character.model.Character
import okio.IOException
import retrofit2.HttpException

class CharacterPagingSource(
  private val api: CharacterApi,
  private val query: String?
) : PagingSource<Int, Character>() {

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
    val page = params.key ?: 1
    return try {
      val response = api.getCharacters(page = page, name = query)
      LoadResult.Page(
        data = response.results.toDomain(),
        prevKey = if (page == 1) null else page - 1,
        nextKey = if (response.info.next == null) null else page + 1
      )
    } catch (e: HttpException) {
      if (e.code() == 404) {
        LoadResult.Page(data = emptyList(), prevKey = null, nextKey = null)
      } else {
        LoadResult.Error(e)
      }
    } catch (e: IOException) {
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
    return state.anchorPosition?.let { anchor ->
      state.closestPageToPosition(anchor)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
    }
  }
}