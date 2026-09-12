package com.mackbex.rickdex.feature.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mackbex.rickdex.core.ui.components.BodyText
import com.mackbex.rickdex.core.ui.theme.RickdexTheme
import com.mackbex.rickdex.domain.character.model.sampleCharacters


@Composable
fun CharacterListRoute(
  onCharacterClick: (Int) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: CharacterListViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  CharacterListScreen(
    uiState = uiState,
    onCharacterClick = onCharacterClick,
    onRetry = viewModel::retry,
    onRefresh = viewModel::refresh,
    onQueryChange = viewModel::onQueryChange,
    modifier = modifier
  )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
  uiState: CharacterListUiState,
  onCharacterClick: (Int) -> Unit,
  onRetry: () -> Unit,
  onRefresh: () -> Unit,
  onQueryChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(title = { Text("Characters") })
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      OutlinedTextField(
        value = uiState.query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      )

      Box(modifier = Modifier.fillMaxSize()) {
        when {
          uiState.isLoading -> {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
          }

          uiState.errorMessage != null -> {
            Column(
              modifier = Modifier.align(Alignment.Center),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              BodyText(uiState.errorMessage)
              Button(onClick = onRetry) { Text("다시 시도") }
            }
          }

          uiState.characters.isEmpty() -> {
            BodyText(
              text = "검색 결과가 없습니다",
              modifier = Modifier.align(Alignment.Center)
            )
          }

          else -> {
            LazyColumn(
              modifier = Modifier.fillMaxSize(),
              contentPadding = PaddingValues(16.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              items(uiState.characters, key = { it.id }) { character ->
                CharacterCard(
                  character = character,
                  onClick = { onCharacterClick(character.id) }
                )
              }
            }
          }
        }
      }
    }
  }
}


@Preview(showBackground = true)
@Composable
private fun CharacterListPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterListScreen(
      uiState = CharacterListUiState(characters = sampleCharacters),
      onCharacterClick = {},
      onRetry = {},
      onRefresh = {},
      onQueryChange = {}
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun CharacterListLoadingPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterListScreen(
      uiState = CharacterListUiState(isLoading = true),
      onCharacterClick = {},
      onRetry = {},
      onRefresh = {},
      onQueryChange = {}
    )
  }
}