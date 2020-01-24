package com.example.demoone.repository

import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.source.UserManager

class MainRepository(val userManager: UserManager) {

  val progress = MutableLiveData<Boolean>(false)
}