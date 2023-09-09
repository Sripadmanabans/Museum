package com.adjectivemonk2.museum.remote.core

import app.cash.zipline.ZiplineService

public interface HostApi : ZiplineService {

  public suspend fun get(url: String): String
}
