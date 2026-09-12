package com.mackbex.rickdex.data.character.remote

import com.mackbex.rickdex.data.character.remote.dto.CharacterDto
import com.mackbex.rickdex.data.character.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

  @GET("character/")
  suspend fun getCharacters(
    @Query("page") page: Int = 1,
    @Query("name") name: String? = null
  ): CharacterResponseDto

  @GET("character/{id}")
  suspend fun getCharacter(
    @Path("id") id: Int
  ): CharacterDto
}