package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.compose.Row
import app.cash.redwood.layout.compose.Spacer
import app.cash.redwood.lazylayout.compose.ExperimentalRedwoodLazyLayoutApi
import app.cash.redwood.lazylayout.compose.LazyColumn
import app.cash.redwood.lazylayout.compose.items
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.dp
import co.touchlab.kermit.Logger
import com.adjectivemonk2.museum.compose.Image
import com.adjectivemonk2.museum.compose.Surface
import com.adjectivemonk2.museum.compose.Text
import com.adjectivemonk2.museum.repository.gallery.GalleryRepository
import com.adjectivemonk2.museum.repository.gallery.Section
import com.adjectivemonk2.museum.repository.gallery.Sort
import com.adjectivemonk2.museum.repository.gallery.Window
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalRedwoodLazyLayoutApi::class)
@Composable
internal fun GalleryUi(
  galleryRepository: GalleryRepository,
  galleryUiConverter: GalleryUiConverter
) {
  val list = remember { mutableStateListOf<GalleryUiItem>() }
  var isLoading by remember { mutableStateOf(false) }
  LaunchedEffect(Unit) {
    try {
      isLoading = true
      val data = galleryRepository.getGallery(
        section = Section.HOT,
        sort = Sort.VIRAL,
        window = Window.DAY,
        page = 0
      ).first()
      val items = data.mapNotNullTo(list) { galleryUiConverter.toGalleryUiItem(it) }
      Logger.i { "Api Success $items" }
    } catch (throwable: Throwable) {
      Logger.w(throwable) { "Api failed.." }
    } finally {
      isLoading = false
    }
  }

  LazyColumn(
    refreshing = isLoading,
    onRefresh = null,
    width = Constraint.Fill,
    height = Constraint.Fill,
    placeholder = {},
  ) {
    items(list) { item -> GalleryUiItem(item) }
  }
}

@Composable
internal fun GalleryUiItem(item: GalleryUiItem) {
  Surface {
    Row(width = Constraint.Fill) {
      Image(
        url = item.accountImageUrl,
        modifier = Modifier.margin(Margin(12.dp)).size(48.dp, 48.dp)
      )
      Text(
        text = item.userId,
        modifier = Modifier.margin(Margin(vertical = 12.dp))
      )
      Spacer(width = 12.dp)
    }
  }
}
