package com.example.demoone.ui.home

import androidx.lifecycle.LiveData
import com.example.demoone.injection.scope.ActivityScope
import com.example.demoone.repository.MainRepository
import com.example.demoone.ui.base.BaseViewModel
import javax.inject.Inject

@ActivityScope
class HomeViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var mainRepository: MainRepository

  val progress: LiveData<Boolean> by lazy { mainRepository.progress }

}