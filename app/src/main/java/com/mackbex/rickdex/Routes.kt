package com.mackbex.rickdex

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object CharacterListRoute : NavKey

@Serializable
data class CharacterDetailRoute(val characterId: Int) : NavKey