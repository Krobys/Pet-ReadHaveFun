package com.akrivonos.a2chparser.pojomodel.boardmodel

import com.google.gson.annotations.Expose

class BoardTheme(boardConcretes: List<BoardConcrete>?) {

    @Expose
    var boardThemeName: String? = null
    @Expose
    var boardConcretes: List<BoardConcrete>? = null

    init {
        if (boardConcretes != null && boardConcretes.isNotEmpty()) {
            boardThemeName = boardConcretes[0].category
            this.boardConcretes = boardConcretes
        }
    }


}
