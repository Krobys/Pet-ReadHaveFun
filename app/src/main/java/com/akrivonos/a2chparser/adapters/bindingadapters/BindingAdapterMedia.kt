package com.akrivonos.a2chparser.adapters.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide

object BindingAdapterMedia {
    @BindingAdapter("app:imageUrlAsyncLoad")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view)
                .load(url)
                .into(view)
    }
}
