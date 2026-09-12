package com.mackbex.rickdex.domain.character.usecase

import androidx.paging.PagingData
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
  private val repository: CharacterRepository
) {

  operator fun invoke(
    query: String?
  ): Flow<PagingData<Character>> = repository.getCharactersPaged(query)
}