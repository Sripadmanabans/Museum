package com.adjectivemonk2.museum.model.gallery

public data class Gallery(
  val id: String,
  val title: String,
  val dateTime: Long,
  val description: String?,
  val accountUrl: String,
  val accountId: Long,
  val views: Long,
  val ups: Long,
  val downs: Long,
  val nsfw: Boolean,
  val commentCount: Long,
  val favoriteCount: Long,
  val mediaCount: Long,
  val media: List<Media>,
  val tags: List<Tag>,
)
