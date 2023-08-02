package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.content.ContentProviderClient;
import android.net.Uri;
import android.os.IBinder;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: d.class */
public class d {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static CountDownLatch h = new CountDownLatch(0);

    /* loaded from: d$a.class */
    public class a {
        private final boolean a;
        private final String b;

        public a(boolean z, String str) {
            this.a = z;
            this.b = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.d$1, android.os.IBinder$DeathRecipient] */
    private static a a(final Uri uri) {
        ContentProviderClient contentProviderClient = null;
        try {
            try {
                Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "start acquire contentProviderClient: %s", new Object[]{uri});
                ContentProviderClient acquireUnstableContentProviderClient = StrategyFramework.getFrameworkContext().getContentResolver().acquireUnstableContentProviderClient(uri);
                if (acquireUnstableContentProviderClient == null) {
                    Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "contentProviderClient is null");
                    a aVar = new a(false, "client_is_null");
                    if (acquireUnstableContentProviderClient != null) {
                        acquireUnstableContentProviderClient.release();
                        Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "release contentProviderClient");
                    }
                    return aVar;
                }
                final IBinder iBinder = (IBinder) ReflectUtils.instance().invokeSysMethod(ReflectUtils.instance().getSysFieldValue(acquireUnstableContentProviderClient, "mContentProvider"), "asBinder", new Object[0]);
                iBinder.linkToDeath(new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.d.1
                    public void binderDied(IBinder iBinder2) {
                        binderDied();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    public void binderDied() {
                        try {
                            Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "binderDied: " + uri);
                            d.h.countDown();
                            iBinder.unlinkToDeath(this, 0);
                        } catch (Exception e2) {
                            Logger.e("LVBA.AliveModule.Provider.LauncherDeathMonitor", "unlink to death failed", e2);
                        }
                    }
                }, 0);
                a aVar2 = new a(true, null);
                if (acquireUnstableContentProviderClient != null) {
                    acquireUnstableContentProviderClient.release();
                    Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "release contentProviderClient");
                }
                return aVar2;
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.Provider.LauncherDeathMonitor", "link to death failed", e2);
                a aVar3 = new a(false, e2.getMessage());
                if (0 != 0) {
                    contentProviderClient.release();
                    Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "release contentProviderClient");
                }
                return aVar3;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                contentProviderClient.release();
                Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "release contentProviderClient");
            }
            throw th;
        }
    }

    public static void a() {
        h = new CountDownLatch(0);
        Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "reset monitor");
    }

    public static boolean b() {
        boolean z = false;
        String str = null;
        int i = RemoteConfig.instance().getInt("pinduoduo_Android.hw_fp_launcher_death_wait_ms_61500", 2900);
        Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "start check launcher death, waitTimeInMs: %d", new Object[]{Integer.valueOf(i)});
        long currentTimeMillis = System.currentTimeMillis();
        try {
            int i2 = RemoteConfig.instance().getInt("pinduoduo_Android.hw_fp_launcher_death_wait_retry_cnt_61500", 1);
            for (int i3 = 0; i3 < i2; i3++) {
                z = h.await(i, TimeUnit.MILLISECONDS);
                str = "latch_result";
                Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "latch await result: %s, retry times: %d", new Object[]{Boolean.valueOf(z), Integer.valueOf(i3 + 1)});
                if (z) {
                    break;
                }
            }
        } catch (InterruptedException e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherDeathMonitor", "fail to wait latch", e2);
            z = false;
            str = "latch_exception";
        } finally {
            a();
        }
        a("isLauncherDead", z, str, System.currentTimeMillis() - currentTimeMillis);
        return z;
    }

    public static boolean a(String str) {
        String message;
        boolean z;
        if (h.getCount() >= 1) {
            a("startMonitor", false, "concurrent_monitor", 0L);
            Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "start monitor failed for concurrent monitor");
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        try {
            String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.hw_fp_launcher_death_monitor_uri_format_61500", "content://%s.settings/badge");
            Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "start monitor, launcher pkg: %s, uri format: %s", new Object[]{str, configValue});
            a a2 = a(Uri.parse(String.format(configValue, str)));
            z = a2.a;
            message = a2.b;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherDeathMonitor", "fail to start monitor", e2);
            message = e2.getMessage();
            z = false;
        }
        if (z) {
            h = new CountDownLatch(1);
        }
        a("startMonitor", z, message, System.currentTimeMillis() - currentTimeMillis);
        Logger.i("LVBA.AliveModule.Provider.LauncherDeathMonitor", "start monitor success: %s", new Object[]{Boolean.valueOf(z)});
        return z;
    }

    private static void a(String str, boolean z, String str2, long j) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_launcher_death_monitor_track_61500", false)) {
            return;
        }
        long j2 = RemoteConfig.instance().getLong("pinduoduo_Android.hw_fp_launcher_track_pmm_group_id_61500", 0L);
        HashMap hashMap = new HashMap();
        hashMap.put("business", "LauncherDeathMonitor");
        hashMap.put("action", str);
        hashMap.put("result", Boolean.valueOf(z));
        hashMap.put("resultInterval", Long.valueOf(j));
        hashMap.put("message", str2);
        hashMap.put("launcherVersion", g.c());
        StrategyFramework.trackCsDataEvent("", j2, new TrackEventOption(hashMap, "perf", "alive", (Integer) null));
    }
}
