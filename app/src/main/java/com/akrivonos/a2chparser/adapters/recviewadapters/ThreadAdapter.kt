package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemThreadsForBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenThreadListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.viewholders.ThreadViewHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ThreadAdapter(private val context: Context, private val contentMediaListener: ShowContentMediaListener, private val openThreadListener: OpenThreadListener, private val boardId: String?) : RecyclerView.Adapter<ThreadViewHolder>() {

    private var threads = ArrayList<Thread>()
    private var fullThreads = ArrayList<Thread>()
    private var filteredThreads = ArrayList<Thread>()
    private var disposable: Disposable? = null
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun setThreads(threads: List<Thread>?) {
        threads?.let {
            fullThreads = ArrayList(it)
            this.threads = fullThreads
        }
    }

    fun filter(textFilter: String) {
        filteredThreads.clear()
        disposable?.dispose()
        disposable = Observable.just(fullThreads)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { listThread ->
                    val tempList = ArrayList<Thread>()
                    for (thread: Thread in listThread) {
                        thread.comment?.let {
                            if (it.contains(textFilter)) {
                                tempList.add(thread)
                            }
                        }
                    }
                    filteredThreads = tempList
                    threads = tempList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    notifyDataSetChanged()
                    Toast.makeText(context, "search count ${threads.size}", Toast.LENGTH_SHORT).show()
                }
    }

    fun undoFilter() {
        threads = fullThreads
        notifyDataSetChanged()
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
