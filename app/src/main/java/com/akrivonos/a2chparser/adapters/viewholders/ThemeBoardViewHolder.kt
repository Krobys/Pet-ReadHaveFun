package com.akrivonos.a2chparser.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.databinding.AdapteritemThemeBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme

class ThemeBoardViewHolder(private var bindingImpl: AdapteritemThemeBoardBinding, bottomSheetListener: OpenDetailsBoardsBottomSheetListener) : RecyclerView.ViewHolder(bindingImpl.root) {

    fun bind(boardTheme: BoardTheme) {
        bindingImpl.boardTheme = boardTheme
        bindingImpl.executePendingBindings()
    }

    init {
        itemView.setOnClickListener { bottomSheetListener.openBottomSheet(bindingImpl.boardTheme) }
    }
}
