package com.adjectivemonk2.museum.presenter.gallery

import app.cash.redwood.Modifier
import app.cash.redwood.layout.compose.RowScope
import app.cash.redwood.layout.widget.RowValue
import app.cash.redwood.ui.dp
import assertk.assertThat
import assertk.assertions.containsExactly
import com.adjectivemonk2.museum.remote.gallery.fake.FakeGalleryRemoteDataSource
import com.adjectivemonk2.museum.widget.ImageValue
import com.adjectivemonk2.museum.widget.MuseumTester
import com.adjectivemonk2.museum.widget.TextValue
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

internal class GalleryUiTest {

  @Test
  fun testingGalleryUi() = runTest {
    val remoteDataSource = FakeGalleryRemoteDataSource()
    MuseumTester {
      setContent {
        GalleryUi(remoteDataSource)
      }

      val snapshot0 = awaitSnapshot()
      assertThat(snapshot0).containsExactly(
        RowValue(
          children = listOf(
            TextValue(text = "Hello Android!!"),
            ImageValue(
              url = "https://picsum.photos/200",
              modifier = with(object : RowScope {}) {
                Modifier.size(48.dp, 48.dp)
              },
            )
          )
        )
      )
    }
  }
}
