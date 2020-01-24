package com.example.demoone.ui.home.login

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.User
import com.example.demoone.data.source.Result
import com.example.demoone.repository.MainRepository
import com.example.demoone.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var mainRepository: MainRepository

  var password = ""

  private val loginState = MutableLiveData<Result<User>>()
  private val deleteUserState = MutableLiveData<Result<User>>()

  var greeting = ""

  fun getLoginState(): LiveData<Result<User>> {
    return loginState
  }

  fun getDeleteUserState(): LiveData<Result<User>> {
    return deleteUserState
  }

  fun verifyLogin() {
    CoroutineScope(Dispatchers.IO).launch {
      mainRepository.progress.postValue(true)
      val result = mainRepository.userManager.verifyLoginPassword(password)
      mainRepository.progress.postValue(false)
      loginState.postValue(result)
    }
  }

  fun loadArguments(arguments: Bundle?) {
    arguments?.let {
      greeting = "Welcome back, ${it.getString(User.NAME, "")}"
    }
  }

  fun deleteUser() {
    mainRepository.progress.value = true
    CoroutineScope(Dispatchers.IO).launch {
      val result = mainRepository.userManager.deleteUser()
      mainRepository.progress.postValue(false)
      deleteUserState.postValue(result)
    }
  }

}