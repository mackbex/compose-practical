package com.mackbex.rickdex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mackbex.rickdex.data.character.local.BookmarkDao
import com.mackbex.rickdex.data.character.local.entity.BookmarkEntity

@Database(
  entities = [BookmarkEntity::class],
  version = 1,
  exportSchema = false
)
abstract class RickdexDatabase : RoomDatabase() {
  abstract fun bookmarkDao(): BookmarkDao
}