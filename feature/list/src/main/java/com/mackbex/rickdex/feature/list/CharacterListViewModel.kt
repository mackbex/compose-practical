package com.mackbex.rickdex.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel
class CharacterListViewModel @Inject constructor(
  private val getCharacters: GetCharactersUseCase
) : ViewModel() {

  private val _query = MutableStateFlow("")
  val query: StateFlow<String> = _query.asStateFlow()

  @OptIn(ExperimentalCoroutinesApi::class)
  val characters: Flow<PagingData<Character>> = _query
    .debounce(400.milliseconds)
    .distinctUntilChanged()
    .flatMapLatest { query -> getCharacters(query.ifBlank { null }) }
    .cachedIn(viewModelScope)


  fun onQueryChange(query: String) {
    _query.value = query
  }
}