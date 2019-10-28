package com.akrivonos.a2chparser

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.akrivonos.a2chparser.fragments.BOARD_INFO
import com.akrivonos.a2chparser.fragments.FavoritePageConcreteFragment.Companion.INFO_SAVE_PAGE
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.models.SaveTypeModel
import com.akrivonos.a2chparser.models.database.Board
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), OpenBoardListener, SetUpToolbarModeListener, PageDisplayModeListener, OpenDetailedSavePage, OpenThreadListener {

    private var toolbar: Toolbar? = null
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpScreen()
    }

    private fun setUpScreen() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        bottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun openBoard(board: Board?) {
        board?.let {
            val bundle = Bundle()
            bundle.putParcelable(BOARD_INFO, it)
            navController.navigate(R.id.navigation_concrete_board_fragment, bundle)
        }
    }

    override fun setMode(mode: Int, title: String?) {
        when (mode) {
            TOOLBAR_MODE_FULL -> {
                setToolbarMode(displayBackButton = true, displayTitle = true)
                setTitleToolbar(title)
            }
            TOOLBAR_MODE_TITLE -> {
                setToolbarMode(displayBackButton = false, displayTitle = true)
                setTitleToolbar(title)
            }
            TOOLBAR_MODE_BACK_BUTTON -> setToolbarMode(displayBackButton = true, displayTitle = false)
        }
    }

    private fun setToolbarMode(displayBackButton: Boolean, displayTitle: Boolean) {
        val actionBar = supportActionBar
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(displayBackButton)
            it.setDisplayShowHomeEnabled(displayBackButton)
            it.setDisplayShowTitleEnabled(displayTitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.popBackStack()
            return true
        }
        return false
    }

    private fun setTitleToolbar(titleToolbar: String?) {
        val actionBar = supportActionBar
        actionBar?.title = titleToolbar

    }

    override fun setPageMode(mode: Int) {
        when (mode) {
            PAGE_MODE_FULL -> setPageDisplay(isToolbar = true, isNavBar = true)
            PAGE_MODE_ONLY_NAVBAR -> setPageDisplay(isToolbar = false, isNavBar = true)
            PAGE_MODE_ONLY_TOOLBAR -> setPageDisplay(isToolbar = true, isNavBar = false)
            PAGE_MODE_EMPTY -> setPageDisplay(isToolbar = false, isNavBar = false)
        }
    }

    private fun setPageDisplay(isToolbar: Boolean, isNavBar: Boolean) {
        toolbar?.visibility = if (isToolbar)
            View.VISIBLE
        else
            View.GONE
        bottomNavigationView.visibility = if (isNavBar)
            View.VISIBLE
        else
            View.GONE
    }

    override fun openSavePage(saveTypeModel: SaveTypeModel) {
        val bundle = Bundle()
        bundle.putParcelable(INFO_SAVE_PAGE, saveTypeModel)
        navController.navigate(R.id.navigation_saved_page_concrete_fragment, bundle)
    }

    override fun openThread(nameBoard: String?, numberThread: String?) {
        val bundle = Bundle()
        bundle.putString(NAME_BOARD, nameBoard)
        bundle.putString(NUMBER_THREAD, numberThread)
        navController.navigate(R.id.navigation_concrete_thread_fragment, bundle)//TODO DOOOO
    }

    companion object {
        const val TOOLBAR_MODE_FULL = 1
        const val TOOLBAR_MODE_BACK_BUTTON = 2
        const val TOOLBAR_MODE_TITLE = 3

        const val PAGE_MODE_FULL = 1
        const val PAGE_MODE_ONLY_TOOLBAR = 2
        const val PAGE_MODE_ONLY_NAVBAR = 3
        const val PAGE_MODE_EMPTY = 4

        const val NAME_BOARD = "name_board"
        const val NUMBER_THREAD = "number_thread"
    }
}
