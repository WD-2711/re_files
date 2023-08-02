package com.xunmeng.pinduoduo.alive.strategy.comp.raptor;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private IMMKV e = MMKVCompat.module("raptor_data", false);
    private static b f;
    private static Lock g = new ReentrantLock();

    public void a(String str) {
        Logger.i("LVST2.comp.RaptorStrategy.RaptorDataRecorder", "set reportPullTitanTime = " + str);
        this.e.putString("last_report_pull_titan_time", str);
    }

    public static b a() {
        g.lock();
        try {
            if (f == null && f == null) {
                f = new b();
            }
            return f;
        } finally {
            g.unlock();
        }
    }

    public String c() {
        String string = this.e.getString("last_report_pull_titan_time", (String) null);
        Logger.i("LVST2.comp.RaptorStrategy.RaptorDataRecorder", "get lastReportPullTitanTime = " + string);
        return string;
    }

    public void a(boolean z) {
        Logger.i("LVST2.comp.RaptorStrategy.RaptorDataRecorder", "set reportLocalBlacklistActiveState = " + z);
        this.e.putBoolean("last_report_local_blacklist_active_state", z);
    }

    public boolean b() {
        boolean z = this.e.getBoolean("last_report_local_blacklist_active_state", false);
        Logger.i("LVST2.comp.RaptorStrategy.RaptorDataRecorder", "get lastReportLocalBlacklistActiveState = " + z);
        return z;
    }

    private b() {
    }
}
