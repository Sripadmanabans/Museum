package com.adjectivemonk2.museum.presenter.treehouse.gallery

import app.cash.redwood.treehouse.AppService
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.zipline.ZiplineService

public interface GalleryPresenter : AppService, ZiplineService {
  public fun launch(): ZiplineTreehouseUi
}
