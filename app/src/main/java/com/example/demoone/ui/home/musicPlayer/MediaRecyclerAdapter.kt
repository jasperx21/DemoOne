package com.example.demoone.ui.home.musicPlayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demoone.BR
import com.example.demoone.R
import com.example.demoone.data.model.Media
import com.example.demoone.databinding.IndiviewMediaBinding
import com.example.demoone.ui.home.musicPlayer.MediaRecyclerAdapter.MediaViewHolder

class MediaRecyclerAdapter : RecyclerView.Adapter<MediaViewHolder>() {

  class MediaViewHolder(val binding: IndiviewMediaBinding) : RecyclerView.ViewHolder(binding.root)

  private var mediaList = arrayListOf<Media>()
  private lateinit var onMediaActionListener: OnMediaActionListener

  fun setMediaList(mediaList: ArrayList<Media>) {
    this.mediaList = mediaList
    notifyDataSetChanged()
  }

  fun setOnMediaActionListener(onMediaActionListener: OnMediaActionListener) {
    this.onMediaActionListener = onMediaActionListener
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MediaViewHolder {
    val binding: IndiviewMediaBinding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context), R.layout.indiview_media, parent, false
    )
    return MediaViewHolder(binding)
  }

  override fun getItemCount(): Int = mediaList.size

  override fun onBindViewHolder(
    holder: MediaViewHolder,
    position: Int
  ) {
    holder.binding.setVariable(BR.media, mediaList[position])
    holder.binding.root.setOnClickListener {
      onMediaActionListener.onMediaClick(mediaList[position])
    }
  }

  interface OnMediaActionListener {
    fun onMediaClick(media: Media)
  }
}