package com.adjectivemonk2.museum.remote.gallery.fake

import com.adjectivemonk2.museum.model.gallery.Gallery
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource
import kotlinx.coroutines.CompletableDeferred

public class FakeGalleryRemoteDataSource : GalleryRemoteDataSource {

  private var galleries = CompletableDeferred<List<Gallery>>()

  override suspend fun getGallery(
    section: String,
    sort: String,
    window: String,
    page: Int
  ): List<Gallery> {
    return galleries.await()
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
