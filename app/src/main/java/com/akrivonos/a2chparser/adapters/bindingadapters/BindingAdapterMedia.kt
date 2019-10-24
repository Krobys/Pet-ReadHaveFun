package com.akrivonos.a2chparser.adapters.bindingadapters

import android.net.Uri
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapterMedia {
    @BindingAdapter("app:imageUrlAsyncLoad")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view)
                .load(url)
                .into(view)
    }

    @BindingAdapter("app:videoUrlLoad")
    @JvmStatic
    fun loadVideo(videoView: VideoView, url: String) {
        videoView.setVideoURI(Uri.parse(url))
        videoView.setMediaController(MediaController(videoView.context))
        videoView.start()
        videoView.requestFocus()
    }
}
