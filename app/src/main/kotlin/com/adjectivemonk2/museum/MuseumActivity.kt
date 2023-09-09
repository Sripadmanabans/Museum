package com.adjectivemonk2.museum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.ui.Modifier
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
import com.adjectivemonk2.museum.remote.core.impl.RealHostApi
import com.adjectivemonk2.museum.ui.ComposeMuseumWidgetFactory
import com.adjectivemonk2.museum.ui.theme.MuseumTheme
import com.adjectivemonk2.museum.widget.MuseumProtocolNodeFactory
import com.adjectivemonk2.museum.widget.MuseumWidgetFactories
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import co.touchlab.kermit.Logger.Companion as KermitLogger

@NoLiveLiterals
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

    setContent {
      MuseumTheme {
        Box(modifier = Modifier.safeDrawingPadding()) {
          TreehouseContent(treehouseApp, widgetSystem, contentSource = treehouseContentSource)
        }
      }
    }
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

    val hostApi = RealHostApi(httpClient())

    val treehouseApp = treehouseAppFactory.create(
      appScope = scope,
      spec = MuseumAppSpec(manifestUrl = manifestUrlFlow, hostApi = hostApi),
    )

    treehouseApp.start()

    return treehouseApp
  }

  private fun httpClient(): HttpClient {
    return HttpClient(CIO) {
      install(HttpCache) {
        publicStorage(FileStorage(cacheDir))
      }

      install(Logging) {
        logger = object : Logger {
          private val _logger by lazy(LazyThreadSafetyMode.NONE) {
            KermitLogger.withTag("HTTP")
          }

          override fun log(message: String) {
            _logger.v { message }
          }
        }
        level = LogLevel.BODY
      }

      install(ContentNegotiation) {
        json(Json)
      }

      defaultRequest {
        url("https://api.imgur.com/3/")
        header("Authorization", "Client-ID ${BuildConfig.MUSEUM_CLIENT_ID}")
      }
    }
  }
}

private val appEventListener: EventListener = object : EventListener() {
  override fun codeLoadFailed(
    app: TreehouseApp<*>,
    manifestUrl: String?,
    exception: Exception,
    startValue: Any?,
  ) {
    KermitLogger.w(exception) { "codeLoadFailed" }
  }

  override fun codeLoadSuccess(
    app: TreehouseApp<*>,
    manifestUrl: String?,
    manifest: ZiplineManifest,
    zipline: Zipline,
    startValue: Any?,
  ) {
    KermitLogger.i { "codeLoadSuccess" }
  }
}
