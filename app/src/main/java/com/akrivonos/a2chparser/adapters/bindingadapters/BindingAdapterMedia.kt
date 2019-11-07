package com.akrivonos.a2chparser.adapters.bindingadapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.rtoshiro.view.video.FullscreenVideoView

object BindingAdapterMedia {
    @BindingAdapter("app:imageUrlAsyncLoad")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        if (view.visibility == View.VISIBLE) {
            Glide.with(view)
                    .load(url)
                    .into(view)
        }
    }

    @BindingAdapter("app:videoUrlLoad")
    @JvmStatic
    fun loadVideo(videoView: FullscreenVideoView, url: String) {
        if (videoView.visibility == View.VISIBLE) {
            videoView.setVideoURI(Uri.parse(url))
            videoView.start()
            videoView.requestFocus()
        }
    }
}
