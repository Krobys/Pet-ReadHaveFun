package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel


class ConcreteBoardFragment : Fragment(), ShowContentMediaListener {

    private var threadAdapter: ThreadAdapter? = null
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ConcreteBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("test", "onCreate: ConcreteBoardFragmen")
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(ConcreteBoardViewModel::class.java)
    }

    private fun setUpAdapterAndListeners() {
        activity?.let {
            pageDisplayModeListener = it as PageDisplayModeListener?
            toolbarModeListener = it as SetUpToolbarModeListener?
            val showContentMediaListener = this
            threadAdapter = ThreadAdapter(it, false, showContentMediaListener)
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
        Log.d("test", "startLoadThreadsForBoard: ")
        arguments?.let { argument ->
            Log.d("test", "startLoadThreadsForBoard: arg")
            argument.getParcelable<Board>(BOARD_INFO)?.let { it ->
                Log.d("test", "startLoadThreadsForBoard: boardinfo: ${it.idBoard}")
                viewModel.getThreadsFoBoard(it.idBoard).observe(this, androidx.lifecycle.Observer<List<Thread>> {
                    Log.d("test", "list size: ${it.size} ")
                    if (it.isNotEmpty()) {
                        threadAdapter?.apply {
                            Log.d("test", "setThreads in Adapter: ")
                            setThreads(it)
                            notifyDataSetChanged()
                        }
                    }
                    progressBar.visibility = View.GONE
                })
                progressBar.visibility = View.VISIBLE
                toolbarModeListener?.setMode(TOOLBAR_MODE_FULL, it.nameBoards)
            }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        arguments?.let {
            outState.putBundle()
        }
        super.onSaveInstanceState(outState)
    }
    private fun setUpScreen(view: View?, context: Context?) {
        view?.let {
            it.findViewById<RecyclerView>(R.id.board_threads_rec_view)?.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(DecorationDirection.BOTTOM, 50))
                adapter = threadAdapter
            }
            progressBar = it.findViewById(R.id.progressBar)

            pageDisplayModeListener?.setPageMode(PAGE_MODE_ONLY_TOOLBAR)
        }
    }

    override fun showContent(pathMedia: String?, mediaType: Int) {
        pathMedia?.let {
            MediaZoomedDialog(activity!!, it, mediaType).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                show()
            }
        }
    }
}
