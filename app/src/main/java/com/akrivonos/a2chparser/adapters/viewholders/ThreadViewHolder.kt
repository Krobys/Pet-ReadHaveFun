package com.akrivonos.a2chparser.adapters.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.AdapteritemThreadsForBoardBinding
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils

class ThreadViewHolder(private var binder: AdapteritemThreadsForBoardBinding) : RecyclerView.ViewHolder(binder.root) {

    private val mediaContentThreadRecView: RecyclerView = binder.root.findViewById(R.id.media_content_rec_view)

    constructor(binder: AdapteritemThreadsForBoardBinding, context: Context, layoutInflater: LayoutInflater, isFullMode: Boolean, contentMediaListener: ShowContentMediaListener) : this(binder) {
        mediaContentThreadRecView.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))
        binder.layoutManagerRecycleView = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        binder.adapter = MediaAdapter(layoutInflater, isFullMode, contentMediaListener)
    }

    fun setThreadDataWithMedia(thread: Thread) {
        binder.thread = thread
        binder.adapter?.setMediaList(thread.files)
        binder.adapter?.notifyDataSetChanged()
    }

    fun setThreadDataWithoutMedia(thread: Thread) {
        binder.thread = thread
        mediaContentThreadRecView.visibility = View.GONE
    }
}
