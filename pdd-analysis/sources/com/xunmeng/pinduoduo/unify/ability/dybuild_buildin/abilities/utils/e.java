package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.os.Build;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppInLockBox;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.JsonRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.JsonResult;
import org.json.JSONObject;

/* loaded from: e.class */
public class e {
    private static final String a = null;

    public static boolean a() {
        if (AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())) {
            Logger.i("LVUA.Dybuild.Utils", "not check system filter when foreground");
            return false;
        } else if (c()) {
            Logger.i("LVUA.Dybuild.Utils", "filter by box lock, return");
            return true;
        } else if (b()) {
            Logger.i("LVUA.Dybuild.Utils", "filter by limit app, return");
            return true;
        } else {
            Logger.i("LVUA.Dybuild.Utils", "not filter by system");
            return false;
        }
    }

    public static boolean b() {
        if (!RomOsUtil.instance().isXiaomiManufacture() || Build.VERSION.SDK_INT < 28) {
            return false;
        }
        Logger.i("LVUA.Dybuild.Utils", "check xm");
        return StrategyFramework.getFrameworkContext().getPackageManager().isPackageSuspended();
    }

    public static boolean c() {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15401000) {
            return d();
        }
        return false;
    }

    private static boolean d() {
        return AppInLockBox.instance().inLockBox();
    }

    public static boolean a(String str) {
        try {
            if (!AbilityFramework.isSupport("SystemDoubleOpen")) {
                return false;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("packageName", str);
            JsonResult executeAbility = AbilityFramework.executeAbility("SystemDoubleOpen", new JsonRequest(jSONObject.toString()));
            if (executeAbility == null) {
                Logger.i("LVUA.Dybuild.Utils", "json result is null");
                return false;
            }
            JSONObject json = executeAbility.json();
            if (!executeAbility.isSuccess()) {
                Logger.i("LVUA.Dybuild.Utils", executeAbility.getErrorMsg());
                return false;
            }
            boolean optBoolean = json.optBoolean("isDoubleOpen");
            Logger.i("LVUA.Dybuild.Utils", "isSystemopen = " + optBoolean);
            return optBoolean;
        } catch (Exception e) {
            Logger.i("LVUA.Dybuild.Utils", e);
            return false;
        }
    }
}
