package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze;

import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.HashMap;
import java.util.Map;

/* loaded from: c.class */
class c {
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

    public static void a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("msg", str2);
        a(str, hashMap);
    }

    c() {
    }

    public static void a(String str, Map map) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_track_61700", false)) {
                return;
            }
            Logger.i("LVUA.Buildin.RyzeTracker", "start track event");
            HashMap hashMap = new HashMap();
            if (map != null) {
                hashMap.putAll(map);
            }
            if (!TextUtils.isEmpty(str)) {
                hashMap.put("log_action", str);
            }
            hashMap.put("business", "RyzePopupAbility");
            g.a("", 23134263, new TrackEventOption(hashMap, "perf", "alive", 0));
            Logger.i("LVUA.Buildin.RyzeTracker", "tracker end");
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.RyzeTracker", th);
        }
    }
}
