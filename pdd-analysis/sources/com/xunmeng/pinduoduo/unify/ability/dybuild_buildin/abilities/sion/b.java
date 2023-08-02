package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionResult;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    private static final String a = null;

    public boolean isSupport(String str) {
        Logger.i("LVUA.Dybuild.Sion.EmptySionAbilityImpl", "isSupport");
        return false;
    }

    public boolean isSupport(List list, String str) {
        Logger.i("LVUA.Dybuild.Sion.EmptySionAbilityImpl", "isSupport");
        return false;
    }

    public SionResult start(SionRequest sionRequest) {
        return new SionResult(false, "empty");
    }
}
