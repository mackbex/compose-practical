package com.mackbex.rickdex.domain.character.repository

import androidx.paging.PagingData
import com.mackbex.rickdex.domain.character.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
  fun getCharactersPaged(query: String?): Flow<PagingData<Character>>
  suspend fun getCharacter(id: Int): Character
}