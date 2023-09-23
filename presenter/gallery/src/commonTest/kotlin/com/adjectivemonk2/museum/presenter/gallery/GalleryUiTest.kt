package com.adjectivemonk2.museum.presenter.gallery

import app.cash.redwood.Modifier
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.layout.compose.RowScope
import app.cash.redwood.layout.widget.RowValue
import app.cash.redwood.layout.widget.SpacerValue
import app.cash.redwood.lazylayout.api.ScrollItemIndex
import app.cash.redwood.lazylayout.widget.RefreshableLazyListValue
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.dp
import assertk.assertThat
import assertk.assertions.containsExactly
import com.adjectivemonk2.museum.repository.gallery.fake.FakeGalleryRepository
import com.adjectivemonk2.museum.repository.gallery.fake.galleryWithMedia1
import com.adjectivemonk2.museum.repository.gallery.fake.galleryWithMedia2
import com.adjectivemonk2.museum.widget.ImageValue
import com.adjectivemonk2.museum.widget.MuseumTester
import com.adjectivemonk2.museum.widget.SurfaceValue
import com.adjectivemonk2.museum.widget.TextValue
import kotlinx.coroutines.test.runTest
import okio.IOException
import kotlin.test.Test

internal class GalleryUiTest {

  @Test
  fun when_api_success_show_list() = runTest {
    val galleryRepository = FakeGalleryRepository()
    val accountImageUrlGenerator = AccountImageUrlGenerator()
    val mediaUiConverter = MediaUiConverter()
    val galleryUiConverter = GalleryUiConverter(mediaUiConverter, accountImageUrlGenerator)
    MuseumTester {
      setContent {
        GalleryUi(galleryRepository, galleryUiConverter)
      }

      val snapshot0 = awaitSnapshot()
      val state0 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = false,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
      )
      assertThat(snapshot0).containsExactly(state0)
      val snapshot1 = awaitSnapshot()
      val state1 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = true,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
      )
      assertThat(snapshot1).containsExactly(state1)
      val galleries = listOf(galleryWithMedia1, galleryWithMedia2)
      galleryRepository.setGalleries(galleries)
      val snapshot2 = awaitSnapshot()
      val state2Items = galleries.map {
        SurfaceValue(
          children = listOf(
            RowValue(
              width = Constraint.Fill,
              children = listOf(
                ImageValue(
                  url = accountImageUrlGenerator.thumbnail(it.accountUrl),
                  modifier = with(object : RowScope {}) {
                    Modifier
                      .margin(Margin(12.dp))
                      .size(48.dp, 48.dp)
                  },
                ),
                TextValue(
                  text = it.accountUrl,
                  modifier = with(object : RowScope {}) {
                    Modifier.margin(Margin(vertical = 12.dp))
                  },
                ),
                SpacerValue(width = 12.dp)
              )
            )
          )
        )
      }
      val state2 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = false,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
        items = state2Items
      )
      assertThat(snapshot2).containsExactly(state2)
    }
  }

  @Test
  fun when_api_failure_resets_loading() = runTest {
    val galleryRepository = FakeGalleryRepository()
    val accountImageUrlGenerator = AccountImageUrlGenerator()
    val mediaUiConverter = MediaUiConverter()
    val galleryUiConverter = GalleryUiConverter(mediaUiConverter, accountImageUrlGenerator)
    MuseumTester {
      setContent {
        GalleryUi(galleryRepository, galleryUiConverter)
      }

      val snapshot0 = awaitSnapshot()
      val state0 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = false,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
      )
      assertThat(snapshot0).containsExactly(state0)
      val snapshot1 = awaitSnapshot()
      val state1 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = true,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
      )
      assertThat(snapshot1).containsExactly(state1)
      galleryRepository.setGalleries(throwable = IOException())
      val snapshot2 = awaitSnapshot()
      val state2 = RefreshableLazyListValue(
        isVertical = true,
        onViewportChanged = { _, _ -> },
        itemsBefore = 0,
        itemsAfter = 0,
        refreshing = false,
        onRefresh = null,
        width = Constraint.Fill,
        height = Constraint.Fill,
        margin = Margin(all = 0.dp),
        crossAxisAlignment = CrossAxisAlignment.Start,
        scrollItemIndex = ScrollItemIndex(id = 0, index = 0),
        pullRefreshContentColor = 0xFF000000u,
      )
      assertThat(snapshot2).containsExactly(state2)
    }
  }
}
