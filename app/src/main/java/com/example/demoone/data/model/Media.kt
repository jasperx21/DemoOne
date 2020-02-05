package com.example.demoone.data.model

import java.io.Serializable

data class Media(
  val title: String?,
  val artist: String?,
  val uriString: String,
  val albumArtUriString: String?
) : Serializable