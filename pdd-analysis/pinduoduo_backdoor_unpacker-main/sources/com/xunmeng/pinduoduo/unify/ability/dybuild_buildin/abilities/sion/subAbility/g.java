package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import java.util.Map;

/* loaded from: g.class */
public class g implements a.AnonymousClass1 {
    public StatusResult start(SionRequest sionRequest) {
        String str = null;
        Map extra = sionRequest.getExtra();
        if (extra != null && "ryze_activity_proxy".equals(extra.get("start_type"))) {
            str = "RumbleProxyActivity";
        }
        return AbilityFramework.executeAbility("RumbleStartAbility", new IntentRequest(sionRequest.getIntent(), str, sionRequest.getCaller()), new StatusResult(false, "default failed"));
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(AbilityFramework.isSupport("RumbleStartAbility"), null);
    }
}
