package com.akrivonos.a2chparser.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.akrivonos.a2chparser.databinding.AdapteritemThemeBoardBinding
import com.akrivonos.a2chparser.interfaces.OpenDetailsBoardsBottomSheetListener
import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme

class ThemeBoardViewHolder(private var binding: AdapteritemThemeBoardBinding,
                           private val bottomSheetListener: OpenDetailsBoardsBottomSheetListener)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(boardTheme: BoardTheme) {
        binding.boardTheme = boardTheme
        binding.holder = this
        binding.executePendingBindings()
    }

    fun openBottomSheet() {
        bottomSheetListener.openBottomSheet(binding.boardTheme)
    }
}
