package com.baofu.feedback;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.Locale;

public class FeedbackUtils {
    /**
     * 获取app versionCode
     */
    public static int getAppVersionCode(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取获取app versionName
     */
    public static String getAppVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().toString();
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机设备名
     *
     * @return  手机设备名
     */
    public static String getSystemDevice() {
        return Build.DEVICE;
    }
    @SuppressLint("HardwareIds")
    public static String getUuid(Context context) {
//        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        if (TextUtils.isEmpty(id)) {
//            id = Build.SERIAL;
//        }
//        return id;
        String uuid = FeedbackSharePreference.getUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            try {
                uuid = java.util.UUID.randomUUID().toString();
                FeedbackSharePreference.saveUUID(context, uuid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return uuid;
    }

    public static String getDeviceInfo(Context context) {

        return "语言:" + getSystemLanguage() + " 版本号：" + getSystemVersion() + " 型号:" + getSystemModel() + " 名称：" + getSystemDevice() + "uuid:" + getUuid(context);
    }
}
