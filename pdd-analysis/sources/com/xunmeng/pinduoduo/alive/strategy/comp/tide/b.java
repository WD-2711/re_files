package com.xunmeng.pinduoduo.alive.strategy.comp.tide;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.BaseAccount;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.pa.interfaces.adapter.proxy.ResourceManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* loaded from: b.class */
public class b extends com.xunmeng.pinduoduo.alive.strategy.comp.common.c {
    private static final String b = null;
    private static final String d = null;
    private static final String e = null;
    private static volatile b f;
    private static final IMMKV c = MMKVCompat.module("biz_tide_mmkv", false);
    private static volatile boolean g = false;
    private static int h = 3600;
    private static final Lock i = new ReentrantLock();

    public static boolean i() {
        return System.currentTimeMillis() - c.getLong("last_sync_time", System.currentTimeMillis()) > ((long) (h() * 1000));
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String a() {
        return j() ? ResourceManager.getInstance().getTideAccountName() : ResourceManager.getInstance().getTideAccountNameOutside();
    }

    public static int h() {
        Logger.i("LVST2.comp.Tide.TideManager", "getAccountSyncPeriod, hasInitPeriod: %s", new Object[]{Boolean.valueOf(g)});
        int i2 = g ? h : c.getInt("v1_period", h);
        Logger.i("LVST2.comp.Tide.TideManager", "getAccountSyncPeriod: %s", new Object[]{Integer.valueOf(i2)});
        return i2;
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String b() {
        return ResourceManager.getInstance().getTideAccountType();
    }

    public static void a(TideConfig tideConfig) {
        h = b(tideConfig);
        c.putInt("v1_period", h);
        g = true;
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String c() {
        return ResourceManager.getInstance().getTideContentAuthority();
    }

    public static b g() {
        i.lock();
        try {
            if (f == null) {
                f = new b();
            }
            return f;
        } finally {
            i.unlock();
        }
    }

    private static int b(TideConfig tideConfig) {
        int i2 = tideConfig.periodV1;
        try {
            Logger.i("LVST2.comp.Tide.TideManager", "exp default:" + i2);
            if (AliveAbility.instance().isAbilityDisabled()) {
                return tideConfig.periodAlive;
            }
            if (tideConfig.optPeriodV1 > 0) {
                Logger.i("LVST2.comp.Tide.TideManager", "opt period v1 is " + tideConfig.optPeriodV1);
                return tideConfig.optPeriodV1;
            } else if (TextUtils.isEmpty(tideConfig.expKey) || TextUtils.isEmpty(tideConfig.expParams)) {
                return i2;
            } else {
                String expValue = RemoteConfig.instance().getExpValue(tideConfig.expKey, "");
                Logger.i("LVST2.comp.Tide.TideManager", "exp value:" + expValue);
                if (TextUtils.isEmpty(expValue)) {
                    return i2;
                }
                JSONObject jSONObject = new JSONObject(expValue);
                Logger.i("LVST2.comp.Tide.TideManager", "params:" + tideConfig.expParams);
                if (!jSONObject.has(tideConfig.expParams)) {
                    Logger.i("LVST2.comp.Tide.TideManager", tideConfig.expParams + "not exist");
                    return i2;
                }
                int optInt = jSONObject.optInt(tideConfig.expParams, i2);
                Logger.i("LVST2.comp.Tide.TideManager", "new period:" + optInt);
                return optInt;
            }
        } catch (Exception e2) {
            Logger.e("LVST2.comp.Tide.TideManager", e2);
            return i2;
        }
    }

    public static void a(long j) {
        c.putLong("last_sync_time", j);
        Logger.i("LVST2.comp.Tide.TideManager", "updateLastSyncTime: %s", new Object[]{Long.valueOf(j)});
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.tide.b$1, java.lang.Runnable] */
    private b() {
        try {
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            final String b2 = b();
            final ArrayList arrayList = new ArrayList(1);
            arrayList.add(ResourceManager.getInstance().getAppName());
            if (j()) {
                BaseAccount.instance().initAccount(frameworkContext, ResourceManager.getInstance().getTideAccountNameOutside(), b2, c());
                arrayList.add(ResourceManager.getInstance().getTideAccountNameToBeDelete());
            } else {
                arrayList.add(ResourceManager.getInstance().getTideAccountName());
            }
            ThreadPool.instance().scheduleTask(ThreadBiz.CS, "TideManager#<init>", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.tide.b.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    for (String str : arrayList) {
                        BaseAccount.instance().removeAccount(str, b2);
                    }
                }
            }, 0L, TimeUnit.MILLISECONDS);
        } catch (Exception e2) {
            Logger.e("LVST2.comp.Tide.TideManager", e2);
        }
    }

    private boolean l() {
        int i2;
        if (TextUtils.isEmpty("android_go_settings_flag")) {
            return false;
        }
        int i3 = MMKVCompat.module("lifecycle", false).getInt("android_go_settings_flag", -1);
        if (i3 == -1) {
            try {
                String str = StrategyFramework.getFrameworkContext().getPackageManager().getApplicationInfo("com.android.settings", 0).sourceDir;
                if (str != null) {
                    if (str.contains("HwMtkSettings")) {
                        i2 = 1;
                        i3 = i2;
                    }
                }
                i2 = 0;
                i3 = i2;
            } catch (Throwable th) {
                try {
                    Logger.e("LVST2.comp.Tide.TideManager", th);
                    i3 = 0;
                } finally {
                    MMKVCompat.module("lifecycle", false).putInt("android_go_settings_flag", i3);
                }
            }
        }
        return i3 == 1;
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public void d() {
        Logger.i("LVST2.comp.Tide.TideManager", "start");
        super.d();
    }

    private boolean j() {
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.pa_strategy_biz_ab_tide_XAccount_in_O_5800", true)) {
            return (RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture()) && Build.VERSION.SDK_INT == 26;
        }
        return (RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture()) && !(k() && l());
    }

    private boolean k() {
        return ((RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) && Build.VERSION.SDK_INT <= 27) && RemoteConfig.instance().getBoolean("pinduoduo_Android.pa_strategy_biz_ab_tide_check_huawei_go_5720", false);
    }
}
