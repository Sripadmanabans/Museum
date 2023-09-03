package com.adjectivemonk2.museum.ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.adjectivemonk2.museum.widget.Image
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiImage : Image<@Composable () -> Unit> {
  private var url by mutableStateOf("")
  private var onClick by mutableStateOf<(() -> Unit)?>(null)

  override fun url(url: String) {
    this.url = url
  }

  override fun onClick(onClick: (() -> Unit)?) {
    this.onClick = onClick
  }

  override var modifier: RedwoodModifier = RedwoodModifier

  override val value = @Composable {
    val modifier = onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier
    AsyncImage(model = url, contentDescription = null, modifier = modifier)
  }
}
