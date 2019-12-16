package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.activities.MainActivity.Companion.ID_BOARD
import com.akrivonos.a2chparser.activities.MainActivity.Companion.NAME_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.ThreadAdapter
import com.akrivonos.a2chparser.base.BaseFragment
import com.akrivonos.a2chparser.databinding.FragmentConcreteBoardBinding
import com.akrivonos.a2chparser.dialogs.FilterSettingsDialog
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.models.database.Board
import com.akrivonos.a2chparser.pojomodel.threadmodel.Thread
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils.DecorationDirection
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.ConcreteBoardViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject


class ConcreteBoardFragment : BaseFragment<ConcreteBoardViewModel, FragmentConcreteBoardBinding>(), SearchView.OnQueryTextListener, OnBackPressedFragmentsListener {

    private lateinit var threadAdapter: ThreadAdapter
    private var pageDisplayModeListener: NavBarDisplayModeListener? = null
    private var searchView: SearchView? = null

    override val viewModelClass: Class<ConcreteBoardViewModel>
        get() = ConcreteBoardViewModel::class.java
    override val layoutId: Int
        get() = R.layout.fragment_concrete_board

    @Inject
    lateinit var itemDecoratorUtils: ItemDecoratorUtils
    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
    }

    private fun setUpAdapterAndListeners() {
        activity?.let {
            pageDisplayModeListener = it as NavBarDisplayModeListener?
            val showContentMediaListener = it as ShowContentMediaListener
            val openThreadListener = it as OpenThreadListener
            val boardId = getBoard()?.idBoard
            threadAdapter = ThreadAdapter(it, showContentMediaListener, openThreadListener, boardId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        startLoadThreadsForBoard()
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
    private fun showThreadList(listItems: List<FilteredItem>) {
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

    override fun setUpScreen() {
        binding.boardThreadsRecView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(DecorationDirection.BOTTOM, 50))
            adapter = threadAdapter
        }
        (activity as? MainActivity)?.setSupportActionBar(toolbar)
        progressBar = binding.progressBar
        pageDisplayModeListener?.setNavbarMode(MainActivity.Companion.NavbarMode.INVISIBLE)
        getBoard()?.let { board ->
            activity?.actionBar?.title = board.nameBoards
        }
    }

    private fun setUpSearchView(menu: Menu) {
        menu.findItem(R.id.action_search)?.let {
            searchView = it.actionView as SearchView
            searchView?.apply {
                isIconified = true
                setOnQueryTextListener(this@ConcreteBoardFragment)
                (this.findViewById(R.id.search_close_btn) as ImageView).setOnClickListener {
                    undoFilter()
                }
            }
        }
    }

    private fun undoFilter() {
        searchView?.apply {
            threadAdapter.undoFilter()
            clearFocus()
            isIconified = true
            isIconified = true//х2 потому что не срабатывает х1
        }
    }

    private fun setUpFilterButton(menu: Menu) {
        menu.findItem(R.id.filter_button)?.let {mi ->
            mi.setOnMenuItemClickListener {
                showFilterSettingsDialog(context, mi)
                true
            }
            setUpFilterStateIcon(mi, (sharedPreferenceUtils.isFilterEnable(context)))
        }
    }

    private fun showFilterSettingsDialog(context: Context?, menuItem: MenuItem?) {
        context?.let {
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
        setUpBackButton()
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpBackButton() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
    }


    override fun onBackPressed() {
        if (threadAdapter.isFilterEnable()) {
            undoFilter()
        } else {
            (activity as MainActivity).pressBackSuper()
        }
    }


}
