package com.akrivonos.a2chparser.utils

import android.content.Context
import androidx.preference.PreferenceManager

object SharedPreferenceUtils {
    private const val ADULT_PARAMETER = "adult_parameter"
    private const val ADULT_SET_FLAG = "adult_set_flag"
    private const val LAST_BOARD_VALUE = "last_board_value"

    fun setAdultSetting(context: Context?, isAdult: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
                .putBoolean(ADULT_PARAMETER, isAdult)
                .putBoolean(ADULT_SET_FLAG, true)
                .apply()

    }

    fun getAdultSetting(context: Context?): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(ADULT_PARAMETER, false)
    }

    fun isAdultSettingsSet(context: Context?): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(ADULT_SET_FLAG, false)
    }

    fun setLastBoard(nameBoard: String?, context: Context?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
                .putString(LAST_BOARD_VALUE, nameBoard)
                .apply()
    }

    fun getLastBoard(context: Context?): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(LAST_BOARD_VALUE, "b")
    }
}
