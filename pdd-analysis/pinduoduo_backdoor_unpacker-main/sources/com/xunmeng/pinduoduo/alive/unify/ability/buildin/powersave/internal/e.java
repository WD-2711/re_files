package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: e.class */
public class e extends d {
    private static final String f = null;

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean a() {
        return f() != 404;
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult d() {
        try {
            String str = PddSystemProperties.instance().get("sys.super_power_save", "");
            Logger.i(this.a, "vivo getPowerSuperSaveMode result:" + ((Object) str));
            return new PowerSaveResult("on".equals(str) ? true : PddSystemProperties.instance().getBoolean("sys.super_power_save", false) ? 2 : 0);
        } catch (Exception e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return new PowerSaveResult(e.getMessage());
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    public PowerSaveResult c() {
        boolean z = f() == 2;
        if (!z) {
            z = a((Context) this.c);
        }
        return new PowerSaveResult(z ? 1 : 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal.d
    protected boolean b() {
        return !TextUtils.isEmpty(PddSystemProperties.instance().get("sys.super_power_save", ""));
    }

    private int f() {
        try {
            int i = Settings.System.getInt(this.c.getContentResolver(), "power_save_type");
            Logger.i(this.a, "vivo getPowerSaveType result:" + i);
            return i;
        } catch (Settings.SettingNotFoundException e) {
            Logger.e(this.a, e);
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.b.a(e);
            return 404;
        }
    }
}
