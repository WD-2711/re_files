package com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.internal;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IRomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.powersave.PowerSaveResult;

/* loaded from: d.class */
public class d {
    protected static final int b = 0;
    public static final String d = null;
    public static final String e = null;
    protected String a = "LVUA.Buildin.PowerSaveModeGainer";
    protected final Context c = StrategyFramework.getFrameworkContext();

    public boolean a(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (Build.VERSION.SDK_INT >= 21) {
            return powerManager.isPowerSaveMode();
        }
        return false;
    }

    protected boolean a() {
        return false;
    }

    public PowerSaveResult d() {
        return new PowerSaveResult("default");
    }

    public PowerSaveResult c() {
        return new PowerSaveResult("default");
    }

    protected boolean b() {
        return false;
    }

    public boolean a(String str) {
        if ("power_save_mode".equals(str)) {
            return a();
        }
        if (!"power_super_save_mode".equals(str)) {
            return false;
        }
        return b();
    }

    public static d e() {
        IRomOsUtil instance = RomOsUtil.instance();
        return (instance.isNewHuaweiManufacture() || instance.isHonerManufacture()) ? new b() : instance.isXiaomiManufacture() ? new f() : instance.isOppoManufacture() ? new c() : instance.isVivoManufacture() ? new e() : new a();
    }
}
