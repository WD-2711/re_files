package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.net.Uri;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotLog;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;

/* renamed from: com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.d */
/* loaded from: d.class */
public class PermissionAndUriLogger {
    private static final String a = null;

    /* renamed from: a */
    public static void logMethodCall(String str, String str2, String str3) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            BotLog.i("SAPDD", "call method[%s], perm[%s], caller[%s]", new Object[]{str2, str, str3});
        }
    }

    /* renamed from: a */
    public static void logUriUsage(Uri uri, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            BotLog.i("SAPDD", "%s uri[%s],caller[%s]", new Object[]{str, uri.toString(), str2});
        }
    }
}