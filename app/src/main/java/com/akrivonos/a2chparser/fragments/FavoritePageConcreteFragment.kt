package com.akrivonos.a2chparser.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity.PAGE_MODE_ONLY_TOOLBAR
import com.akrivonos.a2chparser.MainActivity.TOOLBAR_MODE_FULL
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.SaveConcreteBoardsAdapter
import com.akrivonos.a2chparser.adapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.adapters.SaveListTypesAdapter.Companion.SAVE_TYPE_COMMENT
import com.akrivonos.a2chparser.adapters.SaveListTypesAdapter.Companion.SAVE_TYPE_MEDIA
import com.akrivonos.a2chparser.adapters.SaveListTypesAdapter.Companion.SAVE_TYPE_THREAD
import com.akrivonos.a2chparser.database.RoomAppDatabase
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.interfaces.SetUpToolbarModeListener
import com.akrivonos.a2chparser.models.SaveTypeModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavoritePageConcreteFragment : Fragment() {

    private lateinit var pageDisplayListener: PageDisplayModeListener
    private lateinit var toolbarModeListener: SetUpToolbarModeListener
    private lateinit var adapterSaves: SaveConcreteBoardsAdapter
    private lateinit var disposable: Disposable

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
        setUpTypePage()
        return view
    }

    private fun setUpScreen(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rec_view_concrete_saves)
        adapterSaves = SaveConcreteBoardsAdapter(context)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapterSaves
        pageDisplayListener.setPageMode(PAGE_MODE_ONLY_TOOLBAR)
        setUpTypePage()
    }

    private fun setUpTypePage() {
        val saveTypeModel = arguments?.getParcelable<SaveTypeModel>(INFO_SAVE_PAGE)
        val context = context
        if (context != null) {
            val database = RoomAppDatabase.getAppDataBase(context)
            if (database != null)
                when (saveTypeModel?.typeSaveItem) {
                    SAVE_TYPE_BOARD -> {
                        disposable = database.boardsDao().getSavedBoardsList()
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .observeOn(Schedulers.io())
                                .subscribe { boardList ->
                                    if (boardList.isNotEmpty()) {
                                        adapterSaves.setBoardsList(boardList)
                                        adapterSaves.notifyDataSetChanged()
                                    } else {
                                        //TODO SET UP EMPTY MESSAGE
                                    }
                                }
                    }
                    SAVE_TYPE_THREAD -> TODO()
                    SAVE_TYPE_COMMENT -> TODO()
                    SAVE_TYPE_MEDIA -> TODO()
                }
        }
        toolbarModeListener.setMode(TOOLBAR_MODE_FULL, saveTypeModel?.nameSave)
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
