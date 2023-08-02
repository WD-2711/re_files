package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;

/* loaded from: e.class */
public class e implements a.AnonymousClass1 {
    private static final String a = null;

    public StatusResult start(SionRequest sionRequest) {
        return AbilityFramework.executeAbility("RivanStartSmartShortcutAbility", new IntentRequest(sionRequest.getIntent()), new StatusResult(false, "default error"));
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(AbilityFramework.isSupport("RivanStartSmartShortcutAbility"), null);
    }
}
