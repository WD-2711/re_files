package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

import android.content.Context;
import android.content.Intent;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NetworkUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.a$1, java.lang.Runnable] */
    public void grantAutoStartPermission() {
        if (!c.a()) {
            Logger.i("LVBA.AliveModule", "grantAutoStartPermission device not support");
            return;
        }
        final Context frameworkContext = StrategyFramework.getFrameworkContext();
        int b = c.b();
        if (b == 3) {
            Logger.i("LVBA.AliveModule", "grantAutoStartPermission device not support");
        } else if (b == 1) {
            Logger.i("LVBA.AliveModule", "already has AutoStart permission");
        } else {
            ThreadPool.instance().computeTask(ThreadBiz.CS, "AliveStartupImpl#grantAutoStartPermission", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.a.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    BlackListItem cachedConfig = MSCManager.instance().getCachedConfig(StrategyFramework.getFrameworkContext(), new SceneRequest("0"));
                    if ((cachedConfig == null || cachedConfig.isBlack()) && NetworkUtils.instance().isConnected(frameworkContext)) {
                        Logger.i("LVBA.AliveModule", "grantAutoStartPermission hit black list");
                        return;
                    }
                    try {
                        int parseInt = NumberUtils.instance().parseInt(Configuration.instance().getConfiguration("x.auto_start_perm_interval", "24")) * 3600 * 1000;
                        long currentTimeMillis = System.currentTimeMillis() - a.this.a();
                        if (currentTimeMillis < parseInt) {
                            Logger.i("LVBA.AliveModule", "grantAutoStartPermission too frequently:interval=%d,min_interval=%d", new Object[]{Long.valueOf(currentTimeMillis), Integer.valueOf(parseInt)});
                            return;
                        }
                        if (!c.c()) {
                            Logger.i("LVBA.AliveModule", "AutoStart setPermission failed.");
                        }
                        a.this.b();
                    } catch (Throwable th) {
                        Logger.e("LVBA.AliveModule", th);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long a() {
        return MMKVCompat.module("alive_strategy", true).getLong("last_grant_auto_start_perm_time", 0L);
    }

    public void startBackgroundActivity(Intent intent) {
        b.startBackgroundActivity(intent);
    }

    public void startBackgroundByFullScreenNotification(Intent intent) {
        b.shouldStartBgActivityWithFullScreenNotification(intent);
    }

    public boolean canStartBgActivityByAlarm(int i, boolean z) {
        return b.canStartBgActivityByAlarm(i, z);
    }

    public void startBackgroundActivityByAlarm(Intent intent) {
        b.startBgActivityByAlarm(intent);
    }

    public boolean canStartBackgroundActivity() {
        return b.canStartBackgroundActivity();
    }

    public boolean startBackgroundActivityByAssistant(Intent intent) {
        return b.retnFalse(intent);
    }

    public int hasAutoStartPermission() {
        if (c.a()) {
            return c.b();
        }
        Logger.i("LVBA.AliveModule", "hasAutoStartPermission device not support");
        return 3;
    }

    public boolean canStartBgActivityByFullScreenNotification(int i, boolean z) {
        return b.shouldStartBgActivityByNotification(i, z);
    }

    public void startBackgroundActivityByTheme(Intent intent) {
        b.startBgActivityByThemeManager(intent);
    }

    public boolean canStartBgActivityByFullScreenNotification() {
        return b.isBackgroundStartActivityEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        MMKVCompat.module("alive_strategy", true).putLong("last_grant_auto_start_perm_time", System.currentTimeMillis());
    }
}
