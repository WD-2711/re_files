package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.common.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.PddHandler;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: h.class */
public class h {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final String k = null;
    private static final String q = null;
    private static final IMMKV e = MMKVCompat.module("df_fp_provider", false);
    private static final Lock l = new ReentrantLock();
    private static final PddHandler m = ThreadPool.instance().getWorkerHandler(ThreadBiz.CS);
    private static final Set n = new HashSet();
    private static VMDynamicReceiver o = null;
    private static final AtomicBoolean p = new AtomicBoolean(false);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: h$a.class */
    public class a implements a.AnonymousClass1 {
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v20, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.h$b, java.lang.Object, java.lang.Runnable] */
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (action == null || !TextUtils.equals(action, "android.intent.action.SCREEN_ON") || h.n.isEmpty()) {
                    return;
                }
                Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "screen on, back to launcher");
                if (RemoteConfig.instance().getBoolean("ab_track_back_to_launcher_since_screen_on_62900", false)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("action", "screen_on");
                    LogUtils.logEventAndErrorWithTracker("iqoo_secure", "grant_permission", (Map) hashMap, true, true);
                }
                h.c("screen_on");
                h.l.lock();
                for (?? r0 : h.n) {
                    h.m.removeCallbacks((Runnable) r0);
                    h.n.remove(r0);
                }
                h.l.unlock();
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.Provider.IqooSecureFpUtils", "screen on receiver error", th);
            }
        }

        private a() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: h$b.class */
    public class b implements a.AnonymousClass1 {
        private final String a;
        private final String b;
        private final String c;

        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            try {
                Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "scene : %s, start home activity", new Object[]{this.a});
                h.c(this.a);
                h.e("iqoo_secure_fail_record");
                h.l.lock();
                h.n.remove(this);
                h.l.unlock();
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.Provider.IqooSecureFpUtils", "back to launcher error", th);
            }
        }

        public b(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    public static boolean a(String str, String str2, Intent intent) {
        if (d("iqoo_secure_fail_record") >= RemoteConfig.instance().getInt("pinduoduo_Android.ka_fp_v2_iqoo_secure_max_fail_time_every_one_week_62500", 7)) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since more than fail time ");
            LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_more_than_failed_time");
            return false;
        } else if (ScreenUtils.instance().isScreenOn()) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since screen on");
            LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_no_support_screen_on");
            return false;
        } else {
            try {
                int i2 = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo("com.iqoo.secure", 0).versionCode;
                if (i2 < RemoteConfig.instance().getInt("pinduoduo_Android.ka_fp_v2_min_iqoo_secure_version_62500", 226558160)) {
                    Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since iqoo secure version lower than min version");
                    LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_version_too_low");
                    return false;
                } else if (i2 > RemoteConfig.instance().getInt("pinduoduo_Android.ka_fp_v2_max_iqoo_secure_version_62500", -32769)) {
                    Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since iqoo secure version higher than max version");
                    LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_version_too_high");
                    return false;
                } else {
                    try {
                        Intent component = new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.MainGuideActivity"));
                        component.putExtra("jump_origin_activity", true);
                        component.putExtra("origin_jump_resource", new ComponentName("com.vivo.assistant", "com.vivo.assistant.baseapp.frame.jump.ResultProxyActivity"));
                        component.addFlags(-2147463168);
                        component.putExtra("request_intent", intent);
                        component.putExtra("from_imanager", true);
                        if (!com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str)) {
                            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since bg start not support");
                            LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_start_bg_not_support");
                            return false;
                        }
                        boolean a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str, component);
                        if (a2) {
                            b(intent);
                            g();
                        }
                        LogUtils.logGrantPermissionEventAndTracker(str, str2, a2, "iqoo_secure");
                        Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "scene %s grant permission success : %s", new Object[]{str, Boolean.valueOf(a2)});
                        return a2;
                    } catch (Exception e2) {
                        Logger.e("LVBA.AliveModule.Provider.IqooSecureFpUtils", "start grant permission by iqoo secure error", e2);
                        LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_fail");
                        LogUtils.logExceptionErrorEvent(str, "startGrantPermissionByIqooSecure", e2);
                        return false;
                    }
                }
            } catch (Exception e3) {
                Logger.e("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip grant permission since iqoo not exist");
                LogUtils.logGrantPermissionEventAndTracker(str, str2, false, "iqoo_secure_not_exist");
                return false;
            }
        }
    }

    private static void g() {
        if (o == null) {
            o = new VMDynamicReceiver(new a(), "vivoFpScreenOnReceiver");
        }
        if (p.compareAndSet(false, true)) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "register screen on receiver");
            StrategyFramework.getFrameworkContext().registerReceiver(o, new IntentFilter("android.intent.action.SCREEN_ON"));
        }
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.h$b, java.lang.Object, java.lang.Runnable] */
    private static void b(Intent intent) {
        try {
            String a2 = a(intent, "fp_scene");
            String a3 = a(intent, "fp_src");
            String a4 = a(intent, "fp_request_id");
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "register timeout runnable for scene : %s", new Object[]{a2});
            ?? bVar = new b(a2, a3, a4);
            l.lock();
            n.add(bVar);
            l.unlock();
            m.postDelayed("DefaultFileProviderImpl#startGrantPermissionByIqooSecure", (Runnable) bVar, RemoteConfig.instance().getLong("pinduoduo_Android.ka_fp_v2_delay_finish_iqoo_secure_in_second_62500", 0L) * 0);
        } catch (Exception e2) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "register timeout runnable error", e2);
        }
    }

    private static void f(String str) {
        e.putStringSet(str, new HashSet());
    }

    private static Intent e() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setFlags(-2147463168);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(String str) {
        Set<String> stringSet = e.getStringSet(str, new HashSet());
        HashSet hashSet = new HashSet();
        hashSet.add(String.valueOf(TimeStamp.instance().getRealLocalTimeV2()));
        for (String str2 : stringSet) {
            if (TimeStamp.instance().getRealLocalTimeV2() - NumberUtils.instance().parseLong(str2, 0L) < 0) {
                hashSet.add(str2);
            }
        }
        e.putStringSet(str, hashSet);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.h$b, java.lang.Object, java.lang.Runnable] */
    /* renamed from: a */
    public static void cancelIqooSecureTimeoutTask(Intent intent) {
        try {
            f("iqoo_secure_fail_record");
            String a2 = a(intent, "fp_scene");
            String a3 = a(intent, "fp_src");
            String a4 = a(intent, "fp_request_id");
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "unregister timeout runnable for scene : %s", new Object[]{a2});
            l.lock();
            for (?? r0 : n) {
                if (TextUtils.equals(((b) r0).a, a2) && TextUtils.equals(((b) r0).b, a3) && TextUtils.equals(((b) r0).c, a4)) {
                    m.removeCallbacks((Runnable) r0);
                    n.remove(r0);
                    l.unlock();
                    return;
                }
            }
            l.unlock();
        } catch (Exception e2) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "unregister timeout runnable error", e2);
        }
    }

    /* renamed from: a */
    public static boolean canUseIqooSecurePermissionManager() {
        return g("pinduoduo_Android.ka_fp_v2_grant_perm_by_iqoo_secure_62500") && com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider.iqoo_secure") && !f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(String str) {
        if (com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str)) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "start home activity");
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "start home success : " + com.xunmeng.pinduoduo.alive.base.ability.impl.provider.a.a("fd.provider." + str, e()));
        }
    }

    private static String a(Intent intent, String str) {
        HashMap hashMap;
        if (RomOsUtil.instance().isVivo()) {
            String stringExtra = intent.getStringExtra("extra");
            if (TextUtils.isEmpty(stringExtra) || (hashMap = (HashMap) JSONFormatUtils.instance().fromJson(stringExtra, HashMap.class)) == null) {
                return "";
            }
            String str2 = (String) hashMap.get(str);
            return TextUtils.isEmpty(str2) ? "" : str2;
        }
        return intent.getStringExtra(str);
    }

    private static boolean f() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_fp_v2_grant_perm_by_iqoo_secure_access_salt_64400", false) && AliveAbility.isAbilityDisabled2022Q3("grant_permission_by_iqoo_secure");
    }

    private static boolean g(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.IqooSecureFpUtils", "skip check ab for null");
            return false;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "false"));
    }

    private static int d(String str) {
        int i2 = 0;
        Iterator it = e.getStringSet(str, new HashSet()).iterator();
        while (it.hasNext()) {
            if (TimeStamp.instance().getRealLocalTimeV2() - NumberUtils.instance().parseLong((String) it.next(), 0L) < 0) {
                i2++;
            }
        }
        return i2;
    }
}