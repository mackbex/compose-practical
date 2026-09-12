package com.mackbex.rickdex.domain.character.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mackbex.rickdex.domain.character.model.Character
import com.mackbex.rickdex.domain.character.repository.BookmarkRepository
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
  private val characterRepository: CharacterRepository,
  private val bookmarkRepository: BookmarkRepository
) {

  operator fun invoke(
    query: String?,
    scope: CoroutineScope
  ): Flow<PagingData<Character>> =
    combine(
      characterRepository.getCharactersPaged(query).cachedIn(scope),
      bookmarkRepository.observeBookmarkIds()
    ) { pagingData, bookmarkedIds ->
      pagingData.map { it.copy(isBookmarked = it.id in bookmarkedIds) }
    }
}