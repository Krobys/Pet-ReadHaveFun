package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemThreadsForBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenThreadListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.viewholders.ThreadViewHolder
import java.util.*

class ThreadAdapter(private val context: Context, private val contentMediaListener: ShowContentMediaListener, private val openThreadListener: OpenThreadListener, private val boardId: String?) : RecyclerView.Adapter<ThreadViewHolder>() {

    private var threads = ArrayList<Thread>()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun setThreads(threads: List<Thread>?) {
        threads?.let {
            this.threads = ArrayList(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val thread = threads[position]
        val files = thread.files
        return if (files != null)
            TYPE_WITH_MEDIA
        else
            TYPE_WITHOUT_MEDIA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadViewHolder {
        val binding = DataBindingUtil.inflate<AdapteritemThreadsForBoardBinding>(layoutInflater, R.layout.adapteritem_threads_for_board, parent, false)
        return if (viewType == TYPE_WITH_MEDIA)
            ThreadViewHolder(binding, context, layoutInflater, contentMediaListener, openThreadListener, boardId)
        else
            ThreadViewHolder(binding, openThreadListener, boardId)

    }

    override fun onBindViewHolder(holder: ThreadViewHolder, position: Int) {
        val thread = threads[position]
        if (holder.itemViewType == TYPE_WITH_MEDIA) {
            holder.setThreadDataWithMedia(thread)
        } else {
            holder.setThreadDataWithoutMedia(thread)
        }
    }

    override fun getItemCount(): Int {
        return threads.size
    }

    companion object {
        const val TYPE_WITH_MEDIA = 1
        const val TYPE_WITHOUT_MEDIA = 2
    }
}
