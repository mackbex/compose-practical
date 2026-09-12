package com.mackbex.rickdex.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mackbex.rickdex.core.ui.components.CaptionText
import com.mackbex.rickdex.core.ui.components.TitleText
import com.mackbex.rickdex.core.ui.theme.RickdexTheme
import com.mackbex.rickdex.core.ui.theme.StatusAlive
import com.mackbex.rickdex.core.ui.theme.StatusDead
import com.mackbex.rickdex.core.ui.theme.StatusUnknown
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.model.sampleCharacters


@Composable
fun CharacterCard(
  character: Character,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onClick,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = character.imageUrl,
        contentDescription = character.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(72.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
      )
      Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        TitleText(
          text = character.name,
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
          CaptionText(
            text = "${character.status} - ${character.species}",
          )
        }
        CaptionText(
          text = character.origin,
        )
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
private fun CharacterCardPreview() {
  RickdexTheme(dynamicColor = false) {
    CharacterCard(sampleCharacters[0], {})
  }
}