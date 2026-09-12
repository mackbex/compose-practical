package com.mackbex.rickdex.domain.settings.repository

import com.mackbex.rickdex.domain.settings.model.ThemeMode
import kotlinx.coroutines.flow.Flow


interface SettingsRepository {
  val themeMode: Flow<ThemeMode>
  val dynamicColor: Flow<Boolean>
  suspend fun setThemeMode(mode: ThemeMode)
  suspend fun setDynamicColor(enabled: Boolean)

}