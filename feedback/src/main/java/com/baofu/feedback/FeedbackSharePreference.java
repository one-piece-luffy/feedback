package com.baofu.feedback;

import android.content.Context;
import android.content.SharedPreferences;


public class FeedbackSharePreference {

    public static void savePraise(Context context,boolean boo) {

        SharedPreferences mSharedPreferences =context.getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("feedback_app_praise", boo);
        editor.commit();
    }

    public static boolean getPraise(Context context) {
        SharedPreferences sp = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        boolean result = sp.getBoolean("feedback_app_praise", false);
        return result;

    }

    public static void saveUUID(Context context,String boo) {

        SharedPreferences mSharedPreferences =context.getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("feedback_app_uuid", boo);
        editor.apply();
    }

    public static String getUUID(Context context) {
        SharedPreferences sp = context.getSharedPreferences("app", Context.MODE_PRIVATE);
        String result = sp.getString("feedback_app_uuid", null);
        return result;

    }


}
