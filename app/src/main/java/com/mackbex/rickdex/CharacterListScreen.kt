package com.mackbex.rickdex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mackbex.rickdex.ui.theme.RickdexTheme

@Composable
fun CharacterListScreen(
  characters: List<Character>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item{
      Text("Header")
    }
    items(
      items = characters,
      key = { it.id }
    ) { character ->
      CharacterCard(character)
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CharacterListPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterListScreen(sampleCharacters)
  }
}