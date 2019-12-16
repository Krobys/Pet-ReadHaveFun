package com.akrivonos.a2chparser.fragments

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.activities.MainActivity
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_BOARD
import com.akrivonos.a2chparser.adapters.recviewadapters.SaveListTypesAdapter.Companion.SAVE_TYPE_NOVALUE
import com.akrivonos.a2chparser.base.BaseFragmentWithoutViewModel
import com.akrivonos.a2chparser.databinding.FragmentFavoritePageBinding
import com.akrivonos.a2chparser.interfaces.NavBarDisplayModeListener
import com.akrivonos.a2chparser.interfaces.OpenDetailedSavePage
import com.akrivonos.a2chparser.models.SaveTypeModel

class FavoritePageThemesList : BaseFragmentWithoutViewModel<FragmentFavoritePageBinding>(){

    private lateinit var pageDisplayListener: NavBarDisplayModeListener


    override val layoutId: Int
        get() = R.layout.fragment_favorite_page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        pageDisplayListener = activity as NavBarDisplayModeListener
    }

    override fun setUpScreen() {
        binding.recViewSavedThemes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SaveListTypesAdapter(context, activity as OpenDetailedSavePage).apply {
                setSaveTypeList(generateSaveList())
                notifyDataSetChanged()
            }
        }
        pageDisplayListener.setNavbarMode(MainActivity.Companion.NavbarMode.VISIBLE)

    }

    private fun generateSaveList(): ArrayList<SaveTypeModel> {
        val saveTypesArray = ArrayList<SaveTypeModel>()
        saveTypesArray.add(SaveTypeModel(getString(R.string.boards_name_item), SAVE_TYPE_BOARD))
//        saveTypesArray.add(SaveTypeModel(getString(R.string.threads_name_item), SAVE_TYPE_THREAD))
//        saveTypesArray.add(SaveTypeModel(getString(R.string.comments_name_item), SAVE_TYPE_COMMENT))
//        saveTypesArray.add(SaveTypeModel(getString(R.string.media_name_item), SAVE_TYPE_MEDIA))
        saveTypesArray.add(SaveTypeModel(getString(R.string.threads_name_item), SAVE_TYPE_NOVALUE))
        saveTypesArray.add(SaveTypeModel(getString(R.string.comments_name_item), SAVE_TYPE_NOVALUE))
        saveTypesArray.add(SaveTypeModel(getString(R.string.media_name_item), SAVE_TYPE_NOVALUE))
        return saveTypesArray
    }

}
