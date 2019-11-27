package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
import com.akrivonos.a2chparser.dialogs.FilterSettingsDialog
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
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
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

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
                binding.boardThreadsRecView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                viewModel.getThreadsForBoard(idBoard)
                        .observe(this, Observer { showThreadList(it) })
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun showThreadList(listItems: List<FilteredItem>){
        val listThreads: List<Thread> = listItems as List<Thread>
        if (listItems.isNotEmpty()) {
            threadAdapter.apply {
                setThreads(listThreads)
                notifyDataSetChanged()
            }
        }
        binding.progressBar.visibility = View.GONE
        binding.boardThreadsRecView.visibility = View.VISIBLE
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
            it.setOnMenuItemClickListener {
                showFilterSettingsDialog(context, it)
                true
            }
            setUpFilterStateIcon(it, (sharedPreferenceUtils.isFilterEnable(context)))
        }
    }

    private fun showFilterSettingsDialog(context: Context?, menuItem: MenuItem?) {
        context?.let { context ->
            FilterSettingsDialog(context, object : CallBack {
                override fun call() {
                    startLoadThreadsForBoard()
                    setUpFilterStateIcon(menuItem, (sharedPreferenceUtils.isFilterEnable(context)))
                }
            }).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                show()
            }
        }
    }

    private fun setUpFilterStateIcon(menuItem: MenuItem?, boolean: Boolean) {
        menuItem?.setIcon(if (boolean)
            R.drawable.filter_on
        else R.drawable.filter_off)
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
        setUpFilterButton(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
