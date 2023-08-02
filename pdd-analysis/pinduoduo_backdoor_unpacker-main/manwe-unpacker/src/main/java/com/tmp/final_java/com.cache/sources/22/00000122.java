package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.content.ContentResolver;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotLog;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotPhoneApi;

/* loaded from: e.class */
public class e {
    private static final String a = null;
    private static final String b = null;

    private static String d(ContentResolver contentResolver, String str, String str2) {
        return BotPhoneApi.getString(contentResolver, str, str2);
    }

    public static String b(ContentResolver contentResolver, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15990920) {
            return e(contentResolver, str, str2);
        }
        try {
            if (a(str)) {
                PermissionAndUriLogger.logMethodCall("read_phone_state", "getAndroidId", str2);
            }
            return Settings.Global.getString(contentResolver, str);
        } catch (Throwable th) {
            BotLog.e("SA.PhoneApi", th);
            return "";
        }
    }

    private static String e(ContentResolver contentResolver, String str, String str2) {
        return BotPhoneApi.getGlobalString(contentResolver, str, str2);
    }

    public static String c(ContentResolver contentResolver, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15990920) {
            return f(contentResolver, str, str2);
        }
        try {
            if (a(str)) {
                PermissionAndUriLogger.logMethodCall("read_phone_state", "getAndroidId", str2);
            }
            return Settings.System.getString(contentResolver, str);
        } catch (Throwable th) {
            BotLog.e("SA.PhoneApi", th);
            return "";
        }
    }

    private static String f(ContentResolver contentResolver, String str, String str2) {
        return BotPhoneApi.getSystemString(contentResolver, str, str2);
    }

    public static String a(ContentResolver contentResolver, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return d(contentResolver, str, str2);
        }
        try {
            if (a(str)) {
                PermissionAndUriLogger.logMethodCall("read_phone_state", "getAndroidId", str2);
            }
            return Settings.Secure.getString(contentResolver, str);
        } catch (Throwable th) {
            BotLog.e("SA.PhoneApi", th);
            return "";
        }
    }

    private static boolean a(String str) {
        return TextUtils.equals("android_id", str);
    }
}