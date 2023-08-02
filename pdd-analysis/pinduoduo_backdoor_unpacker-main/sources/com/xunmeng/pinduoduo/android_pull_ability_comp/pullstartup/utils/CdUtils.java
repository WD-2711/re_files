package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils;

import android.content.Intent;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.aepm.activity.ActivityUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.api.AliveSionAbility;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: CdUtils.class */
public class CdUtils {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: CdUtils$PullCdConfig.class */
    public class PullCdConfig {
        public List seqConfig = new ArrayList();

        private PullCdConfig() {
        }
    }

    public static boolean a() {
        boolean z = RemoteConfig.instance().getBoolean("ab_pull_activity_sion_direct_64400", false);
        Logger.i("SpecialPullAbility.Comp.CdUtils", "isUseSionDirectAbility:" + z);
        return z;
    }

    public static boolean b(String str) {
        try {
            String configValue = RemoteConfig.instance().getConfigValue("ab_pull_activity_sion_special_seq_64400", "");
            Logger.i("SpecialPullAbility.Comp.CdUtils", "isSupportSpecialSeqAbility get config:" + configValue);
            if (TextUtils.isEmpty(str)) {
                Logger.i("SpecialPullAbility.Comp.CdUtils", "caller is null");
                return false;
            }
            List c2 = c(configValue);
            return c2.isEmpty() ? AliveSionAbility.instance().isSupport(str) : AliveSionAbility.instance().isSupport(c2, str);
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.CdUtils", th);
            return false;
        }
    }

    public static boolean a(Intent intent, String str) {
        try {
            if (AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())) {
                Logger.i("SpecialPullAbility.Comp.CdUtils", "startActivityBySionDirect on foreground");
                ActivityUtils.instance().startActivity("com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils", BotBaseApplication.getContext(), intent);
                return true;
            }
            Logger.i("SpecialPullAbility.Comp.CdUtils", "startActivityBySionDirect");
            return AliveSionAbility.instance().start(new SionRequest(intent, str, UUID.randomUUID().toString(), Collections.singletonList("DirectSubAbility"), (Map) null)).isSuccess();
        } catch (Throwable th) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("startActivityBySionDirectException");
            Logger.e("SpecialPullAbility.Comp.CdUtils", th);
            return false;
        }
    }

    private static List c(String str) {
        try {
            Logger.i("SpecialPullAbility.Comp.CdUtils", "configStr:" + str);
            IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin");
            PullCdConfig pullCdConfig = (PullCdConfig) pluginJSONFormatUtils.fromJson(str, PullCdConfig.class);
            Logger.i("SpecialPullAbility.Comp.CdUtils", pluginJSONFormatUtils.toJson(pullCdConfig));
            return pullCdConfig.seqConfig;
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.CdUtils", th);
            return new ArrayList();
        }
    }

    public static boolean b() {
        boolean z = !TextUtils.isEmpty(RemoteConfig.instance().getConfigValue("ab_pull_activity_sion_special_seq_64400", ""));
        Logger.i("SpecialPullAbility.Comp.CdUtils", "isUseSionSpecialSeqAbility:" + z);
        return z;
    }

    public static boolean a(String str) {
        try {
            if (ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext())) {
                Logger.i("SpecialPullAbility.Comp.CdUtils", "app on foreground");
                return true;
            }
            boolean isSupport = AliveSionAbility.instance().isSupport(Collections.singletonList("DirectSubAbility"), str);
            Logger.i("SpecialPullAbility.Comp.CdUtils", "isSupportSionDirect:" + isSupport);
            return isSupport;
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.CdUtils", th);
            return false;
        }
    }

    public static boolean b(Intent intent, String str) {
        try {
            Logger.i("SpecialPullAbility.Comp.CdUtils", "startActivityBySionSpecialSeqAbility");
            String configValue = RemoteConfig.instance().getConfigValue("ab_pull_activity_sion_special_seq_64400", "");
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            SionRequest sionRequest = new SionRequest(intent, str, UUID.randomUUID().toString());
            List c2 = c(configValue);
            if (!c2.isEmpty()) {
                sionRequest.setSubAbilityNames(c2);
            }
            return AliveSionAbility.instance().start(sionRequest).isSuccess();
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.CdUtils", th);
            return false;
        }
    }
}
