package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: c.class */
public class c extends d {
    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean a() {
        return f() != 404;
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult d() {
        try {
            return new PowerSaveResult(g() == 1 ? 2 : 0);
        } catch (Exception e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return new PowerSaveResult(e.getMessage());
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult c() {
        boolean z = f() != 0;
        if (!z) {
            z = a((Context) this.c);
        }
        return new PowerSaveResult(z ? 1 : 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean b() {
        return g() != 404;
    }

    private int f() {
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                int i = Settings.Global.getInt(this.c.getContentResolver(), "low_power");
                Logger.i(this.a, "oppo getLowPower result:" + i);
                return i;
            } catch (Settings.SettingNotFoundException e) {
                Logger.e(this.a, e);
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
                return 404;
            }
        }
        return 404;
    }

    private int g() {
        try {
            int i = Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "super_powersave_mode_state");
            Logger.i(this.a, "oppo getPowerSaveModeState result:" + i);
            return i;
        } catch (Settings.SettingNotFoundException e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return 404;
        }
    }
}
