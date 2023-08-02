package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.base.BaseAbilityRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: c.class */
public class c extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private final d b = d.e();
    private final String c = "pinduoduo_Android.build_in_power_super_save_key_61800";

    public boolean isSupport() {
        try {
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_power_super_save_key_61800", false)) {
                return this.b.a("power_super_save_mode");
            }
            Logger.i("LVUA.Buildin.PowerSuperSaveModeAbility", "power super save is false");
            return false;
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.PowerSuperSaveModeAbility", th);
            b.a(th);
            return false;
        }
    }

    /* renamed from: a */
    public PowerSaveResult execute(BaseAbilityRequest baseAbilityRequest) {
        try {
            PowerSaveResult d = this.b.d();
            if (d.isSuccess() && d.getPowerMode() == 2) {
                b.a("power_super_save");
            }
            Logger.i("LVUA.Buildin.PowerSuperSaveModeAbility", "isSuccess:" + d.isSuccess() + " powerSaveMode:" + d.getPowerMode());
            return d;
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.PowerSuperSaveModeAbility", th);
            b.a(th);
            return new PowerSaveResult(th.getMessage());
        }
    }
}
