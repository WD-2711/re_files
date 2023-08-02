package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.d;
import com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.VoidRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    static final String a = null;
    private final String b = "pinduoduo_Android.build_in_step_info_config_62100";
    private final e c = d.a();

    private boolean a(StepsInfoConfig stepsInfoConfig) {
        if (TextUtils.isEmpty(stepsInfoConfig.blackSceneId)) {
            return false;
        }
        BlackListItem cachedConfig = MSCManager.instance().getCachedConfig(StrategyFramework.getFrameworkContext(), new SceneRequest(stepsInfoConfig.blackSceneId, Long.valueOf(stepsInfoConfig.refreshTTLMills), Long.valueOf(stepsInfoConfig.invalidTTLMills), (String) null, (String) null));
        return cachedConfig == null || cachedConfig.isBlack();
    }

    public boolean isSupport() {
        try {
            StepsInfoConfig a2 = a();
            if (a2 == null) {
                Logger.i("LVUA.Buildin.StepsInfoAbility", "steps info config is null");
                return false;
            } else if (!a(a2)) {
                return this.c.a();
            } else {
                Logger.i("LVUA.Buildin.StepsInfoAbility", "hit black list");
                return false;
            }
        } catch (Exception e) {
            Logger.i("LVUA.Buildin.StepsInfoAbility", "is support error", e);
            return false;
        }
    }

    private StepsInfoConfig a() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.build_in_step_info_config_62100", "");
        Logger.i("LVUA.Buildin.StepsInfoAbility", "raw config:" + configValue);
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        return (StepsInfoConfig) PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin").fromJson(configValue, StepsInfoConfig.class);
    }

    /* renamed from: a */
    public KarmaResult execute(VoidRequest voidRequest) {
        StepsInfoConfig a2 = a();
        if (a2 == null) {
            Logger.i("LVUA.Buildin.StepsInfoAbility", "steps info config is null");
            return new KarmaResult("not_support");
        } else if (a(a2)) {
            Logger.i("LVUA.Buildin.StepsInfoAbility", "hit black list");
            return new KarmaResult("hit_black_list");
        } else if (TimeStamp.instance().getRealLocalTimeV2() - b.a() < a2.coolDownTimeInSeconds * 0) {
            Logger.i("LVUA.Buildin.StepsInfoAbility", "in cd time, use last result");
            if (!b.c()) {
                return new KarmaResult("in_cd_time");
            }
            int b = b.b();
            long a3 = b.a();
            Logger.i("LVUA.Buildin.StepsInfoAbility", "last grant steps : %s, last grant time : %s", new Object[]{Integer.valueOf(b), Long.valueOf(a3)});
            return new KarmaResult(b, a3);
        } else {
            KarmaResult b2 = this.c.b();
            if (b2.isSuccess()) {
                Logger.i("LVUA.Buildin.StepsInfoAbility", "grant success, steps : %s, grant time : %s", new Object[]{Integer.valueOf(b2.getSteps()), Long.valueOf(b2.getTimeStamp())});
                b.a(b2.getSteps());
            }
            b.a(b2.isSuccess());
            b.a(TimeStamp.instance().getRealLocalTimeV2());
            return b2;
        }
    }
}
