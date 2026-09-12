package com.mackbex.rickdex.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


@Singleton
class SettingsDataStore @Inject constructor(
  @param:ApplicationContext private val context: Context
) {

  private object Keys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
  }

  val themeMode: Flow<String> = context.dataStore.data
    .map { it[Keys.THEME_MODE] ?: "SYSTEM" }

  val dynamicColor: Flow<Boolean> = context.dataStore.data
    .map { it[Keys.DYNAMIC_COLOR] ?: false }

  suspend fun setThemeMode(mode: String) {
    context.dataStore.edit { it[Keys.THEME_MODE] = mode }
  }

  suspend fun setDynamicColor(enabled: Boolean) {
    context.dataStore.edit { it[Keys.DYNAMIC_COLOR] = enabled }
  }
}