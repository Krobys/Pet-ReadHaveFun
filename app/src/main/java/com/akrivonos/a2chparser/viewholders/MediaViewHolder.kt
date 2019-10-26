package com.akrivonos.a2chparser.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter.Companion.ITEM_TYPE_VIDEO
import com.akrivonos.a2chparser.databinding.AdapteritemMediaPhotoBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.File

class MediaViewHolder(private var binder: AdapteritemMediaPhotoBinding, private var contentMediaListener: ShowContentMediaListener) : RecyclerView.ViewHolder(binder.root) {
    private var mediaPathFull: String? = null

    fun showContent() {
        contentMediaListener.showContent(mediaPathFull, itemViewType)
    }

    fun setUpMediaItem(fileMedia: File?, modeThreadOpenFull: Boolean) {
        val STANDART_PATH = "https://2ch.hk"
        mediaPathFull = STANDART_PATH + fileMedia?.path
        val mediaPathThumbnail = STANDART_PATH + fileMedia?.thumbnail

        binder.file = fileMedia
        binder.pathPhoto = if (modeThreadOpenFull) mediaPathFull else mediaPathThumbnail
        binder.playIconState = itemViewType == ITEM_TYPE_VIDEO
        binder.holder = this
    }
}
