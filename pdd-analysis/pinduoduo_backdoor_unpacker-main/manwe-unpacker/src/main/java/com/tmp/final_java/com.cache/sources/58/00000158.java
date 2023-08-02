package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* loaded from: RyzeSubAbility.class */
public class RyzeSubAbility implements a.AnonymousClass1 {
    public static final String a = null;
    public static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static boolean i = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: RyzeSubAbility$RyzeConfig.class */
    public class RyzeConfig {
        public long maxSupportVersion;
        String extra;
        List banVersions;
        public long minSupportVersion;
        public String type = "broadcast";
        public String blackSceneId = "3075";
        public boolean mscUseSyncApi = true;
        public long refreshTTLMills = 0;
        public long invalidTTLMills = 0;
        public boolean isIgnoreNoneBlack = true;
        public int compStateWaitTimeSec = 2;
        public String aliveUri = "content://com.xiaomi.ad.LockScreenAdProvider";

        RyzeConfig() {
        }
    }

    private void a(String str) {
        try {
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "isPulledAdSolution:" + i);
            if (i) {
                return;
            }
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "pullAliveSolution");
            i = true;
            StrategyFramework.getFrameworkContext().getContentResolver().getType(Uri.parse(str));
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.RyzeSubAbility", th);
        }
    }

    private boolean a(RyzeConfig ryzeConfig) {
        if (TextUtils.isEmpty(ryzeConfig.blackSceneId)) {
            return false;
        }
        SceneRequest sceneRequest = new SceneRequest(ryzeConfig.blackSceneId, Long.valueOf(ryzeConfig.refreshTTLMills), Long.valueOf(ryzeConfig.invalidTTLMills), (String) null, (String) null);
        BlackListItem config = ryzeConfig.mscUseSyncApi ? MSCManager.instance().getConfig(StrategyFramework.getFrameworkContext(), sceneRequest) : MSCManager.instance().getCachedConfig(StrategyFramework.getFrameworkContext(), sceneRequest);
        if (ryzeConfig.isIgnoreNoneBlack && config == null) {
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "ignore null black");
            return false;
        } else if (config != null && !config.isBlack()) {
            return false;
        } else {
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "hit black list: %s, ability not support", new Object[]{ryzeConfig.blackSceneId});
            return true;
        }
    }

    public StatusResult start(SionRequest sionRequest) {
        return isSupport().isSuccess() ? a(sionRequest) : new StatusResult(false, "final error");
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        return a();
    }

    private StatusResult a(SionRequest sionRequest) {
        Uri data;
        try {
            Map extra = sionRequest.getExtra();
            Intent intent = sionRequest.getIntent();
            Logger.d("LVUA.Dybuild.Sion.RyzeSubAbility", "execute");
            if (extra == null || !"ryze_activity_proxy".equals(extra.get("start_type"))) {
                if (extra == null || !"true".equalsIgnoreCase(String.valueOf(extra.get("ryze_start_by_intent")))) {
                    data = intent.getData();
                    if (data == null) {
                        return new StatusResult(false, "uri is null");
                    }
                } else {
                    data = Uri.parse(intent.toUri(0));
                    List queryIntentActivitiesForComponentUtilsWithFilter = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.queryIntentActivitiesForComponentUtilsWithFilter(new Intent("android.intent.action.VIEW", data));
                    if (queryIntentActivitiesForComponentUtilsWithFilter == null || queryIntentActivitiesForComponentUtilsWithFilter.isEmpty()) {
                        return new StatusResult(false, "not found activity by uri");
                    }
                    Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "validated intent uri");
                }
            } else if (ScreenUtils.instance().isScreenLocked()) {
                Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "screen locked");
                return new StatusResult(false, "screen locked");
            } else if (!ScreenUtils.instance().isScreenOn()) {
                Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "screen off");
                return new StatusResult(false, "screen off");
            } else {
                data = new Uri.Builder().scheme("ryze").authority("com.ryze.activity").appendPath("61700").appendQueryParameter("intent", intent.toUri(1)).appendQueryParameter("component_name", "RyzeProxyActivity").build();
            }
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "final uri:" + data.toString());
            RyzeConfig b2 = b();
            if (b2 == null) {
                return new StatusResult(false, "config error");
            }
            a(b2.aliveUri);
            return TextUtils.equals("broadcast", b2.type) ? a(data, b2.extra, b2.compStateWaitTimeSec) : new StatusResult(false, "type error");
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.RyzeSubAbility", th);
            return new StatusResult(false, Logger.getStackTraceString(th));
        }
    }

    private RyzeConfig b() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.build_in_miui_ad_solution_key_61800", "");
        Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "raw config:" + configValue);
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "parse json, plugin name = " + AbilityFramework.getPluginName());
        IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
        RyzeConfig ryzeConfig = (RyzeConfig) pluginJSONFormatUtils.fromJson(configValue, RyzeConfig.class);
        Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "format json:" + pluginJSONFormatUtils.toJson(ryzeConfig));
        return ryzeConfig;
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e a() {
        try {
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "isSupportV2");
            HashMap hashMap = new HashMap();
            RyzeConfig b2 = b();
            if (b2 == null) {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "solutionConfig is null");
            }
            if (a(b2)) {
                Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "hit black list");
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "hit black list");
            }
            int i2 = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo("com.miui.systemAdSolution", 0).versionCode;
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "AdSolutionVersionCode:" + i2);
            hashMap.put("msg", "ad_solution_version_not_support");
            hashMap.put("adSolutionVersion", String.valueOf(i2));
            if (b2.maxSupportVersion >= b2.minSupportVersion) {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(((long) i2) >= b2.minSupportVersion && ((long) i2) <= b2.maxSupportVersion, "ryze_not_support", hashMap);
            } else if (b2.minSupportVersion > 0) {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(((long) i2) >= b2.minSupportVersion, "ryze_not_support", hashMap);
            } else {
                List list = b2.banVersions;
                if (list == null || list.isEmpty() || !list.contains(Integer.valueOf(i2))) {
                    return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "unknown");
                }
                Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "ban version " + i2);
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "ryze_not_support", hashMap);
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.RyzeSubAbility", th);
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, Logger.getStackTraceString(th));
        }
    }

    private StatusResult a(Uri uri, String str, int i2) {
        Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "popupActivityByBroadcast");
        Intent intent = new Intent("miui.intent.miad.APP_INSTALL_NOTIFICATION.click");
        intent.putExtra("deeplink", uri.toString());
        if ("ryze".equals(uri.getScheme())) {
            boolean componentEnabledState = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(1);
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_check_comp_state_64700", true) && !componentEnabledState) {
                try {
                    Thread.sleep(i2 * 0);
                } catch (Exception e2) {
                    Logger.e("LVUA.Dybuild.Sion.RyzeSubAbility", "popupActivityByBroadcast sleep error", e2);
                }
                if (!com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(1)) {
                    return new StatusResult(false, "component disabled");
                }
            }
        }
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("packageName")) {
                    intent.putExtra("packageName", jSONObject.optString("packageName", ""));
                }
                if (jSONObject.has("mediaType")) {
                    intent.putExtra("mediaType", jSONObject.optString("mediaType", ""));
                }
                if (jSONObject.has("uuid")) {
                    intent.putExtra("uuid", jSONObject.optString("uuid", ""));
                }
            }
        } catch (Throwable th) {
            Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", th);
        }
        Logger.i("LVUA.Dybuild.Sion.RyzeSubAbility", "send broad cast:" + intent.toString());
        StrategyFramework.getFrameworkContext().sendBroadcast(intent);
        return new StatusResult(true, "success");
    }
}