package com.mackbex.rickdex.domain.character.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeBookmarkRepository : BookmarkRepository {
  private val ids = MutableStateFlow(emptySet<Int>())

  override fun observeBookmarkIds(): Flow<Set<Int>> = ids
  override fun isBookmarked(id: Int): Flow<Boolean> = ids.map { id in it }
  override suspend fun toggle(id: Int) {
    ids.value = if (id in ids.value) ids.value - id else ids.value + id
  }
}