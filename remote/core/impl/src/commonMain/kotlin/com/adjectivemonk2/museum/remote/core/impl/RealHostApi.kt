package com.adjectivemonk2.museum.remote.core.impl

import com.adjectivemonk2.museum.remote.core.HostApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

public class RealHostApi(private val httpClient: HttpClient) : HostApi {
  override suspend fun get(url: String): String {
    return httpClient.get(url).body()
  }
}
