package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;

/* loaded from: j.class */
public class j implements a.AnonymousClass1 {
    private static final String a = null;

    public StatusResult start(SionRequest sionRequest) {
        com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a("com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.sion.subAbility.VivoOverlaySubAbility", StrategyFramework.getFrameworkContext(), sionRequest.getIntent());
        return new StatusResult(true, "success");
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        if (!RomOsUtil.instance().isVivoManufacture()) {
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "not vivo");
        }
        if (!DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "OVERLAY")) {
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "not overlay permission");
        }
        Logger.i("LVUA.Dybuild.Sion.VivoOverlaySubAbility", "pdd has overlay permission.");
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(true, "pdd has overlay permission");
    }
}
