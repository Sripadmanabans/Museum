package com.adjectivemonk2.museum.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import app.cash.redwood.Modifier
import app.cash.redwood.widget.compose.ComposeWidgetChildren
import com.adjectivemonk2.museum.widget.Surface

internal class ComposeUiSurface : Surface<@Composable () -> Unit> {

  override val children = ComposeWidgetChildren()

  override var modifier: Modifier = Modifier

  override val value: @Composable () -> Unit = {
    Surface {
      children.render()
    }
  }
}
