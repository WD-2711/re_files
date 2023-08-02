package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.PluginStrategyService;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.HashMap;
import java.util.Map;

/* loaded from: f.class */
public class f {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;

    private static Map b(String str, Map map) {
        HashMap hashMap = new HashMap();
        if (map != null && !map.isEmpty()) {
            hashMap.putAll(map);
        }
        hashMap.put("log_action", str);
        hashMap.put("is_overlay", Boolean.valueOf(DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "OVERLAY")));
        hashMap.put("business", "SionInternalAbility");
        hashMap.put("rom_os_version", RomOsUtil.instance().getVersion());
        hashMap.put("rom_os_name", RomOsUtil.instance().getName());
        hashMap.put("rom_build_id", com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.b());
        hashMap.put("screen_state", Integer.valueOf(ScreenUtils.instance().getScreenState()));
        hashMap.put("is_pdd_foreground", Boolean.valueOf(AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())));
        hashMap.put("secure_patch_ver", com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a());
        String pluginName = AbilityFramework.getPluginName();
        if (pluginName != null) {
            hashMap.put("plugin_version", PluginStrategyService.instance(pluginName).getRunningPluginVersion());
            hashMap.put("plugin_name", pluginName);
        }
        return hashMap;
    }

    public static void a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2)) {
            HashMap hashMap = new HashMap();
            hashMap.put("msg", str2);
            hashMap.put("caller", str3);
            a(str, hashMap);
        }
    }

    private static void b(String str, TrackEventOption trackEventOption) {
        StrategyFramework.trackCsEvent(str, trackEventOption);
    }

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            HashMap hashMap = new HashMap();
            hashMap.put("msg", str2);
            a(str, hashMap);
        }
    }

    public static void a(String str, HashMap hashMap) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_sion_pmm_tracker_63500", false)) {
                Logger.i("LVUA.Dybuild.Sion.SionTracker", "sion tracker hit gray");
                return;
            }
            Logger.i("LVUA.Dybuild.Sion.SionTracker", "start track pmm event, action = " + str);
            TrackEventOption trackEventOption = new TrackEventOption(b(str, hashMap), "perf", "alive", 0);
            if (RomOsUtil.instance().isOppo() && RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_sion_oppo_light_os_64700", true)) {
                trackEventOption.append("is_oppo_light_os", Boolean.valueOf(com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.a(false)));
            }
            g.a("", 23199779, trackEventOption);
            Logger.i("LVUA.Dybuild.Sion.SionTracker", "tracker pmm end");
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.SionTracker", th);
        }
    }

    public static void a(String str, TrackEventOption trackEventOption) {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15138980) {
            b(str, trackEventOption);
        }
    }

    public static void a(String str, Throwable th) {
        a(str, Logger.getStackTraceString(th), "Sion");
    }

    public static void a(String str, Map map) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_sion_cs_tracker_63500", false)) {
            Logger.i("LVUA.Dybuild.Sion.SionTracker", "tracker hit gray");
            return;
        }
        Logger.i("LVUA.Dybuild.Sion.SionTracker", "start track cs event, action = " + str);
        TrackEventOption trackEventOption = new TrackEventOption(b(str, map));
        trackEventOption.setOp("alive");
        trackEventOption.setSubOp("sion_ability");
        a("AliveSionAbility", trackEventOption);
    }
}
