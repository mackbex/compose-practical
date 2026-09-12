package com.mackbex.rickdex.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.domain.settings.model.ThemeMode
import com.mackbex.rickdex.domain.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val settingsRepository: SettingsRepository
) : ViewModel() {
  val themeMode = settingsRepository.themeMode
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ThemeMode.SYSTEM)

  val dynamicColor = settingsRepository.dynamicColor
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

  fun setThemeMode(mode: ThemeMode) {
    viewModelScope.launch { settingsRepository.setThemeMode(mode) }
  }

  fun setDynamicColor(enabled: Boolean) {
    viewModelScope.launch { settingsRepository.setDynamicColor(enabled) }
  }
}