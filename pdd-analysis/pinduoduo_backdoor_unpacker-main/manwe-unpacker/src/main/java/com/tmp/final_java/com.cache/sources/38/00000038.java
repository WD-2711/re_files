package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.util.Log;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.BaseAbilityTracker;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotRomUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.h */
/* loaded from: h.class */
public class EventLogger {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;

    /* renamed from: c */
    private static void addFixedFieldsToMap(String str, Map map) {
        map.put("module_v2", "hw_fp");
        map.put("category", str);
        map.put("strategy", "ProviderTracker");
    }

    /* renamed from: a */
    public static void logGrantPermissionResultEventAndError(boolean z, String str, boolean z2) {
        HashMap hashMap = new HashMap();
        hashMap.put("stage", z ? "start" : "result");
        hashMap.put("src", str);
        hashMap.put("result", String.valueOf(z2));
        hashMap.put("secure_patch_ver", BotRomUtils.getSecurityPatchVersion());
        hashMap.put("rom_build_id", com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.b());
        if (RemoteConfig.instance().getBoolean("ab_hw_fp_result_track_perf_event_61600", true)) {
            logEvent("GrantPermission", hashMap);
        }
        if (RemoteConfig.instance().getBoolean("ab_hw_fp_result_track_cs_event_61600", false)) {
            LogUtils.logEventWithTracker("GrantPermission", hashMap);
        }
        if (z || z2) {
            return;
        }
        logErrorEvent("GrantPermission", hashMap);
    }

    /* renamed from: a */
    public static void logErrorEvent(String str, boolean z) {
        if (!z || DeprecatedAb.instance().isFlowControl("ab_hw_fp_track_invoke_success_57300", false)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scene", str);
            hashMap.put("success", String.valueOf(z));
            logErrorEvent("InvokeResult", hashMap);
        }
    }

    /* renamed from: b */
    private static void logErrorEvent(String str, Map map) {
        if (!DeprecatedAb.instance().isFlowControl("ab_hw_fp_track_marmot_57400", true)) {
            return;
        }
        addFixedFieldsToMap(str, map);
        BaseAbilityTracker.instance().trackError(StrategyFramework.getFrameworkContext(), 31100, 30069, "", map);
    }

    /* renamed from: a */
    public static void logPermissionRevoked(long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("persistTs", String.valueOf(j));
        logEvent("PermissionRevoked", hashMap);
        logErrorEvent("PermissionRevoked", hashMap);
    }

    /* renamed from: a */
    public static void logLauncherRestartResult(boolean z) {
        if (!z || DeprecatedAb.instance().isFlowControl("ab_hw_fp_track_restart_57300", true)) {
            HashMap hashMap = new HashMap();
            hashMap.put("result", String.valueOf(z));
            logEvent("RestartLauncher", hashMap);
            if (z) {
                return;
            }
            logErrorEvent("RestartLauncher", hashMap);
        }
    }

    /* renamed from: a */
    public static void logExceptionEvent(String str, Throwable th) {
        if (DeprecatedAb.instance().isFlowControl("ab_hw_fp_track_error_57300", true)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scene", str);
            hashMap.put("exception", Log.getStackTraceString(th));
            logErrorEvent("Exception", hashMap);
        }
    }

    /* renamed from: a */
    private static void logEvent(String str, Map map) {
        if (!DeprecatedAb.instance().isFlowControl("ab_hw_fp_track_event_57400", true)) {
            return;
        }
        addFixedFieldsToMap(str, map);
        BaseAbilityTracker.instance().trackEvent(StrategyFramework.getFrameworkContext(), "PERF", "Pdd.LVST", map);
    }
}