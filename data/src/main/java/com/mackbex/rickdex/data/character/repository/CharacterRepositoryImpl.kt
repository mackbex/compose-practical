package com.mackbex.rickdex.data.character.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mackbex.rickdex.data.character.remote.CharacterApi
import com.mackbex.rickdex.data.character.remote.CharacterPagingSource
import com.mackbex.rickdex.data.character.remote.mapper.toDomain
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
  private val api: CharacterApi
) : CharacterRepository {

  override suspend fun getCharacter(id: Int): Character = api.getCharacter(
    id = id
  ).toDomain()

  override fun getCharactersPaged(query: String?): Flow<PagingData<Character>> = Pager(
    config = PagingConfig(
      pageSize = 20,
      enablePlaceholders = false
    ),
    pagingSourceFactory = { CharacterPagingSource(api, query) }
  ).flow
}