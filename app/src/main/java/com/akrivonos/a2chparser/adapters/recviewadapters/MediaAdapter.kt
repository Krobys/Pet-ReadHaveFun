package com.akrivonos.a2chparser.adapters.recviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemMediaPhotoBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.File
import com.akrivonos.a2chparser.viewholders.MediaViewHolder
import java.util.*

class MediaAdapter(private val layoutInflater: LayoutInflater, private val isFullMode: Boolean, private val contentMediaListener: ShowContentMediaListener) : RecyclerView.Adapter<MediaViewHolder>() {

    private var mediaList = ArrayList<File>()

    fun setMediaList(mediaListToAdapt: List<File>) {
        mediaList = ArrayList(mediaListToAdapt)
    }

    override fun getItemViewType(position: Int): Int {
        val file = mediaList[position]
        return when (file.type) {
            MEDIA_TYPE_JPG -> return ITEM_TYPE_IMAGE
            MEDIA_TYPE_PNG -> return ITEM_TYPE_IMAGE
            MEDIA_TYPE_GIF -> return ITEM_TYPE_GIF
            MEDIA_TYPE_WEBM -> return ITEM_TYPE_VIDEO
            MEDIA_TYPE_MP4 -> return ITEM_TYPE_VIDEO
            else -> ITEM_TYPE_IMAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binder: AdapteritemMediaPhotoBinding = DataBindingUtil.inflate(layoutInflater, R.layout.adapteritem_media_photo, parent, false)
        return MediaViewHolder(binder, contentMediaListener)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.setUpMediaItem(mediaList[position], isFullMode)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    companion object {

        const val MEDIA_TYPE_JPG = 1
        const val MEDIA_TYPE_PNG = 2
        const val MEDIA_TYPE_GIF = 4
        const val MEDIA_TYPE_WEBM = 6
        const val MEDIA_TYPE_MP4 = 10

        const val ITEM_TYPE_IMAGE = 1
        const val ITEM_TYPE_GIF = 2
        const val ITEM_TYPE_VIDEO = 3
    }
}
