package com.adjectivemonk2.museum.presenter.gallery

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.adjectivemonk2.museum.repository.gallery.fake.galleryWithMedia1
import com.adjectivemonk2.museum.repository.gallery.fake.galleryWithOutMedia
import kotlin.test.Test

internal class GalleryConverterTest {

  @Test
  fun gallery_with_media() {
    val mediaUiConverter = MediaUiConverter()
    val accountImageUrlGenerator = AccountImageUrlGenerator()
    val galleryConverter = GalleryUiConverter(mediaUiConverter, accountImageUrlGenerator)
    val gallery = galleryWithMedia1
    val actual = galleryConverter.toGalleryUiItem(gallery)
    val mediaItems = gallery.media.map { mediaUiConverter.toMediaUi(it) }
    val mediaItem = mediaItems.first()
    val expected = GalleryUiItem(
      galleryId = gallery.id,
      userId = gallery.accountUrl,
      accountImageUrl = accountImageUrlGenerator.thumbnail(gallery.accountUrl),
      mediaUi = mediaItem,
      title = gallery.title,
      showDownArrow = false,
      diff = (gallery.ups - gallery.downs).toString(),
      commentCount = gallery.commentCount.toString(),
      views = gallery.views.toString(),
      showItemCount = mediaItems.size > 1,
      itemCount = (mediaItems.size).toString(),
    )
    assertThat(actual).isEqualTo(expected)
  }

  @Test
  fun gallery_with_out_media() {
    val mediaUiConverter = MediaUiConverter()
    val accountImageUrlGenerator = AccountImageUrlGenerator()
    val galleryConverter = GalleryUiConverter(mediaUiConverter, accountImageUrlGenerator)
    val gallery = galleryWithOutMedia
    val actual = galleryConverter.toGalleryUiItem(gallery)
    assertThat(actual).isNull()
  }
}
