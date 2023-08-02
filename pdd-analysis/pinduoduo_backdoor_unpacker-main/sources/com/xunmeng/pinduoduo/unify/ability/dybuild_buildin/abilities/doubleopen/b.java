package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final long c = 0;
    private static final IMMKV d = MMKVCompat.module("DoubleOpenTracker", false);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Throwable th) {
        HashMap hashMap = new HashMap();
        hashMap.put("error_msg", Log.getStackTraceString(th));
        a("do_error", hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("msg", str2);
        a(str, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, Map map) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.dybuild_double_open_track_61900", false)) {
                return;
            }
            long parseLong = NumberUtils.instance().parseLong(RemoteConfig.instance().getConfigValue("pinduoduo_Android.dybuild_double_open_track_interval_62300", String.valueOf(0L)), 0L);
            long currentTimeMillis = System.currentTimeMillis() - d.getLong(AppUtils.instance().getPddId(), 0L);
            Logger.i("LVUA.Dybuild.DoubleOpenTracker", "diff:" + currentTimeMillis);
            if (currentTimeMillis < parseLong) {
                Logger.i("LVUA.Dybuild.DoubleOpenTracker", "double open tracker in cd");
                return;
            }
            Logger.i("LVUA.Dybuild.DoubleOpenTracker", "start track event");
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            if (!TextUtils.isEmpty(str)) {
                hashMap.put("log_action", str);
            }
            hashMap.put("business", "DoubleOpenAbility");
            g.a("", 23134263, new TrackEventOption(hashMap, "perf", "alive", 0));
            d.putLong(AppUtils.instance().getPddId(), System.currentTimeMillis());
            Logger.i("LVUA.Dybuild.DoubleOpenTracker", "tracker end");
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.DoubleOpenTracker", th);
        }
    }
}
