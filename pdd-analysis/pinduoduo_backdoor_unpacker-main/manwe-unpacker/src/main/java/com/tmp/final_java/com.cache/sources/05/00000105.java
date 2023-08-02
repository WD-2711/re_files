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

    // 返回sonaconfig，从RemoteConfig中获取名为"pinduoduo_Android.alive_sona_ability_config_64200"的配置值，然后将其解析为SonaConfig对象。
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

    // 判断是否支持所传入的字符串
    public boolean isSupport(String str) {
        return a(str, true);
    }

    // 它会对传入的字符串进行处理，并将处理结果与类中的白名单进行比较，然后返回一个布尔值表示是否符合条件
    private boolean a(String str, boolean z) {
        status checkFunctionSupport;
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "isSupport invoked: %s, track: %s", new Object[]{str, Boolean.valueOf(z)});
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String a2 = d.a(str);
        if (!this.f.callerWhiteList.contains(a2)) {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "caller not in white list: " + a2);
            checkFunctionSupport = new status(false, "not_in_whitelist");
        } else {
            checkFunctionSupport = this.d.checkFunctionSupport(str);
        }
        if (z) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("isSupport", str, a2, null, checkFunctionSupport.getBoolean(), checkFunctionSupport.b());
        }
        return checkFunctionSupport.getBoolean();
    }

    // 首先检查传入的sonaRequest是否合法，然后使用"a(String str, boolean z)"方法判断请求的调用者是否在白名单中。如果在，检查是否需要使用"alivePullStartUp"或者"sonaStartUp"方法启动特殊活动。最后，将结果打包成SonaResult对象并返回。
    public SonaResult start(SonaRequest sonaRequest) {
        status processIntent;
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "start invoked: " + sonaRequest);
        if (sonaRequest == null || TextUtils.isEmpty(sonaRequest.getCaller()) || TextUtils.isEmpty(sonaRequest.getRequestId()) || sonaRequest.getIntent() == null) {
            return new SonaResult(false, "invalid request");
        }
        if (!a(sonaRequest.getCaller(), false)) {
            processIntent = new status(false, "caller_not_whitelist");
        } else if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.alive_sona_startup_ab_64500", false) || !this.e.b()) {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "startSpecialActivity by alivePullStartUp: %s", new Object[]{sonaRequest.toString()});
            processIntent = this.d.processIntent(sonaRequest.getIntent());
        } else {
            Logger.i("SpecialPullAbility.Comp.SonaAbility", "startSpecialActivity by sonaStartUp: %s", new Object[]{sonaRequest.toString()});
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start", sonaRequest);
            processIntent = this.e.a(sonaRequest, this.f);
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("result", sonaRequest, processIntent, null);
        }
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start", sonaRequest.getCaller(), null, sonaRequest, processIntent.getBoolean(), processIntent.b());
        return new SonaResult(processIntent.getBoolean(), processIntent.b());
    }

    // 调用AlivePullAbility实例的"isCacheIntentBusy"方法，检查缓存意图是否正忙
    public boolean isBusy(String str) {
        Logger.i("SpecialPullAbility.Comp.SonaAbility", "isBusy invoked: " + str);
        boolean isCacheIntentBusy = AlivePullAbility.instance().isCacheIntentBusy(str);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("isBusy", str, null, null, isCacheIntentBusy, null);
        return isCacheIntentBusy;
    }
}