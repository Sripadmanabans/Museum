package com.adjectivemonk2.museum.presenter.gallery

import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import app.cash.redwood.layout.compose.Row
import app.cash.redwood.ui.dp
import com.adjectivemonk2.museum.compose.Image
import com.adjectivemonk2.museum.compose.Text

@Composable
internal fun GalleryUi() {
  Row {
    Text("Hello Android!!")
    Image(url = "https://picsum.photos/200", modifier = Modifier.size(48.dp, 48.dp))
  }
}
