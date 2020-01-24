package com.example.demoone.ui.home.splash

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.User
import com.example.demoone.repository.MainRepository
import com.example.demoone.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var mainRepository: MainRepository

    private val isUserRegistered = MutableLiveData<Boolean>()

    fun getIsUserRegistered(): LiveData<Boolean> {
        return isUserRegistered
    }

    fun isUserRegistered() {
        CoroutineScope(Dispatchers.IO).launch {
            isUserRegistered.postValue(mainRepository.userManager.isUserRegistered())
        }
    }

    fun createArgumentsFromSplashToLogin(): Bundle {
        return Bundle().apply { putString(User.NAME, mainRepository.userManager.getName()) }
    }
}