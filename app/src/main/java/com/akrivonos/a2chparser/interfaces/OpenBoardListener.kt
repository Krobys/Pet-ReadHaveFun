package com.akrivonos.a2chparser.interfaces

import com.akrivonos.a2chparser.models.database.Board

interface OpenBoardListener {
    fun openBoard(board: Board?)
}
