package com.example.demoone.data.source

import android.content.SharedPreferences
import com.example.demoone.data.model.User
import com.example.demoone.data.source.Result.Failure
import com.example.demoone.data.source.Result.Success

class UserManager constructor(private val sharedPreferences: SharedPreferences) {

  fun registerUser(user: User): Result<User> {
    if (user.userName.isEmpty() || user.password.isEmpty() || user.name.isEmpty())
        return Failure(
            Error("Invalid credentials")
        )
    else {
      Thread.sleep(3000)
      sharedPreferences.edit()
          .putString(User.USER_NAME, user.userName)
          .putString(User.NAME, user.name)
          .putString(User.PASSWORD, user.password)
          .apply()
        return Success(user)
    }
  }

  fun verifyLoginPassword(
    password: String
  ): Result<User> {
    Thread.sleep(2000)
    return if (password != sharedPreferences.getString(User.PASSWORD, ""))
        Failure(Error("Wrong Password"))
    else
        Success(User("", "", ""))
  }

  fun deleteUser(): Result<User> {
    Thread.sleep(2000)
    sharedPreferences.edit()
        .clear()
        .apply()
      return Success(User("", "", ""))
  }

  fun isUserRegistered(): Boolean {
      Thread.sleep(2000)
    return (sharedPreferences.contains(User.USER_NAME) && sharedPreferences.contains(User.PASSWORD))
  }

    fun getName(): String {
    return sharedPreferences.getString(User.NAME, "")!!
  }

}

sealed class Result<out T> {
  data class Success<T>(val body: T) : Result<T>()
  data class Failure<T>(val errorResponse: Error? = null) : Result<T>()
}