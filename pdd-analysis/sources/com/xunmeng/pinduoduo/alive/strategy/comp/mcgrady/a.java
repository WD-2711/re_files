package com.xunmeng.pinduoduo.alive.strategy.comp.mcgrady;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;

/* loaded from: a.class */
public class a {
    public static final String a = null;
    private static IMMKV b;
    private static a c;

    public static a a() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    public IMMKV b() {
        return b;
    }

    private a() {
        b = MMKVCompat.module("McGradyStrategy", false);
    }
}
