package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hwselfstart;

import android.content.ContentValues;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hwselfstart.b */
/* loaded from: b.class */
public class HuaweiSelfStartManager extends BaseBizProviderImpl implements a.AnonymousClass1 {
    static final String a = null;
    private final int b = Integer.parseInt(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_hw_provider_task_limit_57500", "2"));

    public boolean hasAbility(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (hasPermission()) {
            return DeprecatedAb.instance().isFlowControl("ab_hw_fp_ability_57500_" + str, true);
        }
        Logger.i("LVBA.AliveModule.HwSelfStartProvider", "no permission to use now ability!");
        return false;
    }

    protected String getProviderScene() {
        return "HW_SS_UPDATE_DB";
    }

    public boolean setSelfStart(boolean z, boolean z2, String str) {
        if (!hasAbility("setSelfStart")) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "skip setSS for no ability !");
            return false;
        }
        return startSelfStart(z, z2, str);
    }

    public boolean canSelfStart(boolean z, String str) {
        if (!DeprecatedAb.instance().isFlowControl("ab_hw_fp_ability_57500_canSelfStart", true)) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "AbKey restrict cannot execute!");
            return false;
        }
        return c.a(z, str);
    }

    public boolean setSelfStart(boolean z, boolean z2) {
        return setSelfStart(z, z2, StrategyFramework.getFrameworkContext().getPackageName());
    }

    public HuaweiSelfStartManager() {
        Logger.i("LVBA.AliveModule.HwSelfStartProvider", "finish init HwSelfStartProviderImpl, task time out limit is %s", new Object[]{Integer.valueOf(this.b)});
    }

    public boolean hasPermission() {
        return f.a().hasPermission(getProviderScene());
    }

    /* renamed from: a */
    private boolean startSelfStart(boolean z, boolean z2, String str) {
        boolean enableSelfStart = z ? enableSelfStart(z2, str) : disableSelfStart(str);
        Logger.i("LVBA.AliveModule.HwSelfStartProvider", "start to ss!");
        return enableSelfStart;
    }

    /* renamed from: a */
    private boolean enableSelfStart(boolean z, String str) {
        if (!c.a()) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "enable SS failed!");
            return false;
        }
        ContentValues contentValues = new ContentValues();
        if (z) {
            contentValues.put("isShow", "0");
        } else {
            contentValues.put("isShow", "1");
        }
        contentValues.put("packageName", str);
        contentValues.put("operation", "0");
        contentValues.put("autoStart", "0");
        contentValues.put("autoAwakedBy", "0");
        if (c.b()) {
            contentValues.put("background", "0");
        } else {
            contentValues.put("background", "1");
        }
        return c.a(contentValues, z, str);
    }

    /* renamed from: a */
    private boolean disableSelfStart(String str) {
        if (!c.a()) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "disable SS failed!");
            return false;
        }
        return c.a(str);
    }

    public boolean isSetCompleted(boolean z, String str) {
        if (!hasAbility("isSetCompleted")) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "skip isSetCompleted for no ability !");
            return false;
        }
        return c.b(z, str);
    }
}