package com.example.demoone.repository

import android.content.SharedPreferences
import com.example.demoone.data.model.User

class UserManager constructor(private val sharedPreferences: SharedPreferences) {

  fun registerUser(user: User): Result<User> {
    if (user.userName.isEmpty() || user.password.isEmpty() || user.name.isEmpty())
      return Result.Failure(Error("Invalid credentials"))
    else {
      Thread.sleep(3000)
      sharedPreferences.edit()
          .putString(User.USER_NAME, user.userName)
          .putString(User.NAME, user.name)
          .putString(User.PASSWORD, user.password)
          .apply()
      return Result.Success(user)
    }
  }

  fun verifyLoginPassword(
    password: String
  ): Result<User> {
    Thread.sleep(2000)
    return if (password != sharedPreferences.getString(User.PASSWORD, ""))
      Result.Failure(Error("Wrong Password"))
    else
      Result.Success(User("", "", ""))
  }

  fun deleteUser(): Result<User> {
    Thread.sleep(2000)
    sharedPreferences.edit()
        .clear()
        .apply()
    return Result.Success(User("", "", ""))
  }

  fun isUserRegistered(): Boolean {
    return (sharedPreferences.contains(User.USER_NAME) && sharedPreferences.contains(User.PASSWORD))
  }

  private fun getUserName(): String {
    return sharedPreferences.getString(User.NAME, "")!!
  }

}

sealed class Result<out T> {
  data class Success<T>(val body: T) : Result<T>()
  data class Failure<T>(val errorResponse: Error? = null) : Result<T>()
}