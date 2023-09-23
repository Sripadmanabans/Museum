package com.adjectivemonk2.museum.presenter.gallery

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

internal class AccountImageUrlGeneratorTest {

  @Test
  fun account_thumbnail_url_generator_test() {
    val id = "account id"
    val actual = AccountImageUrlGenerator().thumbnail(id)
    val expected = """https://imgur.com/user/$id/avatar?maxwidth=290"""
    assertThat(actual).isEqualTo(expected)
  }
}
