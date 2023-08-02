package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.BaseAbilityTracker;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import java.util.HashMap;

/* loaded from: f.class */
public class f {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static IMMKV e;

    private static IMMKV a() {
        if (e != null) {
            return e;
        }
        e = MMKVCompat.module("debug_check", false);
        return e;
    }

    public static void a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("category", "CheckDebug");
        hashMap.put("action", str);
        hashMap.put("result", str2);
        BaseAbilityTracker.instance().trackEvent(StrategyFramework.getFrameworkContext(), "PERF", "Pdd.LVST", hashMap);
        c(str, str2);
    }

    public static void b(String str, String str2) {
        long j = a().getLong("mmkv_key_last_debug", 0L);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - j <= NumberUtils.instance().parseLong(RemoteConfig.instance().getConfigValue("pinduoduo_Android.track_debug_interval_58900", "7200000"))) {
            Logger.i("LVBA.AliveModule", "invalid interval, not track debug !");
        } else {
            a(str, str2);
        }
    }

    static void c(String str, String str2) {
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.track_debug_by_cs_61000", "false"))) {
            Logger.i("LVBA.AliveModule", "debug check no need to track cs !");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("category", "CheckDebug");
        hashMap.put("action", str);
        hashMap.put("result", str2);
        BaseAbilityTracker.instance().trackCsEvent(StrategyFramework.getFrameworkContext(), "perf", "Pdd.debug_check", hashMap);
    }
}
