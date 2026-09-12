package com.mackbex.rickdex.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.domain.character.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
  private val getCharacter: GetCharacterUseCase,
) : ViewModel() {

  private val _uiState = MutableStateFlow(CharacterDetailUiState())
  val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()


  fun load(id: Int) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }
      try {
        val character = getCharacter(id)
        _uiState.update { it.copy(isLoading = false, character = character) }
      } catch (e: Exception) {
        _uiState.update {
          it.copy(isLoading = false, errorMessage = e.message ?: "불러오기 실패")
        }
      }
    }
  }
}