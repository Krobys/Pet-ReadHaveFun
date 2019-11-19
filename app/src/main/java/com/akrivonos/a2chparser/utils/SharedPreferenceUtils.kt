package com.akrivonos.a2chparser.utils

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferenceUtils @Inject constructor() {

    fun setAdultSetting(context: Context?, isAdult: Boolean) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
                .putBoolean(Companion.ADULT_PARAMETER, isAdult)
                .putBoolean(Companion.ADULT_SET_FLAG, true)
                .apply()

    }

    fun getAdultSetting(context: Context?): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(Companion.ADULT_PARAMETER, false)
    }

    fun isAdultSettingsSet(context: Context?): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getBoolean(Companion.ADULT_SET_FLAG, false)
    }

    fun changeFilterThreadStatus(context: Context?){
        context?.let{
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(THREAD_FILTER_STATUS, !isFilterThreadsEnable(context))
                    .apply()
        }
    }

    fun isFilterThreadsEnable(context: Context?): Boolean {
        context?.let {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getBoolean(THREAD_FILTER_STATUS, false)
        }
        return false
    }

    companion object {
        private const val ADULT_SET_FLAG = "adult_set_flag"
        private const val ADULT_PARAMETER = "adult_parameter"
        private const val THREAD_FILTER_STATUS = "thread_filter_status"
    }
}
