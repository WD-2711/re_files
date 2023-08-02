package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.PddHandler;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: e.class */
public class e {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final Lock k = new ReentrantLock();
    private static final PddHandler l = ThreadPool.instance().getWorkerHandler(ThreadBiz.CS);
    private static final Set m = new HashSet();
    private static VMDynamicReceiver n = null;
    private static final AtomicBoolean o = new AtomicBoolean(false);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: e$a.class */
    public class a implements a.AnonymousClass1 {
        /* JADX WARN: Type inference failed for: r0v25, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.e$b, java.lang.Runnable] */
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (action == null || !TextUtils.equals(action, "android.intent.action.SCREEN_ON") || e.m.isEmpty()) {
                    return;
                }
                Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "screen on, back to launcher");
                HashMap hashMap = new HashMap();
                hashMap.put("action", "screen_on");
                hashMap.put("reason", "receive");
                LogUtils.logEventAndErrorWithTracker("fp_monitor", "fp_monitor", (Map) hashMap, true, true);
                if (e.b("ab_key_fp_monitor_back_home_when_screen_on_64400")) {
                    Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "need to back to home when screen on");
                    e.c("screen_on", "screen_on");
                }
                e.k.lock();
                Iterator it = e.m.iterator();
                while (it.hasNext()) {
                    e.l.removeCallbacks((Runnable) ((b) it.next()));
                    it.remove();
                }
                e.k.unlock();
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.Provider.FPMonitorUtils", "screen on receiver error", th);
            }
        }

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: e$b.class */
    public class b implements a.AnonymousClass1 {
        private final String a;
        private final String b;
        private final String c;

        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            try {
                Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", " scene : %s, src : %s, requestId : %s, start home activity", new Object[]{this.a, this.b, this.c});
                e.c("over_time", this.a);
                HashMap hashMap = new HashMap();
                hashMap.put("action", "over_time");
                hashMap.put("reason", this.a);
                LogUtils.logEventAndErrorWithTracker("fp_monitor", "fp_monitor", (Map) hashMap, true, true);
                e.k.lock();
                e.m.remove(this);
                e.k.unlock();
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.Provider.FPMonitorUtils", "back to launcher error", th);
            }
        }

        public b(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    private static Intent f() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(-2147463168);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "skip check ab for null");
            return false;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "false"));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean d(Intent intent) {
        if (!Boolean.parseBoolean(RemoteConfig.instance().getExpValue("ab_hfp_plugin_61800", "false"))) {
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "plugin ab disabled");
            return false;
        }
        boolean booleanExtra = intent.getBooleanExtra("enablePlugin", false);
        Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "enablePlugin: %s", new Object[]{Boolean.valueOf(booleanExtra)});
        return booleanExtra;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.e$b, java.lang.Object, java.lang.Runnable] */
    public static void c(Intent intent) {
        try {
            String intentStringExtra = FileProviderV2.getIntentStringExtra(intent, "fp_scene");
            String intentStringExtra2 = FileProviderV2.getIntentStringExtra(intent, "fp_src");
            String intentStringExtra3 = FileProviderV2.getIntentStringExtra(intent, "fp_request_id");
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "unregister timeout runnable for scene : %s, src : %s, requestId : %s", new Object[]{intentStringExtra, intentStringExtra2, intentStringExtra3});
            k.lock();
            for (?? r0 : m) {
                if (TextUtils.equals(((b) r0).a, intentStringExtra) && TextUtils.equals(((b) r0).b, intentStringExtra2) && TextUtils.equals(((b) r0).c, intentStringExtra3)) {
                    l.removeCallbacks((Runnable) r0);
                    m.remove(r0);
                    k.unlock();
                    return;
                }
            }
            k.unlock();
        } catch (Exception e2) {
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "unregister timeout runnable error", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.e$b, java.lang.Object, java.lang.Runnable] */
    public static void a(Intent intent) {
        try {
            Intent b2 = b(intent);
            String intentStringExtra = FileProviderV2.getIntentStringExtra(b2, "fp_scene");
            String intentStringExtra2 = FileProviderV2.getIntentStringExtra(b2, "fp_src");
            String intentStringExtra3 = FileProviderV2.getIntentStringExtra(b2, "fp_request_id");
            String expValue = RemoteConfig.instance().getExpValue("config_key_ignore_failure_white_list_64500", "");
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "ignore white list : " + expValue);
            if (!TextUtils.isEmpty(expValue) && expValue.contains(intentStringExtra + ",")) {
                Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "ignore scene: " + intentStringExtra);
                return;
            }
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "register timeout runnable for scene : %s, src : %s, requestId : %s", new Object[]{intentStringExtra, intentStringExtra2, intentStringExtra3});
            ?? bVar = new b(intentStringExtra, intentStringExtra2, intentStringExtra3);
            k.lock();
            m.add(bVar);
            k.unlock();
            l.postDelayed("FPMonitorUtils#registerTimeoutRunnable", (Runnable) bVar, RemoteConfig.instance().getLong("config_key_monitor_activity_finish_delay_time_64400", 0L) * 0);
        } catch (Exception e2) {
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "register timeout runnable error", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a() {
        if (n == null) {
            n = new VMDynamicReceiver(new a(), "fpMonitorReceiver");
        }
        if (o.compareAndSet(false, true)) {
            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "register screen on receiver");
            StrategyFramework.getFrameworkContext().registerReceiver(n, new IntentFilter("android.intent.action.SCREEN_ON"));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b() {
        return b("ab_key_fp_monitor_enable_activity_finish_64500");
    }

    static Intent b(Intent intent) {
        Set<String> keySet;
        Intent intent2 = intent;
        boolean z = true;
        while (z) {
            z = false;
            Bundle extras = intent2.getExtras();
            if (extras != null && (keySet = extras.keySet()) != null && !keySet.isEmpty()) {
                Iterator<String> it = keySet.iterator();
                while (true) {
                    if (it.hasNext()) {
                        String next = it.next();
                        Object obj = extras.get(next);
                        if (obj instanceof Intent) {
                            Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "key : " + next + " intent : " + ((Intent) obj));
                            z = true;
                            intent2 = (Intent) obj;
                            break;
                        }
                    }
                }
            }
        }
        Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "final Intent : " + intent2);
        return intent2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.e$1, java.lang.Runnable] */
    public static void c(final String str, final String str2) {
        if (!com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str2)) {
            HashMap hashMap = new HashMap();
            hashMap.put("action", str);
            hashMap.put("reason", "no_support");
            LogUtils.logEventAndErrorWithTracker("fp_monitor", "fp_monitor", (Map) hashMap, true, true);
        } else if (!RemoteConfig.instance().getBoolean("ab_fp_call_startBgActivity_on_bg_6470", false)) {
            d(str, str2);
        } else if (Looper.myLooper() == Looper.getMainLooper()) {
            l.post("FPMonitorUtils#backToLauncher", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.e.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    e.d(str, str2);
                }
            });
        } else {
            d(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(String str, String str2) {
        Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "start home activity");
        boolean a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str2, f());
        Logger.i("LVBA.AliveModule.Provider.FPMonitorUtils", "start home success : " + a2);
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("reason", "start_home");
        hashMap.put("result", String.valueOf(a2));
        LogUtils.logEventAndErrorWithTracker("fp_monitor", "fp_monitor", (Map) hashMap, true, true);
    }
}