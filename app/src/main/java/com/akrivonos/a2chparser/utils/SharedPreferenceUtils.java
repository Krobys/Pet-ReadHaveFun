package com.akrivonos.a2chparser.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceUtils {
    private static final String ADULT_PARAMETER = "adult_parameter";
    private static final String ADULT_SET_FLAG = "adult_set_flag";

    public static void setAdultSetting(Context context, boolean isAdult) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit()
                .putBoolean(ADULT_PARAMETER, isAdult)
                .putBoolean(ADULT_SET_FLAG, true)
                .apply();

    }

    public static boolean getAdultSetting(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(ADULT_PARAMETER, false);
    }

    public static boolean isAdultSettingsSet(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(ADULT_SET_FLAG, false);
    }
}
