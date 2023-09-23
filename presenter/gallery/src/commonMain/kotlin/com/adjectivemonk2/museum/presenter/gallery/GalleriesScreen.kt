package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.StableMarker

@Immutable
internal data class GalleryUiItem(
  val galleryId: String,
  val userId: String,
  val accountImageUrl: String,
  val mediaUi: MediaUi,
  val title: String,
  val showDownArrow: Boolean,
  val diff: String,
  val commentCount: String,
  val views: String,
  val showItemCount: Boolean,
  val itemCount: String,
)

@StableMarker
internal sealed class MediaUi {
  internal abstract val id: String
  internal abstract val url: String

  @Immutable
  data class Image(
    override val id: String,
    override val url: String,
  ) : MediaUi()

  @Immutable
  data class Gif(
    override val id: String,
    override val url: String,
  ) : MediaUi()

  @Immutable
  data class Video(
    override val id: String,
    override val url: String,
  ) : MediaUi()
}
