package com.adjectivemonk2.museum.presenter.treehouse.gallery

import app.cash.zipline.Zipline

private val zipline by lazy { Zipline.get() }

@OptIn(ExperimentalJsExport::class)
@JsExport
public fun preparePresenters() {

  zipline.bind<GalleryPresenter>(
    name = "GalleryPresenter",
    instance = RealGalleryPresenter(zipline.json),
  )
}
