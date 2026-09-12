package com.mackbex.rickdex.domain.character.repository

import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {
  fun observeBookmarkIds(): Flow<Set<Int>>
  fun isBookmarked(id: Int): Flow<Boolean>
  suspend fun toggle(id: Int)
}