package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.startup.ResourceHelper;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.AliveModule;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.AliveNativeInterface;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.BaseApplication;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NotificationUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: NotificationSubAbility.class */
public class NotificationSubAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static Boolean g;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final String k = null;
    private static Integer f = 0;
    private static final CopyOnWriteArrayList l = new CopyOnWriteArrayList();

    /* loaded from: NotificationSubAbility$NotificationConfig.class */
    public class NotificationConfig {
        private int importance;
        private String sound;
        private Boolean shouldShowLights;
        private Boolean shouldVibrate;

        public String getSound() {
            return this.sound;
        }

        public Boolean isShouldVibrate() {
            return this.shouldVibrate;
        }

        public int getImportance() {
            return this.importance;
        }

        public Boolean isShouldShowLights() {
            return this.shouldShowLights;
        }

        public String toString() {
            return "NotificationConfig{sound='" + this.sound + "', shouldVibrate=" + this.shouldVibrate + ", shouldShowLights=" + this.shouldShowLights + ", importance=" + this.importance + '}';
        }

        public NotificationConfig(String str, Boolean bool, Boolean bool2, int i) {
            this.sound = str;
            this.shouldVibrate = bool;
            this.shouldShowLights = bool2;
            this.importance = i;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0089, code lost:
        if (android.text.TextUtils.equals(r8.getSound().toString(), r9.getSound()) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(android.app.NotificationChannel r8) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility.a(android.app.NotificationChannel):boolean");
    }

    private Application h() {
        return BaseApplication.instance().getApplication();
    }

    private boolean a(Context context, String str) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager == null) {
            return false;
        }
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(str);
        if (notificationChannel != null) {
            return a(notificationChannel);
        }
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "channel is empty");
        return true;
    }

    private String a(String str, String str2) {
        return Configuration.instance().getConfiguration(str, str2);
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        boolean z;
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_android.ka_start_by_ntf_max_patch_ver_64600", "");
        if (!TextUtils.isEmpty(configValue) && com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a(configValue)) {
            if (!(RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_startup_notification_oppo_fix_20221001_6470", false) && !com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("oppo_fix_ntf_221001_23Q1") && !com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.a())) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "can't start bg act by notify as secure patch version big than maxVersion:" + configValue);
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "securePatch");
            }
        }
        if (AppBuildInfo.instance().getRealVersionCode() >= 15532160) {
            return c();
        }
        if (AppBuildInfo.instance().getRealVersionCode() >= 15335520) {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "execute alive module");
            z = b();
        } else {
            z = true;
        }
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(z, z ? "success" : "failed");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.app.Application$ActivityLifecycleCallbacks, com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility$2] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility$3, java.lang.Runnable] */
    private void a(final Application application, final String str, final int i2) {
        if (!ScreenUtils.instance().isScreenOn()) {
            com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification.a.a(i2);
        }
        Integer valueOf = Integer.valueOf(f.intValue() + 1);
        f = valueOf;
        l.add(Integer.valueOf(valueOf.intValue()));
        final ?? r0 = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility.2
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            public void onActivityPostPaused(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostPaused");
            }

            public void onActivityStarted(Activity activity) {
                if (TextUtils.equals(activity.getClass().getName(), str)) {
                    Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "clear full screen notification on activity start");
                    NotificationSubAbility.a(application, i2);
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification.a.b(i2);
                }
            }

            public void onActivityPreDestroyed(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreDestroyed");
            }

            public void onActivityPostStopped(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreStopped");
            }

            public void onActivityPostDestroyed(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostDestroyed");
            }

            public void onActivityPostCreated(Activity activity, Bundle bundle) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostCreated");
            }

            public void onActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostSaveInstanceState");
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivityPreResumed(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreResumed");
            }

            public void onActivityPostStarted(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostStarted");
            }

            public void onActivityDestroyed(Activity activity) {
            }

            public void onActivityPreCreated(Activity activity, Bundle bundle) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreCreated");
            }

            public void onActivityPostResumed(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPostResumed");
            }

            public void onActivityPreStarted(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreStarted");
            }

            public void onActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreSaveInstanceState");
            }

            public void a(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityConfigurationChanged");
            }

            public void onActivityPrePaused(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPrePaused");
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            public void onActivityPreStopped(Activity activity) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "onActivityPreStopped");
            }
        };
        application.registerActivityLifecycleCallbacks(r0);
        long j2 = 0;
        if (DeprecatedAb.instance().isFlowControl("ab_get_delay_from_config_5520", false)) {
            j2 = NumberUtils.instance().parseLong(a("x.cancel_notify_delay_ms", "3000"), 0L);
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "get delay form config: %s", new Object[]{Long.valueOf(j2)});
        }
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("AliveStartupUtils#cancelNotificationAfterActivityStart", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility.3
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "auto cancel notification");
                application.unregisterActivityLifecycleCallbacks(r0);
                NotificationSubAbility.a(application, i2);
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.notification.a.b(i2);
            }
        }, j2);
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "post delay clear:" + i2);
    }

    private com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e a(int i2, boolean z) {
        boolean d2 = d();
        if (!com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.h.a()) {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "privacyPassed is false");
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "privacyPassed is false");
        } else if (!d2 && DeprecatedAb.instance().isFlowControl("fsn_check_notify_perm_5250", true)) {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "can't start bg act by notify when notify switch off");
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "notify switch off");
        } else if (Build.VERSION.SDK_INT >= 26 && DeprecatedAb.instance().isFlowControl("fsn_check_notification_channel_5930", true) && !a(StrategyFramework.getFrameworkContext(), "regular")) {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "can't start bg act by notify when channel switch off");
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "channel switch off");
        } else {
            boolean z2 = true;
            if (RomOsUtil.instance().isXiaomiManufacture() && a()) {
                z2 = z;
            } else if ((RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) && Build.VERSION.SDK_INT >= 29) {
                z2 = e();
            }
            ScreenUtils.instance();
            if (i2 == 2 && !f()) {
                if (!g() || Build.VERSION.SDK_INT < 26) {
                    Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "skip start bg act by notify when screen present");
                    z2 = false;
                } else {
                    Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "for notification optimize, no need to judge screen state");
                }
            }
            ScreenUtils.instance();
            if (i2 == 1) {
                if (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) {
                    z2 = z2 && Settings.Secure.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "wakeup_when_receive_notification", 0) == 0;
                } else if (RomOsUtil.instance().isXiaomiManufacture()) {
                    z2 = z2 && Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "wakeup_for_keyguard_notification", 0) == 0;
                } else if (RomOsUtil.instance().isOppoManufacture()) {
                    z2 = z2 && Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "keyguard_notice_wakelock_state", 0) == 0;
                } else if (RomOsUtil.instance().isVivoManufacture()) {
                    z2 = z2 && Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "notification_wake_up", 0) == 0;
                }
            }
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "defaultAb: %s", new Object[]{Boolean.valueOf(z2)});
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(DeprecatedAb.instance().isFlowControl("start_bg_act_by_notify_5230", z2), null);
        }
    }

    private int j() {
        return AliveModule.instance().getNotificationID();
    }

    public static void a(Context context, int i2) {
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "cancel Notification ID: %s", new Object[]{Integer.valueOf(i2)});
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            try {
                notificationManager.cancel(i2);
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("cancel_ntf", "id:" + i2);
            } catch (Throwable th) {
                Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", th);
            }
        }
    }

    private int i() {
        int i2 = (MMKVCompat.module("alive_strategy", true).getInt("fsn_auto_increment_id", 0) + 1) % 10;
        MMKVCompat.module("alive_strategy", true).putInt("fsn_auto_increment_id", i2);
        return j() + i2;
    }

    public boolean a() {
        int i2 = -1;
        String version = RomOsUtil.instance().getVersion();
        if (version != null) {
            i2 = version.length() > 1 ? NumberUtils.instance().parseInt(version.substring(1)) : -1;
        }
        return i2 >= 12;
    }

    boolean a(Application application, Intent intent, NotificationManager notificationManager, NotificationCompat.Builder builder) {
        try {
            boolean z = RomOsUtil.instance().isOppo() && com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a("2022-10-01");
            int i2 = i();
            if (g()) {
                if (Build.VERSION.SDK_INT >= 26 && !z) {
                    Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "above android 8 ban float view");
                    builder.setGroup(String.valueOf(i2));
                    builder.setGroupAlertBehavior(1);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "above android 9 set mediaStyle");
                    builder.setCategory((String) null);
                    builder.setStyle(new NotificationCompat.MediaStyle());
                }
            }
            if (z) {
                Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "oppo 12 after securePatch:2022-10-01, set envelope");
                Bundle bundle = new Bundle();
                bundle.putBoolean("envelope", true);
                builder.setExtras(bundle);
            }
            notificationManager.notify(i2, builder.build());
            String className = intent.getComponent() != null ? intent.getComponent().getClassName() : null;
            a(application, className, i2);
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "start bg activity " + className + " by notification");
            com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("post_ntf", "id:" + i2);
            return true;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", th);
            return false;
        }
    }

    private boolean a(Intent intent) {
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", "intent package is null");
        }
        Application h2 = h();
        PendingIntent pendingIntent = null;
        try {
            pendingIntent = PendingIntent.getActivity(h2, 0, intent, 2048);
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", th);
        }
        if (pendingIntent == null) {
            Logger.w("LVUA.Dybuild.Sion.NotificationSubAbility", "getActivity fail");
            return false;
        }
        NotificationCompat.Builder fullScreenIntent = new NotificationCompat.Builder(h2, "regular").setSmallIcon(ResourceHelper.getNotifySmallIconTransparent()).setContentTitle("").setContentText("").setCustomContentView(new RemoteViews(h2.getPackageName(), ResourceHelper.getAliveFullScreenNotification())).setVibrate(new long[]{0}).setSound((Uri) null).setPriority(0).setCategory("call").setContentIntent(pendingIntent).setVisibility(-1).setFullScreenIntent(pendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) h2.getSystemService("notification");
        if (null == notificationManager) {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "notify manage is null");
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26 && null == a(notificationManager, "regular")) {
            NotificationChannel notificationChannel = new NotificationChannel("regular", "常用通知", 4);
            notificationChannel.setVibrationPattern(new long[]{0});
            notificationChannel.setSound(null, null);
            notificationChannel.enableVibration(false);
            notificationChannel.enableLights(false);
            try {
                if ((RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture()) && Build.VERSION.SDK_INT >= 29 && e()) {
                    return a(h2, intent, notificationManager, fullScreenIntent, notificationChannel);
                }
                com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.g.a(notificationManager, notificationChannel, "com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.sion.subAbility.NotificationSubAbility");
            } catch (Throwable th2) {
                Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", th2);
                return false;
            }
        }
        return a(h2, intent, notificationManager, fullScreenIntent);
    }

    private com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e c() {
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "BACKGROUND_START_ACTIVITY");
        int screenState = ScreenUtils.instance().getScreenState();
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "startBackgroundActivity: hasBgStartActivityPermission: %s, screenState: %s", new Object[]{Boolean.valueOf(hasPermission), Integer.valueOf(screenState)});
        return a(screenState, hasPermission);
    }

    public StatusResult start(SionRequest sionRequest) {
        boolean z = true;
        if (AppBuildInfo.instance().getRealVersionCode() >= 15532160) {
            z = a(sionRequest.getIntent());
        } else {
            Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "AliveModule startBackgroundByFullScreenNotification");
            AliveModule.instance().startBackgroundByFullScreenNotification(sionRequest.getIntent());
        }
        return new StatusResult(z, z ? "success" : "failed");
    }

    private boolean b() {
        return AliveModule.instance().canStartBgActivityByFullScreenNotification();
    }

    private boolean e() {
        Boolean bool = g;
        if (bool == null) {
            Boolean valueOf = Boolean.valueOf(AppBuildInfo.instance().isDEBUG() || DeprecatedAb.instance().isFlowControl("fc_intent_compat_hwQ_5240", false));
            g = valueOf;
            bool = valueOf;
        }
        return bool.booleanValue();
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility$1, java.lang.Runnable] */
    private boolean a(final Application application, final Intent intent, final NotificationManager notificationManager, final NotificationCompat.Builder builder, final NotificationChannel notificationChannel) {
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "create high importance channel");
        AliveNativeInterface.instance().duoduo002(notificationManager, notificationChannel);
        AliveNativeInterface.instance().duoduo001(notificationManager, "regular");
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("AliveStartupUtils#hwNotify", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                AliveNativeInterface.instance().duoduo002(notificationManager, notificationChannel);
                NotificationSubAbility.this.a(application, intent, notificationManager, builder);
            }
        }, NumberUtils.instance().parseInt(a("x.whole_wheat_create_delay", "1000")));
        return true;
    }

    private boolean d() {
        return NotificationUtils.instance().isNotifyOpen(StrategyFramework.getFrameworkContext());
    }

    private boolean f() {
        return DeprecatedAb.instance().isFlowControl("support_fsn_on_present_5300", RomOsUtil.instance().isVivoManufacture() || RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture() || (RomOsUtil.instance().isXiaomiManufacture() && !a()));
    }

    private boolean g() {
        String configValue = RemoteConfig.instance().getConfigValue("enable_full_screen_notification_optimize_5920", "false");
        Logger.i("LVUA.Dybuild.Sion.NotificationSubAbility", "enableFullScreenNotificationOptimize :" + configValue);
        return TextUtils.equals("true", configValue) || AppBuildInfo.instance().isDEBUG();
    }

    private NotificationChannel a(NotificationManager notificationManager, String str) {
        List<NotificationChannel> list = null;
        try {
            list = notificationManager.getNotificationChannels();
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.NotificationSubAbility", th);
        }
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (NotificationChannel notificationChannel : list) {
            if (TextUtils.equals(notificationChannel.getId(), str)) {
                return notificationChannel;
            }
        }
        return null;
    }
}
