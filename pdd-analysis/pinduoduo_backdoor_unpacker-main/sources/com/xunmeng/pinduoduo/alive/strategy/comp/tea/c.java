package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;

/* loaded from: c.class */
public class c {
    private static final String a = null;

    public static boolean a() {
        IMMKV module = MMKVCompat.module("FKDemoService", false);
        long j = module.getLong("DISABLE_TIME", 0L);
        long realLocalTimeV2 = TimeStamp.instance().getRealLocalTimeV2();
        long j2 = RemoteConfig.instance().getLong("pinduoduo_Android.pa_strategy_fk_demo_sleeping_limit_64900", 0L);
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper2", "shouldEnableMiuiScreenServices " + realLocalTimeV2 + " " + j + " " + j2);
        if (realLocalTimeV2 - j <= j2) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper2", "still sleep");
            return false;
        }
        module.putLong("DISABLE_TIME", 0L);
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper2", "enable");
        return true;
    }
}
