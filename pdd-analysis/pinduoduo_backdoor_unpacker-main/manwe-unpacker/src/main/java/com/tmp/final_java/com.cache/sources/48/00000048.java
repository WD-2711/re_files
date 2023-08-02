package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu;

import android.os.SystemClock;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.i;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;

/* loaded from: e.class */
public class e extends BaseBizProviderImpl implements a.AnonymousClass1 {
    private final String a = "LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl";
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static IMMKV f;

    private static IMMKV a() {
        if (f != null) {
            return f;
        }
        f = MMKVCompat.module("oppo_lock_display", false);
        return f;
    }

    protected String getProviderScene() {
        return "provider_oppo_ld";
    }

    public Boolean isLockDisplayAble() {
        if (!hasAbility("isLockDisplayAble")) {
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "start isLockDisplayAble");
        if (!hasPermission()) {
            if (i.a().b("pinduoduo_Android.ka_providerV2_oppo_ld_cache_57700")) {
                Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "get isLockDisplayAble from mmkv");
                return i.a().a(getProviderScene());
            }
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "skip isLockDisplayAble since no permission");
            return null;
        }
        Boolean a = c.a();
        LogUtils.logInvokeResultEventAndError(getProviderScene(), "isLockDisplayAble", a != null);
        if (a != null) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "read database success, start update mmkv, enableLockDisplay: %s", new Object[]{a});
            i.a().a(getProviderScene(), a);
        } else {
            a = i.a().a(getProviderScene());
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "read database failed, get isLockDisplayAble from mmkv, enableLockDisplay: %s", new Object[]{a});
        }
        if (a != null && a.booleanValue() && a().getLong("oppo_lock_display_last_success_time", 0L) == 0) {
            a().putLong("oppo_lock_display_last_success_time", System.currentTimeMillis());
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "record timestamp if no record when read succeed.");
        }
        return a;
    }

    public Boolean canLockDisplay() {
        Boolean isLockDisplayAble = isLockDisplayAble();
        if (null == isLockDisplayAble) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "canLockDisplay : %s", new Object[]{isLockDisplayAble});
            if (!Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_providerV2_track_can_lock_display_59700", "true"))) {
                return null;
            }
            LogUtils.logInvokeResultEventAndError(getProviderScene(), "canLockDisplay", false);
            return null;
        } else if (!isLockDisplayAble.booleanValue()) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "canLockDisplay : %s", new Object[]{isLockDisplayAble});
            return false;
        } else {
            long currentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            long j = a().getLong("oppo_lock_display_last_success_time", 0L);
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "key is oppo_lock_display_last_success_time lastSuccessTime is " + j + " deviceBootTime " + currentTimeMillis);
            if (j == 0) {
                return false;
            }
            Boolean bool = currentTimeMillis > j ? true : null;
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "canLockDisplay : %s", new Object[]{bool});
            return bool;
        }
    }

    public boolean setLockDisplay(boolean z) {
        if (!hasAbility("setLockDisplay")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "skip setLockDisplay since no permission");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "start setLockDisplay, enableLockDisplay: %s", new Object[]{Boolean.valueOf(z)});
        boolean a = c.a(z);
        if (a) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "write database success, start update mmkv, enableLockDisplay: %s", new Object[]{Boolean.valueOf(z)});
            i.a().a(getProviderScene(), Boolean.valueOf(z));
            a().putLong("oppo_lock_display_last_success_time", System.currentTimeMillis());
            Logger.i("LVBA.AliveModule.Provider.OppoLockDisplayProviderImpl", "record timestamp when succeed.");
        }
        LogUtils.logInvokeResultEventAndError(getProviderScene(), "setLockDisplay", a);
        return a;
    }

    public boolean hasPermission() {
        return com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f.a().hasPermission(getProviderScene());
    }
}