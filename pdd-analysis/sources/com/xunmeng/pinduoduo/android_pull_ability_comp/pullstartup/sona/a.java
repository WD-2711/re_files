package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaResult;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.AlivePullAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.status;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private final com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a d = new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a();
    private final e e = e.a(this.d);
    private final SonaConfig f = a();

    // 返回sonaconfig
    private SonaConfig a() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.alive_sona_ability_config_64200", ""); // 获得config
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "sonaConfig: " + configValue);
        if (TextUtils.isEmpty(configValue)) {
            return new SonaConfig();
        }
        IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin");
        SonaConfig sonaConfig = (SonaConfig) pluginJSONFormatUtils.fromJson(configValue, SonaConfig.class); // 给alive_base_ability_plugin配置config
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "config json result: " + pluginJSONFormatUtils.toJson(sonaConfig));
        return sonaConfig;
    }

    public boolean isSupport(String str) {
        return a(str, true);
    }

    private boolean a(String str, boolean z) {
        status a2;
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "isSupport invoked: %s, track: %s", new Object[]{str, Boolean.valueOf(z)});
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String a3 = d.a(str);
        if (!this.f.callerWhiteList.contains(a3)) {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "caller not in white list: " + a3);
            a2 = new status(false, "not_in_whitelist");
        } else {
            a2 = this.d.a(str);
        }
        if (z) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("isSupport", str, a3, null, a2.getBoolean(), a2.b());
        }
        return a2.getBoolean();
    }

    public SonaResult start(SonaRequest sonaRequest) {
        status a2;
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "start invoked: " + sonaRequest);
        if (sonaRequest == null || TextUtils.isEmpty(sonaRequest.getCaller()) || TextUtils.isEmpty(sonaRequest.getRequestId()) || sonaRequest.getIntent() == null) {
            return new SonaResult(false, "invalid request");
        }
        if (!a(sonaRequest.getCaller(), false)) {
            a2 = new status(false, "caller_not_whitelist");
        } else if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.alive_sona_startup_ab_64500", false) || !this.e.b()) {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "startSpecialActivity by alivePullStartUp: %s", new Object[]{sonaRequest.toString()});
            a2 = this.d.a(sonaRequest.getIntent());
        } else {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "startSpecialActivity by sonaStartUp: %s", new Object[]{sonaRequest.toString()});
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start", sonaRequest);
            a2 = this.e.a(sonaRequest, this.f);
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("result", sonaRequest, a2, null);
        }
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start", sonaRequest.getCaller(), null, sonaRequest, a2.getBoolean(), a2.b());
        return new SonaResult(a2.getBoolean(), a2.b());
    }

    public boolean isBusy(String str) {
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "isBusy invoked: " + str);
        boolean isCacheIntentBusy = AlivePullAbility.instance().isCacheIntentBusy(str);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("isBusy", str, null, null, isCacheIntentBusy, null);
        return isCacheIntentBusy;
    }
}
