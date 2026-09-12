package com.mackbex.rickdex.data.character.repository

import com.mackbex.rickdex.data.character.local.BookmarkDao
import com.mackbex.rickdex.data.character.local.entity.BookmarkEntity
import com.mackbex.rickdex.domain.character.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
  private val dao: BookmarkDao
) : BookmarkRepository {

  override fun observeBookmarkIds(): Flow<Set<Int>> = dao.observeBookmarkIds().map { it.toSet() }
  override fun isBookmarked(id: Int): Flow<Boolean> = dao.isBookmarked(id)
  override suspend fun toggle(id: Int) {
    val bookmarked = dao.isBookmarked(id).first()
    if (bookmarked) dao.delete(id) else dao.insert(BookmarkEntity(id))
  }
}