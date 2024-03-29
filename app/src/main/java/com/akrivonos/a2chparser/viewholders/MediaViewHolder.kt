package com.akrivonos.a2chparser.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.BuildConfig
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter.Companion.ITEM_TYPE_VIDEO
import com.akrivonos.a2chparser.databinding.AdapteritemMediaPhotoBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.File

class MediaViewHolder(private var binder: AdapteritemMediaPhotoBinding, private var contentMediaListener: ShowContentMediaListener) : RecyclerView.ViewHolder(binder.root) {
    private var mediaPathFull: String? = null
    private var nameMedia: String? = null

    fun showContent() {
        contentMediaListener.showContent(mediaPathFull, itemViewType, nameMedia)
    }

    fun setUpMediaItem(fileMedia: File?) {
        fileMedia?.let {
            val standardPath = BuildConfig.BASE_URL.dropLast(1)
            mediaPathFull = standardPath + it.path
            nameMedia = it.name

            val mediaPathThumbnail = standardPath + it.thumbnail

            binder.file = it
            binder.pathPhoto = mediaPathThumbnail
            binder.playIconState = itemViewType == ITEM_TYPE_VIDEO
            binder.holder = this
        }
    }
}
