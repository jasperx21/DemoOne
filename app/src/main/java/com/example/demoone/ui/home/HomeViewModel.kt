package com.example.demoone.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.User
import com.example.demoone.repository.Result
import com.example.demoone.repository.UserManager
import com.example.demoone.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var userManager: UserManager

  var user = User("", "", "")
  var loading = MutableLiveData<Boolean>(false)

  fun isUserRegistered(): Boolean {
    return userManager.isUserRegistered()
  }

  fun registerUser() {
    loading.value = true
    CoroutineScope(Dispatchers.IO).launch {
      val result = userManager.registerUser(user)
      loading.postValue(false)
      Timber.d(result.toString())
      if (result is Result.Failure)
        throw Exception(result.errorResponse)
      else if (result is Result.Success)
        user = result.body
    }
  }

  fun deleteUser() {
    userManager.deleteUser()
  }

}