package com.mackbex.rickdex.domain.model

data class Character(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val origin: String
)

val sampleCharacters = listOf(
  Character(1, "Rick Sanchez", "Alive", "Human", "Earth (C-137)"),
  Character(2, "Morty Smith", "Alive", "Human", "Earth (C-137)"),
  Character(3, "Summer Smith", "Alive", "Human", "Earth (Replacement Dimension)"),
  Character(4, "Beth Smith", "Alive", "Human", "Earth (Replacement Dimension)"),
  Character(5, "Jerry Smith", "Alive", "Human", "Earth (Replacement Dimension)"),
  Character(6, "Abadango Cluster Princess", "Alive", "Alien", "Abadango"),
  Character(7, "Abradolf Lincler", "unknown", "Human", "Earth (Replacement Dimension)"),
  Character(8, "Adjudicator Rick", "Dead", "Human", "unknown"),
  Character(9, "Agency Director", "Dead", "Human", "Earth (Replacement Dimension)"),
  Character(10, "Alan Rails", "Dead", "Human", "unknown")
)