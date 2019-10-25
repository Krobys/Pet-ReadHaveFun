package com.akrivonos.a2chparser.interfaces

import com.akrivonos.a2chparser.pojomodel.boardmodel.BoardTheme

interface OpenDetailsBoardsBottomSheetListener {
    fun openBottomSheet(boardTheme: BoardTheme?)
}
