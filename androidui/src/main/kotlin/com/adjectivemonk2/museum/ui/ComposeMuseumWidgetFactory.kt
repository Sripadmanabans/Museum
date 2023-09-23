package com.adjectivemonk2.museum.ui

import androidx.compose.runtime.Composable
import com.adjectivemonk2.museum.widget.Image
import com.adjectivemonk2.museum.widget.MuseumWidgetFactory
import com.adjectivemonk2.museum.widget.Surface
import com.adjectivemonk2.museum.widget.Text

public class ComposeMuseumWidgetFactory : MuseumWidgetFactory<@Composable () -> Unit> {

  override fun Surface(): Surface<@Composable () -> Unit> = ComposeUiSurface()

  override fun Text(): Text<@Composable () -> Unit> = ComposeUiText()

  override fun Image(): Image<@Composable () -> Unit> = ComposeUiImage()
}
