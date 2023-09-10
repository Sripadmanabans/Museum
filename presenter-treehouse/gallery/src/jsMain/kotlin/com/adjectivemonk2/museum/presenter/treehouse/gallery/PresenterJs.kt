package com.adjectivemonk2.museum.presenter.treehouse.gallery

import app.cash.zipline.Zipline
import com.adjectivemonk2.museum.remote.core.HostApi
import com.adjectivemonk2.museum.remote.gallery.impl.RealGalleryRemoteDataSource
import com.adjectivemonk2.museum.remote.gallery.impl.converter.GalleryConverter
import com.adjectivemonk2.museum.remote.gallery.impl.converter.MediaConverter
import com.adjectivemonk2.museum.remote.gallery.impl.converter.TagConverter
import com.adjectivemonk2.museum.repository.gallery.impl.RealGalleryRepository

@OptIn(ExperimentalJsExport::class)
@JsExport
public fun preparePresenters() {
  val zipline = Zipline.get()

  val hostApi = zipline.take<HostApi>("HostApi")
  val tagConverter = TagConverter()
  val mediaConverter = MediaConverter(tagConverter)
  val galleryConverter = GalleryConverter(tagConverter, mediaConverter)
  val remoteDataSource = RealGalleryRemoteDataSource(hostApi, galleryConverter)
  val galleryRepository = RealGalleryRepository(remoteDataSource)

  zipline.bind<GalleryPresenter>(
    name = "GalleryPresenter",
    instance = RealGalleryPresenter(galleryRepository, zipline.json),
  )
}
