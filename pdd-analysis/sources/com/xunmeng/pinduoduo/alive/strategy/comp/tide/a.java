package com.xunmeng.pinduoduo.alive.strategy.comp.tide;

import android.content.Context;
import android.content.Intent;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import java.util.concurrent.TimeUnit;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    public static long a = 0;
    private static final String b = null;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.alive.strategy.comp.tide.a$1, java.lang.Runnable] */
    public void onReceive(Context context, Intent intent) {
        if (!a()) {
            Logger.i("LVST2.comp.TideStrategy.NewTideReceiver", "ab forbid, return");
        } else if (intent == null || intent.getAction() == null) {
            Logger.e("LVST2.comp.TideStrategy.NewTideReceiver", "intent or intent.action is null, return");
        } else {
            Logger.i("LVST2.comp.TideStrategy.NewTideReceiver", "receiver broadcast : %s", new Object[]{intent.getAction()});
            if (!intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                return;
            }
            ThreadPool.instance().scheduleTask(ThreadBiz.CS, "NewTideReceiver#onReceive", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.tide.a.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    long currentTimeMillis = System.currentTimeMillis();
                    Logger.i("LVST2.comp.TideStrategy.NewTideReceiver", "currentTime = %s, lastSyncTime = %s, SyncPeriod = %s", new Object[]{Long.valueOf(currentTimeMillis), Long.valueOf(a.a), Integer.valueOf(b.h() * 1000)});
                    if (currentTimeMillis - a.a < b.h() * 1000) {
                        Logger.i("LVST2.comp.TideStrategy.NewTideReceiver", "interval duration is less than sync period, return");
                        return;
                    }
                    Logger.i("LVST2.comp.TideStrategy.NewTideReceiver", "start to update sync job");
                    b.g().d();
                }
            }, 0L, TimeUnit.MILLISECONDS);
        }
    }

    public static boolean a() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_enable_tide_receiver_57400", false);
    }
}
