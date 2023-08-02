package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.HashMap;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static IMMKV f;

    private static IMMKV d() {
        if (f != null) {
            return f;
        }
        f = MMKVCompat.module("steps_info", false);
        return f;
    }

    public static void a(String str) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_step_info_track_62100", false)) {
                return;
            }
            Logger.i("LVUA.Buildin.StepsInfoAbility", "start track event");
            HashMap hashMap = new HashMap();
            hashMap.put("action", str);
            hashMap.put("business", "StepsInfoAbility");
            StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(hashMap, "perf", "alive", 0));
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.StepsInfoAbility", "track error", th);
        }
    }

    public static long a() {
        return d().getLong("last_grant_time", 0L);
    }

    public static void a(int i) {
        d().putInt("last_grant_steps", i);
    }

    public static void a(boolean z) {
        d().putBoolean("last_grant_success", z);
    }

    public static void a(long j) {
        d().putLong("last_grant_time", j);
    }

    public static int b() {
        return d().getInt("last_grant_steps", -1);
    }

    public static boolean c() {
        return d().getBoolean("last_grant_success", false);
    }
}
