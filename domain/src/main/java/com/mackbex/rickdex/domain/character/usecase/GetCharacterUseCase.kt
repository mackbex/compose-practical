package com.mackbex.rickdex.domain.character.usecase

import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
  private val repository: CharacterRepository
) {
  suspend operator fun invoke(id: Int): Character = repository.getCharacter(id)
}