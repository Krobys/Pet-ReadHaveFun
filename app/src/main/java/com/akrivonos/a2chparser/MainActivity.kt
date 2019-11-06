package com.akrivonos.a2chparser

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.akrivonos.a2chparser.dialogs.MediaZoomedDialog
import com.akrivonos.a2chparser.fragments.FavoritePageConcreteFragment.Companion.INFO_SAVE_PAGE
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.models.SaveTypeModel
import com.akrivonos.a2chparser.models.database.Board
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), OpenBoardListener, SetUpToolbarModeListener,
        PageDisplayModeListener, OpenDetailedSavePage, OpenThreadListener,
        ShowContentMediaListener {

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

    override fun setMode(mode: ToolbarMode, title: String?) {
        when (mode) {
            Companion.ToolbarMode.FULL -> {
                setToolbarMode(displayBackButton = true, displayTitle = true)
                setTitleToolbar(title)
            }
            Companion.ToolbarMode.MODE_TITLE -> {
                setToolbarMode(displayBackButton = false, displayTitle = true)
                setTitleToolbar(title)
            }
            Companion.ToolbarMode.BACK_BUTTON -> setToolbarMode(displayBackButton = true, displayTitle = false)
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

    override fun setPageMode(mode: PageMode) {
        when (mode) {
            Companion.PageMode.FULL -> setPageDisplay(isToolbar = true, isNavBar = true)
            Companion.PageMode.ONLY_NAVBAR -> setPageDisplay(isToolbar = false, isNavBar = true)
            Companion.PageMode.ONLY_TOOLBAR -> setPageDisplay(isToolbar = true, isNavBar = false)
            Companion.PageMode.EMPTY -> setPageDisplay(isToolbar = false, isNavBar = false)
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
        bundle.putString(ID_BOARD, nameBoard)
        bundle.putString(NUMBER_THREAD, numberThread)
        navController.navigate(R.id.navigation_concrete_thread_fragment, bundle)
    }

    override fun openBoard(board: Board?) {
        board?.let {
            val bundle = Bundle()
            bundle.putString(NAME_BOARD, board.nameBoards)
            bundle.putString(ID_BOARD, board.idBoard)
            navController.navigate(R.id.navigation_concrete_board_fragment, bundle)
        }
    }

    override fun showContent(pathMedia: String?, mediaType: Int) {
        pathMedia?.let {
            MediaZoomedDialog(this, it, mediaType).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                show()
            }
        }
    }

    companion object {
        enum class ToolbarMode {
            FULL, BACK_BUTTON, MODE_TITLE
        }

        enum class PageMode {
            FULL, ONLY_TOOLBAR, ONLY_NAVBAR, EMPTY
        }

        const val ID_BOARD = "id_board"
        const val NAME_BOARD = "name_board"
        const val NUMBER_THREAD = "number_thread"
    }
}
