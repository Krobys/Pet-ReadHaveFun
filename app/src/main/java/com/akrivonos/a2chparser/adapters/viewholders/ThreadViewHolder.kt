package com.akrivonos.a2chparser.adapters.viewholders

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.MediaAdapter
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils

class ThreadViewHolder : RecyclerView.ViewHolder {

    private val nameThreadTextView: TextView
    private val idThreadTextView: TextView
    private val dateThreadTextView: TextView
    private val contentThreadTextView: TextView
    private val mediaContentThreadRecView: RecyclerView
    private var mediaAdapter: MediaAdapter? = null
    private var idThread: String? = null

    constructor(itemView: View) : super(itemView) {
        Log.d("test", "ThreadViewHolder: 1")
        nameThreadTextView = itemView.findViewById(R.id.name_thread)
        idThreadTextView = itemView.findViewById(R.id.id_thread)
        dateThreadTextView = itemView.findViewById(R.id.date_thread)
        contentThreadTextView = itemView.findViewById(R.id.text_content)
        mediaContentThreadRecView = itemView.findViewById(R.id.media_content_rec_view)
        mediaContentThreadRecView.visibility = View.GONE
    }

    constructor(itemView: View, context: Context, layoutInflater: LayoutInflater, isFullMode: Boolean, contentMediaListener: ShowContentMediaListener) : super(itemView) {
        Log.d("test", "ThreadViewHolder: 2")
        nameThreadTextView = itemView.findViewById(R.id.name_thread)
        idThreadTextView = itemView.findViewById(R.id.id_thread)
        dateThreadTextView = itemView.findViewById(R.id.date_thread)
        contentThreadTextView = itemView.findViewById(R.id.text_content)
        mediaContentThreadRecView = itemView.findViewById(R.id.media_content_rec_view)
        mediaContentThreadRecView.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        mediaContentThreadRecView.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.RIGHT, 40))

        mediaAdapter = MediaAdapter(layoutInflater, isFullMode, contentMediaListener)

        mediaContentThreadRecView.adapter = mediaAdapter
    }

    fun setThreadDataWithMedia(thread: Thread) {
        nameThreadTextView.text = thread.subject
        idThread = thread.num
        idThreadTextView.text = idThread
        dateThreadTextView.text = thread.date
        contentThreadTextView.text = HtmlCompat.fromHtml(thread.comment, HtmlCompat.FROM_HTML_MODE_LEGACY)
        mediaAdapter?.setMediaList(thread.files)
        mediaAdapter?.notifyDataSetChanged()
    }

    fun setThreadDataWithoutMedia(thread: Thread) {
        nameThreadTextView.text = thread.subject
        idThread = thread.num
        idThreadTextView.text = idThread
        dateThreadTextView.text = thread.date
        contentThreadTextView.text = HtmlCompat.fromHtml(thread.comment, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
