package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    private static final String a = null;

    private StatusResult a(Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVUA.Dybuild.Sion.DirectSubAbility", "intent package is null");
        }
        try {
            PendingIntent.getActivity(frameworkContext, 0, intent, 2048).send();
            Logger.i("LVUA.Dybuild.Sion.DirectSubAbility", "startActivityDirectly");
            return new StatusResult(true, "success");
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.DirectSubAbility", th);
            return new StatusResult(false, th.getMessage());
        }
    }

    public StatusResult start(SionRequest sionRequest) {
        return a(sionRequest.getIntent());
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "OVERLAY");
        boolean hasPermission2 = DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "BACKGROUND_START_ACTIVITY");
        Logger.i("LVUA.Dybuild.Sion.DirectSubAbility", "startBackgroundActivity: hasOverlayPermission: %s, hasBgStartActivityPermission: %s, screenState: %s", new Object[]{Boolean.valueOf(hasPermission), Boolean.valueOf(hasPermission2), Integer.valueOf(ScreenUtils.instance().getScreenState())});
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(a(hasPermission, hasPermission2), null);
    }

    private boolean a(boolean z, boolean z2) {
        boolean z3 = true;
        if (RomOsUtil.instance().isVivoManufacture()) {
            z3 = z2;
        } else if (RomOsUtil.instance().isXiaomiManufacture()) {
            if (Build.VERSION.SDK_INT >= 29) {
                z3 = z && z2;
            } else {
                z3 = z2;
            }
        } else if (Build.VERSION.SDK_INT >= 29) {
            z3 = z;
        }
        return RemoteConfig.instance().getBoolean("can_start_bg_activity_5230", z3);
    }
}
