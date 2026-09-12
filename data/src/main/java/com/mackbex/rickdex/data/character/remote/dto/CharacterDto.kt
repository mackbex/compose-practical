package com.mackbex.rickdex.data.character.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseDto(
  val info: PageInfoDto,
  val results: List<CharacterDto>
)

@Serializable
data class PageInfoDto(
  val count: Int,
  val pages: Int,
  val next: String? = null,
  val prev: String? = null
)

@Serializable
data class CharacterDto(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val type: String = "",
  val gender: String,
  val origin: LocationRefDto,
  val location: LocationRefDto,
  val image: String,
  val episode: List<String> = emptyList(),
  val url: String = "",
  val created: String = ""
)

@Serializable
data class LocationRefDto(
  val name: String,
  val url: String = ""
)