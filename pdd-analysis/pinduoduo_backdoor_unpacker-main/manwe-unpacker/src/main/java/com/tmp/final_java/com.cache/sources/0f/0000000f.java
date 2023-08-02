package com.xunmeng.pinduoduo.alive.base.ability.impl.doubleinstance;

import android.app.ActivityManager;
import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.io.File;
import java.lang.reflect.Method;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.doubleinstance.a */
/* loaded from: a.class */
public class DoubleInstanceDetector implements a.AnonymousClass1 {
    private static final String a = null;
    private final String c = "/data/";
    private final Context b = StrategyFramework.getFrameworkContext();

    /* renamed from: a */
    private boolean isDirExist(String str, int i) {
        return new File("/data/user/" + i + "/" + str).exists();
    }

    public Boolean isDoubleOpen(String str) {
        int deviceId = getDeviceId();
        if (deviceId == -1) {
            return null;
        }
        return Boolean.valueOf(deviceId != 0 && isDirExist(str, deviceId));
    }

    /* renamed from: a */
    private int getDeviceId() {
        if (RomOsUtil.instance().isVivo() || RomOsUtil.instance().isVivoManufacture() || RomOsUtil.instance().isOppo() || RomOsUtil.instance().isOppoManufacture() || RomOsUtil.instance().isXiaomiManufacture()) {
            return new File("/data/user/999").exists() ? 999 : 0;
        } else if (!RomOsUtil.instance().isNewHuaweiManufacture()) {
            return -1;
        } else {
            ActivityManager activityManager = (ActivityManager) this.b.getSystemService("activity");
            try {
                Method method = ActivityManager.class.getMethod("isUserRunning", Integer.TYPE);
                if (method == null) {
                    return -1;
                }
                for (int i = 1; i <= 999; i++) {
                    if (((Boolean) method.invoke(activityManager, Integer.valueOf(i))).booleanValue()) {
                        return i;
                    }
                }
                return 0;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.DoubleInstance", th);
                return -1;
            }
        }
    }
}