package com.mackbex.feature.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mackbex.rickdex.core.ui.theme.RickdexTheme
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.feature.list.CharacterCard
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class CharacterCardTest {

  @get:Rule
  val composeRule = createComposeRule()

  private val rick = Character(1, "Rick Sanchez", "Alive", "Human", "Earth", "url")

  @Test
  fun 캐릭터_이름이_표시된다() {
    composeRule.setContent {
      RickdexTheme { CharacterCard(rick, onClick = {}, onBookmarkClick = {}) }
    }

    composeRule.onNodeWithText("Rick Sanchez").assertIsDisplayed()
  }

  @Test
  fun 카드를_누르면_id가_전달된다() {
    var clickedId: Int? = null

    composeRule.setContent {
      RickdexTheme {
        CharacterCard(rick, onClick = { clickedId = it }, onBookmarkClick = {})
      }
    }

    composeRule.onNodeWithText("Rick Sanchez").performClick()

    assertEquals(1, clickedId)
  }

  @Test
  fun 북마크_상태에_따라_설명이_바뀐다() {
    composeRule.setContent {
      RickdexTheme {
        CharacterCard(
          rick.copy(isBookmarked = true),
          onClick = {}, onBookmarkClick = {}
        )
      }
    }

    composeRule.onNodeWithContentDescription("북마크 해제").assertExists()
  }
}