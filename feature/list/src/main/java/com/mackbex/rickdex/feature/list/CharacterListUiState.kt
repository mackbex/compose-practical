package com.mackbex.rickdex.feature.list

import com.mackbex.rickdex.domain.character.model.Character

data class CharacterListUiState(
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val characters: List<Character> = emptyList(),
  val errorMessage: String? = null
)