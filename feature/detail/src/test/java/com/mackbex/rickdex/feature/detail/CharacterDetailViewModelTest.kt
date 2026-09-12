package com.mackbex.rickdex.feature.detail

import app.cash.turbine.test
import com.mackbex.rickdex.core.ui.navigation.CharacterDetailNavKey
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.FakeCharacterRepository
import com.mackbex.rickdex.domain.character.usecase.GetCharacterUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CharacterDetailViewModelTest {

  private val dispatcher = StandardTestDispatcher()

  @Before
  fun setUp() {
    Dispatchers.setMain(dispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `초기화하면 캐릭터를 불러온다`() = runTest {
    val repo = FakeCharacterRepository().apply {
      characters = listOf(
        Character(1, "Rick", "Alive", "Human", "Earth", "url")
      )
    }
    val viewModel = CharacterDetailViewModel(
      navKey = CharacterDetailNavKey(1),
      getCharacter = GetCharacterUseCase(repo)
    )

    viewModel.uiState.test {
      awaitItem()                          // 초기 상태 (버림)

      val loading = awaitItem()
      assertTrue(loading.isLoading)

      val loaded = awaitItem()
      assertEquals("Rick", loaded.character?.name)
      assertFalse(loaded.isLoading)

      cancelAndIgnoreRemainingEvents()
    }
  }
}