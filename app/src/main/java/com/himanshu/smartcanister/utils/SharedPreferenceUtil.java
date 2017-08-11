package com.himanshu.smartcanister.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ritwick on 11/8/17.
 */

public class SharedPreferenceUtil
{
    static String PREFS="smartcanisterpreferences";
    Context context;
    static SharedPreferences prefs;
    static SharedPreferences.Editor prefedit;

    public static void saveNewUser(Context context,Boolean b)
    {
        prefs = context.getSharedPreferences(PREFS, MODE_PRIVATE);
        prefedit=prefs.edit();
        prefedit.putBoolean("NEWUSER",b).apply();
    }

    public static Boolean getNewUser(Context context)
    {
        prefs = context.getSharedPreferences(PREFS, MODE_PRIVATE);
        return prefs.getBoolean("NEWUSER",true);
    }
}
