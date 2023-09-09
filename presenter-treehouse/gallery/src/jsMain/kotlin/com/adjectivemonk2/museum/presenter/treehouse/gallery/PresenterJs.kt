package com.adjectivemonk2.museum.presenter.treehouse.gallery

import app.cash.zipline.Zipline
import com.adjectivemonk2.museum.remote.core.HostApi
import com.adjectivemonk2.museum.remote.gallery.impl.RealGalleryRemoteDataSource

@OptIn(ExperimentalJsExport::class)
@JsExport
public fun preparePresenters() {
  val zipline = Zipline.get()

  val hostApi = zipline.take<HostApi>("HostApi")
  val remoteDataSource = RealGalleryRemoteDataSource(hostApi)

  zipline.bind<GalleryPresenter>(
    name = "GalleryPresenter",
    instance = RealGalleryPresenter(remoteDataSource, zipline.json),
  )
}
