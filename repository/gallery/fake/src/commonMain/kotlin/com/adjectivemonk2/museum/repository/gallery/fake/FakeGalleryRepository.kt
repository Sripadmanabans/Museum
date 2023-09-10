package com.adjectivemonk2.museum.repository.gallery.fake

import com.adjectivemonk2.museum.model.gallery.Gallery
import com.adjectivemonk2.museum.repository.gallery.GalleryRepository
import com.adjectivemonk2.museum.repository.gallery.Section
import com.adjectivemonk2.museum.repository.gallery.Sort
import com.adjectivemonk2.museum.repository.gallery.Window
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeGalleryRepository : GalleryRepository {

  private var galleries = CompletableDeferred<List<Gallery>>()

  override fun getGallery(
    section: Section,
    sort: Sort,
    window: Window,
    page: Int
  ): Flow<List<Gallery>> {
    return flow { emit(galleries.await()) }
  }

  public fun setGalleries(galleries: List<Gallery>?, throwable: Throwable?) {
    check((galleries != null) xor (throwable != null)) {
      "galleries or throwable should be provided!!"
    }
    when {
      galleries != null -> this.galleries.complete(galleries)
      throwable != null -> this.galleries.completeExceptionally(throwable)
    }
  }

  public fun reset() {
    galleries = CompletableDeferred()
  }
}
