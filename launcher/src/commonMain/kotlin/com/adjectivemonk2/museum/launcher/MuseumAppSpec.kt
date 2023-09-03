package com.adjectivemonk2.museum.launcher

import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.Zipline
import com.adjectivemonk2.museum.presenter.treehouse.gallery.GalleryPresenter
import kotlinx.coroutines.flow.Flow

public class MuseumAppSpec(
  override val manifestUrl: Flow<String>,
) : TreehouseApp.Spec<GalleryPresenter>() {

  override val name: String = "Museum"

  override fun bindServices(zipline: Zipline): Unit = Unit

  override fun create(zipline: Zipline): GalleryPresenter {
    return zipline.take("GalleryPresenter")
  }
}
