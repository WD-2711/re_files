package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.net.Uri;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotLog;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;

/* loaded from: d.class */
public class d {
    private static final String a = null;

    public static void a(String str, String str2, String str3) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            BotLog.i("SAPDD", "call method[%s], perm[%s], caller[%s]", new Object[]{str2, str, str3});
        }
    }

    public static void a(Uri uri, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            BotLog.i("SAPDD", "%s uri[%s],caller[%s]", new Object[]{str, uri.toString(), str2});
        }
    }
}
