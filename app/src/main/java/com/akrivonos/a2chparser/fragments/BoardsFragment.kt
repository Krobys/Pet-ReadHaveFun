package com.akrivonos.a2chparser.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardConcreteAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardThemeAdapter
import com.akrivonos.a2chparser.databinding.FragmentBoardsBinding
import com.akrivonos.a2chparser.dialogs.AdulthoodDialog
import com.akrivonos.a2chparser.interfaces.*
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.utils.ItemDecoratorUtils
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.akrivonos.a2chparser.viewmodels.BoardsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rxchainretrier.base.BaseFragment
import javax.inject.Inject

class BoardsFragment : BaseFragment<BoardsViewModel, FragmentBoardsBinding>(), OpenDetailsBoardsBottomSheetListener,
        OnBackPressedFragmentsListener {

    private var sheetBehavior: BottomSheetBehavior<*>? = null
    private var bottomSheet: FrameLayout? = null
    private var boardAdapter: BoardThemeAdapter? = null
    private var pageDisplayModeListener: PageDisplayModeListener? = null
    private lateinit var boardConcreteAdapter: BoardConcreteAdapter

    override val viewModelClass: Class<BoardsViewModel>
        get() = BoardsViewModel::class.java

    override var progressBar: ProgressBar? = null

    override val layoutId: Int
        get() = R.layout.fragment_boards

    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    @Inject
    lateinit var itemDecoratorUtils: ItemDecoratorUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapterAndListeners()
    }

    private fun setUpAdapterAndListeners() {
        val boardsBottomSheetListener = this
        pageDisplayModeListener = activity as PageDisplayModeListener?
        boardAdapter = BoardThemeAdapter(context, boardsBottomSheetListener)
    }

    override fun doAfterCreateView() {
        setUpScreen()
        manageLoadBoardsInformation()
    }

    private fun manageLoadBoardsInformation() {
        boardAdapter?.let {
            if (!it.isSet) {
                context?.let {
                    if (!sharedPreferenceUtils.isAdultSettingsSet(context)) {
                        showAdultDialog(context)
                    } else {
                        startLoadBoards()
                    }
                }
            }
        }
    }

    private fun showAdultDialog(context: Context?) {
        context?.let {
            AdulthoodDialog(it, object : CallBack {
                override fun call() {
                    startLoadBoards()
                }
            }).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(false)
                show()
            }
        }
    }

    private fun setUpScreen() {
            binding.boardsRecView.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 20))
                adapter = boardAdapter
            }
            progressBar = binding.progressBarBoardsTheme
            setUpBottomSheetCurrent()
        pageDisplayModeListener?.setPageMode(MainActivity.Companion.PageMode.ONLY_NAVBAR)
    }

    private fun startLoadBoards() {
        viewModel.getBoardThemes().observe(this, Observer<List<BoardTheme>> { boardThemes ->
            boardThemes?.let {
                boardAdapter?.apply {
                    setBoardThemes(it)
                    notifyDataSetChanged()
                }
            }
            binding.progressBarBoardsTheme.visibility = View.GONE
        })
        binding.progressBarBoardsTheme.visibility = View.VISIBLE
    }

    private fun setUpBottomSheetCurrent() {
        val activity = activity
        if (activity != null) {
            bottomSheet = binding.bottomSheetDetailedBoards as FrameLayout
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
            bottomSheetBoardsRecView?.addItemDecoration(itemDecoratorUtils.createItemDecorationOffsets(ItemDecoratorUtils.DecorationDirection.BOTTOM, 15))
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

    override fun onBackPressed() {
        if (sheetBehavior != null && sheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
                activity?.finish()
            }
    }


}
