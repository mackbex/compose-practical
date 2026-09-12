package com.mackbex.rickdex.data.character.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mackbex.rickdex.data.character.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

  @Query("SELECT characterId FROM bookmarks")
  fun observeBookmarkIds(): Flow<List<Int>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(bookmark: BookmarkEntity)

  @Query("DELETE FROM bookmarks WHERE characterId = :id")
  suspend fun delete(id: Int)

  @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE characterId = :id)")
  fun isBookmarked(id: Int): Flow<Boolean>
}