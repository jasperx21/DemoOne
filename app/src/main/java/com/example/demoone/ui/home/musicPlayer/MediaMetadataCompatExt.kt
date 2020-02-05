package com.example.demoone.ui.home.musicPlayer

import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource

inline val MediaMetadataCompat.id: String
  get() = getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)

inline val MediaMetadataCompat.title: String?
  get() = getString(MediaMetadataCompat.METADATA_KEY_TITLE)

inline val MediaMetadataCompat.artist: String?
  get() = getString(MediaMetadataCompat.METADATA_KEY_ARTIST)

inline val MediaMetadataCompat.album: String?
  get() = getString(MediaMetadataCompat.METADATA_KEY_ALBUM)

inline val MediaMetadataCompat.duration: Long
  get() = getLong(MediaMetadataCompat.METADATA_KEY_DURATION)

inline val MediaMetadataCompat.albumArtUri: Uri
  get() = this.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI).toUri()

inline val MediaMetadataCompat.mediaUri: Uri
  get() = this.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri()

const val NO_GET = "Property does not have a 'get'"

inline var MediaMetadataCompat.Builder.id: String
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, value)
  }

inline var MediaMetadataCompat.Builder.title: String?
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_TITLE, value)
  }

inline var MediaMetadataCompat.Builder.artist: String?
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_ARTIST, value)
  }

inline var MediaMetadataCompat.Builder.album: String?
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_ALBUM, value)
  }

inline var MediaMetadataCompat.Builder.duration: Long
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putLong(MediaMetadataCompat.METADATA_KEY_DURATION, value)
  }

inline var MediaMetadataCompat.Builder.mediaUri: String?
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, value)
  }

inline var MediaMetadataCompat.Builder.albumArtUri: String?
  @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
  get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
  set(value) {
    putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, value)
  }

inline val MediaMetadataCompat.fullDescription
  get() =
    description.also {
      it.extras?.putAll(bundle)
    }

fun MediaMetadataCompat.toMediaSource(dataSourceFactory: DataSource.Factory) =
  ProgressiveMediaSource.Factory(dataSourceFactory)
      .setTag(fullDescription)
      .createMediaSource(mediaUri)

fun List<MediaMetadataCompat>.toMediaSource(
  dataSourceFactory: DataSource.Factory
): ConcatenatingMediaSource {

  val concatenatingMediaSource = ConcatenatingMediaSource()
  forEach {
    concatenatingMediaSource.addMediaSource(it.toMediaSource(dataSourceFactory))
  }
  return concatenatingMediaSource
}