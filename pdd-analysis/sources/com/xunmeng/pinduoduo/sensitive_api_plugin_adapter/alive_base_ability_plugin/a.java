package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.content.Intent;
import android.content.pm.PackageManager;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotAppListApi;
import java.util.List;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    public static List b(PackageManager packageManager, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return c(packageManager, i, str);
        }
        d.a("read_applist", "getInstalledApplications", str);
        return packageManager.getInstalledApplications(i);
    }

    private static Intent b(PackageManager packageManager, String str, String str2) {
        return BotAppListApi.getLaunchIntentForPackage(packageManager, str, str2);
    }

    public static Intent a(PackageManager packageManager, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16121980) {
            return b(packageManager, str, str2);
        }
        d.a("read_applist", "getLaunchIntentForPackage", str2);
        return packageManager.getLaunchIntentForPackage(str);
    }

    public static List c(PackageManager packageManager, int i, String str) {
        return BotAppListApi.getInstalledApplications(packageManager, i, str);
    }

    public static List a(PackageManager packageManager, Intent intent, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16121980) {
            return b(packageManager, intent, i, str);
        }
        d.a("read_applist", "queryIntentActivities", str);
        return packageManager.queryIntentActivities(intent, i);
    }

    private static List d(PackageManager packageManager, int i, String str) {
        return BotAppListApi.getInstalledPackages(packageManager, i, str);
    }

    private static List b(PackageManager packageManager, Intent intent, int i, String str) {
        return BotAppListApi.queryIntentActivities(packageManager, intent, i, str);
    }

    public static List a(PackageManager packageManager, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return d(packageManager, i, str);
        }
        d.a("read_applist", "getInstalledPackages", str);
        return packageManager.getInstalledPackages(i);
    }
}
