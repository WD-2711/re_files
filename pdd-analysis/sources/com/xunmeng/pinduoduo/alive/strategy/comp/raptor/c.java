package com.xunmeng.pinduoduo.alive.strategy.comp.raptor;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.util.HashMap;

/* loaded from: c.class */
public class c {
    public static final String a = null;
    public static final String b = null;
    private static final String c = null;

    public static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVST2.comp.RaptorStrategy.RaptorReporter", "track error, no action");
            return;
        }
        Logger.i("LVST2.comp.RaptorStrategy.RaptorReporter", "report action = " + str);
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("rom_version", RomOsUtil.instance().getVersion());
        StrategyFramework.trackPerfEvent(a(), hashMap);
    }

    public static void a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Logger.e("LVST2.comp.RaptorStrategy.RaptorReporter", "track error, no action");
            return;
        }
        Logger.i("LVST2.comp.RaptorStrategy.RaptorReporter", "report param = " + str + "; value = " + str2);
        HashMap hashMap = new HashMap();
        hashMap.put(str, str2);
        StrategyFramework.trackPerfEvent(a(), hashMap);
    }

    private static String a() {
        return "RaptorStrategy";
    }

    public static void a(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVST2.comp.RaptorStrategy.RaptorReporter", "track error, no action");
            return;
        }
        Logger.i("LVST2.comp.RaptorStrategy.RaptorReporter", "report action = " + str + "; activeTime = " + j);
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("active_time", Long.valueOf(j));
        hashMap.put("rom_version", RomOsUtil.instance().getVersion());
        if (a.g()) {
            Logger.i("LVST2.comp.RaptorStrategy.RaptorReporter", "report data by cs event");
            StrategyFramework.trackCsEvent(a(), new TrackEventOption(hashMap, "PERF", "Pdd.LVST", 0));
        }
        Logger.i("LVST2.comp.RaptorStrategy.RaptorReporter", "report data by perf event");
        StrategyFramework.trackPerfEvent(a(), hashMap);
    }
}
