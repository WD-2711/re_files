package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.VoidRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private final d b = d.e();
    private final String c = "pinduoduo_Android.build_in_power_save_key_61800";

    public boolean isSupport() {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_power_save_key_61800", false)) {
                Logger.i("LVUA.Buildin.PowerSaveModeAbility", "power save ab is false");
                return false;
            }
            boolean a2 = this.b.a("power_save_mode");
            Logger.i("LVUA.Buildin.PowerSaveModeAbility", "isSupport:" + a2);
            return a2;
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.PowerSaveModeAbility", th);
            b.a(th);
            return false;
        }
    }

    /* renamed from: a */
    public PowerSaveResult execute(VoidRequest voidRequest) {
        try {
            PowerSaveResult c = this.b.c();
            if (c.isSuccess() && c.getPowerMode() == 1) {
                b.a("power_save");
            }
            Logger.i("LVUA.Buildin.PowerSaveModeAbility", "isSuccess:" + c.isSuccess() + " powerSaveMode:" + c.getPowerMode());
            return c;
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.PowerSaveModeAbility", th);
            b.a(th);
            return new PowerSaveResult(th.getMessage());
        }
    }
}
