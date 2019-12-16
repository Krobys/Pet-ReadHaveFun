package com.akrivonos.a2chparser.pojomodel.boardmodel

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class BoardModel {

    @SerializedName("Игры")
    @Expose
    var games: ArrayList<BoardConcrete>? = null
    @SerializedName("Политика")
    @Expose
    var politic: ArrayList<BoardConcrete>? = null
    @SerializedName("Пользовательские")
    @Expose
    var custom: ArrayList<BoardConcrete>? = null
    @SerializedName("Разное")
    @Expose
    var different: ArrayList<BoardConcrete>? = null
    @SerializedName("Творчество")
    @Expose
    var art: ArrayList<BoardConcrete>? = null
    @SerializedName("Тематика")
    @Expose
    var theme: ArrayList<BoardConcrete>? = null
    @SerializedName("Техника и софт")
    @Expose
    var tech: ArrayList<BoardConcrete>? = null
    @SerializedName("Японская культура")
    @Expose
    var japan: ArrayList<BoardConcrete>? = null

    @Ignore
    fun getBoardThemes(): List<BoardTheme>? {
        val boardThemes = ArrayList<BoardTheme>()
        boardThemes.apply {
            add(BoardTheme(games))
            add(BoardTheme(politic))
            add(BoardTheme(custom))
            add(BoardTheme(different))
            add(BoardTheme(art))
            add(BoardTheme(theme))
            add(BoardTheme(tech))
            add(BoardTheme(japan))
        }
        return boardThemes
    }

}
