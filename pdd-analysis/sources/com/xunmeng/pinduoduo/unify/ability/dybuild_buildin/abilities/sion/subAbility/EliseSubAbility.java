package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.app.Presentation;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.IThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.config.BlackConfig;
import java.util.List;
import java.util.Map;

/* loaded from: EliseSubAbility.class */
public class EliseSubAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility$1 */
    /* loaded from: EliseSubAbility$1.class */
    public class AnonymousClass1 implements a.AnonymousClass1 {
        final /* synthetic */ VirtualDisplay a;
        final /* synthetic */ IThreadPool b;
        final /* synthetic */ EliseConfig c;
        final /* synthetic */ SionRequest d;
        final /* synthetic */ DisplayManager e;

        /* JADX WARN: Type inference failed for: r3v3, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility$1$1, java.lang.Runnable] */
        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "try to create virtual display in main thread");
            final Presentation presentation = new Presentation(StrategyFramework.getFrameworkContext(), this.a.getDisplay());
            presentation.show();
            Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "virtual display has created.");
            this.b.ioTaskDelay(ThreadBiz.CS, "EliseSubAbility$1", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility.1.1
                /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility$1$1$1, java.lang.Runnable] */
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    int i = AnonymousClass1.this.c.callbackTimeout;
                    Map extra = AnonymousClass1.this.d.getExtra();
                    if (extra != null && extra.containsKey("elise_delay_release_time_mills_extra")) {
                        int parseInt = NumberUtils.instance().parseInt(String.valueOf(extra.get("elise_delay_release_time_mills_extra")), 0);
                        if (parseInt <= 0) {
                            parseInt = 0;
                        }
                        i += parseInt;
                    }
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "delayReleaseTime is " + i);
                    for (Display display : AnonymousClass1.this.e.getDisplays()) {
                        Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", display.toString());
                    }
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a("EliseSubAbility", StrategyFramework.getFrameworkContext(), AnonymousClass1.this.d.getIntent());
                    AnonymousClass1.this.b.ioTaskDelay(ThreadBiz.CS, "EliseSubAbility$2", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility.1.1.1
                        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                        public void run() {
                            try {
                                Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "timeout dismiss");
                                if (presentation.isShowing()) {
                                    presentation.dismiss();
                                }
                                AnonymousClass1.this.a.release();
                            } catch (Throwable th) {
                                Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", th);
                            }
                        }
                    }, i);
                }
            }, this.c.delayTime);
        }

        AnonymousClass1(VirtualDisplay virtualDisplay, IThreadPool iThreadPool, EliseConfig eliseConfig, SionRequest sionRequest, DisplayManager displayManager) {
            EliseSubAbility.this = r4;
            this.a = virtualDisplay;
            this.b = iThreadPool;
            this.c = eliseConfig;
            this.d = sionRequest;
            this.e = displayManager;
        }
    }

    /* loaded from: EliseSubAbility$EliseConfig.class */
    public final class EliseConfig {
        Integer maxSupportOsVersion;
        int minSupportOsVersion = 19;
        int delayTime = 300;
        int callbackTimeout = 3000;
        public BlackConfig blackConfig = new BlackConfig();

        private EliseConfig() {
        }
    }

    private boolean a(EliseConfig eliseConfig) {
        try {
            BlackConfig blackConfig = eliseConfig.blackConfig;
            if (blackConfig == null) {
                Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "black config is null");
                return false;
            } else if (TextUtils.isEmpty(blackConfig.blackSceneId)) {
                return false;
            } else {
                SceneRequest sceneRequest = new SceneRequest(blackConfig.blackSceneId, Long.valueOf(blackConfig.refreshTTLMills), Long.valueOf(blackConfig.invalidTTLMills), (String) null, (String) null);
                BlackListItem config = blackConfig.mscUseSyncApi ? MSCManager.instance().getConfig(StrategyFramework.getFrameworkContext(), sceneRequest) : MSCManager.instance().getCachedConfig(StrategyFramework.getFrameworkContext(), sceneRequest);
                if (blackConfig.isIgnoreNoneBlack && config == null) {
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "ignore null black");
                    return false;
                } else if (config != null && !config.isBlack()) {
                    return false;
                } else {
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "hit black list: %s, ability not support", new Object[]{blackConfig.blackSceneId});
                    return true;
                }
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", th);
            return false;
        }
    }

    private ActivityInfo a(Intent intent) {
        List b2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.b(intent);
        if (b2 == null || b2.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) b2.get(0)).activityInfo;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility$1, java.lang.Runnable] */
    public StatusResult start(SionRequest sionRequest) {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                EliseConfig a2 = a();
                if (a2 == null) {
                    return new StatusResult(false, "config is null");
                }
                ActivityInfo a3 = a(sionRequest.getIntent());
                if (a3 == null) {
                    return new StatusResult(false, "not found activity");
                }
                if (!a3.packageName.equals(StrategyFramework.getFrameworkContext().getPackageName()) && !a3.exported) {
                    return new StatusResult(false, a3.name + " exported is false");
                }
                DisplayManager displayManager = (DisplayManager) StrategyFramework.getFrameworkContext().getSystemService("display");
                VirtualDisplay createVirtualDisplay = displayManager.createVirtualDisplay("Elise", 5, 5, 3, null, 0);
                IThreadPool instance = ThreadPool.instance();
                instance.getMainHandler(ThreadBiz.CS).post("EliseSubAbility$DisplayManager", (Runnable) new AnonymousClass1(createVirtualDisplay, instance, a2, sionRequest, displayManager));
                return new StatusResult(true, "success");
            } catch (Exception e) {
                Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", e);
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_error", Logger.getStackTraceString(e));
                return new StatusResult(false, e.getMessage());
            }
        }
        return new StatusResult(false, "os version not support");
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        try {
            Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "EliseSubAbility is support v3");
            EliseConfig a2 = a();
            if (a2 == null) {
                Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "config is null");
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "config is null");
            } else if (AppBuildInfo.instance().getRealVersionCode() < 15532160) {
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_not_support", "app version low");
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "app version low");
            } else {
                if (a2.minSupportOsVersion < 19) {
                    a2.minSupportOsVersion = 19;
                }
                if (Build.VERSION.SDK_INT < a2.minSupportOsVersion) {
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "os version low");
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_not_support", "os version low");
                    return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "os version low");
                } else if (a2.maxSupportOsVersion != null && Build.VERSION.SDK_INT > a2.maxSupportOsVersion.intValue()) {
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "os version high");
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_not_support", "os version high");
                    return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "os version high");
                } else if (!a(a2)) {
                    return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(true, "success");
                } else {
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_not_support", "hit black list");
                    Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "hit black list");
                    return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "hit black list");
                }
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", th);
            com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("elise_support_error", Logger.getStackTraceString(th));
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, Logger.getStackTraceString(th));
        }
    }

    private EliseConfig a() {
        try {
            String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.dybuild_elise_config_63700", "");
            Logger.i("LVUA.Dybuild.Sion.EliseSubAbility", "config:" + configValue);
            if (TextUtils.isEmpty(configValue)) {
                return null;
            }
            IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
            EliseConfig eliseConfig = (EliseConfig) pluginJSONFormatUtils.fromJson(configValue, EliseConfig.class);
            Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", "eliseConfig to json:" + pluginJSONFormatUtils.toJson(eliseConfig));
            return eliseConfig;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.EliseSubAbility", th);
            return null;
        }
    }
}
