package com.example.demoone.ui.home.musicPlayer

import android.app.PendingIntent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaBrowserServiceCompat
import com.example.demoone.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MusicPlayerService : MediaBrowserServiceCompat() {

  private lateinit var notificationBuilder: NotificationBuilder
  private lateinit var mediaSession: MediaSessionCompat
  private lateinit var notificationManager: NotificationManagerCompat
  private lateinit var mediaController: MediaControllerCompat
  private lateinit var mediaSessionConnector: MediaSessionConnector

  private val playerAudioAttributes = AudioAttributes.Builder()
      .setContentType(C.CONTENT_TYPE_MUSIC)
      .setUsage(C.USAGE_MEDIA)
      .build()

  private val player: SimpleExoPlayer by lazy {
    ExoPlayerFactory.newSimpleInstance(this)
        .apply { setAudioAttributes(playerAudioAttributes, true) }
  }

  private var presentPlayingMetadata: MediaMetadataCompat? = null
  private var isForegroundService = false
  private lateinit var dataSourceFactory: DefaultDataSourceFactory

  private val musicSource = ArrayList<MediaMetadataCompat>()

  override fun onCreate() {
    super.onCreate()
    notificationBuilder = NotificationBuilder(this)
    dataSourceFactory = DefaultDataSourceFactory(this, "UAS")

    val sessionActivityPendingIntent =
      packageManager?.getLaunchIntentForPackage(packageName)
          ?.let { sessionIntent ->
            PendingIntent.getActivity(this, 0, sessionIntent, 0)
          }

    mediaSession = MediaSessionCompat(this, "MusicService")
        .apply {
          setSessionActivity(sessionActivityPendingIntent)
          isActive = true
          setMediaButtonReceiver(null)
        }

    sessionToken = mediaSession.sessionToken

    mediaController = MediaControllerCompat(this, mediaSession).apply {
      registerCallback(MediaControllerCallback())
    }

    notificationBuilder = NotificationBuilder(this)
    notificationManager = NotificationManagerCompat.from(this)

    mediaSessionConnector = MediaSessionConnector(mediaSession).also { connector ->
      val playbackPreparer = object : MediaSessionConnector.PlaybackPreparer {
        override fun onPrepareFromSearch(
          query: String?,
          playWhenReady: Boolean,
          extras: Bundle?
        ) = Unit

        override fun onCommand(
          player: Player?,
          controlDispatcher: ControlDispatcher?,
          command: String?,
          extras: Bundle?,
          cb: ResultReceiver?
        ): Boolean = false

        override fun getSupportedPrepareActions(): Long =
          PlaybackStateCompat.ACTION_PREPARE_FROM_URI

        override fun onPrepareFromMediaId(
          mediaId: String?,
          playWhenReady: Boolean,
          extras: Bundle?
        ) = Unit

        override fun onPrepareFromUri(
          uri: Uri?,
          playWhenReady: Boolean,
          extras: Bundle?
        ) {
          var itemToPlay: MediaMetadataCompat? = null
          for (media in musicSource) {
            if (media.mediaUri == uri) {
              itemToPlay = media
              break
            }
          }
          if (itemToPlay != null) {
            val metadataList = buildPlaylist(itemToPlay)
            val mediaSource = metadataList.toMediaSource(dataSourceFactory)
            player.prepare(mediaSource)
          }
        }

        override fun onPrepare(playWhenReady: Boolean) = Unit

        private fun buildPlaylist(item: MediaMetadataCompat): List<MediaMetadataCompat> =
          musicSource.filter { it.mediaUri == item.mediaUri }

        private fun buildMediaSource(uri: Uri): MediaSource {
          return ProgressiveMediaSource.Factory(dataSourceFactory)
              .createMediaSource(uri)
        }

      }
      connector.setPlayer(player)
      connector.setPlaybackPreparer(playbackPreparer)
      connector.setQueueNavigator(
          object : TimelineQueueNavigator(mediaSession) {
            private val window = Timeline.Window()
            override fun getMediaDescription(
              player: Player,
              windowIndex: Int
            ): MediaDescriptionCompat =
              player.currentTimeline.getWindow(
                  windowIndex,
                  window
              ).tag as MediaDescriptionCompat
          })
    }
  }

  override fun onLoadChildren(
    parentId: String,
    result: Result<MutableList<MediaItem>>
  ) {
    result.sendResult(ArrayList())
  }

  override fun onGetRoot(
    clientPackageName: String,
    clientUid: Int,
    rootHints: Bundle?
  ): BrowserRoot? {
    return BrowserRoot(getString(R.string.app_name), null)
  }

  private fun buildMediaSource(uri: Uri): MediaSource {
    val dataSourceFactory = DefaultDataSourceFactory(this, "GWE")
    return ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(uri)
  }

  private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
      presentPlayingMetadata = metadata
      mediaController.playbackState?.let { updateNotification(it) }
    }

    private fun updateNotification(playbackState: PlaybackStateCompat) {
      val updatedState = playbackState.state
      val notification = if (mediaController.metadata != null
          && updatedState != PlaybackStateCompat.STATE_NONE
      ) {
        notificationBuilder.buildNotification(mediaSession.sessionToken)
      } else {
        null
      }
      when (updatedState) {
        PlaybackStateCompat.STATE_BUFFERING,
        PlaybackStateCompat.STATE_PLAYING -> {
          if (notification != null) {
            notificationManager.notify(NOW_PLAYING_NOTIFICATION, notification)
            if (!isForegroundService) {
              startForeground(NOW_PLAYING_NOTIFICATION, notification)
              isForegroundService = true
            }
          }
        }
        else -> {
          if (isForegroundService) {
            stopForeground(false)
            isForegroundService = false

            if (updatedState == PlaybackStateCompat.STATE_NONE) {
              stopSelf()
            }

            if (notification != null)
              notificationManager.notify(NOW_PLAYING_NOTIFICATION, notification)
            else
              stopForeground(true)
          }
        }
      }
    }
  }
}