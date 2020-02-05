package com.example.demoone.ui.home.musicPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demoone.data.model.Media
import com.example.demoone.ui.base.BaseViewModel
import javax.inject.Inject

class MusicViewModel @Inject constructor() : BaseViewModel() {

  private val mediaList = MutableLiveData<ArrayList<Media>>(
      arrayListOf()
  )

  private val playlist = MutableLiveData<ArrayList<Media>>()

  private val currentMedia = MutableLiveData<Media>()

  fun getCurrentMedia(): LiveData<Media> {
    return currentMedia
  }

  fun getMediaFromPlaylistAtIndex(int: Int): Media {
    return playlist.value?.get(int)!!
  }

  fun addMediaToPlaylist(media: Media) {
    var temp = playlist.value
    if (temp == null)
      temp = arrayListOf(media)
    else
      temp.add(media)
    playlist.postValue(temp)
  }

  fun setCurrentMedia(media: Media) {
    currentMedia.postValue(media)
  }

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