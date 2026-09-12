package com.mackbex.rickdex.data.settings.di

import com.mackbex.rickdex.data.settings.repository.SettingsRepositoryImpl
import com.mackbex.rickdex.domain.settings.repository.SettingsRepository
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
  abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}