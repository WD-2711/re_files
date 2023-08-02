package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Proguard;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import java.util.Calendar;

/* loaded from: a.class */
public class a {
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static IMMKV g = MMKVCompat.module("TeaStrategy", false);
    public static final int a = RemoteConfig.instance().getInt(Proguard.marks("pinduoduo_Android.mijia_strategy_max_bind_count"), 3);

    private static String d() {
        String string = g.getString("titan_last_online_time", "");
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "get titanLastOnlineTime = " + string);
        return string;
    }

    public static boolean a(String str, int i) {
        return b(str) >= i;
    }

    public static boolean c(String str) {
        String str2 = str + "_enable_state";
        boolean z = g.getBoolean(str2, true);
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "get " + str2 + " = " + z);
        return z;
    }

    public static void a(boolean z) {
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "set irregular_start = " + z);
        g.putBoolean("irregular_start", z);
    }

    public static void d(String str) {
        if (c(str)) {
            b(str, b(str) + 1);
            return;
        }
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "reset onBind count");
        b(str, 1);
        a(str, true);
    }

    private static void e(String str) {
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "set titanLastOnlineTime = " + str);
        g.putString("titan_last_online_time", str);
    }

    public static IMMKV a() {
        return g;
    }

    public static int b(String str) {
        String str2 = str + "_onBindCount";
        int i = g.getInt(str2, 0);
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "get " + str2 + " = " + i);
        return i;
    }

    public static void a(String str, boolean z) {
        String str2 = str + "_enable_state";
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "set " + str2 + " = " + z);
        g.putBoolean(str2, z);
    }

    public static void b(String str, int i) {
        String str2 = str + "_onBindCount";
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "set " + str2 + " = " + i);
        g.putInt(str2, i);
    }

    public static boolean b() {
        Calendar calendar = Calendar.getInstance();
        String str = calendar.get(1) + "_" + calendar.get(2) + "_" + calendar.get(5);
        Logger.i("LVST2.comp.TeaStrategy.TeaDataManager", "currentDay = " + str);
        return str.equals(d());
    }

    public static boolean a(String str) {
        return b(str) >= a;
    }

    public static void c() {
        Calendar calendar = Calendar.getInstance();
        e(calendar.get(1) + "_" + calendar.get(2) + "_" + calendar.get(5));
    }
}
