package com.akrivonos.a2chparser.adapters.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.akrivonos.a2chparser.R
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme

class ThemeBoardViewHolder(itemView: View, bottomSheetListener: OpenDetailsBoardsBottomSheetListener) : RecyclerView.ViewHolder(itemView) {

    private val boardThemeNameTextView: TextView = itemView.findViewById(R.id.board_name)
    var boardTheme: BoardTheme? = null

    fun setBoardThemes(boardTheme: BoardTheme) {
        this.boardTheme = boardTheme
        //boardThemeNameTextView.text = boardTheme.boardThemeName
    }

    init {
        itemView.setOnClickListener { bottomSheetListener.openBottomSheet(boardTheme) }
    }
}
