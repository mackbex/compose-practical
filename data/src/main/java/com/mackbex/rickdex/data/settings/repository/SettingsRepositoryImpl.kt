package com.mackbex.rickdex.data.settings.repository

import com.mackbex.rickdex.data.preferences.SettingsDataStore
import com.mackbex.rickdex.domain.settings.model.ThemeMode
import com.mackbex.rickdex.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
  private val dataStore: SettingsDataStore
) : SettingsRepository {

  override val themeMode: Flow<ThemeMode> = dataStore.themeMode
    .map { runCatching { ThemeMode.valueOf(it) }.getOrDefault(ThemeMode.SYSTEM) }

  override val dynamicColor: Flow<Boolean> = dataStore.dynamicColor

  override suspend fun setThemeMode(mode: ThemeMode) {
    dataStore.setThemeMode(mode.name)
  }

  override suspend fun setDynamicColor(enabled: Boolean) {
    dataStore.setDynamicColor(enabled)
  }
}