package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import app.cash.redwood.treehouse.TreehouseUi
import com.adjectivemonk2.museum.repository.gallery.GalleryRepository

public class GalleryTreehouseUi(
  private val galleryRepository: GalleryRepository,
  private val galleryUiConverter: GalleryUiConverter,
) : TreehouseUi {
  @Composable
  override fun Show() {
    GalleryUi(galleryRepository, galleryUiConverter)
  }
}
