package com.xunmeng.pinduoduo.alive.strategy.comp.wheel;

import android.content.ComponentName;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.HashMap;
import java.util.Map;

/* loaded from: c.class */
public class c {
    public static final String a = null;
    public static final String b = null;
    private static final String c = null;

    public static void a(HashMap hashMap, long j, ComponentName componentName) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_wheel_upload_data_59200", true)) {
            Logger.i("LVST2.comp.WheelStrategy.WheelReporter", "not report, action: %s", new Object[]{"polling_service_status"});
            return;
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelReporter", "report, action: %s", new Object[]{"polling_service_status"});
        HashMap hashMap2 = new HashMap();
        hashMap2.put("action", "polling_service_status");
        for (Map.Entry entry : hashMap.entrySet()) {
            hashMap2.put((String) entry.getKey(), entry.getValue());
            Logger.i("LVST2.comp.WheelStrategy.WheelReporter", "report, key: %s, entry: %s", new Object[]{entry.getKey(), entry.getValue()});
        }
        hashMap2.put("interval_time", Long.valueOf(j));
        hashMap2.put("pulled_component", componentName == null ? "" : componentName.getShortClassName());
        StrategyFramework.trackPerfEvent("WheelStrategy", hashMap2);
    }
}
