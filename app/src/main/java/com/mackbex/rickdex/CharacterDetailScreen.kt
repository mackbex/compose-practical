package com.mackbex.rickdex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mackbex.rickdex.core.ui.components.BodyText
import com.mackbex.rickdex.core.ui.components.LabelText
import com.mackbex.rickdex.core.ui.theme.RickdexTheme
import com.mackbex.rickdex.domain.model.sampleCharacters


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
  characterId: Int,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val character = sampleCharacters.find { it.id == characterId }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text(character?.name ?: "Unknown")
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
    if (character == null) {
      Text("Could not find a character", modifier = Modifier.padding(innerPadding))
      return@Scaffold
    }

    Column(
      modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      DetailRow("Status", character.status)
      DetailRow("Species", character.species)
      DetailRow("Origin", character.origin)
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
    CharacterDetailScreen(characterId = 1, onBack = {})
  }
}