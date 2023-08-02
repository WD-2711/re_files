package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.startup.ICmAutoStartManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import java.lang.reflect.Method;

/* loaded from: c.class */
public class c {
    public static final int a = 0;
    public static final int b = 0;
    public static final int c = 0;

    /* renamed from: a */
    public static boolean isSamsungDevice() {
        Logger.i("LVBA.AliveModule", "Build.MANUFACTURER:" + Build.MANUFACTURER);
        return "samsung".equalsIgnoreCase(Build.MANUFACTURER) && DeprecatedAb.instance().isFlowControl("asp_sx_5360", true);
    }

    /* renamed from: d */
    private static int getAutoStartState() {
        Object invoke;
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method method = cls.getMethod("getService", String.class);
            if (method == null || (invoke = method.invoke(cls, "cmautostart")) == null) {
                return 3;
            }
            return ICmAutoStartManager.Stub.asInterface((IBinder) invoke).getAutoStartState(frameworkContext.getPackageName(), Process.myUid()) == 1 ? 1 : 2;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", th);
            return 3;
        }
    }

    /* renamed from: e */
    private static boolean enableAutoStart() {
        Object invoke;
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method method = cls.getMethod("getService", String.class);
            if (method == null || (invoke = method.invoke(cls, "cmautostart")) == null) {
                return false;
            }
            ICmAutoStartManager.Stub.asInterface((IBinder) invoke).setAutoStartState(frameworkContext.getPackageName(), 1, 1, Process.myUid());
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", th);
            return false;
        }
    }

    /* renamed from: b */
    public static int getAutoStartStateForSamsungDevice() {
        if ("samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
            return getAutoStartState();
        }
        return 3;
    }

    /* renamed from: c */
    public static boolean enableAutoStartForSamsungDevice() {
        if ("samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
            return enableAutoStart();
        }
        return false;
    }
}