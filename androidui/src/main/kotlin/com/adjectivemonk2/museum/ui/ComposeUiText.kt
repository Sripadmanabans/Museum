package com.adjectivemonk2.museum.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.adjectivemonk2.museum.widget.Text
import app.cash.redwood.Modifier as RedwoodModifier

internal class ComposeUiText : Text<@Composable () -> Unit> {

  private var text by mutableStateOf("")
  private var onClick by mutableStateOf<(() -> Unit)?>(null)

  override var modifier: RedwoodModifier = RedwoodModifier

  override val value = @Composable {
    val modifier = onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier
    Text(text = text, modifier = modifier, overflow = TextOverflow.Ellipsis)
  }

  override fun text(text: String) {
    this.text = text
  }

  override fun onClick(onClick: (() -> Unit)?) {
    this.onClick = onClick
  }
}
