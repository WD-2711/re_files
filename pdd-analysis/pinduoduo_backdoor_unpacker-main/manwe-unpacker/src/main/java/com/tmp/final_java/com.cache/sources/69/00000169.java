package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.BaseApplication;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.aepm.activity.ActivityUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import java.util.List;

/* loaded from: b.class */
public class b {
    public static final String a = null;
    private static final String b = null;

    /* renamed from: a */
    public static PackageInfo getPackageInfoForPackageName(String str) {
        PackageInfo packageInfo = null;
        Logger.i("LVUA.Dybuild.ComponentUtils", "get " + str + " package info");
        try {
            packageInfo = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e("LVUA.Dybuild.ComponentUtils", "pkg not exist", e);
        }
        if (packageInfo == null) {
            Logger.i("LVUA.Dybuild.ComponentUtils", "pkg not installed");
            return null;
        }
        return packageInfo;
    }

    /* renamed from: a */
    public static Application getApplicationIfSupported() {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15532160) {
            return getCurrentApplication();
        }
        return null;
    }

    /* renamed from: b */
    private static Application getCurrentApplication() {
        return BaseApplication.instance().getApplication();
    }

    /* renamed from: a */
    public static boolean hasSingleActivityForIntent(Intent intent) {
        List queryIntentActivitiesForComponentUtilsWithFilter = queryIntentActivitiesForComponentUtilsWithFilter(intent);
        return queryIntentActivitiesForComponentUtilsWithFilter != null && queryIntentActivitiesForComponentUtilsWithFilter.size() == 1;
    }

    /* renamed from: d */
    public static boolean hasSingleServiceForIntent(Intent intent) {
        List<ResolveInfo> queryIntentServices = Build.VERSION.SDK_INT >= 23 ? StrategyFramework.getFrameworkContext().getPackageManager().queryIntentServices(intent, 33554432) : StrategyFramework.getFrameworkContext().getPackageManager().queryIntentServices(intent, 0);
        return queryIntentServices != null && queryIntentServices.size() == 1;
    }

    /* renamed from: b */
    public static List queryIntentActivitiesForComponentUtilsWithFilter(Intent intent) {
        return Build.VERSION.SDK_INT >= 23 ? queryIntentActivitiesForComponentUtils(intent, 33554432) : queryIntentActivitiesForComponentUtils(intent, 0);
    }

    /* renamed from: a */
    public static boolean setComponentEnabledState(int i) {
        try {
            Logger.i("LVUA.Dybuild.ComponentUtils", "[setRyzeActivityState] state:" + i);
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            PackageManager packageManager = frameworkContext.getPackageManager();
            ComponentName componentName = new ComponentName(frameworkContext, "com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.ryze.RyzeActivity");
            int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
            Logger.i("LVUA.Dybuild.ComponentUtils", "[setRyzeActivityState] current state:" + componentEnabledSetting);
            if (componentEnabledSetting == i) {
                return true;
            }
            packageManager.setComponentEnabledSetting(componentName, i, 1);
            int componentEnabledSetting2 = packageManager.getComponentEnabledSetting(componentName);
            Logger.i("LVUA.Dybuild.ComponentUtils", "[setRyzeActivityState] final state:" + componentEnabledSetting2);
            return componentEnabledSetting2 == i;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.ComponentUtils", "[setRyzeActivityState] error.", th);
            return false;
        }
    }

    /* renamed from: a */
    public static List queryIntentActivitiesForComponentUtils(Intent intent, int i) {
        return com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.a.queryIntentActivities(StrategyFramework.getFrameworkContext().getPackageManager(), intent, i, "com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.utils.ComponentUtils");
    }

    /* renamed from: b */
    private static void startNewActivity(String str, Context context, Intent intent) {
        ActivityUtils.instance().startActivity(str, context, intent);
    }

    /* renamed from: a */
    public static void startActivityWithFlagsIfNeeded(String str, Context context, Intent intent) {
        try {
            if (!(context instanceof Activity)) {
                intent.addFlags(4096);
            }
            if (AppBuildInfo.instance().getRealVersionCode() >= 15663220) {
                startNewActivity(str, context, intent);
            } else {
                context.startActivity(intent);
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.ComponentUtils", th);
        }
    }

    /* renamed from: c */
    public static ResolveInfo getResolveInfoForActivity(Intent intent) {
        return Build.VERSION.SDK_INT >= 23 ? StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 33554432) : StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 0);
    }
}