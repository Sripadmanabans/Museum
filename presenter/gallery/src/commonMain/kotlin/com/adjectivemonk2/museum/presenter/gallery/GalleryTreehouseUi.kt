package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import app.cash.redwood.treehouse.TreehouseUi
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource

public class GalleryTreehouseUi(
  private val galleryRemoteDataSource: GalleryRemoteDataSource
) : TreehouseUi {
  @Composable
  override fun Show() {
    GalleryUi(galleryRemoteDataSource)
  }
}
