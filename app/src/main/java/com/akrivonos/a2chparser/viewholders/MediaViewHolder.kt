package com.akrivonos.a2chparser.viewholders

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter.ITEM_TYPE_VIDEO
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.File
import com.bumptech.glide.Glide

class MediaViewHolder(itemView: View, contentMediaListener: ShowContentMediaListener) : RecyclerView.ViewHolder(itemView) {
    private val nameMediaTextView: TextView = itemView.findViewById(R.id.name_media)
    private val widthMediaTextView: TextView = itemView.findViewById(R.id.width_media)
    private val heightMediaTextView: TextView = itemView.findViewById(R.id.height_media)
    private val sizeMediaTextView: TextView = itemView.findViewById(R.id.size_media)
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)
    private var mediaPathFull: String? = null
    private var mediaPathThumbnail: String? = null

    init {
        itemView.setOnClickListener {
            contentMediaListener.showContent(mediaPathFull, itemViewType)
        }
    }

    fun setUpMediaItem(fileMedia: File?, modeThreadOpenFull: Boolean) {
        if (fileMedia != null) {
            nameMediaTextView.text = fileMedia.name.toString()
            val size = fileMedia.size?.toString() + "Кб"
            sizeMediaTextView.text = size
            heightMediaTextView.text = fileMedia.height.toString()
            widthMediaTextView.text = fileMedia.width.toString()
            val STANDART_PATH = "https://2ch.hk"
            mediaPathFull = STANDART_PATH + fileMedia.path
            mediaPathThumbnail = STANDART_PATH + fileMedia.thumbnail
            Glide.with(imageView)
                    .load(Uri.parse(if (modeThreadOpenFull)
                        mediaPathFull
                    else
                        mediaPathThumbnail))
                    .into(imageView)
            if (itemViewType == ITEM_TYPE_VIDEO) {
                val playIcon = itemView.findViewById<ImageView>(R.id.play_icon)
                playIcon.visibility = View.VISIBLE
            }
        }
    }
}
