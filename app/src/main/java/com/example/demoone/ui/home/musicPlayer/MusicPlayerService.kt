package com.example.demoone.ui.home.musicPlayer

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.example.demoone.data.model.Media
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player.EventListener
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit.SECONDS

class MusicPlayerService : Service() {
  companion object {
    const val MEDIA = "media"
    const val ACTION_ADD_MEDIA_TO_PLAYLIST = "addMediaToPlaylist"
    const val ACTION_PLAY_MEDIA = "addPlayMedia"
    const val ACTION_PLAY = "play"
    const val ACTION_PAUSE = "pause"
    const val ACTION_SKIP_TO_PREVIOUS = "skipToPrevious"
    const val ACTION_SKIP_TO_NEXT = "skipToNext"
    const val ACTION_STOP = "stop"
    const val STATE_PLAYING = true
    const val STATE_PAUSED = false
  }

  private val player: SimpleExoPlayer by lazy {
    ExoPlayerFactory.newSimpleInstance(this)
  }

  var playWhenReady = true

  private var currentWindow = 0
  private var playbackPosition: Long = 0

  private val mediaSource = ConcatenatingMediaSource()

  private lateinit var notificationBuilder: NotificationBuilder
  private lateinit var notificationManager: NotificationManagerCompat

  val playlist = ArrayList<Media>()

  val playbackPositionObservable: Observable<Int> = Observable.interval(1, SECONDS)
      .observeOn(AndroidSchedulers.mainThread())
      .map {
        playbackPosition = player.currentPosition

        ((playbackPosition * 100) / player.duration).toInt()
      }

  private val binder = MusicPlayerServiceBinder()
  override fun onBind(p0: Intent?): IBinder? {
    return binder
  }

  private var currentTitle: String = ""
  private var currentArtist: String = ""

  override fun onCreate() {
    super.onCreate()
    notificationBuilder = NotificationBuilder(this)
    notificationManager = NotificationManagerCompat.from(this)
    initializePlayer()
  }

  override fun onStartCommand(
    intent: Intent?,
    flags: Int,
    startId: Int
  ): Int {
    intent?.let {
      when (it.action) {
        ACTION_PLAY_MEDIA -> {
        }
        ACTION_ADD_MEDIA_TO_PLAYLIST -> {
          addToMediaSource(it.getSerializableExtra(MEDIA) as Media)
        }
        ACTION_PLAY -> play()
        ACTION_PAUSE -> pause()
        ACTION_SKIP_TO_PREVIOUS -> skipToPrevious()
        ACTION_SKIP_TO_NEXT -> skipToNext()
        ACTION_STOP -> stop()
      }
    }

    return START_STICKY
  }

  private fun buildNotification(): Notification {
    currentTitle = playlist[currentWindow].title!!
    currentArtist = playlist[currentWindow].artist!!
    return notificationBuilder.buildNotification(currentTitle, currentArtist, playWhenReady)
  }

  private fun startForegroundAndNotify() {
    val notification = buildNotification()
    startForeground(NOW_PLAYING_NOTIFICATION, notification)

  }

  private fun stopForegroundAndNotify() {
    startForeground(NOW_PLAYING_NOTIFICATION, buildNotification())
    stopForeground(false)
  }

  private fun buildMediaSource(uri: Uri): MediaSource {
    val dataSourceFactory = DefaultDataSourceFactory(this, "GWE")
    return ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri)
  }

  private fun addToMediaSource(media: Media?) {
    media?.let {
      mediaSource.addMediaSource(buildMediaSource(it.uriString.toUri()))
      playlist.add(it)
    }
  }

  fun seekToPosition(progress: Int) {
    val newPlayBackPosition = player.duration / 100 * progress
    player.seekTo(newPlayBackPosition)
  }

  fun play() {
    player.playWhenReady = true
  }

  fun pause() {
    player.playWhenReady = false
  }

  fun skipToNext() {
    if (player.hasNext()) {
      player.next()
      currentWindow++
    }

  }

  fun skipToPrevious() {
    if (player.hasPrevious()) {
      player.previous()
      currentWindow--
    }
  }

  fun stop() {
    stopSelf()
  }

  private fun initializePlayer() {
    player.playWhenReady = playWhenReady
    player.seekTo(currentWindow, playbackPosition)
    player.prepare(mediaSource, false, false)
    player.addListener(object : EventListener {
      override fun onPlayerStateChanged(
        playWhenReady: Boolean,
        playbackState: Int
      ) {
        this@MusicPlayerService.playWhenReady = playWhenReady
        if (playlist.isNotEmpty()) {
          if (playWhenReady)
            startForegroundAndNotify()
          else
            stopForegroundAndNotify()
        }
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    releasePlayer()
    stopForeground(true)
  }

  private fun releasePlayer() {
    playWhenReady = player.playWhenReady
    playbackPosition = player.currentPosition
    currentWindow = player.currentWindowIndex
    player.release()
  }

  inner class MusicPlayerServiceBinder : Binder() {
    fun getService(): MusicPlayerService = this@MusicPlayerService
  }
}