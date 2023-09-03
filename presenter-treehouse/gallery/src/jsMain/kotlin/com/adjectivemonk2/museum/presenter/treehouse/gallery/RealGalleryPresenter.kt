package com.adjectivemonk2.museum.presenter.treehouse.gallery

import app.cash.redwood.treehouse.StandardAppLifecycle
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.redwood.treehouse.asZiplineTreehouseUi
import com.adjectivemonk2.museum.compose.MuseumProtocolBridge
import com.adjectivemonk2.museum.presenter.gallery.GalleryTreehouseUi
import kotlinx.serialization.json.Json

public class RealGalleryPresenter(json: Json) : GalleryPresenter {

  override val appLifecycle: StandardAppLifecycle = StandardAppLifecycle(
    protocolBridgeFactory = MuseumProtocolBridge,
    json = json,
    widgetVersion = 0U,
  )

  override fun launch(): ZiplineTreehouseUi {
    val treehouseUi = GalleryTreehouseUi()
    return treehouseUi.asZiplineTreehouseUi(appLifecycle)
  }
}
