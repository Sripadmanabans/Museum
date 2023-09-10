package com.adjectivemonk2.museum.remote.gallery

import com.adjectivemonk2.museum.model.gallery.Gallery

public interface GalleryRemoteDataSource {
  public suspend fun getGallery(
    section: String,
    sort: String,
    window: String,
    page: Int,
  ): List<Gallery>
}
