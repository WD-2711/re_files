package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave;

import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;

    public static void a(Throwable th) {
        HashMap hashMap = new HashMap();
        hashMap.put("error_msg", Log.getStackTraceString(th));
        a("ryze_error", hashMap);
    }

    public static void a(String str) {
        a(str, new HashMap());
    }

    public static void a(String str, Map map) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_power_save_track_61800", false)) {
                return;
            }
            Logger.i("LVUA.Buildin.PowerSaveTracker", "start track event");
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            if (!TextUtils.isEmpty(str)) {
                hashMap.put("log_action", str);
            }
            hashMap.put("business", "PowerSave");
            StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(hashMap, "perf", "alive", 0));
            Logger.i("LVUA.Buildin.PowerSaveTracker", "tracker end");
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.PowerSaveTracker", th);
            a(th);
        }
    }
}
