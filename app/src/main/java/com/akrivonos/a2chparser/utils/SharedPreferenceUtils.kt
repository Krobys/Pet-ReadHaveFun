package com.akrivonos.a2chparser.utils

import android.content.Context
import androidx.preference.PreferenceManager

object SharedPreferenceUtils {
    private val ADULT_PARAMETER = "adult_parameter"
    private val ADULT_SET_FLAG = "adult_set_flag"

    fun setAdultSetting(context: Context, isAdult: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
                .putBoolean(ADULT_PARAMETER, isAdult)
                .putBoolean(ADULT_SET_FLAG, true)
                .apply()

    }

    fun getAdultSetting(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(ADULT_PARAMETER, false)
    }

    fun isAdultSettingsSet(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(ADULT_SET_FLAG, false)
    }
}