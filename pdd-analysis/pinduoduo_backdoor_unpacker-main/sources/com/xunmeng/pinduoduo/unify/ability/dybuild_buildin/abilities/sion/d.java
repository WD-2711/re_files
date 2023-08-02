package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionInternalRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionInternalResult;

/* loaded from: d.class */
public class d extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;

    public boolean isSupport() {
        return true;
    }

    private SionConfig a() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.lvua_sion_ability_config_63500", "");
        Logger.i("LVUA.Dybuild.Sion.SionInternalAbility", "sionConfig=" + configValue);
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
        SionConfig sionConfig = (SionConfig) pluginJSONFormatUtils.fromJson(configValue, SionConfig.class);
        Logger.i("LVUA.Dybuild.Sion.SionInternalAbility", "to json result:" + pluginJSONFormatUtils.toJson(sionConfig));
        return sionConfig;
    }

    public boolean shouldFilterByFramework() {
        return false;
    }

    /* renamed from: a */
    public SionInternalResult execute(SionInternalRequest sionInternalRequest) {
        Logger.i("LVUA.Dybuild.Sion.SionInternalAbility", "enter execute");
        SionConfig a2 = a();
        SionInternalResult sionInternalResult = new SionInternalResult(a2 != null ? new a(sionInternalRequest, a2) : new b());
        Logger.i("LVUA.Dybuild.Sion.SionInternalAbility", "finish execute");
        return sionInternalResult;
    }
}
