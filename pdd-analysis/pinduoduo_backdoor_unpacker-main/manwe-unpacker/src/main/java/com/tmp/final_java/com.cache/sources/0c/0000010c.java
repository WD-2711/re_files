package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RuntimeInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.aepm.activity.ActivityUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.AlivePullAbility;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.status;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.HashMap;

/* loaded from: e.class */
public class e {
    public static final String a = null;
    private static e b;
    private final com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a c;
    private final g d;

    private e(com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a aVar) {
        this.c = aVar;
        this.d = aVar.a();
    }

    public static e a(com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a aVar) {
        if (b != null) {
            return b;
        }
        e eVar = new e(aVar);
        b = eVar;
        return eVar;
    }

    private void a(ComponentName componentName) {
        try {
            PackageManager packageManager = StrategyFramework.getFrameworkContext().getPackageManager();
            if (packageManager.getComponentEnabledSetting(componentName) == 1) {
                return;
            }
            Logger.i("SpecialPullAbility.Comp.SonaStartUp", "enable sona activity");
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.SonaStartUp", "fail to enable sona activity");
        }
    }

    private status b(SonaRequest sonaRequest, SonaConfig sonaConfig) {
        Intent c = c(sonaRequest, sonaConfig);
        a(c.getComponent());
        if (this.d.f()) {
            Logger.i("SpecialPullAbility.Comp.SonaStartUp", "start accountSettings activity directly");
            ActivityUtils.instance().startActivity("dd", StrategyFramework.getFrameworkContext(), c);
            HashMap hashMap = new HashMap();
            hashMap.put("start_method", "direct");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_sona_activity", sonaRequest, null, hashMap);
            return new status(true);
        } else if (!this.d.e()) {
            Logger.e("SpecialPullAbility.Comp.SonaStartUp", "no background start condition, return");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("not_support_background", sonaRequest.getIntent());
            return new status(false, "not_support_background");
        } else {
            status statusVar = new status(this.d.b(c));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("start_method", "bg");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_sona_activity", sonaRequest, statusVar, hashMap2);
            return statusVar;
        }
    }

    private Intent c(SonaRequest sonaRequest, SonaConfig sonaConfig) {
        Intent intent = new Intent();
        intent.setClassName(StrategyFramework.getFrameworkContext(), "com.xunmeng.pinduoduo.android_pull_ability.activity.SonaAssistActivity");
        intent.addFlags(4096);
        intent.putExtra("plugin_name", "alive_base_ability_plugin");
        intent.putExtra("component_name", "SonaActivity");
        intent.putExtra("sona_request", d.a(sonaRequest));
        intent.putExtra("delay_finish_time", sonaConfig.activityDelayFinishTimeInMs);
        intent.putExtra("intent_build_time", System.currentTimeMillis());
        return intent;
    }

    public static e a() {
        return b;
    }

    public boolean b() {
        return RuntimeInfo.instance().isTitanProcess();
    }

    public void a(Activity activity, SonaRequest sonaRequest, int i) {
        Logger.i("SpecialPullAbility.Comp.SonaStartUp", "onSonaActivityStart");
        AlivePullAbility.instance().cacheIntent(sonaRequest.getIntent());
        ActivityUtils.instance().startActivityForResult("dd", activity, this.c.createAccount(sonaRequest.getIntent()), i);
    }

    public status a(SonaRequest sonaRequest, SonaConfig sonaConfig) {
        Intent intent = sonaRequest.getIntent();
        status checkFunctionSupport = this.c.checkFunctionSupport(sonaRequest.getCaller());
        if (!checkFunctionSupport.getBoolean()) {
            Logger.i("SpecialPullAbility.Comp.SonaStartUp", "not satisfy condition: " + checkFunctionSupport);
            return checkFunctionSupport;
        } else if (this.d.a(intent)) {
            return !this.c.isIntentProcessable(intent) ? new status(false, "preCheck_fail") : b(sonaRequest, sonaConfig);
        } else {
            Logger.i("SpecialPullAbility.Comp.SonaStartUp", "cannot start when screen " + (ScreenUtil.isScreenOn() ? "on" : "off") + ", screen lock: " + ScreenUtil.isScreenLocked());
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("cannot_start", intent);
            return new status(false, "cannot_start");
        }
    }
}