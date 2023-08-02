package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.HashMap;

/* loaded from: e.class */
public class e {
    public static final String a = null;
    private static final String b = null;

    public static void a(String str) {
        b("self_disable_" + str);
    }

    public static void b(String str) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_tea_upload_data_58600", true)) {
            Logger.i("LVST2.comp.TeaStrategy.TeaReporter", "not report, action: %s", new Object[]{str});
            return;
        }
        Logger.i("LVST2.comp.TeaStrategy.TeaReporter", "report, action: %s", new Object[]{str});
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        StrategyFramework.trackPerfEvent("TeaStrategy", hashMap);
    }
}
