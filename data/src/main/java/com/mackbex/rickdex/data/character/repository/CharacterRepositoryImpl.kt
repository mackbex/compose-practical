package com.mackbex.rickdex.data.character.repository

import com.mackbex.rickdex.data.character.remote.CharacterApi
import com.mackbex.rickdex.data.character.remote.mapper.toDomain
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
  private val api: CharacterApi
) : CharacterRepository {

  override suspend fun getCharacter(id: Int): Character = api.getCharacter(
    id = id
  ).toDomain()

  override suspend fun getCharacters(page: Int, name: String?): List<Character> =
    api.getCharacters(page = page, name = name).results.toDomain()
}