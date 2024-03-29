package com.akrivonos.a2chparser.fragments


import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardConcreteAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_COMMENT
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_MEDIA
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_THREAD
import com.akrivonos.a2chparser.base.BaseFragmentWithoutViewModel
import com.akrivonos.a2chparser.database.BoardsDao
import com.akrivonos.a2chparser.databinding.FragmentFavoritePageConcreteBinding
import com.akrivonos.a2chparser.interfaces.NavBarDisplayModeListener
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.models.SaveTypeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoritePageConcreteFragment : BaseFragmentWithoutViewModel<FragmentFavoritePageConcreteBinding>() {

    private lateinit var pageDisplayListener: NavBarDisplayModeListener
    private lateinit var disposable: Disposable
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyMessage: RelativeLayout
    @Inject
    lateinit var boardsDao: BoardsDao

    override val layoutId: Int
        get() = R.layout.fragment_favorite_page_concrete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        pageDisplayListener = activity as NavBarDisplayModeListener
    }

    override fun setUpScreen() {
        recyclerView = binding.recViewConcreteSaves
        recyclerView.layoutManager = LinearLayoutManager(context)
        pageDisplayListener.setNavbarMode(MainActivity.Companion.NavbarMode.VISIBLE)
        emptyMessage = binding.empMes as RelativeLayout
        progressBar = binding.progressBar
        setUpTypePage()
    }

    private fun setUpTypePage() {
        val saveTypeModel = arguments?.getParcelable<SaveTypeModel>(INFO_SAVE_PAGE)
        context?.let {
            when (saveTypeModel?.typeSaveItem) {
                SAVE_TYPE_BOARD -> {
                    val boardConcreteAdapter = BoardConcreteAdapter(it, activity as OpenBoardListener)
                    recyclerView.adapter = boardConcreteAdapter
                    disposable = boardsDao.getSavedBoardsList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { boardList ->
                                if (boardList.isNotEmpty()) {
                                    boardConcreteAdapter.setBoards(boardList)
                                    boardConcreteAdapter.notifyDataSetChanged()
                                    hideEmptyMessage()
                                } else {
                                    showEmptyMessage()
                                }

                            }
                }
                SAVE_TYPE_THREAD -> TODO()
                SAVE_TYPE_COMMENT -> TODO()
                SAVE_TYPE_MEDIA -> TODO()
            }
        }
    }

    private fun showEmptyMessage() {
        recyclerView.visibility = View.GONE
        emptyMessage.visibility = View.VISIBLE
    }

    private fun hideEmptyMessage() {
        recyclerView.visibility = View.VISIBLE
        emptyMessage.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeAll()
    }

    private fun disposeAll() {
        disposable.dispose()
    }

    companion object {
        const val INFO_SAVE_PAGE = "info_save_page"
    }

}
