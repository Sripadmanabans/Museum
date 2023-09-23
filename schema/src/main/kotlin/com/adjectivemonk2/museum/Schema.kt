package com.adjectivemonk2.museum

import app.cash.redwood.layout.RedwoodLayout
import app.cash.redwood.lazylayout.RedwoodLazyLayout
import app.cash.redwood.schema.Children
import app.cash.redwood.schema.Default
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Schema
import app.cash.redwood.schema.Widget

@Schema(
  members = [
    Surface::class,
    Text::class,
    Image::class
  ],
  dependencies = [
    Schema.Dependency(1, RedwoodLayout::class),
    Schema.Dependency(2, RedwoodLazyLayout::class)
  ]
)
public interface Museum

@Widget(1)
public data class Surface(
  @Children(1) val children: () -> Unit,
)

@Widget(2)
public data class Text(
  @Property(1) val text: String,
  @Property(2) @Default("null") val onClick: (() -> Unit)? = null,
)

@Widget(3)
public data class Image(
  @Property(1) val url: String,
  @Property(2) @Default("null") val onClick: (() -> Unit)? = null,
)
