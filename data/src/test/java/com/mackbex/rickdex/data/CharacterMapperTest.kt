package com.mackbex.rickdex.data

import com.mackbex.rickdex.data.character.remote.dto.CharacterDto
import com.mackbex.rickdex.data.character.remote.dto.LocationRefDto
import com.mackbex.rickdex.data.character.remote.mapper.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class CharacterMapperTest {

  @Test
  fun `DTO의 origin 객체에서 name만 추출한다`() {
    val dto = CharacterDto(
      id = 1,
      name = "Rick Sanchez",
      status = "Alive",
      species = "Human",
      gender = "Male",
      origin = LocationRefDto(name = "Earth (C-137)", url = "https://..."),
      location = LocationRefDto(name = "Citadel", url = ""),
      image = "https://example.com/1.jpeg"
    )

    val character = dto.toDomain()

    assertEquals("Earth (C-137)", character.origin)
  }

  @Test
  fun `변환된 도메인 모델의 북마크는 기본값 false다`() {
    val dto = CharacterDto(
      id = 1, name = "Rick", status = "Alive", species = "Human",
      gender = "Male",
      origin = LocationRefDto("Earth"), location = LocationRefDto("Citadel"),
      image = "url"
    )

    assertFalse(dto.toDomain().isBookmarked)
  }
}