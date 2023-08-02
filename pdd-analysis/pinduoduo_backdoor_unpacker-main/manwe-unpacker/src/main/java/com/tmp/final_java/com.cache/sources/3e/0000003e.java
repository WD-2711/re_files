package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.BaseAbilityTracker;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotRomUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.j */
/* loaded from: j.class */
public class LogUtils {
    public static final String a = null;
    public static final String b = null;
    static final String c = null;
    static final String d = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final String k = null;
    private static final String l = null;
    private static final String m = null;
    private static final int n = 0;
    static final boolean e = RemoteConfig.instance().getBoolean("ab_fp_enable_append_tracker_data_6470", false);
    private static final IMMKV o = MMKVCompat.module("df_fp_provider", false);

    /* renamed from: a */
    public static void logGrantPermissionEventAndTracker(String str, String str2, long j2, boolean z, String str3) {
        logGrantPermissionEventAndTracker(str, false, str2, z, Long.valueOf(j2), null, str3);
    }

    /* renamed from: a */
    public static void logInvokeResultEventAndError(String str, String str2, boolean z) {
        if (!z || DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_invoke_success_57500", false)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scene", str);
            hashMap.put("invoke", str2);
            hashMap.put("success", String.valueOf(z));
            logMarmotErrorEvent("InvokeResult", hashMap);
        }
    }

    /* renamed from: a */
    public static void logPermissionRevokedEvent(String str, long j2) {
        HashMap hashMap = new HashMap();
        hashMap.put("scene", str);
        hashMap.put("persistTs", String.valueOf(j2));
        logEventWithTrack("PermissionRevoked", hashMap);
    }

    /* renamed from: d */
    private static void addFixedFieldsToMap(String str, Map map) {
        map.put("module_v2", "fp_v2");
        map.put("category", str);
        map.put("strategy", "ProviderTracker");
    }

    /* renamed from: a */
    public static void logGrantPermissionEventAndTracker(String str, String str2, boolean z, String str3) {
        String str4 = null;
        if (e) {
            str4 = o.getString("fp_env_black_list_tracker_data_6470", "");
        }
        logGrantPermissionEventAndTracker(str, true, str2, z, null, str3, str4);
    }

    /* renamed from: a */
    private static void logGrantPermissionEventAndTracker(String str, boolean z, String str2, boolean z2, Long l2, String str3, String str4) {
        if (!DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_grant_permission_57500", true)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("stage", z ? "start" : "result");
        hashMap.put("src", str2);
        hashMap.put("scene", str);
        hashMap.put("result", String.valueOf(z2));
        hashMap.put("secure_patch_ver", BotRomUtils.getSecurityPatchVersion());
        hashMap.put("rom_build_id", com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.b());
        if (l2 != null) {
            hashMap.put("duration", l2);
        }
        if (str3 != null) {
            hashMap.put("fail_reason", str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            hashMap.put("tracker_data", str4);
        }
        Logger.i("LVBA.AliveModule.ProviderV2.Tracker", "trackGrantPermission: %s.", new Object[]{hashMap});
        if (RemoteConfig.instance().getBoolean("ab_hw_fp_result_track_perf_event_61600", true)) {
            logEventWithTrack("GrantPermission", hashMap);
        }
        if (!RemoteConfig.instance().getBoolean("ab_hw_fp_result_track_cs_event_61600", false)) {
            return;
        }
        logEventWithTracker("GrantPermission", hashMap);
    }

    /* renamed from: a */
    public static void logEventWithTracker(String str, Map map) {
        addFixedFieldsToMap(str, map);
        StrategyFramework.trackCsEvent("ProviderTracker", new TrackEventOption(map, "perf", "Pdd.LVST", (Integer) null));
    }

    /* renamed from: c */
    private static void logMarmotErrorEvent(String str, Map map) {
        if (!DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_marmot_57500", true)) {
            return;
        }
        addFixedFieldsToMap(str, map);
        BaseAbilityTracker.instance().trackError(StrategyFramework.getFrameworkContext(), 31100, 30069, "", map);
    }

    /* renamed from: a */
    public static void logRestartEventAndError(String str, boolean z) {
        if (!z || DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_restart_57500", true)) {
            HashMap hashMap = new HashMap();
            hashMap.put("result", String.valueOf(z));
            hashMap.put("scene", str);
            logEventWithTrack("RestartLauncher", hashMap);
            if (z) {
                return;
            }
            logMarmotErrorEvent("RestartLauncher", hashMap);
        }
    }

    /* renamed from: a */
    public static void logEventAndErrorWithTracker(String str, String str2, Map map, boolean z, boolean z2) {
        map.put("scene", str);
        if (z) {
            logEventWithTrack(str2, map);
        }
        if (z2) {
            logMarmotErrorEvent(str2, map);
        }
    }

    /* renamed from: b */
    private static void logEventWithTrack(String str, Map map) {
        if (!DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_event_57500", true)) {
            return;
        }
        addFixedFieldsToMap(str, map);
        BaseAbilityTracker.instance().trackEvent(StrategyFramework.getFrameworkContext(), "PERF", "Pdd.LVST", map);
    }

    /* renamed from: a */
    public static void logExceptionErrorEvent(String str, String str2, Throwable th) {
        if (DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_error_57500", true)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scene", str);
            hashMap.put("invoke", str2);
            String stackTraceString = Log.getStackTraceString(th);
            if (stackTraceString.length() > 1536) {
                stackTraceString = stackTraceString.substring(0, 1536);
            }
            hashMap.put("exception", stackTraceString);
            logMarmotErrorEvent("Exception", hashMap);
        }
    }

    /* renamed from: a */
    public static void logHFPServiceEvent(String str, Long l2, String str2) {
        if (DeprecatedAb.instance().isFlowControl("ab_fp_v2_track_hfp_service_58500", true)) {
            HashMap hashMap = new HashMap();
            hashMap.put("scene", str);
            if (l2 != null) {
                hashMap.put("duration", l2);
            }
            if (str2 != null) {
                hashMap.put("src", str2);
            }
            logEventWithTrack("HFPService", hashMap);
            logMarmotErrorEvent("HFPService", hashMap);
        }
    }
}