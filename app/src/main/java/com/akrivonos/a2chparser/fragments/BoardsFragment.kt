package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity.Companion.PAGE_MODE_ONLY_NAVBAR
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardConcreteAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardThemeAdapter
import com.akrivonos.a2chparser.dialogs.AdulthoodDialog
import com.akrivonos.a2chparser.interfaces.CallBack
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BoardsFragment : Fragment(), OpenDetailsBoardsBottomSheetListener {

    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var bottomSheet: FrameLayout? = null
    private var boardAdapter: BoardThemeAdapter? = null
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var progressBarBoards: ProgressBar? = null
    private lateinit var boardConcreteAdapter: BoardConcreteAdapter
    private lateinit var viewModel: BoardsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(BoardsViewModel::class.java)
        }
    }

    private fun setUpAdapterAndListeners() {
            val boardsBottomSheetListener = this
            pageDisplayModeListener = activity as PageDisplayModeListener?
        boardAdapter = BoardThemeAdapter(context, boardsBottomSheetListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_boards, container, false)
        setUpScreen(fragmentView, context)
        manageLoadBoardsInformation()
        return fragmentView
    }

    private fun manageLoadBoardsInformation() {
        Log.d("test", "manageLoadBoardsInformation")
        if (!boardAdapter?.isSet!!) {
            context?.let {
                if (!SharedPreferenceUtils.isAdultSettingsSet(context)) {
                    showAdultDialog(context)
                } else {
                    startLoadBoards()
                }
            }
        }
    }

    private fun showAdultDialog(context: Context?) {
        Log.d("test", "showAdultDialog")
        context?.let {
            val cdd = AdulthoodDialog(it, object : CallBack {
                override fun call() {
                    startLoadBoards()
                }
            })
            cdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            cdd.setCanceledOnTouchOutside(false)
            cdd.show()
        }
    }

    private fun setUpScreen(view: View?, context: Context?) {
        if (!(view == null || context == null)) {
            val recyclerViewBoards = view.findViewById<RecyclerView>(R.id.boards_rec_view)
            recyclerViewBoards.layoutManager = LinearLayoutManager(context)
            recyclerViewBoards.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 20))
            recyclerViewBoards.adapter = boardAdapter
            progressBarBoards = view.findViewById(R.id.progressBarBoardsTheme)
            setUpBottomSheetCurrent(view)
        }

        pageDisplayModeListener?.setPageMode(PAGE_MODE_ONLY_NAVBAR)
    }

    private fun startLoadBoards() {
        Log.d("test", "startLoadBoards")
        viewModel.getBoardThemes().observe(this, Observer<List<BoardTheme>> { boardThemes ->
            boardThemes?.let {
                boardAdapter?.apply {
                    setBoardThemes(it)
                    notifyDataSetChanged()
                }
            }
            progressBarBoards?.visibility = View.GONE
        })
        progressBarBoards?.visibility = View.VISIBLE
    }

    private fun setUpBottomSheetCurrent(view: View) {
        val activity = activity
        if (activity != null) {
            bottomSheet = view.findViewById(R.id.bottom_sheet_detailed_boards)
            sheetBehavior = BottomSheetBehavior.from(bottomSheet)
            sheetBehavior?.apply {
                isHideable = true
                skipCollapsed = true
                state = BottomSheetBehavior.STATE_HIDDEN
            }

            val openBoardListener = activity as OpenBoardListener
            boardConcreteAdapter = BoardConcreteAdapter(activity, openBoardListener)
            val bottomSheetBoardsRecView = bottomSheet?.findViewById<RecyclerView>(R.id.rec_view_boards_for_theme)
            bottomSheetBoardsRecView?.layoutManager = LinearLayoutManager(activity)
            bottomSheetBoardsRecView?.addItemDecoration(ItemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 15))
            bottomSheetBoardsRecView?.adapter = boardConcreteAdapter
            val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        boardConcreteAdapter.disposeAll()
                    }
                }

                override fun onSlide(p0: View, p1: Float) {
                }
            }
            sheetBehavior?.setBottomSheetCallback(bottomSheetCallback)
        }
    }

    override fun openBottomSheet(boardTheme: BoardTheme?) {
        boardTheme?.boardConcretes?.let {
            boardConcreteAdapter.apply {
                setBoardConcretes(it)
                notifyDataSetChanged()
            }
            sheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    companion object {
        const val BOARD_INFO = "board_info"
    }

}
