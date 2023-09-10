package com.adjectivemonk2.museum.remote.gallery.impl.converter

import com.adjectivemonk2.museum.model.gallery.Gallery
import com.adjectivemonk2.museum.remote.model.gallery.GalleryFromRemote

public class GalleryConverter(
  private val tagConverter: TagConverter,
  private val mediaConverter: MediaConverter,
) {
  internal fun convert(galleryFromRemote: GalleryFromRemote): Gallery {
    val media = galleryFromRemote.media.orEmpty()
    return Gallery(
      id = galleryFromRemote.id,
      title = galleryFromRemote.title,
      dateTime = galleryFromRemote.dateTime,
      description = galleryFromRemote.description,
      accountUrl = galleryFromRemote.accountUrl,
      accountId = galleryFromRemote.accountId,
      views = galleryFromRemote.views,
      ups = galleryFromRemote.ups,
      downs = galleryFromRemote.downs,
      nsfw = galleryFromRemote.nsfw,
      commentCount = galleryFromRemote.commentCount,
      favoriteCount = galleryFromRemote.favoriteCount,
      mediaCount = galleryFromRemote.imagesCount ?: 0,
      media = media.mapNotNullTo(ArrayList(media.size)) { mediaConverter.convert(it) },
      tags = galleryFromRemote.tags.map { tagConverter.convert(it) },
    )
  }
}
