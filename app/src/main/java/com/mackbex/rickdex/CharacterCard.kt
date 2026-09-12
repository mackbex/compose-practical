package com.mackbex.rickdex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mackbex.rickdex.ui.theme.RickdexTheme
import com.mackbex.rickdex.ui.theme.StatusAlive
import com.mackbex.rickdex.ui.theme.StatusDead
import com.mackbex.rickdex.ui.theme.StatusUnknown


@Composable
fun CharacterCard(
  character: Character,
  modifier: Modifier = Modifier
) {
  Card(modifier = modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Text(
        text = character.name,
        style = MaterialTheme.typography.titleMedium
      )
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Surface(
          shape = CircleShape,
          color = when (character.status) {
            "Alive" -> StatusAlive
            "Dead" -> StatusDead
            else -> StatusUnknown
          },
          modifier = Modifier.size(8.dp)
        ) { }
        Text(
          text = "${character.status} - ${character.species}",
          style = MaterialTheme.typography.bodySmall
        )
      }
      Text(
        text = character.origin,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CharacterCardPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterCard(sampleCharacters[0])
  }
}