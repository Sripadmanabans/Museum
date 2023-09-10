package com.adjectivemonk2.museum.repository.gallery.impl

import com.adjectivemonk2.museum.model.gallery.Gallery
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource
import com.adjectivemonk2.museum.repository.gallery.GalleryRepository
import com.adjectivemonk2.museum.repository.gallery.Section
import com.adjectivemonk2.museum.repository.gallery.Sort
import com.adjectivemonk2.museum.repository.gallery.Window
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class RealGalleryRepository(
  private val remoteDataSource: GalleryRemoteDataSource
) : GalleryRepository {
  override fun getGallery(
    section: Section,
    sort: Sort,
    window: Window,
    page: Int
  ): Flow<List<Gallery>> {
    return flow {
      emit(remoteDataSource.getGallery(section.param, sort.param, window.param, page))
    }
  }
}
