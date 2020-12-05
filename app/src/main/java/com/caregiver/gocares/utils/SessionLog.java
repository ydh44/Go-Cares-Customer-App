package com.caregiver.gocares.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionLog {
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void SaveDalamPesanan(Context context, boolean dalamPesanan){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key.KEY_DALAM_PESANAN,  dalamPesanan);
        editor.apply();
    }

    public static boolean GetDalamPesanan(Context context){
        return getSharedPreference(context).getBoolean(Key.KEY_DALAM_PESANAN, false);
    }

    public static void SaveHomeMessage(Context context, boolean homeMessage){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key.KEY_HOME_MESSAGE,  homeMessage);
        editor.apply();
    }

    public static boolean GetHomeMessage(Context context){
        return getSharedPreference(context).getBoolean(Key.KEY_HOME_MESSAGE, false);
    }


    public static void SaveHomeRefresh(Context context, boolean homeRefresh){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key.KEY_HOME_REFRESH,  homeRefresh);
        editor.apply();
    }

    public static boolean GetHomeRefresh(Context context){
        return getSharedPreference(context).getBoolean(Key.KEY_HOME_REFRESH, false);
    }

    public static void SaveFcm(Context context, String fcm){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        String cek = getSharedPreference(context).getString(Key.KEY_FCM, null);
        if(cek != null){
            editor.remove(Key.KEY_FCM);
            editor.apply();
        }
        editor.putString(Key.KEY_FCM, fcm);
        editor.apply();
    }
    public static String GetFcm(Context context){
        return getSharedPreference(context).getString(Key.KEY_FCM, null);
    }

    public static void SaveFcmUploaded(Context context, boolean uploaded){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(Key.KEY_FCM_UPLOADED, uploaded);
        editor.apply();
    }

    public static boolean GetFcmUploaded(Context context){
        return getSharedPreference(context).getBoolean(Key.KEY_FCM_UPLOADED, false);
    }

    public static void SaveId(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_ID, id);
        editor.apply();
    }
    public static String GetId(Context context){
        return getSharedPreference(context).getString(Key.KEY_ID, null);
    }

    public static void SaveUserName(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_NAME, id);
        editor.apply();
    }
    public static String GetUserName(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_NAME, null);
    }

    public static void SaveUserEmail(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_EMAIL, id);
        editor.apply();
    }
    public static String GetUserEmail(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_EMAIL, null);
    }

    public static void SaveUserAge(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_AGE, id);
        editor.apply();
    }
    public static String GetUserAge(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_AGE, null);
    }

    public static void SaveUserAddress(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_ADDRESS, id);
        editor.apply();
    }
    public static String GetUserAddress(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_ADDRESS, null);
    }

    public static void SaveUserGender(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_GENDER, id);
        editor.apply();
    }
    public static String GetUserGender(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_GENDER, null);
    }

    public static void SaveUserPhone(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(Key.KEY_USER_PHONE, id);
        editor.apply();
    }
    public static String GetUserPhone(Context context){
        return getSharedPreference(context).getString(Key.KEY_USER_PHONE, null);
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
        editor.remove(Key.KEY_USER_NAME);
        editor.remove(Key.KEY_USER_EMAIL);
        editor.remove(Key.KEY_USER_AGE);
        editor.remove(Key.KEY_USER_ADDRESS);
        editor.remove(Key.KEY_USER_GENDER);
        editor.remove(Key.KEY_USER_PHONE);
        editor.apply();
    }
}
