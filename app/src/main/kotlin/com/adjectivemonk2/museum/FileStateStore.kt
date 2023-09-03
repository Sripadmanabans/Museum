package com.adjectivemonk2.museum

import app.cash.redwood.treehouse.StateSnapshot
import app.cash.redwood.treehouse.StateStore
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.FileNotFoundException
import okio.FileSystem
import okio.Path

internal class FileStateStore(
  private val json: Json,
  private val fileSystem: FileSystem,
  private val directory: Path,
) : StateStore {
  init {
    fileSystem.createDirectories(directory)
    // TODO add a mechanism to delete files older than 24 hours (https://github.com/Sripadmanabans/Museum/issues/10)
  }

  override suspend fun put(id: String, value: StateSnapshot) {
    val path = directory / "$id.state"
    val tmpPath = directory / "$id.state.tmp"
    fileSystem.write(tmpPath) {
      writeUtf8(json.encodeToString(value))
    }
    fileSystem.atomicMove(tmpPath, path)
  }

  override suspend fun get(id: String): StateSnapshot? {
    return try {
      val path = directory / "$id.state"
      fileSystem.read(path) {
        json.decodeFromString<StateSnapshot>(readUtf8())
      }
    } catch (_: FileNotFoundException) {
      null
    }
  }
}
