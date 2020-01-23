package com.example.demoone.ui.home.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.User
import com.example.demoone.repository.MainRepository
import com.example.demoone.repository.Result
import com.example.demoone.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var mainRepository: MainRepository

  val user = User("", "", "")

  private val registrationState = MutableLiveData<Result<User>>()

  fun getRegistrationState(): LiveData<Result<User>> {
    return registrationState
  }

  fun registerUser() {
    CoroutineScope(Dispatchers.IO).launch {
      mainRepository.progress.postValue(true)
      val result = mainRepository.userManager.registerUser(user)
      mainRepository.progress.postValue(false)
      registrationState.postValue(result)
    }
  }

}