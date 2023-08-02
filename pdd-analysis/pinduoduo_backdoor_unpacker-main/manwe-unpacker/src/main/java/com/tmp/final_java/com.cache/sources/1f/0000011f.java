package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.content.Intent;
import android.content.pm.PackageManager;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotAppListApi;
import java.util.List;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    /* renamed from: b */
    public static List getInstalledApplications(PackageManager packageManager, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return getInstalledApplicationsWithFilter(packageManager, i, str);
        }
        PermissionAndUriLogger.logMethodCall("read_applist", "getInstalledApplications", str);
        return packageManager.getInstalledApplications(i);
    }

    /* renamed from: b */
    private static Intent getLaunchIntentForPackageWithLog(PackageManager packageManager, String str, String str2) {
        return BotAppListApi.getLaunchIntentForPackage(packageManager, str, str2);
    }

    /* renamed from: a */
    public static Intent getLaunchIntentForPackage(PackageManager packageManager, String str, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16121980) {
            return getLaunchIntentForPackageWithLog(packageManager, str, str2);
        }
        PermissionAndUriLogger.logMethodCall("read_applist", "getLaunchIntentForPackage", str2);
        return packageManager.getLaunchIntentForPackage(str);
    }

    /* renamed from: c */
    public static List getInstalledApplicationsWithFilter(PackageManager packageManager, int i, String str) {
        return BotAppListApi.getInstalledApplications(packageManager, i, str);
    }

    /* renamed from: a */
    public static List queryIntentActivities(PackageManager packageManager, Intent intent, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16121980) {
            return queryIntentActivitiesWithFilter(packageManager, intent, i, str);
        }
        PermissionAndUriLogger.logMethodCall("read_applist", "queryIntentActivities", str);
        return packageManager.queryIntentActivities(intent, i);
    }

    /* renamed from: d */
    private static List getInstalledPackagesWithFilter(PackageManager packageManager, int i, String str) {
        return BotAppListApi.getInstalledPackages(packageManager, i, str);
    }

    /* renamed from: b */
    private static List queryIntentActivitiesWithFilter(PackageManager packageManager, Intent intent, int i, String str) {
        return BotAppListApi.queryIntentActivities(packageManager, intent, i, str);
    }

    /* renamed from: a */
    public static List getInstalledPackages(PackageManager packageManager, int i, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return getInstalledPackagesWithFilter(packageManager, i, str);
        }
        PermissionAndUriLogger.logMethodCall("read_applist", "getInstalledPackages", str);
        return packageManager.getInstalledPackages(i);
    }
}