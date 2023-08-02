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

    public static PackageInfo a(String str) {
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

    public static Application a() {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15532160) {
            return b();
        }
        return null;
    }

    private static Application b() {
        return BaseApplication.instance().getApplication();
    }

    public static boolean a(Intent intent) {
        List b2 = b(intent);
        return b2 != null && b2.size() == 1;
    }

    public static boolean d(Intent intent) {
        List<ResolveInfo> queryIntentServices = Build.VERSION.SDK_INT >= 23 ? StrategyFramework.getFrameworkContext().getPackageManager().queryIntentServices(intent, 33554432) : StrategyFramework.getFrameworkContext().getPackageManager().queryIntentServices(intent, 0);
        return queryIntentServices != null && queryIntentServices.size() == 1;
    }

    public static List b(Intent intent) {
        return Build.VERSION.SDK_INT >= 23 ? a(intent, 33554432) : a(intent, 0);
    }

    public static boolean a(int i) {
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

    public static List a(Intent intent, int i) {
        return com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.a.a(StrategyFramework.getFrameworkContext().getPackageManager(), intent, i, "com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.utils.ComponentUtils");
    }

    private static void b(String str, Context context, Intent intent) {
        ActivityUtils.instance().startActivity(str, context, intent);
    }

    public static void a(String str, Context context, Intent intent) {
        try {
            if (!(context instanceof Activity)) {
                intent.addFlags(4096);
            }
            if (AppBuildInfo.instance().getRealVersionCode() >= 15663220) {
                b(str, context, intent);
            } else {
                context.startActivity(intent);
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.ComponentUtils", th);
        }
    }

    public static ResolveInfo c(Intent intent) {
        return Build.VERSION.SDK_INT >= 23 ? StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 33554432) : StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 0);
    }
}
