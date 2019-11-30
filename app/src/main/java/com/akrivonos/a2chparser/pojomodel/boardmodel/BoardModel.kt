package com.akrivonos.a2chparser.pojomodel.boardmodel

import android.content.Context
import android.util.Log
import androidx.room.Ignore
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

    private var preferenceUtils: SharedPreferenceUtils = SharedPreferenceUtils()

    @Ignore
    fun getBoardThemes(context: Context?): List<BoardTheme>? {
        val boardThemes = ArrayList<BoardTheme>()
        val boardTestThemes = ArrayList<BoardTheme?>()
        var isAdult = false
        context?.let {
            isAdult = preferenceUtils.getAdultSetting(it)
        }
        boardThemes.apply {
            if (isAdult)
                add(BoardTheme(adult))
            add(BoardTheme(games))
            add(BoardTheme(politic))
            add(BoardTheme(custom))
            add(BoardTheme(different))
            add(BoardTheme(art))
            add(BoardTheme(theme))
            add(BoardTheme(tech))
            add(BoardTheme(japan))
        }

        boardTestThemes.apply {
            if (isAdult)
                add(BoardTheme(adult))
            add(BoardTheme(games))
            add(BoardTheme(politic))
            add(BoardTheme(custom))
            add(BoardTheme(different))
            add(BoardTheme(art))
            add(BoardTheme(theme))
            add(BoardTheme(tech))
            add(BoardTheme(japan))
        }

        for (boardTheme in boardTestThemes) {
            if (boardTheme?.boardThemeName == null) {
                Log.d("test", "is null: ")
                return null
            }
        }
        return boardThemes
    }

}
