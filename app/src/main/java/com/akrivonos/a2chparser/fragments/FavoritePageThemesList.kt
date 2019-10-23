package com.akrivonos.a2chparser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.MainActivity.PAGE_MODE_ONLY_NAVBAR
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_COMMENT
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_MEDIA
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_THREAD
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.models.SaveTypeModel

class FavoritePageThemesList : Fragment() {

    private lateinit var pageDisplayListener: PageDisplayModeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        pageDisplayListener = activity as PageDisplayModeListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite_page, container, false)
        setUpScreen(view)
        return view
    }

    private fun setUpScreen(view: View) {
        val savesThemesRecView = view.findViewById<RecyclerView>(R.id.rec_view_saved_themes)
        val adapter = SaveListTypesAdapter(context, activity as OpenDetailedSavePage)
        savesThemesRecView?.layoutManager = LinearLayoutManager(context)
        savesThemesRecView?.adapter = adapter
        pageDisplayListener.setPageMode(PAGE_MODE_ONLY_NAVBAR)
        adapter.setSaveTypeList(generateSaveList())
        adapter.notifyDataSetChanged()
    }

    private fun generateSaveList(): ArrayList<SaveTypeModel> {
        val saveTypesArray = ArrayList<SaveTypeModel>()
        saveTypesArray.add(SaveTypeModel(getString(R.string.boards_name_item), SAVE_TYPE_BOARD))
        saveTypesArray.add(SaveTypeModel(getString(R.string.threads_name_item), SAVE_TYPE_THREAD))
        saveTypesArray.add(SaveTypeModel(getString(R.string.comments_name_item), SAVE_TYPE_COMMENT))
        saveTypesArray.add(SaveTypeModel(getString(R.string.media_name_item), SAVE_TYPE_MEDIA))
        return saveTypesArray
    }
}
