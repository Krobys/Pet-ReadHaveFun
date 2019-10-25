package com.akrivonos.a2chparser.pojomodel.boardmodel

import android.content.Context
import com.akrivonos.a2chparser.utils.SharedPreferenceUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class BoardModel {

    @SerializedName("Взрослым")
    @Expose
    var adult: ArrayList<BoardConcrete>? = null
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

    fun getBoardThemes(context: Context?): List<BoardTheme>? {
        val boardThemes = ArrayList<BoardTheme>()
        var isAdult = false
        context?.let {
            isAdult = SharedPreferenceUtils.getAdultSetting(it)
        }
        if (isAdult)
            boardThemes.add(BoardTheme(adult))
        boardThemes.add(BoardTheme(games))
        boardThemes.add(BoardTheme(politic))
        boardThemes.add(BoardTheme(custom))
        boardThemes.add(BoardTheme(different))
        boardThemes.add(BoardTheme(art))
        boardThemes.add(BoardTheme(theme))
        boardThemes.add(BoardTheme(tech))
        boardThemes.add(BoardTheme(japan))
        return boardThemes
    }

}
