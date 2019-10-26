package com.akrivonos.a2chparser.adapters.recviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.databinding.AdapteritemThemeBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme
import com.akrivonos.a2chparser.viewholders.ThemeBoardViewHolder
import java.util.*

class BoardThemeAdapter(context: Context?, private val bottomSheetListener: OpenDetailsBoardsBottomSheetListener) : RecyclerView.Adapter<ThemeBoardViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var themesBoardList = ArrayList<BoardTheme>()

    val isSet: Boolean = themesBoardList.size != 0

    fun setBoardThemes(boardThemes: List<BoardTheme>) {
        themesBoardList = ArrayList(boardThemes)
    }

    fun getBoardThemes(): List<BoardTheme> = themesBoardList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeBoardViewHolder {
        val binding = DataBindingUtil.inflate<AdapteritemThemeBoardBinding>(layoutInflater, R.layout.adapteritem_theme_board, parent, false)
        return ThemeBoardViewHolder(binding, bottomSheetListener)
    }

    override fun onBindViewHolder(holder: ThemeBoardViewHolder, position: Int) {
        val boardTheme = themesBoardList[position]
        holder.bind(boardTheme)
    }

    override fun getItemCount(): Int {
        return themesBoardList.size
    }
}
