package com.adjectivemonk2.museum.presenter.gallery

import com.adjectivemonk2.museum.model.gallery.Gallery

public class GalleryUiConverter(
  private val mediaUiConverter: MediaUiConverter,
  private val accountImageUrlGenerator: AccountImageUrlGenerator,
) {

  internal fun toGalleryUiItem(gallery: Gallery): GalleryUiItem? {
    val mediaItems = gallery.media.map { mediaUiConverter.toMediaUi(it) }
    val mediaItem = mediaItems.firstOrNull()
    return mediaItem?.let {
      GalleryUiItem(
        galleryId = gallery.id,
        userId = gallery.accountUrl,
        accountImageUrl = accountImageUrlGenerator.thumbnail(gallery.accountUrl),
        mediaUi = it,
        title = gallery.title,
        showDownArrow = false,
        diff = (gallery.ups - gallery.downs).toString(),
        commentCount = gallery.commentCount.toString(),
        views = gallery.views.toString(),
        showItemCount = mediaItems.size > 1,
        itemCount = (mediaItems.size).toString(),
      )
    }
  }
}
