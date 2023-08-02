package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.PluginStrategyService;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RuntimeInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeviceUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.d;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.status;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.utils.AlivePullStartUpInterfUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    /* JADX INFO: Access modifiers changed from: private */
    public static Map b(String str, Map map) {
        HashMap hashMap = new HashMap(16);
        if (map != null && !map.isEmpty()) {
            hashMap.putAll(map);
        }
        try {
            hashMap.put("business", "pull_startup");
            hashMap.put("action", str);
            hashMap.put("secure_patch_ver", DeviceUtil.instance().getSecurePatchVersion());
            hashMap.put("rom_version", RomOsUtil.instance().getVersion());
            hashMap.put("rom_build_id", b.d());
            hashMap.put("plugin_name", "alive_base_ability_plugin");
            hashMap.put("plugin_version", PluginStrategyService.instance("alive_base_ability_plugin").getRunningPluginVersion());
        } catch (Exception e) {
            Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", "[putCommonParams] error", e);
        }
        return hashMap;
    }

    public static void a(String str) {
        a(str, (Intent) null);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$3, java.lang.Runnable] */
    public static void a(final String str, final String str2) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "PullUpTrack#trackCandidateClz", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.3
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "trackClzBizWhiteList, clz: %s, biz: %s", new Object[]{str, str2});
                if (!RemoteConfig.instance().getBoolean("ab_start_pull_activity_track_clz_biz_63900", false)) {
                    return;
                }
                Map b = a.b("trackClzBizWhiteList", (Map) null);
                b.put("candidateClz", str);
                b.put("biz", str2);
                try {
                    StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(b, "perf", "alive", (Integer) null));
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", e);
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$4, java.lang.Runnable] */
    public static void a(final String str, final String str2, final String str3, final SonaRequest sonaRequest, final boolean z, final String str4) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "PullUpTrack#trackSonaInvocation", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.4
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                if (!RemoteConfig.instance().getBoolean("ab_sona_ability_invoke_track_perf_64200", false)) {
                    return;
                }
                Map b = a.b("trackSonaInvocation", (Map) null);
                b.put("type", "4000");
                b.put("process_name", RuntimeInfo.instance().getProcessName());
                b.put("screen_state", String.valueOf(ScreenUtils.instance().getScreenState()));
                b.put("foreground_state", String.valueOf(AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())));
                b.put("method", str);
                b.put("caller", str2);
                b.put("normalized_caller", str3);
                if (sonaRequest != null) {
                    b.put("request", sonaRequest.toString());
                    b.put("request_id", sonaRequest.getRequestId());
                    b.put("request_intent", sonaRequest.getIntent() == null ? "" : new Intent(sonaRequest.getIntent()).toUri(0));
                    Bundle extra = sonaRequest.getExtra();
                    b.put("request_extra", extra == null ? "" : extra.toString());
                }
                b.put("success", String.valueOf(z));
                b.put("error_msg", str4);
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "trackSonaInvocation: %s", new Object[]{b});
                try {
                    StrategyFramework.trackEvent("android_pull_ability", new TrackEventOption(b, "PERF", "startup_activity_any_where", (Integer) null));
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", "fail to trackSonaInvocation", e);
                }
                if (!RemoteConfig.instance().getBoolean("ab_sona_ability_invoke_track_pmm_64200", false)) {
                    return;
                }
                try {
                    StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(b, "perf", "alive", (Integer) null));
                } catch (Exception e2) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", "fail to trackSonaInvocation", e2);
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$6, java.lang.Runnable] */
    public static void b(final String str, final String str2) {
        final String stackTraceString = Logger.getStackTraceString(new Exception("LegacyInvokeTrace"));
        ThreadPool.instance().computeTask(ThreadBiz.CS, "PullUpTrack#trackLegacyInvoke", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.6
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                if (!RemoteConfig.instance().getBoolean("ab_dd_legacy_ability_invoke_track_pmm_64200", false)) {
                    return;
                }
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "trackLegacyInvoke, method: %s, caller: %s, stack: %s", new Object[]{str, str2, stackTraceString});
                Map b = a.b("trackLegacyInvoke", (Map) null);
                b.put("method", str);
                b.put("caller", str2);
                try {
                    StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(b, "perf", "alive", (Integer) null));
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", e);
                }
            }
        });
    }

    public static void c(String str, String str2) {
        if (!RemoteConfig.instance().getBoolean("ab_start_pull_track_common_error_pmm_64900", true)) {
            return;
        }
        Map b = b("trackCommonExceptionPmm", (Map) null);
        b.put("type", "3000");
        b.put("error_scene", str);
        b.put("error_msg", str2);
        TrackEventOption trackEventOption = new TrackEventOption(b);
        trackEventOption.setOp("PERF");
        trackEventOption.setSubOp("startup_activity_any_where");
        try {
            StrategyFramework.trackCsDataEvent("", 0L, trackEventOption);
        } catch (Exception e) {
            Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", e);
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$5, java.lang.Runnable] */
    public static void a(final String str, final SonaRequest sonaRequest, final status statusVar, final Map map) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "PullUpTrack#trackSonaStage", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.5
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                if (!RemoteConfig.instance().getBoolean("ab_sona_startup_stage_track_perf_64500", false)) {
                    return;
                }
                Map b = a.b("trackSonaStage", (Map) null);
                b.put("type", "4000");
                b.put("process_name", RuntimeInfo.instance().getProcessName());
                b.put("screen_state", String.valueOf(ScreenUtils.instance().getScreenState()));
                b.put("foreground_state", String.valueOf(AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())));
                b.put("stage", str);
                if (sonaRequest != null) {
                    b.put("caller", sonaRequest.getCaller());
                    b.put("normalized_caller", d.a(sonaRequest.getCaller()));
                    b.put("request_id", sonaRequest.getRequestId());
                    b.put("request_intent", sonaRequest.getIntent() == null ? "" : new Intent(sonaRequest.getIntent()).toUri(0));
                    Bundle extra = sonaRequest.getExtra();
                    b.put("request_extra", extra == null ? "" : extra.toString());
                }
                if (statusVar != null) {
                    b.put("success", String.valueOf(statusVar.getBoolean()));
                    b.put("error_msg", statusVar.b());
                }
                if (map != null) {
                    b.putAll(map);
                }
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "trackSonaStage: %s", new Object[]{b});
                try {
                    StrategyFramework.trackEvent("android_pull_ability", new TrackEventOption(b, "PERF", "startup_activity_any_where", (Integer) null));
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", "fail to trackSonaStage", e);
                }
                if (!RemoteConfig.instance().getBoolean("ab_sona_startup_stage_track_pmm_64500", false)) {
                    return;
                }
                try {
                    StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(b, "perf", "alive", (Integer) null));
                } catch (Exception e2) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", "fail to trackSonaStage", e2);
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$1, java.lang.Runnable] */
    public static void a(final String str, final Intent intent) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "pull_activity_track", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_start_pull_special_activity_disable_track_6020", "false"))) {
                    return;
                }
                Map b = a.b("trackScene", (Map) null);
                String fpScene = AlivePullStartUpInterfUtils.getFpScene(intent);
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "track reason: %s, fpScene: %s", new Object[]{str, fpScene});
                b.put("type", "1000");
                b.put("biz_app", "pdd");
                b.put("app_version", AppBuildInfo.instance().getIntervalVersion());
                b.put("system_version", Integer.valueOf(Build.VERSION.SDK_INT));
                b.put("start_intent", intent == null ? "" : new Intent(intent).toUri(0));
                b.put("scene", fpScene);
                b.put("pull_startup", true);
                b.put("pull_startup_reason", str);
                TrackEventOption trackEventOption = new TrackEventOption(b);
                trackEventOption.setOp("PERF");
                trackEventOption.setSubOp("startup_activity_any_where");
                try {
                    StrategyFramework.trackEvent("android_pull_ability", trackEventOption);
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", e);
                }
            }
        });
    }

    public static void a(String str, SonaRequest sonaRequest) {
        a(str, sonaRequest, null, null);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a$2, java.lang.Runnable] */
    public static void a(final String str, final boolean z, final String str2) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "PullUpTrack#trackCandidateClz", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.2
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                Logger.i("SpecialPullAbility.Comp.StartPullUpTrack", "trackCandidateClz: %s, isValid: %s, reason: %s", new Object[]{str, Boolean.valueOf(z), str2});
                if (RemoteConfig.instance().getBoolean("ab_start_pull_special_activity_disable_track_candidate_clz_63200", true)) {
                    return;
                }
                Map b = a.b("trackCandidateClz", (Map) null);
                b.put("type", "3000");
                b.put("biz_app", "pdd");
                b.put("app_version", AppBuildInfo.instance().getIntervalVersion());
                b.put("system_version", Integer.valueOf(Build.VERSION.SDK_INT));
                b.put("candidateClz", str);
                b.put("isCandidateClzValid", Boolean.valueOf(z));
                b.put("CandidateClzNoValidReason", str2);
                b.put("pull_startup", true);
                b.put("SecurityPatchVersion", DeviceUtil.instance().getSecurePatchVersion());
                TrackEventOption trackEventOption = new TrackEventOption(b);
                trackEventOption.setOp("PERF");
                trackEventOption.setSubOp("startup_activity_any_where");
                try {
                    StrategyFramework.trackEvent("android_pull_ability", trackEventOption);
                    StrategyFramework.trackCsDataEvent("", 0L, trackEventOption);
                } catch (Exception e) {
                    Logger.e("SpecialPullAbility.Comp.StartPullUpTrack", e);
                }
            }
        });
    }
}
