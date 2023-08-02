package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.PluginStrategyService;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.SafeguardUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeviceUtil;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty;

/* loaded from: b.class */
public class b {
    private static Runnable a = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.1
        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            Logger.i("SpecialPullAbility.Comp", "clear sys log");
            SafeguardUtils.getInst().clearSLog("SpecialPullAbility.Comp");
        }
    };

    public static String b() {
        try {
            return PluginStrategyService.instance("alive_base_ability_plugin").loadAndGetRunningPluginVersion(StrategyFramework.getFrameworkContext(), "com.xunmeng.pinduoduo.AliveBaseAbility", true);
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
            return "";
        }
    }

    public static String d() {
        return AppBuildInfo.instance().getRealVersionCode() >= 16580740 ? e() : "";
    }

    public static String a(Intent intent, String str) {
        if (intent == null) {
            return null;
        }
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                return extras.getString(str);
            }
            return null;
        } catch (Exception e) {
            Logger.e("SpecialPullAbility.Comp", "[getIntentStringUnparcel] error for key:" + str, e);
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.c("getIntentStringUnparcel", e.getMessage());
            return null;
        }
    }

    private static String e() {
        return DeviceUtil.instance().getRomBuildId();
    }

    public static void a() {
        long clearLogDelayTime = StartUpPullProperty.getInstance().getClearLogDelayTime();
        Logger.i("SpecialPullAbility.Comp", "will clear sys log after %s ms", new Object[]{Long.valueOf(clearLogDelayTime)});
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).removeCallbacks(a);
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("SpecialPullAbility#clearSysLog", a, clearLogDelayTime);
    }

    public static boolean c() {
        if (!Boolean.parseBoolean(RemoteConfig.instance().getExpValue("ab_pull_alive_enable_blk_6220", "false"))) {
            return false;
        }
        StartUpPullProperty startUpPullProperty = StartUpPullProperty.getInstance();
        if (TextUtils.isEmpty(startUpPullProperty.getBlackSceneId())) {
            return false;
        }
        SceneRequest sceneRequest = new SceneRequest(startUpPullProperty.getBlackSceneId());
        sceneRequest.setRefreshTTLInMs(Long.valueOf(startUpPullProperty.getRefreshTTLMills()));
        sceneRequest.setValidTTLInMs(Long.valueOf(startUpPullProperty.getValidTTLMills()));
        BlackListItem config = startUpPullProperty.isMscUseSyncApi() ? MSCManager.instance().getConfig(BotBaseApplication.getContext(), sceneRequest) : MSCManager.instance().getCachedConfig(BotBaseApplication.getContext(), sceneRequest);
        Logger.i("SpecialPullAbility.Comp", "hit black list config: %s", new Object[]{config});
        return config == null ? startUpPullProperty.isConfigNullDefaultBlack() : config.isBlack();
    }
}
