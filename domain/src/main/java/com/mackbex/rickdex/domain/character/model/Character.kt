package com.mackbex.rickdex.domain.character.model

data class Character(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val origin: String,
  val imageUrl: String
)
