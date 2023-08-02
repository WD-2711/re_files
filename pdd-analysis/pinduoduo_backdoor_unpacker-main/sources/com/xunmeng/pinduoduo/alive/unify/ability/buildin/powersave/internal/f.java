package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal;

import android.content.Context;
import android.provider.Settings;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: f.class */
public class f extends d {
    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean a() {
        return f() != 404;
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult d() {
        return new PowerSaveResult(g() == 1 ? 2 : 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult c() {
        boolean z = f() == 1;
        if (!z) {
            z = a((Context) this.c);
        }
        return new PowerSaveResult(z ? 1 : 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean b() {
        return f() != 404;
    }

    private int f() {
        try {
            int i = Settings.System.getInt(this.c.getContentResolver(), "POWER_SAVE_MODE_OPEN");
            Logger.i(this.a, "XmPowerSaveModeGainer result:" + i);
            return i;
        } catch (Settings.SettingNotFoundException e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return 404;
        }
    }

    private int g() {
        try {
            int i = Settings.System.getInt(this.c.getContentResolver(), "power_supersave_mode_open");
            Logger.i(this.a, "XmPowerSaveModeGainer result:" + i);
            return i;
        } catch (Settings.SettingNotFoundException e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return 404;
        }
    }
}
