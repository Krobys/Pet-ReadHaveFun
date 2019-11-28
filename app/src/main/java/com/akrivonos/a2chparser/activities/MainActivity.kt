package com.akrivonos.a2chparser.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter
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
            ToolbarMode.FULL -> {
                setToolbarMode(displayBackButton = true, displayTitle = true)
                setTitleToolbar(title)
            }
            ToolbarMode.MODE_TITLE -> {
                setToolbarMode(displayBackButton = false, displayTitle = true)
                setTitleToolbar(title)
            }
            ToolbarMode.BACK_BUTTON -> setToolbarMode(displayBackButton = true, displayTitle = false)
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
            PageMode.FULL -> setPageDisplay(isToolbar = true, isNavBar = true)
            PageMode.ONLY_NAVBAR -> setPageDisplay(isToolbar = false, isNavBar = true)
            PageMode.ONLY_TOOLBAR -> setPageDisplay(isToolbar = true, isNavBar = false)
            PageMode.EMPTY -> setPageDisplay(isToolbar = false, isNavBar = false)
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

    override fun openSavePage(saveTypeModel: SaveTypeModel?) {
        saveTypeModel?.let {
            //TODO сделать все типы и убрать этот код
            if (it.typeSaveItem == SaveListTypesAdapter.SAVE_TYPE_NOVALUE) {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
                return
            }
        }
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
            bundle.putString(ID_BOARD, board.idBoard)
            bundle.putString(NAME_BOARD, board.nameBoards)
            navController.navigate(R.id.navigation_concrete_board_fragment, bundle)
        }
    }

    override fun showContent(pathMedia: String?, mediaType: Int, nameMedia: String?) {
        pathMedia?.let {
            Intent(this, MediaScreen::class.java).apply {
                putExtra(PATH_MEDIA, pathMedia)
                putExtra(TYPE_MEDIA, mediaType)
                putExtra(NAME_MEDIA, nameMedia)
                startActivity(this)
            }
        }
    }

    override fun onBackPressed() {
        var backPressedListener: OnBackPressedFragmentsListener? = null
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.primaryNavigationFragment?.let {
            if (it is OnBackPressedFragmentsListener) {
                backPressedListener = it
            }
        }
        backPressedListener?.onBackPressed() ?: super.onBackPressed()
    }

    fun pressBackSuper() {
        super.onBackPressed()
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

        const val PATH_MEDIA = "path_media"
        const val TYPE_MEDIA = "type_media"
        const val NAME_MEDIA = "name_media"
    }


}
