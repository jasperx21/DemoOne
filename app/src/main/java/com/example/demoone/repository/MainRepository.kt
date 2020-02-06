package com.example.demoone.repository

import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.source.UserManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
  val userManager: UserManager,
  val wikiSearchRepository: WikiSearchRepository
) {

  val progress = MutableLiveData<Boolean>(false)
}