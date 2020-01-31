package com.example.demoone.ui.home.musicPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.Media
import com.example.demoone.ui.base.BaseViewModel
import javax.inject.Inject

class MusicViewModel @Inject constructor() : BaseViewModel() {

  private val mediaList = MutableLiveData<ArrayList<Media>>(
      arrayListOf(
          Media("Media1", ""),
          Media("Media2", ""),
          Media("Media3", ""),
          Media("Media4", ""),
          Media("Media5", ""),
          Media("Media6", ""),
          Media("Media7", ""),
          Media("Media8", ""),
          Media("Media9", ""),
          Media("Media10", ""),
          Media("Media11", ""),
          Media("Media12", ""),
          Media("Media13", ""),
          Media("Media14", ""),
          Media("Media15", ""),
          Media("Media16", ""),
          Media("Media17", ""),
          Media("Media18", ""),
          Media("Media19", "")
      )
  )

  fun getMediaList(): LiveData<ArrayList<Media>> {
    return mediaList
  }

  fun clearMediaList() {
    mediaList.postValue(arrayListOf())
  }

  fun addMediaList(newMediaList: ArrayList<Media>) {
    val currentMediaList = mediaList.value
    currentMediaList!!.addAll(newMediaList)
    mediaList.postValue(currentMediaList)
  }

}