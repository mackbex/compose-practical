package com.mackbex.rickdex.data.character.remote.mapper

import com.mackbex.rickdex.data.character.remote.dto.CharacterDto
import com.mackbex.rickdex.domain.character.model.Character

fun CharacterDto.toDomain(): Character = Character(
  id = id,
  name = name,
  status = status,
  species = species,
  origin = origin.name,
  imageUrl = image
)

fun List<CharacterDto>.toDomain(): List<Character> = map { it.toDomain() }