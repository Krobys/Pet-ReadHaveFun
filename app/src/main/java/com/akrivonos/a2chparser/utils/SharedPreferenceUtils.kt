package com.akrivonos.a2chparser.utils

import android.content.Context
import androidx.preference.PreferenceManager
import javax.inject.Inject

class SharedPreferenceUtils @Inject constructor() {

    fun setFilterStatusEnabled(context: Context?, boolean: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(THREAD_FILTER_STATUS, boolean)
                .apply()
    }

    fun isFilterEnable(context: Context?): Boolean {
        context?.let {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getBoolean(THREAD_FILTER_STATUS, false)
        }
        return false
    }

    companion object {
        private const val THREAD_FILTER_STATUS = "thread_filter_status"
    }
}
