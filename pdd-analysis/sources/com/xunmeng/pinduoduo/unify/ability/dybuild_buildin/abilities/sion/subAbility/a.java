package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.AliveModule;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AlarmManagerApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;

    private StatusResult a(Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVUA.Dybuild.Sion.AlarmSubAbility", "intent package is null");
        }
        if (RomOsUtil.instance().isXiaomiManufacture() && Build.VERSION.SDK_INT >= 26) {
            ReflectUtils.instance().invokeSysMethod(intent, "setMiuiFlags", new Object[]{2});
        }
        PendingIntent pendingIntent = null;
        try {
            pendingIntent = PendingIntent.getActivities(frameworkContext, 1, new Intent[]{intent}, 2048);
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.AlarmSubAbility", th);
        }
        if (pendingIntent == null) {
            Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "pendingIntent null");
            return new StatusResult(false, "pendingIntent null");
        }
        AlarmManager alarmManager = (AlarmManager) frameworkContext.getSystemService("alarm");
        if (alarmManager == null) {
            Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "alarmManager null");
            return new StatusResult(false, "alarmManager null");
        }
        long currentTimeMillis = System.currentTimeMillis() + NumberUtils.instance().parseInt(a("x.alarm_bg_activity_delay", "100"));
        Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "start bg activity by alarm");
        try {
            a(DeprecatedAb.instance().isFlowControl("bg_act_alarm_clock_5300", !RomOsUtil.instance().isNewHuaweiManufacture() && !RomOsUtil.instance().isHonerManufacture()), alarmManager, currentTimeMillis, pendingIntent);
            return new StatusResult(true, "success");
        } catch (Throwable th2) {
            Logger.e("LVUA.Dybuild.Sion.AlarmSubAbility", th2);
            return new StatusResult(false, "unknown");
        }
    }

    private void a(boolean z, AlarmManager alarmManager, long j, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 21 && z) {
            AlarmManagerApi.instance().setAlarmClock(alarmManager, new AlarmManager.AlarmClockInfo(j, null), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
            AlarmManagerApi.instance().setExact(alarmManager, 0, j, pendingIntent);
        } else {
            AlarmManagerApi.instance().set(alarmManager, 0, j, pendingIntent);
        }
    }

    private String a(String str, String str2) {
        return Configuration.instance().getConfiguration(str, str2);
    }

    private boolean a(int i) {
        boolean z = true;
        try {
            z = ((Boolean) Class.forName("android.view.OplusWindowManager").getDeclaredMethod("isWindowShownForUid", Integer.TYPE).invoke(Class.forName("android.view.OplusWindowManager").newInstance(), Integer.valueOf(i))).booleanValue();
            Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "ret:" + z);
        } catch (Exception e) {
            Logger.e("LVUA.Dybuild.Sion.AlarmSubAbility", "reflect err:", e);
        }
        return z;
    }

    public StatusResult start(SionRequest sionRequest) {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15401000) {
            return a(sionRequest.getIntent());
        }
        AliveModule.instance().startBackgroundActivityByAlarm(sionRequest.getIntent());
        return new StatusResult(true, "success");
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        return a();
    }

    private com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e a() {
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "BACKGROUND_START_ACTIVITY");
        int screenState = ScreenUtils.instance().getScreenState();
        boolean z = true;
        String str = null;
        if (RomOsUtil.instance().isVivoManufacture()) {
            z = hasPermission;
        }
        ScreenUtils.instance();
        if (screenState != 2) {
            Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "need screen present, current:" + screenState);
            str = "screen error";
            z = false;
        }
        if (RomOsUtil.instance().isXiaomiManufacture()) {
            z = true;
        }
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.dybuild_check_oppo_uid1000_window_53700", true) && RomOsUtil.instance().isOppoManufacture() && !a(1000)) {
            Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "not uid1000 app window");
            str = "not uid1000 app window";
            z = false;
        }
        Logger.i("LVUA.Dybuild.Sion.AlarmSubAbility", "canStartBgActivityByAlarm: defaultAb: %s", new Object[]{Boolean.valueOf(z)});
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_activity_by_alarm_63500", z), str);
    }
}
