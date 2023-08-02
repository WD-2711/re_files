package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DateUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;

/* loaded from: b.class */
public class b extends e {
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static IMMKV g;

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public KarmaResult b() {
        long c2 = c();
        long d2 = d();
        long realLocalTimeV2 = TimeStamp.instance().getRealLocalTimeV2();
        if (c2 >= d2) {
            Logger.i(a, "last failure");
            return new KarmaResult("last_get_failure");
        } else if (!DateUtils.instance().isSameDay(d2, realLocalTimeV2)) {
            Logger.i(a, "no same day");
            return new KarmaResult("today_not_success");
        } else {
            int e2 = e();
            Logger.i(a, "last get time : %s, last step info is : %s", new Object[]{Long.valueOf(d2), Integer.valueOf(e2)});
            return new KarmaResult(e2, d2);
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public boolean a() {
        return true;
    }

    private static IMMKV f() {
        if (g != null) {
            return g;
        }
        g = MMKVCompat.module("karma_strategy", false);
        return g;
    }

    static long c() {
        return f().getLong("last_failure_time", 0L);
    }

    static long d() {
        return f().getLong("last_step_info_time", 0L);
    }

    static int e() {
        return f().getInt("last_steps_info", -1);
    }
}
