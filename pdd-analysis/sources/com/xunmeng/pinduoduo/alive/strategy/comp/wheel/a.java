package com.xunmeng.pinduoduo.alive.strategy.comp.wheel;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;

/* loaded from: a.class */
public class a {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static IMMKV e;
    private static a f;

    public void a(String str) {
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "set roll_component_list = " + str);
        e.putString("roll_component_list", str);
    }

    public static a a() {
        if (f == null) {
            f = new a();
        }
        return f;
    }

    public void a(int i) {
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "set open_component_count = " + i);
        e.putLong("open_component_count", i);
    }

    public String c() {
        String string = e.getString("roll_component_list", "1,2,3,4");
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "get roll_component_list = " + string);
        return string;
    }

    public void a(long j) {
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "set polling_service_last_change_time = " + j);
        e.putLong("polling_service_last_change_time", j);
    }

    public long b() {
        long j = e.getLong("polling_service_last_change_time", 0L);
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "get polling_service_last_change_time = " + j);
        return j;
    }

    public int d() {
        int i = e.getInt("open_component_count", 2);
        Logger.i("LVST2.comp.WheelStrategy.WheelDataManager", "get open_component_count = " + i);
        return i;
    }

    private a() {
        e = MMKVCompat.module("WheelStrategy", false);
    }
}
