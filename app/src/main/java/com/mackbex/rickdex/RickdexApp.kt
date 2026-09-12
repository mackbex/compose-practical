package com.mackbex.rickdex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mackbex.rickdex.domain.model.sampleCharacters


@Composable
fun RickdexApp(modifier: Modifier = Modifier) {
  val backStack = rememberNavBackStack(CharacterListRoute)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<CharacterListRoute> {
        CharacterListScreen(
          characters = sampleCharacters,
          onCharacterClick = { id ->
            backStack.add(CharacterDetailRoute(id))
          }
        )
      }

      entry<CharacterDetailRoute> { key ->
        CharacterDetailScreen(
          characterId = key.characterId,
          onBack = { backStack.removeLastOrNull() }
        )
      }

    }
  )
}