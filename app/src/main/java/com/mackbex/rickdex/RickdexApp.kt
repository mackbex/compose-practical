package com.mackbex.rickdex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.mackbex.rickdex.core.ui.navigation.CharacterDetailNavKey
import com.mackbex.rickdex.core.ui.navigation.CharacterListNavKey
import com.mackbex.rickdex.feature.detail.CharacterDetailRoute
import com.mackbex.rickdex.feature.list.CharacterListRoute


@Composable
fun RickdexApp(modifier: Modifier = Modifier) {
  val backStack = rememberNavBackStack(CharacterListNavKey)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    modifier = modifier,
    entryDecorators = listOf(
      rememberSaveableStateHolderNavEntryDecorator(),
      rememberViewModelStoreNavEntryDecorator()
    ),
    entryProvider = entryProvider {
      entry<CharacterListNavKey> {
        CharacterListRoute(
          onCharacterClick = { id ->
            backStack.add(CharacterDetailNavKey(id))
          }
        )
      }

      entry<CharacterDetailNavKey> { key ->
        CharacterDetailRoute(
          navKey = key,
          onBack = { backStack.removeLastOrNull() }
        )
      }

    }
  )
}