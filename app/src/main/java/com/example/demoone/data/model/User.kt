package com.example.demoone.data.model

data class User(
  var userName: String,
  var name: String,
  var password: String
) {
  companion object {
    const val USER_NAME = "USER_NAME"
    const val NAME = "NAME"
    const val PASSWORD = "PASSWORD"
  }
}