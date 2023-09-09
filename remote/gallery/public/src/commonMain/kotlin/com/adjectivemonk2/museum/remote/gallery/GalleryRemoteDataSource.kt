package com.adjectivemonk2.museum.remote.gallery

import com.adjectivemonk2.museum.remote.model.gallery.GalleryFromRemote

public interface GalleryRemoteDataSource {
  public suspend fun getGallery(
    section: String,
    sort: String,
    window: String,
    page: Int,
  ): List<GalleryFromRemote>
}
