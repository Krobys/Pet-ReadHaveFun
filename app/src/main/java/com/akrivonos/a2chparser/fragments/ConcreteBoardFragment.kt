package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity
import com.akrivonos.a2chparser.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.MainActivity.Companion.NAME_BOARD
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.ThreadAdapter
import com.akrivonos.a2chparser.interfaces.OpenThreadListener
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel


class ConcreteBoardFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var threadAdapter: ThreadAdapter
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ConcreteBoardViewModel
    private lateinit var recyclerView: RecyclerView
    private var searchView: SearchView? = null
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
            val showContentMediaListener = it as ShowContentMediaListener
            val openThreadListener = it as OpenThreadListener
            val boardId = getBoard()?.idBoard
            threadAdapter = ThreadAdapter(it, showContentMediaListener, openThreadListener, boardId)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_concrete_board, container, false)
        setHasOptionsMenu(true)
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
                    board.idBoard = idBoard
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
            }
            progressBar = it.findViewById(R.id.progressBar)

            pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_TOOLBAR)
            getBoard()?.let { board ->
                toolbarModeListener?.setMode(MainActivity.Companion.ToolbarMode.FULL, board.nameBoards)
            }
        }
    }

    private fun setUpSearchView(menu: Menu) {
        menu.findItem(R.id.action_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.apply {
                isIconified = true
                setOnQueryTextListener(this@ConcreteBoardFragment)
                (this.findViewById(R.id.search_close_btn) as ImageView).setOnClickListener {
                    clearFocus()
                    threadAdapter.undoFilter()
                    isIconified = true
                }
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchView?.apply {
                clearFocus()
            }
            threadAdapter.filter(it)
            Log.d("test", "onQueryTextSubmit: ")
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            if (it.length > 1) {
                threadAdapter.filter(it)
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("test", "onCreateOptionsMenu: ")
        inflater.inflate(R.menu.menu_detailed_search, menu)
        setUpSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
