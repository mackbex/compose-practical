package com.mackbex.rickdex.domain.character.repository

import com.mackbex.rickdex.domain.character.model.Character

interface CharacterRepository {
  suspend fun getCharacters(page: Int = 1, name: String? = null): List<Character>
  suspend fun getCharacter(id: Int): Character
}