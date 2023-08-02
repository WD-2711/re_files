package com.xunmeng.pinduoduo.alive.strategy.comp.raptor;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: e.class */
public class e {
    private static final String a = null;
    private static final int b = 0;
    private static final int c = 0;

    public static void a(Context context) {
        a(context, 1);
    }

    private static void a(Context context, int i) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, "com.xunmeng.pinduoduo.service.raptor.MediaService");
        if (i == packageManager.getComponentEnabledSetting(componentName)) {
            Logger.i("LVST2.comp.RaptorStrategy.RaptorUtil", "service is already " + (i == 1 ? "enabled" : "disabled") + ", return");
            return;
        }
        Logger.i("LVST2.comp.RaptorStrategy.RaptorUtil", (i == 1 ? "enable" : "disable") + " service");
        packageManager.setComponentEnabledSetting(componentName, i, 1);
        c.a(i == 1 ? "enable_media_service" : "disable_media_service");
    }

    public static void b(Context context) {
        a(context, 2);
    }
}
