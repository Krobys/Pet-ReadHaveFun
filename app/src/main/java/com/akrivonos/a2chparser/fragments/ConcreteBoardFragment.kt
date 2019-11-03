package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity
import com.akrivonos.a2chparser.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.MainActivity.Companion.NAME_BOARD
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.ThreadAdapter
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel


class ConcreteBoardFragment : Fragment() {

    private lateinit var threadAdapter: ThreadAdapter
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private var toolbarHider: ToolbarHider? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ConcreteBoardViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
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
            toolbarHider = it as ToolbarHider
            val showContentMediaListener = it as ShowContentMediaListener
            val openThreadListener = it as OpenThreadListener
            val boardId = getBoard()?.idBoard
            threadAdapter = ThreadAdapter(it, showContentMediaListener, openThreadListener, boardId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_concrete_board, container, false)
        setUpScreen(view, context)
        startLoadThreadsForBoard()
        return view
    }

    private fun getBoard(): Board? {
        arguments?.let { argument ->
            argument.getString(NAME_BOARD)?.let { nameBoard ->
                argument.getString(ID_BOARD)?.let { idBoard ->
                    val board = Board()
                    board.nameBoards = nameBoard
                    Log.d("test", "nameBoard2: $nameBoard")
                    board.idBoard = idBoard
                    Log.d("test", "idBoard: $idBoard")
                    return board
                }
            }
        }
        return null
    }

    private fun startLoadThreadsForBoard() {
        getBoard()?.let { board ->
            board.idBoard?.let { idBoard ->
                viewModel.getThreadsForBoard(idBoard)
                        .observe(this, androidx.lifecycle.Observer<List<Thread>> {
                            if (it.isNotEmpty()) {
                                threadAdapter.apply {
                                    setThreads(it)
                                    notifyDataSetChanged()
                                }
                            }
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                        })
                recyclerView.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpScreen(view: View?, context: Context?) {
        view?.let {
            recyclerView = it.findViewById(R.id.board_threads_rec_view)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(DecorationDirection.BOTTOM, 50))
                adapter = threadAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    var yState: Int = 0

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                            // fragProductLl.setVisibility(View.VISIBLE);
                            if (yState <= 0) {
                                Log.d("test", "show: ")
                                toolbarHider?.show()
                            } else {
                                Log.d("test", "hide: ")
                                yState = 0
                                toolbarHider?.hide()
                            }
                        }
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        yState = dy
                    }
                })
            }
            progressBar = it.findViewById(R.id.progressBar)

            pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_TOOLBAR)
            getBoard()?.let { board ->
                toolbarModeListener?.setMode(MainActivity.Companion.ToolbarMode.FULL, board.nameBoards)
            }
        }
    }

}
