package com.adjectivemonk2.museum.repository.gallery

import com.adjectivemonk2.museum.model.gallery.Gallery
import kotlinx.coroutines.flow.Flow

public interface GalleryRepository {
  public fun getGallery(
    section: Section = Section.HOT,
    sort: Sort = Sort.VIRAL,
    window: Window = Window.DAY,
    page: Int = 0,
  ): Flow<List<Gallery>>
}
