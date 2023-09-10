package com.adjectivemonk2.museum.model.gallery

public data class Tag(
  val name: String,
  val displayName: String,
  val followers: Long,
  val totalItems: Long,
  val isPromoted: Boolean,
)
