package com.mackbex.rickdex.domain.character.usecase

import com.mackbex.rickdex.domain.character.repository.BookmarkRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
  private val repository: BookmarkRepository
) {
  suspend operator fun invoke(id: Int) = repository.toggle(id)
}