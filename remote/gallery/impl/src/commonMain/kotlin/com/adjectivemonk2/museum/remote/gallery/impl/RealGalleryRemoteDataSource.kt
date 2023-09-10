package com.adjectivemonk2.museum.remote.gallery.impl

import com.adjectivemonk2.museum.model.gallery.Gallery
import com.adjectivemonk2.museum.remote.core.HostApi
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource
import com.adjectivemonk2.museum.remote.gallery.impl.converter.GalleryConverter
import com.adjectivemonk2.museum.remote.model.core.Data
import com.adjectivemonk2.museum.remote.model.gallery.GalleryFromRemote
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
public class RealGalleryRemoteDataSource(
  private val hostApi: HostApi,
  private val galleryConverter: GalleryConverter,
) : GalleryRemoteDataSource {

  private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
  }

  override suspend fun getGallery(
    section: String,
    sort: String,
    window: String,
    page: Int
  ): List<Gallery> {
    val responseJson = hostApi.get("gallery/$section/$sort/$window/$page")
    val data = json.decodeFromString<Data<List<GalleryFromRemote>>>(responseJson).data
    return data.mapTo(ArrayList(data.size)) { galleryConverter.convert(it) }
  }
}
