package com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;

/* loaded from: a.class */
public class a {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;

    public static boolean b(String str) {
        Logger.i("LVBA.AliveAbility", "isAbilityDisabled2023Q1: " + str);
        if (!TextUtils.isEmpty(str) && !c()) {
            boolean a2 = a("ab_key_alive_ability_disable_2023_q1_64600", false);
            if (!a2) {
                return a2;
            }
            Logger.i("LVBA.AliveAbility", "alive ability is disabled");
            return true;
        }
        return true;
    }

    public static boolean a() {
        return false;
    }

    private static boolean a(String str, boolean z) {
        return RemoteConfig.instance().getBoolean(str, z);
    }

    public static boolean b() {
        return a();
    }

    private static boolean c() {
        boolean z = !a("pinduoduo_Android.ka_strategy_framework_init_exp_57000", true);
        if (z) {
            Logger.i("LVBA.AliveAbility", "cs ability is disabled");
        }
        return z;
    }

    private a() {
    }

    public static boolean a(String str) {
        Logger.i("LVBA.AliveAbility", "isAbilityDisabled2022Q3: " + str);
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return c();
    }
}
