package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: b.class */
public class b extends d {
    private static final String f = null;

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean a() {
        return f() != 404;
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult d() {
        boolean z = PddSystemProperties.instance().getBoolean("sys.super_power_save", false);
        Logger.i(this.a, "hw getPowerSuperSaveMode result:" + z);
        return new PowerSaveResult(z ? 2 : 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult c() {
        try {
            boolean z = f() == 4;
            if (!z) {
                z = a((Context) this.c);
            }
            return new PowerSaveResult(z ? 1 : 0);
        } catch (Exception e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return new PowerSaveResult(e.getMessage());
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean b() {
        return !TextUtils.isEmpty(PddSystemProperties.instance().get("sys.super_power_save", (String) null));
    }

    private int f() {
        try {
            int i = Settings.System.getInt(this.c.getContentResolver(), "SmartModeStatus");
            Logger.i(this.a, "hw getSmartModeStatus result:" + i);
            return i;
        } catch (Settings.SettingNotFoundException e) {
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return 404;
        }
    }
}
