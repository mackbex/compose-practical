package com.mackbex.rickdex.feature.detail

import com.mackbex.rickdex.domain.character.model.Character

data class CharacterDetailUiState(
  val isLoading: Boolean = false,
  val character: Character? = null,
  val errorMessage: String? = null
)
