package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import app.cash.redwood.treehouse.TreehouseUi

public class GalleryTreehouseUi : TreehouseUi {
  @Composable
  override fun Show() {
    GalleryUi()
  }
}
