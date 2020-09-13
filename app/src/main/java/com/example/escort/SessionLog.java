package com.example.escort;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionLog {
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void SaveId(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_ID, id);
        editor.apply();
    }
    public static String GetId(Context context){
        return getSharedPreference(context).getString(Key.KEY_ID, null);
    }

    public static void SaveToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_TOKEN, token);
        editor.apply();
    }
    public static String GetToken(Context context){
        return getSharedPreference(context).getString(Key.KEY_TOKEN, null);
    }
    public static void SaveStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key.KEY_LOGIN,status);
        editor.apply();
    }
    public static boolean GetStatus(Context context){
        return getSharedPreference(context).getBoolean(Key.KEY_LOGIN,false);
    }
    public static void Delete(Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(Key.KEY_LOGIN);
        editor.remove(Key.KEY_TOKEN);
        editor.remove(Key.KEY_ID);
        editor.apply();
    }
}
