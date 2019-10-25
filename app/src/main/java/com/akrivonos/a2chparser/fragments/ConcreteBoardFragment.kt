package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity.Companion.PAGE_MODE_ONLY_TOOLBAR
import com.akrivonos.a2chparser.MainActivity.Companion.TOOLBAR_MODE_FULL
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.ThreadAdapter
import com.akrivonos.a2chparser.dialogs.MediaZoomedDialog
import com.akrivonos.a2chparser.fragments.BoardsFragment.Companion.BOARD_INFO
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class ConcreteBoardFragment : Fragment(), ShowContentMediaListener {

    private var threadAdapter: ThreadAdapter? = null
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private var progressBar: ProgressBar? = null

    private val observer = object : Observer<List<Thread>?> {
        lateinit var disposable: Disposable
        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(threads: List<Thread>) {
            if (threads.isNotEmpty()) {
                threadAdapter?.setThreads(threads)
                threadAdapter?.notifyDataSetChanged()
            }
            progressBar?.visibility = View.GONE
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
            disposable.dispose()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
    }

    private fun setUpAdapterAndListeners() {
        val activity = activity
        if (activity != null) {
            pageDisplayModeListener = activity as PageDisplayModeListener?
            toolbarModeListener = activity as SetUpToolbarModeListener?
            val showContentMediaListener = this
            threadAdapter = ThreadAdapter(activity, false, showContentMediaListener)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_concrete_board, container, false)
        setUpScreen(view, context)
        startLoadThreadsForBoard()
        return view
    }

    private fun startLoadThreadsForBoard() {
        val arguments = arguments
        if (arguments != null) {
            val board = arguments.getParcelable<Board>(BOARD_INFO)
            if (board != null) {
                RetrofitSearchDvach.getThreadsForBoardForBoard(board.idBoard, observer)
                progressBar?.visibility = View.VISIBLE
                toolbarModeListener?.setMode(TOOLBAR_MODE_FULL, board.nameBoards)
            }
        }
    }

    private fun setUpScreen(view: View?, context: Context?) {
        if (view != null && context != null) {
            val recyclerViewBoardThreads = view.findViewById<RecyclerView>(R.id.board_threads_rec_view)
            recyclerViewBoardThreads.layoutManager = LinearLayoutManager(context)
            recyclerViewBoardThreads.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(DecorationDirection.BOTTOM, 100))
            recyclerViewBoardThreads.adapter = threadAdapter

            progressBar = view.findViewById(R.id.progressBar)
        }
        pageDisplayModeListener!!.setPageMode(PAGE_MODE_ONLY_TOOLBAR)
    }

    override fun showContent(pathMedia: String?, mediaType: Int) {
        pathMedia?.let {
            val cdd = MediaZoomedDialog(activity!!, it, mediaType)
            cdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            cdd.setCanceledOnTouchOutside(true)
            cdd.requestWindowFeature(Window.FEATURE_NO_TITLE)
            cdd.show()
        }
    }
}
