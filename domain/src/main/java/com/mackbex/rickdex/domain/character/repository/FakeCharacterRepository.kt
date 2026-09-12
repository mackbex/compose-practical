package com.mackbex.rickdex.domain.character.repository

import androidx.paging.PagingData
import com.mackbex.rickdex.domain.character.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCharacterRepository : CharacterRepository {
  var characters = listOf<Character>()

  override fun getCharactersPaged(query: String?): Flow<PagingData<Character>> =
    flowOf(PagingData.from(characters))

  override suspend fun getCharacter(id: Int): Character =
    characters.first { it.id == id }
}