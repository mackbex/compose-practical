package com.mackbex.rickdex.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.domain.character.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val getCharacters: GetCharactersUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow(CharacterListUiState())
  val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

  init {
    loadCharacters()
  }

  fun loadCharacters() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }

      try {
        val characters = getCharacters(page = 1)
        _uiState.update {
          it.copy(isLoading = false, characters = characters)
        }
      } catch (e: Exception) {
        _uiState.update {
          it.copy(isLoading = false, errorMessage = "failed load")
        }
      }
    }
  }

  fun refresh() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }

      try {
        val characters = getCharacters(page = 1)
        _uiState.update {
          it.copy(isLoading = false, characters = characters)
        }
      } catch (e: Exception) {
        _uiState.update {
          it.copy(isLoading = false, errorMessage = "failed load")
        }
      }
    }
  }
}