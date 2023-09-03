package com.adjectivemonk2.museum

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import app.cash.redwood.compose.AndroidUiDispatcher
import app.cash.redwood.layout.composeui.ComposeUiRedwoodLayoutWidgetFactory
import app.cash.redwood.lazylayout.composeui.ComposeUiRedwoodLazyLayoutWidgetFactory
import app.cash.redwood.treehouse.EventListener
import app.cash.redwood.treehouse.TreehouseApp
import app.cash.redwood.treehouse.TreehouseAppFactory
import app.cash.redwood.treehouse.TreehouseContentSource
import app.cash.redwood.treehouse.TreehouseView
import app.cash.redwood.treehouse.composeui.TreehouseContent
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.asZiplineHttpClient
import app.cash.zipline.loader.withDevelopmentServerPush
import com.adjectivemonk2.museum.launcher.MuseumAppSpec
import com.adjectivemonk2.museum.presenter.treehouse.gallery.GalleryPresenter
import com.adjectivemonk2.museum.ui.ComposeMuseumWidgetFactory
import com.adjectivemonk2.museum.ui.theme.MuseumTheme
import com.adjectivemonk2.museum.widget.MuseumProtocolNodeFactory
import com.adjectivemonk2.museum.widget.MuseumWidgetFactories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okio.FileSystem
import okio.Path.Companion.toOkioPath

public class MuseumActivity : ComponentActivity() {

  private val scope: CoroutineScope = CoroutineScope(AndroidUiDispatcher.Main)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val treehouseApp = createTreehouseApp()
    val treehouseContentSource = TreehouseContentSource(GalleryPresenter::launch)

    val widgetSystem = TreehouseView.WidgetSystem { json, protocolMismatchHandler ->
      MuseumProtocolNodeFactory(
        provider = MuseumWidgetFactories(
          Museum = ComposeMuseumWidgetFactory(),
          RedwoodLayout = ComposeUiRedwoodLayoutWidgetFactory(),
          RedwoodLazyLayout = ComposeUiRedwoodLazyLayoutWidgetFactory(),
        ),
        json = json,
        mismatchHandler = protocolMismatchHandler,
      )
    }

    val view = ComposeView(this)
    view.setContent {
      MuseumTheme {
        TreehouseContent(treehouseApp, widgetSystem, contentSource = treehouseContentSource)
      }
    }
    setContentView(view)
  }

  override fun onDestroy() {
    scope.cancel()
    super.onDestroy()
  }

  private fun createTreehouseApp(): TreehouseApp<GalleryPresenter> {
    val httpClient = OkHttpClient()
    val ziplineHttpClient = httpClient.asZiplineHttpClient()

    val treehouseAppFactory = TreehouseAppFactory(
      context = applicationContext,
      httpClient = httpClient,
      manifestVerifier = ManifestVerifier.NO_SIGNATURE_CHECKS,
      eventListener = appEventListener,
      stateStore = FileStateStore(
        json = Json,
        fileSystem = FileSystem.SYSTEM,
        directory = applicationContext.getDir("TreehouseState", MODE_PRIVATE).toOkioPath(),
      ),
    )

    val manifestUrlFlow = flowOf("http://10.0.2.2:8080/manifest.zipline.json")
      .withDevelopmentServerPush(ziplineHttpClient)

    val treehouseApp = treehouseAppFactory.create(
      appScope = scope,
      spec = MuseumAppSpec(manifestUrl = manifestUrlFlow),
    )

    treehouseApp.start()

    return treehouseApp
  }
}

private val appEventListener: EventListener = object : EventListener() {
  override fun codeLoadFailed(
    app: TreehouseApp<*>,
    manifestUrl: String?,
    exception: Exception,
    startValue: Any?,
  ) {
    Log.w("Treehouse", "codeLoadFailed", exception)
  }

  override fun codeLoadSuccess(
    app: TreehouseApp<*>,
    manifestUrl: String?,
    manifest: ZiplineManifest,
    zipline: Zipline,
    startValue: Any?,
  ) {
    Log.i("Treehouse", "codeLoadSuccess")
  }
}
