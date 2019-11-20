package com.akrivonos.a2chparser.fragments


import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.activities.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.activities.MainActivity.Companion.NAME_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.ThreadAdapter
import com.akrivonos.a2chparser.dagger.Injectable
import com.akrivonos.a2chparser.databinding.FragmentConcreteBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenThreadListener
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.interfaces.ShowContentMediaListener
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel
import javax.inject.Inject


class ConcreteBoardFragment : Fragment(), SearchView.OnQueryTextListener, Injectable {

    private lateinit var binding: FragmentConcreteBoardBinding
    private lateinit var threadAdapter: ThreadAdapter
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var toolbarModeListener: SetUpToolbarModeListener? = null
    private var searchView: SearchView? = null
    private lateinit var viewModel: ConcreteBoardViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var itemDecoratorUtils: ItemDecoratorUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ConcreteBoardViewModel::class.java)
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_concrete_board, container, false)
        setHasOptionsMenu(true)
        setUpViewModel()
        setUpScreen()
        startLoadThreadsForBoard()
        return binding.root
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
                            binding.progressBar.visibility = View.GONE
                            binding.boardThreadsRecView.visibility = View.VISIBLE
                        })
                binding.boardThreadsRecView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpScreen() {
            binding.boardThreadsRecView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(DecorationDirection.BOTTOM, 50))
                adapter = threadAdapter
            }

            pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_TOOLBAR)
            getBoard()?.let { board ->
                toolbarModeListener?.setMode(MainActivity.Companion.ToolbarMode.FULL, board.nameBoards)
            }
    }

    private fun setUpSearchView(menu: Menu) {
        menu.findItem(R.id.action_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.apply {
                isIconified = true
                setOnQueryTextListener(this@ConcreteBoardFragment)
                (this.findViewById(R.id.search_close_btn) as ImageView).setOnClickListener {
                    threadAdapter.undoFilter()
                    clearFocus()
                    isIconified = true
                    isIconified = true//х2 потому что не срабатывает х1
                }
            }
        }
    }

    private fun setUpFilterButton(menu: Menu){
        menu.findItem(R.id.filter_button)?.let {

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchView?.clearFocus()
            threadAdapter.filter(it)
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
        inflater.inflate(R.menu.menu_detailed_search, menu)
        setUpSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
