package com.akrivonos.a2chparser.adapters.bindingadapters

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.rtoshiro.view.video.FullscreenVideoView

object BindingAdapterMedia {
    @BindingAdapter(value = ["imageUrl", "isVideo"])
    @JvmStatic
    fun loadImage(view: ImageView, url: String, isVideoT: Boolean = false) {
        if (!isVideoT) {
            Glide.with(view)
                    .load(url)
                    .into(view)
        }
    }

    @BindingAdapter(value = ["videoUrl", "isVideo"])
    @JvmStatic
    fun loadVideo(videoView: FullscreenVideoView, url: String, isVideoT: Boolean) {
        if (isVideoT) {
            videoView.setVideoURI(Uri.parse(url))
            videoView.start()
            videoView.requestFocus()
        }
    }
}
