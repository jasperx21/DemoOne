package com.example.demoone.ui.home.musicPlayer

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import com.example.demoone.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MusicPlayerService : Service() {
  companion object {
    private const val PLAYBACK_CHANNEL_ID = "playback_channel"
    private const val PLAYBACK_NOTIFICATION_ID = 1
  }

  private lateinit var player: SimpleExoPlayer

  private var playWhenReady = true
  private var currentWindow = 0
  private var playbackPosition: Long = 0
  override fun onBind(p0: Intent?): IBinder? {
    return null
  }

  override fun onCreate() {
    super.onCreate()
    player = ExoPlayerFactory.newSimpleInstance(this)
    initializePlayer()
  }

  private fun buildMediaSource(uri: Uri): MediaSource {
    val dataSourceFactory = DefaultDataSourceFactory(this, "GWE")
    return ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri)
  }

  private fun initializePlayer() {

    val uri = Uri.parse(getString(R.string.media_url_mp3));
    val mediaSource = buildMediaSource(uri)
  }

  private fun releasePlayer() {

    if (player != null) {
      playWhenReady = player.getPlayWhenReady()
      playbackPosition = player.getCurrentPosition()
      currentWindow = player.getCurrentWindowIndex()
      player.release()
    }
  }
}