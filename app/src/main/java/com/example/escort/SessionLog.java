package com.example.escort;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionLog {
    SharedPreferences pref;

    public static boolean SaveEmail(Context context, String email){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Key.KEY_EMAIL, email);
        editor.apply();
        return true;
    }
    public String GetEmail(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(Key.KEY_EMAIL, null);
    }
    public static boolean SavePassword(Context context, String password){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Key.KEY_PASSWORD, password);
        editor.apply();
        return true;
    }
    public String GetPassword(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(Key.KEY_PASSWORD, null);
    }
}
