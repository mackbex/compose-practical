package com.mackbex.rickdex.data.di

import android.content.Context
import androidx.room.Room
import com.mackbex.rickdex.data.character.local.BookmarkDao
import com.mackbex.rickdex.data.local.RickdexDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context: Context
  ): RickdexDatabase = Room.databaseBuilder(
    context,
    RickdexDatabase::class.java,
    "rickdex.db"
  ).build()

  @Provides
  fun provideBookmarkDao(database: RickdexDatabase): BookmarkDao = database.bookmarkDao()
}