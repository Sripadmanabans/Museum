package com.adjectivemonk2.museum.presenter.gallery

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.adjectivemonk2.museum.repository.gallery.fake.gifMedia
import com.adjectivemonk2.museum.repository.gallery.fake.jpegMedia
import com.adjectivemonk2.museum.repository.gallery.fake.mp4Media
import com.adjectivemonk2.museum.repository.gallery.fake.pngMedia
import kotlin.test.Test

internal class MediaConverterTest {

  @Test
  fun convert_media_of_type_gif_to_MediaItem_of_gif() {
    val converter = MediaUiConverter()
    val actual = converter.toMediaUi(gifMedia)
    val expected = MediaUi.Gif(gifMedia.id, gifMedia.link)
    assertThat(actual).isEqualTo(expected)
  }

  @Test
  fun convert_media_of_type_png_to_MediaItem_of_image() {
    val converter = MediaUiConverter()
    val actual = converter.toMediaUi(pngMedia)
    val expected = MediaUi.Image(pngMedia.id, pngMedia.link)
    assertThat(actual).isEqualTo(expected)
  }

  @Test
  fun convert_media_of_type_jpeg_to_MediaItem_of_image() {
    val converter = MediaUiConverter()
    val actual = converter.toMediaUi(jpegMedia)
    val expected = MediaUi.Image(jpegMedia.id, jpegMedia.link)
    assertThat(actual).isEqualTo(expected)
  }

  @Test
  fun convert_media_of_type_mp4_to_MediaItem_of_video() {
    val converter = MediaUiConverter()
    val actual = converter.toMediaUi(mp4Media)
    val expected = MediaUi.Video(mp4Media.id, mp4Media.link)
    assertThat(actual).isEqualTo(expected)
  }
}
