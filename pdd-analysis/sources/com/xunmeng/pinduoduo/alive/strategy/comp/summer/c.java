package com.xunmeng.pinduoduo.alive.strategy.comp.summer;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;

/* loaded from: c.class */
public class c {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private IMMKV d = MMKVCompat.module("summer_xml_data", false);
    private static c e;

    public String b() {
        String string = this.d.getString("active_broadcast_version_id", (String) null);
        Logger.i("LVST2.comp.SummerStrategy.XmlDataRecorder", "get activeBroadcatVersionId = " + string);
        return string;
    }

    public void a(String str) {
        Logger.i("LVST2.comp.SummerStrategy.XmlDataRecorder", "set activeBroadcatVersionId = " + str);
        this.d.putString("active_broadcast_version_id", str);
    }

    public static c a() {
        if (e == null) {
            e = new c();
        }
        return e;
    }

    private c() {
    }
}
