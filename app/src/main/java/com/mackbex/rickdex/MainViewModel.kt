package com.mackbex.rickdex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mackbex.rickdex.domain.settings.model.ThemeMode
import com.mackbex.rickdex.domain.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  settingsRepository: SettingsRepository
) : ViewModel() {
  val themeMode =
    settingsRepository.themeMode.stateIn(
      viewModelScope, SharingStarted.WhileSubscribed(5_000),
      ThemeMode.SYSTEM
    )

  val dynamicColor = settingsRepository.dynamicColor
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
}