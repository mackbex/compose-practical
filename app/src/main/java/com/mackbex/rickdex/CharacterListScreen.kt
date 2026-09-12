package com.mackbex.rickdex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mackbex.rickdex.ui.theme.RickdexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
  characters: List<Character>,
  onCharacterClick: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(title = { Text("Characters") })
    }
  ) { innerPadding ->
    LazyColumn(
      modifier = modifier.fillMaxSize(),
      contentPadding = PaddingValues(
        start = 16.dp,
        end = 16.dp,
        top = innerPadding.calculateTopPadding() + 16.dp,
        bottom = 16.dp
      ),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Text("Header")
      }
      items(
        items = characters,
        key = { it.id }
      ) { character ->
        CharacterCard(
          character = character,
          onClick = { onCharacterClick(character.id) }
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CharacterListPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterListScreen(sampleCharacters, onCharacterClick = {})
  }
}