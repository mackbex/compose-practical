package com.mackbex.rickdex.domain.character.usecase

import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
  private val repository: CharacterRepository
) {

  suspend operator fun invoke(
    page: Int = 1,
    name: String? = null
  ): List<Character> = repository.getCharacters(page, name)
}