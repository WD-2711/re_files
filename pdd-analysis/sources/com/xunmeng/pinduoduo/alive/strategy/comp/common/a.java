package com.xunmeng.pinduoduo.alive.strategy.comp.common;

import android.content.ComponentName;
import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.pa.interfaces.adapter.proxy.ResourceManager;
import java.util.HashSet;
import java.util.Set;

/* loaded from: a.class */
public class a {
    public static final long a = 0;
    private static final String b = null;
    private static final String c = StrategyFramework.getFrameworkContext().getPackageName();
    private static final ComponentName d = new ComponentName(c, "com.xunmeng.pinduoduo.service.UserModuleService");
    private static final Set e = new HashSet();

    public static boolean a() {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        if (!RomOsUtil.instance().isHonerManufacture() && !RomOsUtil.instance().isNewHuaweiManufacture()) {
            Logger.i("LVST2.comp.Common.AccountHelper", "not hw, return false");
            return false;
        }
        BlackListItem cachedConfig = MSCManager.instance().getCachedConfig(frameworkContext, new SceneRequest("1035", 0L, 0L, (String) null, (String) null));
        if (cachedConfig == null) {
            Logger.i("LVST2.comp.Common.AccountHelper", "blackListItem == null, return true");
            return true;
        } else if (cachedConfig.isBlack() && !CommonHelper.instance().isHtj()) {
            Logger.i("LVST2.comp.Common.AccountHelper", "account sync hit global black list");
            b.a(ResourceManager.getInstance().getTideAccountName(), ResourceManager.getInstance().getTideAccountName());
            a(false);
            b(false);
            return true;
        } else {
            Logger.i("LVST2.comp.Common.AccountHelper", "account sync not hit black list");
            a(true);
            b(true);
            b.a(frameworkContext, ResourceManager.getInstance().getTideAccountName(), ResourceManager.getInstance().getTideAccountType(), ResourceManager.getInstance().getTideContentAuthority());
            return false;
        }
    }

    static {
        e.add(new ComponentName(c, "com.xunmeng.pinduoduo.lifecycle.service.PDDSyncService"));
        e.add(new ComponentName(c, "com.xunmeng.pinduoduo.service.PDDIISyncService"));
        e.add(new ComponentName(c, "com.xunmeng.pinduoduo.service.PDDVSyncService"));
        e.add(new ComponentName(c, "com.xunmeng.pinduoduo.service.PDDIVSyncService"));
    }

    public static void b(boolean z) {
        Logger.i("LVST2.comp.Common.AccountHelper", "setJanusComponentsState: %s", new Object[]{Boolean.valueOf(z)});
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        int i = z ? 1 : 2;
        for (ComponentName componentName : e) {
            a(frameworkContext, componentName, i);
        }
    }

    public static void a(boolean z) {
        Logger.i("LVST2.comp.Common.AccountHelper", "setTideComponentState: %s", new Object[]{Boolean.valueOf(z)});
        a(StrategyFramework.getFrameworkContext(), d, z ? 1 : 2);
    }

    public static boolean b() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_skip_account_strategy_5750", false);
    }

    public static boolean c() {
        return b() || RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_skip_tide_strategy_5900", false);
    }

    public static boolean d() {
        return b() || RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_skip_janus_strategy_5900", false);
    }

    private static void a(Context context, ComponentName componentName, int i) {
        try {
            context.getPackageManager().setComponentEnabledSetting(componentName, i, 1);
        } catch (Exception e2) {
            Logger.e("LVST2.comp.Common.AccountHelper", "setComponentState error, component: %s, error:%s", new Object[]{componentName, e2});
        }
    }
}
