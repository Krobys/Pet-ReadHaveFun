package com.akrivonos.a2chparser.viewholders

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
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
    private var itemDecoratorUtils: ItemDecoratorUtils = ItemDecoratorUtils()

    constructor(binder: AdapteritemThreadsForBoardBinding,
                context: Context,
                layoutInflater: LayoutInflater,
                contentMediaListener: ShowContentMediaListener,
                openThreadListener: OpenThreadListener,
                boardId: String?)
            : this(binder, openThreadListener, boardId) {
        mediaContentThreadRecView.addItemDecoration(itemDecoratorUtils
                .createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))
        binder.mediaContentRecView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binder.adapter = MediaAdapter(layoutInflater, contentMediaListener)
    }

    fun openThread() {
        binder.thread?.num?.let {
            openThreadListener.openThread(boardId, it)
        }
    }

    fun expandTextView() {
        val tvContent: TextView = binder.textContent
        val tvShowMore: TextView = binder.showFullContent
        val animation = ObjectAnimator.ofInt(tvContent, "maxLines", tvContent.lineCount)
        animation.setDuration(200).start()
        tvShowMore.visibility = View.GONE
    }

    private fun setUpTextViewExpandable() {
        val tv: TextView = binder.textContent
        tv.text = binder.thread?.comment
        val tvShowMore: TextView = binder.showFullContent
        val vto = tv.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val obs = tv.viewTreeObserver
                obs.removeOnGlobalLayoutListener(this)
                val isExpandable = tv.maxLines < tv.lineCount
                tvShowMore.visibility = if (isExpandable) View.VISIBLE else View.GONE
            }
        })

    }

    fun setThreadDataWithMedia(thread: Thread) {
        binder.thread = thread
        binder.holder = this
        binder.adapter?.setMediaList(thread.files)
        binder.adapter?.notifyDataSetChanged()
        setUpTextViewExpandable()
    }

    fun setThreadDataWithoutMedia(thread: Thread) {
        binder.thread = thread
        binder.holder = this
        mediaContentThreadRecView.visibility = View.GONE
        setUpTextViewExpandable()
    }
}
