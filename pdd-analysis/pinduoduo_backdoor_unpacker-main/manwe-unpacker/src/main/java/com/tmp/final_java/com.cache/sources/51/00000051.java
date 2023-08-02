package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLockPull;

import android.content.ComponentName;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: c.class */
public class c extends BaseBizProviderImpl implements a.AnonymousClass1 {
    private final String a = "LVBA.AliveModule.Provider.OppoLockPullProviderImpl";

    public Boolean canLockPull(ComponentName componentName) {
        if (!hasAbility("canLockPull")) {
            return null;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.OppoLockPullProviderImpl", "skip canLockPull since no permission");
            return null;
        }
        Boolean a = b.a(componentName);
        LogUtils.logInvokeResultEventAndError("oppo_lock_pull", "canLockPull", a != null);
        Logger.i("LVBA.AliveModule.Provider.OppoLockPullProviderImpl", "canLockPull is %s", new Object[]{a});
        return a;
    }

    public boolean hasPermission() {
        return f.a().hasPermission("provider_oppo_ld");
    }
}