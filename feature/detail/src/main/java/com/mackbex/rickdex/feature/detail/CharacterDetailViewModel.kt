package com.mackbex.rickdex.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.core.ui.navigation.CharacterDetailNavKey
import com.mackbex.rickdex.domain.character.usecase.GetCharacterUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterDetailViewModel.Factory::class)
class CharacterDetailViewModel @AssistedInject constructor(
  @Assisted private val navKey: CharacterDetailNavKey,
  private val getCharacter: GetCharacterUseCase,
) : ViewModel() {

  @AssistedFactory
  interface Factory {
    fun create(navKey: CharacterDetailNavKey): CharacterDetailViewModel
  }

  private val _uiState = MutableStateFlow(CharacterDetailUiState())
  val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

  init {
    load()
  }

  fun load() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }
      try {
        val character = getCharacter(navKey.characterId)
        _uiState.update { it.copy(isLoading = false, character = character) }
      } catch (e: Exception) {
        _uiState.update {
          it.copy(isLoading = false, errorMessage = e.message ?: "불러오기 실패")
        }
      }
    }
  }
}