package com.akrivonos.a2chparser.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter.Companion.ITEM_TYPE_VIDEO
import com.akrivonos.a2chparser.databinding.DialogDetailedMediaBinding

class MediaZoomedDialog(context: Context, private val mediaPath: String, private val mediaType: Int) : Dialog(context) {
    private lateinit var binder: DialogDetailedMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_detailed_media, null, false)
        setContentView(binder.root)
        setUpNeededMediaContainer(mediaPath)
    }

    private fun setUpNeededMediaContainer(mediaPath: String) {
        binder.isVideo = mediaType == ITEM_TYPE_VIDEO
        binder.srcMedia = mediaPath
    }

}
