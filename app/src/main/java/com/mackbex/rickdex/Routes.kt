package com.mackbex.rickdex

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CharacterListNavKey : NavKey

@Serializable
data class CharacterDetailNavKey(val characterId: Int) : NavKey