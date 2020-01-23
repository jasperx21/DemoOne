package com.example.demoone.repository

import androidx.lifecycle.MutableLiveData

class MainRepository(val userManager: UserManager) {

  val progress = MutableLiveData<Boolean>(false)
}