package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;

/* loaded from: f.class */
public class f implements a.AnonymousClass1 {
    public StatusResult start(SionRequest sionRequest) {
        return AbilityFramework.executeAbility("RivanStartShortcutAbility", new IntentRequest(sionRequest.getIntent()), new StatusResult(false, "default error"));
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(AbilityFramework.isSupport("RivanStartShortcutAbility"), null);
    }
}
