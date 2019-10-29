package com.akrivonos.a2chparser.viewholders

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.MediaAdapter
import com.akrivonos.a2chparser.databinding.AdapteritemThreadsForBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenThreadListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils


class ThreadViewHolder(private var binder: AdapteritemThreadsForBoardBinding, private val openThreadListener: OpenThreadListener, private var boardId: String?) : RecyclerView.ViewHolder(binder.root) {

    private val mediaContentThreadRecView: RecyclerView = binder.root.findViewById(R.id.media_content_rec_view)

    constructor(binder: AdapteritemThreadsForBoardBinding,
                context: Context,
                layoutInflater: LayoutInflater,
                contentMediaListener: ShowContentMediaListener,
                openThreadListener: OpenThreadListener,
                boardId: String?)
            : this(binder, openThreadListener, boardId) {
        mediaContentThreadRecView.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))
        binder.layoutManagerRecycleView = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        binder.adapter = MediaAdapter(layoutInflater, contentMediaListener)
    }

    fun openThread() {
        openThreadListener.openThread(boardId, binder.thread?.num)
    }

    fun expandTextView() {
        val tv: TextView = binder.root.findViewById(R.id.text_content)
        val animation = ObjectAnimator.ofInt(tv, "maxLines", tv.lineCount)
        animation.setDuration(200).start()
    }

    fun setThreadDataWithMedia(thread: Thread) {
        binder.holder = this
        binder.thread = thread
        binder.adapter?.setMediaList(thread.files)
        binder.adapter?.notifyDataSetChanged()
    }

    fun setThreadDataWithoutMedia(thread: Thread) {
        binder.thread = thread
        mediaContentThreadRecView.visibility = View.GONE
    }
}
