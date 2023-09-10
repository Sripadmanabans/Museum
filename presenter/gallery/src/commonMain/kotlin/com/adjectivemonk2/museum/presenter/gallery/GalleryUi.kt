package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.cash.redwood.Modifier
import app.cash.redwood.layout.compose.Row
import app.cash.redwood.ui.dp
import co.touchlab.kermit.Logger
import com.adjectivemonk2.museum.compose.Image
import com.adjectivemonk2.museum.compose.Text
import com.adjectivemonk2.museum.repository.gallery.GalleryRepository
import com.adjectivemonk2.museum.repository.gallery.Section
import com.adjectivemonk2.museum.repository.gallery.Sort
import com.adjectivemonk2.museum.repository.gallery.Window
import kotlinx.coroutines.flow.first

@Composable
internal fun GalleryUi(galleryRepository: GalleryRepository) {
  LaunchedEffect(Unit) {
    try {
      val data = galleryRepository.getGallery(
        section = Section.HOT,
        sort = Sort.VIRAL,
        window = Window.DAY,
        page = 0
      ).first()
      Logger.i { "Api Success $data" }
    } catch (throwable: Throwable) {
      Logger.w(throwable) { "Api failed.." }
    }
  }
  Row {
    Text("Hello Android!!")
    Image(url = "https://picsum.photos/200", modifier = Modifier.size(48.dp, 48.dp))
  }
}
