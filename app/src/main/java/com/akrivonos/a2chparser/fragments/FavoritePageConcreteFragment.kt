package com.akrivonos.a2chparser.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity.Companion.PAGE_MODE_ONLY_TOOLBAR
import com.akrivonos.a2chparser.MainActivity.Companion.TOOLBAR_MODE_FULL
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.BoardConcreteAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_COMMENT
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_MEDIA
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_THREAD
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.interfaces.OpenBoardListener
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.models.SaveTypeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavoritePageConcreteFragment : Fragment() {

    private lateinit var pageDisplayListener: PageDisplayModeListener
    private lateinit var toolbarModeListener: SetUpToolbarModeListener
    private lateinit var disposable: Disposable
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyMessage: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        pageDisplayListener = activity as PageDisplayModeListener
        toolbarModeListener = activity as SetUpToolbarModeListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_page_concrete, container, false)
        setUpScreen(view)
        return view
    }

    private fun setUpScreen(view: View) {
        recyclerView = view.findViewById(R.id.rec_view_concrete_saves)
        recyclerView.layoutManager = LinearLayoutManager(context)
        pageDisplayListener.setPageMode(PAGE_MODE_ONLY_TOOLBAR)
        emptyMessage = view.findViewById(R.id.empty_message)
        setUpTypePage()
    }

    private fun setUpTypePage() {
        val saveTypeModel = arguments?.getParcelable<SaveTypeModel>(INFO_SAVE_PAGE)
        val context = context
        if (context != null) {
            val database = RoomAppDatabase.getAppDataBase(context)
            if (database != null) {
                when (saveTypeModel?.typeSaveItem) {
                    SAVE_TYPE_BOARD -> {
                        val boardConcreteAdapter = BoardConcreteAdapter(context, activity as OpenBoardListener)
                        recyclerView.adapter = boardConcreteAdapter
                        Log.d("test", "SAVE_TYPE_BOARD:")
                        disposable = database.boardsDao().getSavedBoardsList()
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
        toolbarModeListener.setMode(TOOLBAR_MODE_FULL, saveTypeModel?.nameSave)
    }

    fun showEmptyMessage() {
        recyclerView.visibility = View.GONE
        emptyMessage.visibility = View.VISIBLE
    }

    fun hideEmptyMessage() {
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
