package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.UriPermission;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.impl.EmptyFileProviderImpl;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IconInfo;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.LayoutProps;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.b */
/* loaded from: b.class */
public class FileProvider extends EmptyFileProviderImpl implements a.AnonymousClass1 {
    private final String a = "LVBA.AliveModule.Provider.HW";
    private final String b = "ab_key_hw_fp_ability_57300";
    private final String c = "persistTs";
    private final IMMKV d = MMKVCompat.module("fp_hw_provider", false);
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final ReentrantLock h = new ReentrantLock();

    /* renamed from: a */
    private Boolean checkActivityExistence(Intent intent) {
        try {
            ResolveInfo resolveActivity = StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 0);
            return Boolean.valueOf((resolveActivity == null || resolveActivity.activityInfo == null) ? false : true);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.HW", "isActivityExist fail", e2);
            EventLogger.logExceptionEvent("isActivityExist", e2);
            return null;
        }
    }

    /* renamed from: a */
    public static void launchIntentWithHwLauncherProvider(Intent intent, boolean z) {
        com.xunmeng.pinduoduo.alive.base.ability.impl.provider.c.a("fd.provider.provider_hw_launcher", intent);
    }

    /* renamed from: a */
    private boolean checkUriPermissionExistence(List list) {
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(a.LAUNCHER_PROVIDER_URI, ((UriPermission) list.get(i)).getUri().toString())) {
                return true;
            }
        }
        return false;
    }

    public void startGrantPermission(String str) {
        if (!hasAbility("startGrantPermission")) {
            return;
        }
        if (hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip grantPermission since has permission");
            EventLogger.logGrantPermissionResultEventAndError(true, str, false);
            return;
        }
        String huaweiOrHonorDefaultLauncherPackageName = f.getHuaweiOrHonorDefaultLauncherPackageName();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(huaweiOrHonorDefaultLauncherPackageName, huaweiOrHonorDefaultLauncherPackageName + ".RequestPermissionActivity"));
        Boolean checkActivityExistence = checkActivityExistence(intent);
        if (checkActivityExistence == null || !checkActivityExistence.booleanValue()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip grantPermission since RequestPermissionActivity not exist");
            return;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "grantPermission started: arg: %s, requestId: %s, launcherPkg: %s", new Object[]{str, UUID.randomUUID().toString(), huaweiOrHonorDefaultLauncherPackageName});
        try {
            EventLogger.logGrantPermissionResultEventAndError(true, str, true);
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setComponent(new ComponentName(StrategyFramework.getFrameworkContext(), "com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity"));
            intent2.setData(Uri.parse(a.LAUNCHER_PROVIDER_URI));
            intent2.addFlags(128);
            intent2.addFlags(64);
            intent2.addFlags(1);
            intent2.addFlags(2);
            intent2.putExtra("src", str);
            intent.putExtra("target_intent", intent2);
            intent.putExtra("request_permission", new String[]{"android.permission.INTERNET"});
            intent.putExtra("need_check_version", false);
            intent.putExtra("new_task", !ScreenUtils.instance().isScreenOn());
            launchIntentWithHwLauncherProvider(intent, true);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.HW", "grantPermission failed: ", e2);
            EventLogger.logExceptionEvent("grantPermission", e2);
        }
    }

    public void persistPermission(Intent intent) {
        try {
            ContentResolver contentResolver = StrategyFramework.getFrameworkContext().getContentResolver();
            contentResolver.takePersistableUriPermission(Uri.parse(a.LAUNCHER_PROVIDER_URI), 3);
            List<UriPermission> persistedUriPermissions = contentResolver.getPersistedUriPermissions();
            boolean checkUriPermissionExistence = checkUriPermissionExistence(persistedUriPermissions);
            if (checkUriPermissionExistence) {
                this.d.putLong("persistTs", System.currentTimeMillis());
            }
            Logger.i("LVBA.AliveModule.Provider.HW", "takePersistableUriPermission, hasPermission: %s, permissionUris: %s", new Object[]{Boolean.valueOf(checkUriPermissionExistence), uriPermissionListToJson(persistedUriPermissions)});
            EventLogger.logGrantPermissionResultEventAndError(false, intent.getStringExtra("src"), checkUriPermissionExistence);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.HW", "takePersistableUriPermission fail: ", e2);
            EventLogger.logExceptionEvent("persistPermission", e2);
        }
    }

    public boolean removeIcon(int i) {
        if (!hasAbility("removeIcon")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip removeIcon since no permission");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "start removeIcon: iconId: %d", new Object[]{Integer.valueOf(i)});
        boolean a = f.a(i);
        EventLogger.logErrorEvent("removeIcon", a);
        return a;
    }

    /* renamed from: b */
    private String uriPermissionListToJson(List list) {
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UriPermission uriPermission = (UriPermission) it.next();
            hashMap.put(uriPermission.getUri().toString(), Long.valueOf(uriPermission.getPersistedTime()));
        }
        return JSONFormatUtils.instance().toJson(hashMap);
    }

    public boolean updateIcon(IconInfo iconInfo) {
        if (!hasAbility("updateIcon")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip updateIcon since no permission");
            return false;
        } else if (iconInfo == null) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip updateIcon since invalid input");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.HW", "start updateIcon: icon: %s", new Object[]{iconInfo.toString()});
            boolean c = f.c(iconInfo);
            EventLogger.logErrorEvent("updateIcon", c);
            return c;
        }
    }

    public LayoutProps getLayoutProps() {
        if (!hasAbility("getLayoutProps")) {
            return null;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip getLayoutProps since no permission");
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "start getLayoutProps");
        LayoutProps launcherLayoutProps = e.getLauncherLayoutProps();
        EventLogger.logErrorEvent("getLayoutProps", launcherLayoutProps != null);
        return launcherLayoutProps;
    }

    public boolean hasAbility(String str) {
        if (str == null) {
            return false;
        }
        if (!(DeprecatedAb.instance().isFlowControl("ab_key_hw_fp_ability_57300", true) && DeprecatedAb.instance().isFlowControl(new StringBuilder().append("ab_key_hw_fp_ability_57300").append(str).toString(), true))) {
            Logger.i("LVBA.AliveModule.Provider.HW", "no ability for: %s", new Object[]{str});
            return false;
        } else if (!TextUtils.equals(str, "restartLauncher")) {
            return true;
        } else {
            return f.d();
        }
    }

    public boolean moveIconOutFolder(IconInfo iconInfo) {
        if (!hasAbility("moveIconOutFolder")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip moveIconOutFolder since no permission");
            return false;
        } else if (iconInfo == null) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip moveIconOutFolder since invalid input");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.HW", "start moveIconOutFolder: icon: %s", new Object[]{iconInfo.toString()});
            boolean b = f.b(iconInfo);
            EventLogger.logErrorEvent("moveIconOutFolder", b);
            return b;
        }
    }

    public boolean addIcon(IconInfo iconInfo) {
        if (!hasAbility("addIcon")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip addIcon since no permission");
            return false;
        } else if (iconInfo == null) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip addIcon since invalid input");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.HW", "start addIcon: %s", new Object[]{iconInfo.toString()});
            boolean a = f.a(iconInfo);
            EventLogger.logErrorEvent("addIcon", a);
            return a;
        }
    }

    public Integer addScreen() {
        if (!hasAbility("addScreen")) {
            return null;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip addScreen since no permission");
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "start addScreen");
        Integer c = f.c();
        EventLogger.logErrorEvent("addScreen", c != null);
        return c;
    }

    public boolean hasPermission() {
        boolean z = false;
        try {
            if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.hw_fp_permission_by_v2_57600", "true"))) {
                boolean hasPermission = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f.a().hasPermission("provider_hw_launcher", a.LAUNCHER_PROVIDER_URI);
                Logger.i("LVBA.AliveModule.Provider.HW", "check hasPermission by v2: %s", new Object[]{Boolean.valueOf(hasPermission)});
                if (hasPermission) {
                    return true;
                }
            }
            z = checkUriPermissionExistence(StrategyFramework.getFrameworkContext().getContentResolver().getPersistedUriPermissions());
            if (!z) {
                long j = this.d.getLong("persistTs", 0L);
                if (j > 0) {
                    EventLogger.logPermissionRevoked(j);
                    this.d.remove("persistTs");
                    Logger.i("LVBA.AliveModule.Provider.HW", "permission revoked, persistTs: %d", new Object[]{Long.valueOf(j)});
                }
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.HW", "hasPermission fail", e2);
            EventLogger.logExceptionEvent("hasPermission", e2);
        }
        return z;
    }

    public List getLauncherIcons() {
        if (!hasAbility("getLauncherIcons")) {
            return null;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip getLauncherIcons since no permission");
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "start getLauncherIcons");
        List b = f.b();
        EventLogger.logErrorEvent("getLauncherIcons", b != null && !b.isEmpty());
        return b;
    }

    public boolean moveIconToFolder(int i, int i2) {
        if (!hasAbility("moveIconToFolder")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.HW", "skip moveIconToFolder since no permission");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.HW", "start moveIconToFolder: iconId: %d, folderIconId: %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        boolean a = f.a(i, i2);
        EventLogger.logErrorEvent("moveIconToFolder", a);
        return a;
    }

    public boolean restartLauncher() {
        if (!hasAbility("restartLauncher")) {
            return false;
        }
        boolean z = RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_restart_launcher_sync_61600", false);
        Logger.i("LVBA.AliveModule.Provider.HW", "start restartLauncher, sync: %s", new Object[]{Boolean.valueOf(z)});
        if (!z) {
            boolean f2 = f.f();
            EventLogger.logLauncherRestartResult(f2);
            return f2;
        }
        try {
            h.lock();
            boolean f3 = f.f();
            EventLogger.logLauncherRestartResult(f3);
            return f3;
        } finally {
            h.unlock();
        }
    }
}