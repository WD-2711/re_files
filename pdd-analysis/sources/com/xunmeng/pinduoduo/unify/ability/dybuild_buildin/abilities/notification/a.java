package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final AtomicBoolean c = new AtomicBoolean(false);
    private static final CopyOnWriteArraySet d = new CopyOnWriteArraySet();
    private static VMDynamicReceiver e = null;

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification.a$1, java.lang.Runnable] */
    public void onReceive(final Context context, Intent intent) {
        try {
            Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "onReceive:" + intent.getAction());
            if (!ScreenUtils.instance().isScreenOn()) {
                return;
            }
            ThreadPool.instance().ioTask(ThreadBiz.CS, "AliveStartupUtils#NotificationCancelReceiver", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification.a.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    try {
                        Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "start cancel notification:" + a.d);
                        Iterator it = a.d.iterator();
                        while (it.hasNext()) {
                            NotificationSubAbility.a(context, ((Integer) it.next()).intValue());
                        }
                    } catch (Exception e2) {
                        Logger.e("LVUA.Dybuild.Sion.NotificationCancelReceiver", "screen on receiver cancel error", e2);
                    } finally {
                        a.d();
                    }
                }
            });
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.NotificationCancelReceiver", "screen on receiver error", th);
        }
    }

    public static void a(int i) {
        Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "registerReceiver for:" + i);
        if (!c()) {
            return;
        }
        d.add(Integer.valueOf(i));
        if (e == null) {
            e = new VMDynamicReceiver(new a(), "CdNotificationAbilityReceiver");
        }
        if (!c.compareAndSet(false, true)) {
            Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "already registered");
            return;
        }
        Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "do registerReceiver");
        StrategyFramework.getFrameworkContext().registerReceiver(e, new IntentFilter("android.intent.action.SCREEN_ON"));
    }

    public static void b(int i) {
        if (!c()) {
            return;
        }
        d.remove(Integer.valueOf(i));
    }

    private static boolean c() {
        boolean z = RemoteConfig.instance().getBoolean("pinduoduo_android.ka_startup_notification_screen_on_6470", false);
        Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "ab:" + z);
        return z;
    }

    public static void d() {
        if (c() && e != null) {
            d.clear();
            if (!c.compareAndSet(true, false)) {
                Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "already unregistered");
                return;
            }
            Logger.i("LVUA.Dybuild.Sion.NotificationCancelReceiver", "unregisterReceiver and clear list");
            StrategyFramework.getFrameworkContext().unregisterReceiver(e);
        }
    }
}
