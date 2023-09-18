package com.adjectivemonk2.museum.presenter.gallery

public class AccountImageUrlGenerator {

  internal fun thumbnail(accountId: String): String {
    return """https://imgur.com/user/$accountId/avatar?maxwidth=290"""
  }
}
