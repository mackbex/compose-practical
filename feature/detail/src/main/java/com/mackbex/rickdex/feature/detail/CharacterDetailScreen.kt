package com.mackbex.rickdex.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mackbex.rickdex.core.ui.components.BodyText
import com.mackbex.rickdex.core.ui.components.LabelText
import com.mackbex.rickdex.core.ui.theme.RickdexTheme


@Composable
fun CharacterDetailRoute(
  characterId: Int,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: CharacterDetailViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  LaunchedEffect(characterId) {
    viewModel.load(characterId)
  }

  CharacterDetailScreen(
    uiState = uiState,
    onBack = onBack,
    onRetry = { viewModel.load(characterId) },
    modifier = modifier
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
  uiState: CharacterDetailUiState,
  onBack: () -> Unit,
  onRetry: () -> Unit,
  modifier: Modifier = Modifier
) {

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text(uiState.character?.name ?: "Unknown")
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        }
      )
    }
  ) { innerPadding ->

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
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
            Button(onClick = onRetry) { Text("Retry") }
          }
        }

        uiState.character != null -> {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            DetailRow("상태", uiState.character.status)
            DetailRow("종족", uiState.character.species)
            DetailRow("출신", uiState.character.origin)
          }
        }
      }
    }
  }
}

@Composable
private fun DetailRow(label: String, value: String) {
  Column {
    LabelText(
      text = label,
    )
    BodyText(
      text = value,
    )
  }
}


@Preview(showBackground = true)
@Composable
private fun CharacterDetailPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterDetailScreen(CharacterDetailUiState(isLoading = true), onBack = {}, onRetry = {})
  }
}