package com.example.demoone.ui.home

import androidx.lifecycle.LiveData
import com.example.demoone.repository.MainRepository
import com.example.demoone.ui.base.BaseViewModel
import com.mutualmobile.praxis.injection.scope.ActivityScope
import javax.inject.Inject

@ActivityScope
class HomeViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var mainRepository: MainRepository

  val progress: LiveData<Boolean> by lazy { mainRepository.progress }

  fun isUserRegistered(): Boolean {
    return mainRepository.userManager.isUserRegistered()
  }

}