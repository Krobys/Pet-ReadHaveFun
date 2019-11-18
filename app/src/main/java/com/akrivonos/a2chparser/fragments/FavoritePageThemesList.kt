package com.akrivonos.a2chparser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.MainActivity
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.databinding.FragmentFavoritePageBinding
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage
import com.akrivonos.a2chparser.interfaces.PageDisplayModeListener
import com.akrivonos.a2chparser.models.SaveTypeModel

class FavoritePageThemesList : Fragment() {

    private lateinit var binding: FragmentFavoritePageBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_page, container, false)
        setUpScreen()
        return binding.root
    }

    private fun setUpScreen() {
        binding.recViewSavedThemes?.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =SaveListTypesAdapter(context, activity as OpenDetailedSavePage).apply {
                setSaveTypeList(generateSaveList())
                notifyDataSetChanged()
            }
        }
        pageDisplayListener.setPageMode(MainActivity.Companion.PageMode.ONLY_NAVBAR)

    }

    private fun generateSaveList(): ArrayList<SaveTypeModel> {
        val saveTypesArray = ArrayList<SaveTypeModel>()
        saveTypesArray.add(SaveTypeModel(getString(R.string.boards_name_item), SAVE_TYPE_BOARD))
        //saveTypesArray.add(SaveTypeModel(getString(R.string.threads_name_item), SAVE_TYPE_THREAD))
        //saveTypesArray.add(SaveTypeModel(getString(R.string.comments_name_item), SAVE_TYPE_COMMENT))
        //saveTypesArray.add(SaveTypeModel(getString(R.string.media_name_item), SAVE_TYPE_MEDIA))
        return saveTypesArray
    }
}
