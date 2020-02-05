package com.example.demoone.ui.home.musicPlayer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.example.demoone.R
import com.example.demoone.ui.home.HomeActivity
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_PAUSE
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_PLAY
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_SKIP_TO_NEXT
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_SKIP_TO_PREVIOUS
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.ACTION_STOP
import com.example.demoone.ui.home.musicPlayer.MusicPlayerService.Companion.STATE_PLAYING

const val NOW_PLAYING_CHANNEL = "NOW_PLAYING_CHANNEL"
const val NOW_PLAYING_NOTIFICATION = 12

class NotificationBuilder(private val context: Context) {

  private val platformNotificationManager: NotificationManager =
    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

  private val skipToPreviousAction = NotificationCompat.Action(
      R.drawable.ic_skip_previous_black_24dp,
      "Skip to Previous",
      getMusicPlayerServicePendingIntent(ACTION_SKIP_TO_PREVIOUS)
  )

  private val playAction = NotificationCompat.Action(
      R.drawable.ic_play_arrow_black_24dp,
      "Skip to Previous",
      getMusicPlayerServicePendingIntent(ACTION_PLAY)
  )

  private val pauseAction = NotificationCompat.Action(
      R.drawable.ic_pause_black_24dp,
      "Skip to Previous",
      getMusicPlayerServicePendingIntent(ACTION_PAUSE)
  )

  private val skipToNextAction = NotificationCompat.Action(
      R.drawable.ic_skip_next_black_24dp,
      "Skip to Previous",
      getMusicPlayerServicePendingIntent(ACTION_SKIP_TO_NEXT)
  )

  private val stopPendingIntent = getMusicPlayerServicePendingIntent(ACTION_STOP)

  fun buildNotification(
    title: String?,
    artist: String?,
    playbackState: Boolean
  ): Notification {
    if (shouldCreateNowPlayingChannel())
      createNowPlayingChannel()

    val clickIntent = Intent(context, HomeActivity::class.java)
    val clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0)

    val builder = NotificationCompat.Builder(context, NOW_PLAYING_CHANNEL)
    builder.addAction(skipToPreviousAction)

    if (playbackState == STATE_PLAYING)
      builder.addAction(pauseAction)
    else
      builder.addAction(playAction)

    builder.addAction(skipToNextAction)

    val mediaStyle = MediaStyle()
        .setCancelButtonIntent(stopPendingIntent)
        .setShowCancelButton(true)
        .setShowActionsInCompactView(1, 2)


    return builder
        .setContentTitle(title)
        .setContentText(artist)
        .setDeleteIntent(stopPendingIntent)
        .setOnlyAlertOnce(true)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentIntent(clickPendingIntent)
        .setStyle(mediaStyle)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .build()
  }

  private fun getMusicPlayerServicePendingIntent(intentAction: String): PendingIntent {
    return PendingIntent.getService(
        context, 0,
        Intent(context, MusicPlayerService::class.java).apply { action = intentAction }, 0
    )
  }

  private fun shouldCreateNowPlayingChannel() =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowPlayingChannelExists()

  @RequiresApi(VERSION_CODES.O)
  private fun nowPlayingChannelExists() =
    platformNotificationManager.getNotificationChannel(NOW_PLAYING_CHANNEL) != null

  @RequiresApi(VERSION_CODES.O)
  private fun createNowPlayingChannel() {
    val notificationChannel = NotificationChannel(
        NOW_PLAYING_CHANNEL, "Now playing", NotificationManager.IMPORTANCE_LOW
    ).apply {
      description = "Show currently playing music"
    }
    platformNotificationManager.createNotificationChannel(notificationChannel)
  }

}