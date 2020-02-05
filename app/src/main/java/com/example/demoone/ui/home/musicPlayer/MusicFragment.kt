package com.example.demoone.ui.home.musicPlayer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.R
import com.example.demoone.data.model.Media
import com.example.demoone.databinding.FragmentMusicBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

class MusicFragment : BaseFragment<FragmentMusicBinding, MusicViewModel, HomeViewModel>() {
  override fun getViewModelClass(): Class<MusicViewModel> = MusicViewModel::class.java

  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getActivityViewModelOwner(): ViewModelStoreOwner = activity as HomeActivity

  override fun getLayout(): Int = R.layout.fragment_music

  @Inject
  lateinit var rxPermissions: RxPermissions

  companion object {
    const val AUDIO_REQUEST_CODE = 10
  }

  @SuppressLint("CheckResult")
  override fun onStart() {
    super.onStart()
    initializeRecyclerView()
    if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
      rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
          .subscribe { granted ->
            if (granted)
              addClickListeners()
          }
    }
  }

  private fun addClickListeners() {
    binding.buttonAddMusic.setOnClickListener {
      val intentGetAudioFiles = Intent()
      intentGetAudioFiles.type = "audio/*"
      intentGetAudioFiles.action = Intent.ACTION_GET_CONTENT
      if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2) {
        intentGetAudioFiles.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
      }
      startActivityForResult(intentGetAudioFiles, AUDIO_REQUEST_CODE)
    }
    binding.buttonClearAll.setOnClickListener {
      viewModel.clearMediaList()
    }
    binding.buttonNext.setOnClickListener { }
    binding.buttonPause.setOnClickListener { }
    binding.buttonPrev.setOnClickListener { }
  }

  private fun initializeRecyclerView() {
    val adapter = MediaRecyclerAdapter()
    binding.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    binding.recyclerView.adapter = adapter

    viewModel.getMediaList()
        .observe(this, Observer {
          adapter.setMediaList(it)
        })
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == AUDIO_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
      if (data.clipData != null) {
        val count = data.clipData!!.itemCount
        val mediaList = arrayListOf<Media>()
        for (i in 0 until count) {
          val mediaUri: Uri = data.clipData!!.getItemAt(i)
              .uri
          mediaList.add(Media(mediaUri.toString(), mediaUri.toString()))
        }
        viewModel.addMediaList(mediaList)
      }
    }
  }

}