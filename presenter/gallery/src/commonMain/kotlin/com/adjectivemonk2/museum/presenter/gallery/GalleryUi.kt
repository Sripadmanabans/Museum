package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.cash.redwood.Modifier
import app.cash.redwood.layout.compose.Row
import app.cash.redwood.ui.dp
import co.touchlab.kermit.Logger
import com.adjectivemonk2.museum.compose.Image
import com.adjectivemonk2.museum.compose.Text
import com.adjectivemonk2.museum.remote.gallery.GalleryRemoteDataSource
import com.adjectivemonk2.museum.remote.gallery.Section
import com.adjectivemonk2.museum.remote.gallery.Sort
import com.adjectivemonk2.museum.remote.gallery.Window

@Composable
internal fun GalleryUi(galleryRemoteDataSource: GalleryRemoteDataSource) {
  LaunchedEffect(Unit) {
    try {
      val data = galleryRemoteDataSource.getGallery(
        section = Section.HOT.param,
        sort = Sort.VIRAL.param,
        window = Window.DAY.param,
        page = 0
      )
      Logger.i { "Api failed Success $data" }
    } catch (throwable: Throwable) {
      Logger.w(throwable) { "Api failed.." }
    }
  }
  Row {
    Text("Hello Android!!")
    Image(url = "https://picsum.photos/200", modifier = Modifier.size(48.dp, 48.dp))
  }
}
