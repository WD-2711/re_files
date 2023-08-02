package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import java.util.HashMap;
import java.util.List;

/* loaded from: c.class */
public class c {
    private static final String a = null;

    private static boolean a() {
        boolean z = PddSystemProperties.instance().getBoolean("msc.config.support_clone_app", false);
        boolean z2 = PddSystemProperties.instance().getBoolean("ro.config.hw_support_clone_app", false);
        Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "msc=" + z + " ro=" + z2);
        return z || z2;
    }

    private static boolean b(String str) {
        if (Build.VERSION.SDK_INT < 17) {
            Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "sdk version low");
            return false;
        }
        Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "isDOByCrossProfileApps");
        Object a2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.f.a("crossprofileapps");
        if (a2 == null) {
            Logger.e("LVUA.Dybuild.HwSystemDoubleOpen", "crossProfileApps is null");
            return false;
        }
        Object invokeSysMethod = ReflectUtils.instance().invokeSysMethod(a2, "getTargetUserProfiles", new Object[]{str});
        if (!(invokeSysMethod instanceof List)) {
            Logger.e("LVUA.Dybuild.HwSystemDoubleOpen", "userHandles type error");
            return false;
        }
        List list = (List) invokeSysMethod;
        if (list.isEmpty()) {
            Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "userHandleList is empty");
            return false;
        }
        for (Object obj : list) {
            if (obj instanceof UserHandle) {
                Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "userHandle:" + obj);
            } else {
                Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "o type error");
            }
        }
        Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "isDOByCrossProfileApps return true");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Boolean a(String str) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_hw_double_open_62200", false)) {
                Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "hw isDoubleOpen hit ab ");
                return false;
            } else if (!a()) {
                Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "not support clone app");
                return false;
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("packageName", str);
                if (str.equals(StrategyFramework.getFrameworkContext().getPackageName()) && Build.VERSION.SDK_INT >= 17) {
                    int hashCode = Process.myUserHandle().hashCode();
                    Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "current app userId is " + hashCode);
                    hashMap.put("userId", Integer.valueOf(hashCode));
                    b.a("hw_double_open_status", hashMap);
                    if (hashCode != 0 && a(hashCode)) {
                        Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "this is a clone app");
                        return true;
                    }
                }
                boolean b = b(str);
                boolean a2 = a.a(str);
                boolean z = b || a2;
                Logger.i("LVUA.Dybuild.HwSystemDoubleOpen", "isDOByCrossProfileApps:" + b + " isDOByUserHandle:" + a2);
                hashMap.put("isDOByCrossProfileApps", b ? "1" : "0");
                hashMap.put("isDOByLauncherApps", a2 ? "1" : "0");
                hashMap.put("isDoubleOpen", z ? "1" : "0");
                b.a("hw_double_open_status", hashMap);
                if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_hw_isDOByLauncherApps_double_open_62200", true) && a2) {
                    return true;
                }
                return RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_hw_isDOByCrossProfileApps_double_open_62200", true) && b;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.HwSystemDoubleOpen", th);
            return null;
        }
    }

    private static boolean a(int i) {
        return 128 <= i && i < 148;
    }
}
