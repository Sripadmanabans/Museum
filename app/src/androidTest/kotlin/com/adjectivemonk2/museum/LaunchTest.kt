package com.adjectivemonk2.museum

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.adjectivemonk2.museum.ui.theme.MuseumTheme
import de.mannodermaus.junit5.compose.createComposeExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class LaunchTest {

  @Suppress("JUnitMalformedDeclaration")
  @JvmField
  @RegisterExtension
  @OptIn(ExperimentalTestApi::class)
  internal val extension = createComposeExtension()

  @Test
  fun testingLaunchLightTheme() {
    extension.use {
      setContent {
        MuseumTheme {
          Greeting("Android")
        }
      }

      onNodeWithText("Hello Android!").assertIsDisplayed()
    }
  }

  @Test
  fun testingLaunchDarkTheme() {
    extension.use {
      setContent {
        MuseumTheme(true) {
          Greeting("Android")
        }
      }

      onNodeWithText("Hello Android!").assertIsDisplayed()
    }
  }
}