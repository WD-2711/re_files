package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: c.class */
public class c {
    private final b b;
    private BroadcastReceiver c;
    private String a = "SpecialPullAbility.Comp.SonaOpxHelper";
    private boolean d = false;

    public c(b bVar) {
        this.b = bVar;
    }

    /* JADX WARN: Type inference failed for: r1v17, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.c$1, android.view.View$OnTouchListener] */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.c$2, java.lang.Runnable] */
    public void a(Window window, Intent intent) {
        if (window == null) {
            Logger.i(this.a, "setOpx fail, finish activity");
            a();
            return;
        }
        Logger.i(this.a, "setOpx start");
        window.setGravity(51);
        WindowManager.LayoutParams attributes = window.getAttributes();
        int intExtra = intent.getIntExtra("window_high", 1);
        int intExtra2 = intent.getIntExtra("window_width", 1);
        Logger.i(this.a, "set window height %s, set window width %s", new Object[]{Integer.valueOf(intExtra), Integer.valueOf(intExtra2)});
        attributes.x = 0;
        attributes.y = 0;
        attributes.height = intExtra;
        attributes.width = intExtra2;
        window.setAttributes(attributes);
        window.setFlags(16, 16);
        window.setFlags(67108864, 67108864);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(0);
        }
        View decorView = window.getDecorView();
        if (decorView != null) {
            decorView.setOnTouchListener(new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.c.1
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent == null || motionEvent.getAction() != 0) {
                        return false;
                    }
                    Logger.i(c.this.a, "finished on touch");
                    c.this.a();
                    return false;
                }
            });
        }
        b();
        Logger.i(this.a, "setOpx success");
        final long intExtra3 = intent.getIntExtra("delay_finish_time", 2000);
        Logger.i(this.a, "activity will finish after %dms", new Object[]{Long.valueOf(intExtra3)});
        ThreadPool.instance().scheduleTask(ThreadBiz.CS, this.a + "#setOpx#delayFinish", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.c.2
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                if (c.this.d) {
                    Logger.i(c.this.a, "skip delayed activity finish since already finished");
                    return;
                }
                Logger.i(c.this.a, "execute delayed activity finish");
                HashMap hashMap = new HashMap();
                hashMap.put("request_code", String.valueOf(c.this.b.c()));
                hashMap.put("delay_time", String.valueOf(intExtra3));
                com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_delay_finish", c.this.b.b(), null, hashMap);
                c.this.a();
            }
        }, intExtra3, TimeUnit.MILLISECONDS);
    }

    public void a() {
        this.d = true;
        this.b.a();
        if (this.c != null) {
            try {
                StrategyFramework.getFrameworkContext().unregisterReceiver(this.c);
                this.c = null;
            } catch (Exception e) {
                Logger.e(this.a, "fail to unregister receiver", e);
            }
        }
    }

    private void b() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.c = new VMDynamicReceiver(new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.c.3
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (!"android.intent.action.USER_PRESENT".equals(intent.getAction()) && !"android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                        return;
                    }
                    Logger.i(c.this.a, "finished when receive %s", new Object[]{intent.getAction()});
                    HashMap hashMap = new HashMap();
                    hashMap.put("request_code", String.valueOf(c.this.b.c()));
                    hashMap.put("screen_action", intent.getAction());
                    com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_screen_on", c.this.b.b(), null, hashMap);
                    c.this.a();
                }
            }
        }, "SonaOpxHelper.ScreenReceiver");
        try {
            StrategyFramework.getFrameworkContext().registerReceiver(this.c, intentFilter);
        } catch (Throwable th) {
            Logger.e(this.a, "fail to register receiver", th);
        }
    }
}