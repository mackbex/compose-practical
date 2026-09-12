package com.mackbex.rickdex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mackbex.rickdex.feature.detail.CharacterDetailScreen
import com.mackbex.rickdex.feature.list.CharacterListRoute


@Composable
fun RickdexApp(modifier: Modifier = Modifier) {
  val backStack = rememberNavBackStack(CharacterListNavKey)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<CharacterListNavKey> {
        CharacterListRoute(
          onCharacterClick = { id ->
            backStack.add(CharacterDetailNavKey(id))
          }
        )
      }

      entry<CharacterDetailNavKey> { key ->
        CharacterDetailScreen(
          characterId = key.characterId,
          onBack = { backStack.removeLastOrNull() }
        )
      }

    }
  )
}