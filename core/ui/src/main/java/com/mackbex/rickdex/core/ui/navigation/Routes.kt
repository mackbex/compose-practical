package com.mackbex.rickdex.core.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CharacterListNavKey : NavKey

@Serializable
data class CharacterDetailNavKey(val characterId: Int) : NavKey

@Serializable
data object SettingsNavKey : NavKey