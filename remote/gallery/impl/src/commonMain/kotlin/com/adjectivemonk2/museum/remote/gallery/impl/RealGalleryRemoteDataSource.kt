package com.adjectivemonk2.museum.remote.gallery.impl

import com.adjectivemonk2.museum.remote.core.HostApi
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource
import com.adjectivemonk2.museum.remote.model.core.Data
import com.adjectivemonk2.museum.remote.model.gallery.GalleryFromRemote
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
public class RealGalleryRemoteDataSource(private val hostApi: HostApi) : GalleryRemoteDataSource {

  private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
  }

  override suspend fun getGallery(
    section: String,
    sort: String,
    window: String,
    page: Int
  ): List<GalleryFromRemote> {
    val responseJson = hostApi.get("gallery/$section/$sort/$window/$page")
    return json.decodeFromString<Data<List<GalleryFromRemote>>>(responseJson).data
  }
}
