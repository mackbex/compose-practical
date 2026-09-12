package com.mackbex.rickdex.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.domain.character.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val getCharacters: GetCharactersUseCase
) : ViewModel() {

  private val _uiState = MutableStateFlow(CharacterListUiState())
  val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

  init {
    observeQuery()
  }

  private fun observeQuery() {
    viewModelScope.launch {
      _uiState.map { it.query }
        .distinctUntilChanged()
        .debounce(400.milliseconds)
        .collectLatest { query -> load(query) }
    }
  }

  fun load(query: String) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }

      try {
        val characters = getCharacters(name = query.ifBlank { null })
        _uiState.update {
          it.copy(isLoading = false, characters = characters)
        }
      } catch (e: CancellationException) {
        throw e
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

  fun onQueryChange(query: String) {
    _uiState.update { it.copy(query = query) }
    search(query)
  }

  private fun search(query: String) {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true, errorMessage = null) }

      try {
        val characters = getCharacters(name = query.ifBlank { null })
        _uiState.update { it.copy(isLoading = false, characters = characters) }
      } catch (e: Exception) {
        _uiState.update { it.copy(isLoading = false, errorMessage = "failed find") }
      }
    }
  }

  fun retry() {
    viewModelScope.launch { load(_uiState.value.query) }
  }
}