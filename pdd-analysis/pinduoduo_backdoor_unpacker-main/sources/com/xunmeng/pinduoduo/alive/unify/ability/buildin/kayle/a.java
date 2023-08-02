package com.xunmeng.pinduoduo.alive.unify.ability.buildin.kayle;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.VoidRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.kayle.KayleResult;
import java.util.HashMap;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final long c = 0;
    private static final IMMKV d = MMKVCompat.module("KayleAbility", false);

    private Boolean c() {
        Uri parse = Uri.parse("content://com.oppo.launcher.settings/settings");
        if (!a(parse)) {
            parse = Uri.parse("content://com.android.launcher.settings/settings");
        }
        try {
            Bundle call = StrategyFramework.getFrameworkContext().getContentResolver().call(parse, "getLauncherModeSettings", (String) null, (Bundle) null);
            if (call == null) {
                Logger.i("LVUA.Buildin.KayleAbility", "bundle is null");
                return null;
            }
            int i = call.getInt("launcher_mode_vaule", -1);
            Logger.i("LVUA.Buildin.KayleAbility", "oppo simple mode : " + i);
            if (i == -1) {
                return null;
            }
            return Boolean.valueOf(i == 3);
        } catch (Exception e) {
            Logger.e("LVUA.Buildin.KayleAbility", "error", e);
            return null;
        }
    }

    private void a(String str) {
        try {
            if (RemoteConfig.instance().getBoolean("ab_disable_track_failed_reason_simple_mode_62800", false)) {
                Logger.i("LVUA.Buildin.KayleAbility", "hit disable track simple mode");
                return;
            }
            long parseLong = NumberUtils.instance().parseLong(RemoteConfig.instance().getConfigValue("pinduoduo_Android.dybuild_simple_mode_track_interval_62800", String.valueOf(0L)), 0L);
            long currentTimeMillis = System.currentTimeMillis() - d.getLong("last_track_time", 0L);
            Logger.i("LVUA.Buildin.KayleAbility", "diff:" + currentTimeMillis);
            if (currentTimeMillis < parseLong) {
                Logger.i("LVUA.Buildin.KayleAbility", "simple mode tracker in cd");
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("business", "SimpleModeAbility");
            hashMap.put("action", str);
            hashMap.put("rom_version", RomOsUtil.instance().getVersion());
            TrackEventOption trackEventOption = new TrackEventOption(hashMap, "perf", "alive", (Integer) null);
            d.putLong("last_track_time", System.currentTimeMillis());
            StrategyFramework.trackCsDataEvent("", 0L, trackEventOption);
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.KayleAbility", "track error", th);
        }
    }

    private Boolean a() {
        int i = Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "new_simple_mode", -1);
        Logger.i("LVUA.Buildin.KayleAbility", "hw simple mode : " + i);
        if (i == -1) {
            return null;
        }
        return Boolean.valueOf(i == 1);
    }

    /* renamed from: a */
    public KayleResult execute(VoidRequest voidRequest) {
        if (!isSupport()) {
            Logger.i("LVUA.Buildin.KayleAbility", "no support");
            return new KayleResult("no_support");
        }
        Boolean bool = null;
        if (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) {
            bool = a();
        } else if (RomOsUtil.instance().isXiaomiManufacture()) {
            bool = b();
        } else if (RomOsUtil.instance().isOppoManufacture()) {
            bool = c();
        }
        Logger.i("LVUA.Buildin.KayleAbility", "simple mode is : %s", new Object[]{bool});
        a("result_" + bool);
        return new KayleResult(bool);
    }

    public boolean isSupport() {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_simple_mode_62800", false)) {
            Logger.i("LVUA.Buildin.KayleAbility", "simple mode ab is false");
            return false;
        }
        return true;
    }

    private boolean a(Uri uri) {
        try {
            return StrategyFramework.getFrameworkContext().getPackageManager().resolveContentProvider(uri.getAuthority(), 0) != null;
        } catch (Exception e) {
            Logger.e("LVUA.Buildin.KayleAbility", "resolveProvider fail", e);
            return false;
        }
    }

    private Boolean b() {
        int i = Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "elderly_mode", -1);
        Logger.i("LVUA.Buildin.KayleAbility", "xm simple mode : " + i);
        if (i == -1) {
            return null;
        }
        return Boolean.valueOf(i == 1);
    }
}
