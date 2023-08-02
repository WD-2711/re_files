package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import com.xunmeng.pinduoduo.alive_adapter_sdk.BotMMKV;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotSensitive;

/* loaded from: h.class */
public class h {
    public static boolean a() {
        if (BotAppBuildInfo.getRealVersionCode() >= 16253040) {
            return c();
        }
        if (BotAppBuildInfo.getRealVersionCode() < 15073300) {
            return true;
        }
        return b();
    }

    private static boolean b() {
        return BotMMKV.module("force_permission", true).getInt("privacy_passed_5200", 0) == 1;
    }

    private static boolean c() {
        return BotSensitive.privacyPassed();
    }
}
