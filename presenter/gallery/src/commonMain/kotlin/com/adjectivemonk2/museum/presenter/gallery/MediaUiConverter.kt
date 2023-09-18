package com.adjectivemonk2.museum.presenter.gallery

import com.adjectivemonk2.museum.model.gallery.Gif
import com.adjectivemonk2.museum.model.gallery.Jpeg
import com.adjectivemonk2.museum.model.gallery.Media
import com.adjectivemonk2.museum.model.gallery.Mp4
import com.adjectivemonk2.museum.model.gallery.Png

public class MediaUiConverter {

  internal fun toMediaUi(media: Media): MediaUi {
    return with(media) {
      when (this) {
        is Gif -> MediaUi.Gif(id, link)
        is Jpeg -> MediaUi.Image(id, link)
        is Mp4 -> MediaUi.Video(id, link)
        is Png -> MediaUi.Image(id, link)
      }
    }
  }
}
