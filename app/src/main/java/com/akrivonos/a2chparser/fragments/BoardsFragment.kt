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
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardModel
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.retrofit.RetrofitSearchDvach
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class BoardsFragment : Fragment(), OpenDetailsBoardsBottomSheetListener {

    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var bottomSheet: FrameLayout? = null
    private var boardAdapter: BoardThemeAdapter? = null
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private var progressBarBoards: ProgressBar? = null
    private lateinit var disposable: Disposable
    private lateinit var boardConcreteAdapter: BoardConcreteAdapter
    private val observer = object : Observer<BoardModel> {
        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onNext(boardModel: BoardModel) {
            val boardThemes = boardModel.getBoardThemes(context)
            boardThemes?.let {
                boardAdapter?.setBoardThemes(it)
                boardAdapter?.notifyDataSetChanged()
            }
                progressBarBoards?.visibility = View.GONE
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
            Log.d("test", "onComplete: ")
            disposable.dispose()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
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

    override fun onDestroyView() {
        super.onDestroyView()
        disposeAll()
    }

    private fun disposeAll() {
        disposable.dispose()
    }
    private fun manageLoadBoardsInformation() {
        if (!boardAdapter?.isSet!!) {
            val context = context
            if (context != null) {
                if (!SharedPreferenceUtils.isAdultSettingsSet(context)) {
                    showAdultDialog(context)
                } else {
                    startLoadBoards()
                }
            }
        }
    }

    private fun showAdultDialog(context: Context) {
        val cdd = AdulthoodDialog(context, object : CallBack {
            override fun call() {
                startLoadBoards()
            }
        })
        cdd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        cdd.setCanceledOnTouchOutside(false)
        cdd.show()
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
        RetrofitSearchDvach.getBoards(observer)
        progressBarBoards?.visibility = View.VISIBLE
    }

    private fun setUpBottomSheetCurrent(view: View) {
        val activity = activity
        if (activity != null) {
            bottomSheet = view.findViewById(R.id.bottom_sheet_detailed_boards)
            sheetBehavior = BottomSheetBehavior.from(bottomSheet)
            sheetBehavior?.isHideable = true
            sheetBehavior?.skipCollapsed = true
            sheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN

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
            boardConcreteAdapter.setBoardConcretes(it)
            boardConcreteAdapter.notifyDataSetChanged()
            sheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    companion object {
        const val BOARD_INFO = "board_info"
    }

}
