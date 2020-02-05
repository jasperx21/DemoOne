package com.example.demoone.ui.home.musicPlayer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.R
import com.example.demoone.data.model.Media
import com.example.demoone.databinding.FragmentMusicBinding
import com.example.demoone.ui.base.BaseFragment
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.HomeViewModel
import com.example.demoone.ui.home.musicPlayer.MediaRecyclerAdapter.OnMediaActionListener
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_ADD_MEDIA_TO_PLAYLIST
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.MEDIA
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.File
import javax.inject.Inject

class MusicFragment : BaseFragment<FragmentMusicBinding, MusicViewModel, HomeViewModel>() {
  override fun getViewModelClass(): Class<MusicViewModel> = MusicViewModel::class.java

  override fun getActivityViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

  override fun getActivityViewModelOwner(): ViewModelStoreOwner = activity as HomeActivity

  override fun getLayout(): Int = R.layout.fragment_music

  private var compositeDisposable: CompositeDisposable? = null

  @Inject
  lateinit var rxPermissions: RxPermissions

  companion object {
    const val AUDIO_REQUEST_CODE = 10
  }

  private lateinit var musicPlayerService: MusicPlayerService
  private var isBound: Boolean = false

  private val connection = object : ServiceConnection {
    override fun onServiceDisconnected(name: ComponentName?) {
      isBound = false
    }

    override fun onServiceConnected(
      name: ComponentName?,
      service: IBinder?
    ) {
      val binder = service as MusicPlayerService.MusicPlayerServiceBinder
      musicPlayerService = binder.getService()
      isBound = true
      onServiceConnected()
      Log.d("myTag", "here1")
    }
  }

  private fun onServiceConnected() {
    addDisposable(musicPlayerService.playbackPositionObservable
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
          binding.seekBar.progress = it
          Log.d("myTag", "here$it")
        }
    )
    binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
      ) {
        musicPlayerService.seekToPosition(progress)
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}
      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })


    binding.buttonNext.setOnClickListener {
      musicPlayerService.skipToNext()
    }
    binding.buttonPause.setOnClickListener {
      musicPlayerService.pause()
    }
    binding.buttonPrev.setOnClickListener {
      musicPlayerService.skipToPrevious()
    }
  }

  @SuppressLint("CheckResult")
  override fun onStart() {
    super.onStart()
    initializeRecyclerView()
    addOnBackPressedCallback()
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
  }

  private fun initializeRecyclerView() {
    val adapter = MediaRecyclerAdapter()
    adapter.setOnMediaActionListener(object : OnMediaActionListener {
      override fun onMediaClick(media: Media) {
        playMedia(media)
      }
    })

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
          mediaList.add(getMedia(mediaUri))
        }
        viewModel.addMediaList(mediaList)
      }
    }
  }

  fun playMedia(media: Media) {
    val intent = Intent(context, MusicPlayerService::class.java)
    intent.action = ACTION_ADD_MEDIA_TO_PLAYLIST
    intent.putExtra(MEDIA, media)
    context!!.startService(intent)
    context!!.bindService(intent, connection, Context.BIND_AUTO_CREATE)
  }

  private fun getMedia(mediaUri: Uri): Media {
    val file = File(mediaUri.path!!)
    val title: String? = file.name
    val artist: String? = file.extension
    val albumArt: String? = file.absolutePath
    return Media(title, artist, mediaUri.toString(), albumArt)
  }

  override fun onStop() {
    super.onStop()
    compositeDisposable?.let {
      it.dispose()
      it.clear()
      compositeDisposable = null
    }
  }

  private fun addDisposable(disposable: Disposable) {
    if (compositeDisposable == null)
      compositeDisposable = CompositeDisposable()
    compositeDisposable!!.add(disposable)
  }

  private fun addOnBackPressedCallback() {
    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if (binding.rootLayout.currentState == binding.rootLayout.endState)
          binding.rootLayout.transitionToStart()
        else
          findNavController().popBackStack()
      }
    }
    (activity as HomeActivity).onBackPressedDispatcher.addCallback(this, callback)
  }
}