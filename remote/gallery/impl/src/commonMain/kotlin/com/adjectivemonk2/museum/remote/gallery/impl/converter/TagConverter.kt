package com.adjectivemonk2.museum.remote.gallery.impl.converter

import com.adjectivemonk2.museum.model.gallery.Tag
import com.adjectivemonk2.museum.remote.model.gallery.TagFromRemote

public class TagConverter {
  internal fun convert(tagFromRemote: TagFromRemote): Tag {
    return Tag(
      name = tagFromRemote.name,
      displayName = tagFromRemote.displayName,
      followers = tagFromRemote.followers,
      totalItems = tagFromRemote.totalItems,
      isPromoted = tagFromRemote.isPromoted,
    )
  }
}
