package com.mackbex.rickdex.data.character.di

import com.mackbex.rickdex.data.character.repository.CharacterRepositoryImpl
import com.mackbex.rickdex.domain.character.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindCharacterRepository(
    impl: CharacterRepositoryImpl
  ): CharacterRepository
}