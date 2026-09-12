package com.mackbex.rickdex.data.character.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
  @PrimaryKey val characterId: Int,
  val bookmarkedAt: Long = System.currentTimeMillis()
)