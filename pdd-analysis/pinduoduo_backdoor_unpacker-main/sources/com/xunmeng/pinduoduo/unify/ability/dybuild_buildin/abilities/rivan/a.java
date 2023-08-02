package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.LauncherDetect;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import java.util.List;

/* loaded from: a.class */
class a {
    private static final int a = 0;
    private static final int b = 0;
    private long c;
    private static final String d = null;
    private final boolean e;
    private static final int f = 0;
    private static final int g = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Intent b(RivanConfig rivanConfig) {
        Intent intent = new Intent(rivanConfig.broadcastAction);
        intent.setComponent(new ComponentName(rivanConfig.pkgName, rivanConfig.widgetProviderName));
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(RivanConfig rivanConfig) {
        try {
            Logger.i("LVUA.Dybuild.PAChecker", "isSupport called");
            if (this.e) {
                long currentTimeMillis = System.currentTimeMillis() - this.c;
                if (currentTimeMillis <= rivanConfig.intervalTime) {
                    Logger.i("LVUA.Dybuild.PAChecker", "cd time remain " + currentTimeMillis);
                    e.a("cd_interval", currentTimeMillis + "");
                    return false;
                }
            }
            if (Settings.Secure.getInt(b().getContentResolver(), "com.miui.personalassistant.preferences.key_cta_welcome", 1) != 0) {
                Logger.i("LVUA.Dybuild.PAChecker", "user not agree, ability not support");
                e.a("pa_not_agree", null);
                return false;
            } else if (com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a()) {
                Logger.i("LVUA.Dybuild.PAChecker", "hit system filter, ability not support");
                e.a("hit_system_filter", null);
                return false;
            } else {
                PackageInfo packageInfo = null;
                try {
                    packageInfo = b().getPackageManager().getPackageInfo(rivanConfig.pkgName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    Logger.e("LVUA.Dybuild.PAChecker", "pkg not exist", e);
                }
                if (packageInfo == null) {
                    Logger.i("LVUA.Dybuild.PAChecker", "pkg not installed, ability not support");
                    e.a("pkg_not_install", rivanConfig.pkgName);
                    return false;
                }
                int i = packageInfo.versionCode;
                Logger.i("LVUA.Dybuild.PAChecker", "ability pkg version code: %d, version name: %s", new Object[]{Integer.valueOf(i), packageInfo.versionName});
                if ((rivanConfig.maxSupportVersion != null && i > rivanConfig.maxSupportVersion.intValue()) || (rivanConfig.minSupportVersion != null && i < rivanConfig.minSupportVersion.intValue())) {
                    Logger.i("LVUA.Dybuild.PAChecker", "not in version limit, ability not support");
                    e.a("version_not_match", String.valueOf(packageInfo.versionCode));
                    return false;
                }
                List list = rivanConfig.banVersions;
                if (list != null && !list.isEmpty() && list.contains(Integer.valueOf(i))) {
                    Logger.i("LVUA.Dybuild.PAChecker", "ban version " + i);
                    return false;
                }
                List<ResolveInfo> queryBroadcastReceivers = b().getPackageManager().queryBroadcastReceivers(b(rivanConfig), 0);
                if (queryBroadcastReceivers == null || queryBroadcastReceivers.isEmpty()) {
                    Logger.i("LVUA.Dybuild.PAChecker", "receiver not exist, ability not support");
                    e.a("receiver_not_exist", rivanConfig.widgetProviderName);
                    return false;
                }
                if (!d(rivanConfig)) {
                    Logger.i("LVUA.Dybuild.PAChecker", "hit not full screen");
                    Boolean bool = null;
                    if (StrategyFramework.hasCapability("LauncherDetect_isAppForeground")) {
                        bool = a(rivanConfig.miuiLauncherPkgName);
                    }
                    Logger.i("LVUA.Dybuild.PAChecker", "has navigation menu, isLauncherForeground: %s", new Object[]{bool});
                    boolean isScreenOn = ScreenUtils.instance().isScreenOn();
                    Logger.i("LVUA.Dybuild.PAChecker", "has navigation menu, isScreenOn:" + isScreenOn);
                    if (!isScreenOn || bool == null || !bool.booleanValue()) {
                        Logger.i("LVUA.Dybuild.PAChecker", "hit launcher in background");
                        return false;
                    }
                }
                if (!c(rivanConfig)) {
                    return true;
                }
                e.a("hit_black_list", "black");
                return false;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.PAChecker", "isSupport failed", th);
            e.a("is_support_fail", th.getMessage());
            return false;
        }
    }

    private boolean d(RivanConfig rivanConfig) {
        int i = rivanConfig.defaultNavigationMode;
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                i = Settings.Global.getInt(b().getContentResolver(), "force_fsg_nav_bar", 0);
                Logger.i("LVUA.Dybuild.PAChecker", "force_fsg_nav_bar = %d", new Object[]{Integer.valueOf(i)});
            }
        } catch (Exception e) {
            Logger.e("LVUA.Dybuild.PAChecker", "fail to get navigation mode", e);
            e.a("navigation_mode_fail", e.getMessage());
        }
        return i == 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        if (this.e) {
            this.c = System.currentTimeMillis();
        } else {
            Logger.i("LVUA.Dybuild.PAChecker", "do not update");
        }
    }

    private Context b() {
        return StrategyFramework.getFrameworkContext();
    }

    private Boolean a(String str) {
        return LauncherDetect.instance().isAppForeground(str);
    }

    private boolean c(RivanConfig rivanConfig) {
        if (TextUtils.isEmpty(rivanConfig.blackSceneId)) {
            return false;
        }
        SceneRequest sceneRequest = new SceneRequest(rivanConfig.blackSceneId, Long.valueOf(rivanConfig.refreshTTLMills), Long.valueOf(rivanConfig.invalidTTLMills), (String) null, (String) null);
        BlackListItem config = rivanConfig.mscUseSyncApi ? MSCManager.instance().getConfig(b(), sceneRequest) : MSCManager.instance().getCachedConfig(b(), sceneRequest);
        if (rivanConfig.isIgnoreNoneBlack && config == null) {
            Logger.i("LVUA.Dybuild.PAChecker", "ignore null black");
            return false;
        } else if (config != null && !config.isBlack()) {
            return false;
        } else {
            Logger.i("LVUA.Dybuild.PAChecker", "hit black list: %s, ability not support", new Object[]{rivanConfig.blackSceneId});
            return true;
        }
    }
}
