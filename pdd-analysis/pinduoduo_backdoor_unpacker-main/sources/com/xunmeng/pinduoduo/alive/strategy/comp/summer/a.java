package com.xunmeng.pinduoduo.alive.strategy.comp.summer;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    public static void d(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", " close " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception e) {
            Logger.e("LVST2.comp.SummerStrategy.SummerHelper", e);
        }
    }

    public static void a(Context context, String str, ComponentName componentName, boolean z, String str2) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            boolean z2 = RemoteConfig.instance().getBoolean(str, z);
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", str + " ab is open : " + z2 + ";\n " + componentName + " enabled = " + componentEnabledSetting);
            if (!z2) {
                if (componentEnabledSetting == 2) {
                    return;
                }
                a(context, componentName);
            } else if (a(context, str2) == 1) {
                if (componentEnabledSetting == 2) {
                    return;
                }
                a(context, componentName);
            } else if (componentEnabledSetting == 1) {
            } else {
                b(context, componentName);
            }
        } catch (Exception e) {
            Logger.e("LVST2.comp.SummerStrategy.SummerHelper", e);
        }
    }

    public static int a(Context context, String str) {
        if ("skip_blacklist".equals(str)) {
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", "skip blacklist check");
            return 0;
        }
        int i = -1;
        BlackListItem cachedConfig = MSCManager.instance().getCachedConfig(context, new SceneRequest(str));
        if (cachedConfig != null) {
            i = cachedConfig.isBlack() ? 1 : 0;
        }
        Logger.i("LVST2.comp.SummerStrategy.SummerHelper", "sceneId = " + str + "; blacklist result = " + i);
        return i;
    }

    public static void a(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", " close " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception e) {
            Logger.e("LVST2.comp.SummerStrategy.SummerHelper", e);
        }
    }

    private static boolean e(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 2;
        } catch (Exception e) {
            Logger.e("LVST2.comp.SummerStrategy.SummerHelper", e);
            return false;
        }
    }

    public static void b(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", " open " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e) {
            Logger.e("LVST2.comp.SummerStrategy.SummerHelper", e);
        }
    }

    public static String a(ArrayList arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            Logger.i("LVST2.comp.SummerStrategy.SummerHelper", "serviceInfoList is null");
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
        }
        String sb2 = sb.toString();
        Logger.i("LVST2.comp.SummerStrategy.SummerHelper", "convert result = " + sb2);
        return sb2;
    }

    public static void c(Context context, ComponentName componentName) {
        if (!e(context, componentName)) {
            d(context, componentName);
        }
    }

    public static int b(Context context, String str) {
        int i = -1;
        try {
            try {
                i = context.getPackageManager().getApplicationInfo(str, 128).uid;
                Logger.i("LVST2.comp.SummerStrategy.SummerHelper", "get " + str + " uid = " + i);
                return i;
            } catch (PackageManager.NameNotFoundException e) {
                Logger.e("LVST2.comp.SummerStrategy.SummerHelper", "getUid failed, exception e = " + e);
                return i;
            }
        } catch (Throwable th) {
            return i;
        }
    }
}
