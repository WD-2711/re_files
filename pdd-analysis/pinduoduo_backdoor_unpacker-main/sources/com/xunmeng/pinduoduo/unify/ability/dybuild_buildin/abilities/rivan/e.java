package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.HashMap;

/* loaded from: e.class */
class e {
    private static final String a = null;

    public static void a(String str, String str2) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.lvua_start_bg_by_pa_track_61900", false)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("business", "RivanStartAbility");
        hashMap.put("log_action", str);
        hashMap.put("message", str2);
        g.a("", 23134263, new TrackEventOption(hashMap, "perf", "alive", (Integer) null));
    }

    e() {
    }
}
