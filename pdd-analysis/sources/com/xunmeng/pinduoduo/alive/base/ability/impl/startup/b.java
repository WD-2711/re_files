package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.startup.ResourceHelper;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.PddHandler;
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
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AlarmManagerApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppInLockBox;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NotificationUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.api.AliveUnifyAbility;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final int d = 0;
    private static final int e = 0;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static Boolean i;
    private static Boolean j;
    private static Integer k = 0;
    private static CopyOnWriteArrayList l = new CopyOnWriteArrayList();
    private static boolean m;
    private static final String n = null;
    private static final String o = null;
    private static final String p = null;
    private static final String q = null;
    private static final String r = null;
    private static final String s = null;
    private static final String t = null;
    private static final String u = null;
    private static final String v = null;
    private static PddHandler w;

    public static boolean b(Application application, Intent intent, NotificationManager notificationManager, NotificationCompat.Builder builder) {
        try {
            boolean z = RomOsUtil.instance().isOppo() && com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a("2022-10-01") && RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_startup_notification_oppo_fix_20221001_6470", false) && !com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.a() && !com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("oppo_fix_ntf_221001_23Q1");
            int o2 = o();
            if (p()) {
                if (Build.VERSION.SDK_INT >= 26 && !z) {
                    Logger.i("LVBA.AliveModule.AliveStartupUtils", "above android 8 ban float view");
                    builder.setGroup(String.valueOf(o2));
                    builder.setGroupAlertBehavior(1);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    builder.setCategory((String) null);
                    builder.setStyle(new NotificationCompat.MediaStyle());
                }
            }
            if (z) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "oppo 12 after securePatch:2022-10-01, set envelope");
                Bundle bundle = new Bundle();
                bundle.putBoolean("envelope", true);
                builder.setExtras(bundle);
            }
            notificationManager.notify(o2, builder.build());
            String className = intent.getComponent() != null ? intent.getComponent().getClassName() : null;
            a(application, className, o2);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "start bg activity " + className + " by notification");
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
            return false;
        }
    }

    public static boolean i() {
        return h() >= 12;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x0089, code lost:
        if (android.text.TextUtils.equals(r7.getSound().toString(), r8.getSound()) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(android.app.NotificationChannel r7) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.a(android.app.NotificationChannel):boolean");
    }

    public static PddHandler g() {
        PddHandler pddHandler = w;
        if (pddHandler == null) {
            PddHandler newHandler = ThreadPool.instance().newHandler(ThreadBiz.CS, Looper.getMainLooper(), new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.5
                public void handleMessage(Message message) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("rom_version", RomOsUtil.instance().getVersion());
                    StrategyFramework.trackError("AliveStartupUtils", new TrackErrorOption(message.arg1, 30008, (String) null, hashMap, (Integer) null));
                    b.l.remove(Integer.valueOf(message.what));
                    boolean unused = b.m = true;
                }
            });
            w = newHandler;
            pddHandler = newHandler;
        }
        return pddHandler;
    }

    public static boolean d(Intent intent) {
        StatusResult executeAbility = AbilityFramework.executeAbility("RumbleStartAbility", new IntentRequest(intent), new StatusResult(false, "default failed"));
        Logger.i("LVBA.AliveModule.AliveStartupUtils", executeAbility.getErrorMsg());
        return executeAbility.isSuccess();
    }

    public static boolean i(Intent intent) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [android.app.Application$ActivityLifecycleCallbacks, com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b$3] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b$4, java.lang.Runnable] */
    private static void a(final Application application, final String str, final int i2) {
        Integer valueOf = Integer.valueOf(k.intValue() + 1);
        k = valueOf;
        final int intValue = valueOf.intValue();
        l.add(Integer.valueOf(intValue));
        final ?? r0 = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.3
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            public void onActivityPostPaused(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostPaused");
            }

            public void onActivityStarted(Activity activity) {
                if (TextUtils.equals(activity.getClass().getName(), str)) {
                    b.b(intValue, 200);
                    Logger.i("LVBA.AliveModule.AliveStartupUtils", "clear full screen notification on activity start");
                    b.b(application, i2);
                }
            }

            public void onActivityPreDestroyed(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreDestroyed");
            }

            public void onActivityPostStopped(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreStopped");
            }

            public void onActivityPostDestroyed(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostDestroyed");
            }

            public void onActivityPostCreated(Activity activity, Bundle bundle) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostCreated");
            }

            public void onActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostSaveInstanceState");
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivityPreResumed(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreResumed");
            }

            public void onActivityPostStarted(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostStarted");
            }

            public void onActivityDestroyed(Activity activity) {
            }

            public void onActivityPreCreated(Activity activity, Bundle bundle) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreCreated");
            }

            public void onActivityPostResumed(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPostResumed");
            }

            public void onActivityPreStarted(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreStarted");
            }

            public void onActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreSaveInstanceState");
            }

            public void a(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityConfigurationChanged");
            }

            public void onActivityPrePaused(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPrePaused");
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            public void onActivityPreStopped(Activity activity) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "onActivityPreStopped");
            }
        };
        application.registerActivityLifecycleCallbacks(r0);
        long j2 = 0;
        if (DeprecatedAb.instance().isFlowControl("ab_get_delay_from_config_5520", false)) {
            j2 = NumberUtils.instance().parseLong(Configuration.instance().getConfiguration("x.cancel_notify_delay_ms", "3000"), 0L);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "get delay form config: %s", new Object[]{Long.valueOf(j2)});
        }
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("AliveStartupUtils#cancelNotificationAfterActivityStart", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.4
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "auto cancel notification");
                application.unregisterActivityLifecycleCallbacks(r0);
                b.b(application, i2);
                b.b(intValue, 201);
            }
        }, j2);
    }

    public static boolean a() {
        if (!DeprecatedAb.instance().isFlowControl("ab_alive_impl_check_lock_box_55500", false)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "ab not hit, isFilterByBoxLock false");
            return false;
        }
        return AppInLockBox.instance().inLockBox();
    }

    public static boolean a(Intent intent) {
        if (DeprecatedAb.instance().isFlowControl("ab_alive_impl_not_check_system_filter_55700", false)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not checkout system filter condition, return");
            return false;
        } else if (AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not check system filter when foreground");
            return false;
        } else if (!b(intent) && !DeprecatedAb.instance().isFlowControl("ab_alive_impl_ignore_intent_target_check_6040", false)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not check system filter when intent not pdd");
            return false;
        } else if (a()) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "filter by box lock, return");
            return true;
        } else if (b()) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "filter by limit app, return");
            return true;
        } else {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not filter by system");
            return false;
        }
    }

    public static boolean e(Intent intent) {
        if (intent.getData() == null || !TextUtils.equals(intent.getStringExtra("ryzeType"), "uri")) {
            return AliveUnifyAbility.instance().startActivityByRyze(intent).isSuccess();
        }
        return AliveUnifyAbility.instance().startActivityByRyze(intent.getData()).isSuccess();
    }

    public static boolean h(Intent intent) {
        if (a(intent)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByAlarm false when in lock box");
            return false;
        }
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", "intent package is null");
        }
        if (RomOsUtil.instance().isXiaomiManufacture() && Build.VERSION.SDK_INT >= 26) {
            ReflectUtils.instance().invokeSysMethod(intent, "setMiuiFlags", new Object[]{2});
        }
        PendingIntent pendingIntent = null;
        try {
            pendingIntent = PendingIntent.getActivities(frameworkContext, 1, new Intent[]{intent}, 2048);
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
        }
        if (pendingIntent == null) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "pendingIntent null");
            return false;
        }
        AlarmManager alarmManager = (AlarmManager) frameworkContext.getSystemService("alarm");
        if (alarmManager == null) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "alarmManager null");
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() + NumberUtils.instance().parseInt(Configuration.instance().getConfiguration("x.alarm_bg_activity_delay", "100"));
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "start bg activity by alarm");
        try {
            boolean isFlowControl = DeprecatedAb.instance().isFlowControl("bg_act_alarm_clock_5300", !RomOsUtil.instance().isNewHuaweiManufacture() && !RomOsUtil.instance().isHonerManufacture());
            if (Build.VERSION.SDK_INT >= 21 && isFlowControl) {
                AlarmManagerApi.instance().setAlarmClock(alarmManager, new AlarmManager.AlarmClockInfo(currentTimeMillis, null), pendingIntent);
                return true;
            } else if (Build.VERSION.SDK_INT >= 19) {
                AlarmManagerApi.instance().setExact(alarmManager, 0, currentTimeMillis, pendingIntent);
                return true;
            } else {
                AlarmManagerApi.instance().set(alarmManager, 0, currentTimeMillis, pendingIntent);
                return true;
            }
        } catch (Throwable th2) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th2);
            return false;
        }
    }

    public static boolean b(int i2, boolean z) {
        boolean z2 = true;
        if (RomOsUtil.instance().isVivoManufacture()) {
            z2 = z;
        }
        ScreenUtils.instance();
        if (i2 != 2) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "need screen present, current:" + i2);
            z2 = false;
        }
        if (RomOsUtil.instance().isXiaomiManufacture()) {
            z2 = true;
        }
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "canStartBgActivityByAlarm: defaultAb: %s", new Object[]{Boolean.valueOf(z2)});
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_activity_by_alarm_61700", z2);
    }

    public static boolean e() {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(frameworkContext, "OVERLAY");
        boolean hasPermission2 = DeviceCompatPermission.instance().hasPermission(frameworkContext, "BACKGROUND_START_ACTIVITY");
        int screenState = ScreenUtils.instance().getScreenState();
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "canStartBackgroundActivity: hasOverlayPermission: %s, hasBgStartActivityPermission: %s, screenState: %s", new Object[]{Boolean.valueOf(hasPermission), Boolean.valueOf(hasPermission2), Integer.valueOf(screenState)});
        return a(hasPermission, hasPermission2) || e.a() || a(screenState, hasPermission2) || c() || b(screenState, hasPermission2);
    }

    private static void k() {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_alive_utils_pmm_tracker_64000", true)) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "sion tracker hit gray");
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("log_action", "final_start_activity");
            hashMap.put("business", "AliveStartupUtils");
            hashMap.put("rom_os_version", RomOsUtil.instance().getVersion());
            hashMap.put("rom_os_name", RomOsUtil.instance().getName());
            hashMap.put("screen_state", Integer.valueOf(ScreenUtils.instance().getScreenState()));
            hashMap.put("is_pdd_foreground", Boolean.valueOf(AppUtils.instance().isAppOnForeground(StrategyFramework.getFrameworkContext())));
            StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(hashMap, "perf", "alive", 0));
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "tracker pmm end");
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
        }
    }

    public static boolean c() {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_by_rumble_action_63400", false)) {
            return false;
        }
        return AbilityFramework.isSupport("RumbleStartAbility");
    }

    public static boolean a(boolean z, boolean z2) {
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
        return DeprecatedAb.instance().isFlowControl("can_start_bg_activity_5230", z3);
    }

    private static boolean m() {
        Boolean bool = j;
        if (bool == null) {
            Boolean valueOf = Boolean.valueOf(AppBuildInfo.instance().isDEBUG() || DeprecatedAb.instance().isFlowControl("report_fc_intent_result_5240", false));
            j = valueOf;
            bool = valueOf;
        }
        return bool.booleanValue();
    }

    public static void b(Context context, int i2) {
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "cancel Notification ID: %s", new Object[]{Integer.valueOf(i2)});
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager != null) {
            try {
                notificationManager.cancel(i2);
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b$1, java.lang.Runnable] */
    public static void g(final Intent intent) {
        if (com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a(StrategyFramework.getFrameworkContext().getPackageName())) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByThemeManager false when  pdd double open");
        } else if (a(intent)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByThemeManager false when in lock box");
        } else if (intent == null) {
        } else {
            ContentProviderClient contentProviderClient = null;
            ContentProviderClient contentProviderClient2 = null;
            ContentProviderClient contentProviderClient3 = null;
            try {
                try {
                    final Context frameworkContext = StrategyFramework.getFrameworkContext();
                    Uri parse = Uri.parse("content://com.android.thememanager.aod_preview_provider");
                    Uri parse2 = Uri.parse("content://com.android.thememanager.thememanager_settings_search_provider");
                    Uri parse3 = Uri.parse("content://com.android.thememanager.incall");
                    if (Build.VERSION.SDK_INT >= 16) {
                        contentProviderClient = frameworkContext.getContentResolver().acquireUnstableContentProviderClient(parse);
                        contentProviderClient2 = frameworkContext.getContentResolver().acquireUnstableContentProviderClient(parse2);
                        contentProviderClient3 = frameworkContext.getContentResolver().acquireUnstableContentProviderClient(parse3);
                    } else {
                        contentProviderClient = frameworkContext.getContentResolver().acquireContentProviderClient(parse);
                        contentProviderClient2 = frameworkContext.getContentResolver().acquireContentProviderClient(parse2);
                        contentProviderClient3 = frameworkContext.getContentResolver().acquireContentProviderClient(parse3);
                    }
                    long parseLong = NumberUtils.instance().parseLong(RemoteConfig.instance().getConfigValue("pinduoduo_Android.start_bg_by_theme_delay_in_millsec_59300", "2000"), 0L);
                    final String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.start_bg_by_theme_action_59300", "ACTION_DOWNLOAD_CLICK_NOTIFICATIONcom.android.thememanager");
                    ThreadPool.instance().scheduleTask(ThreadBiz.CS, "AliveStartupUtils#startBgActivityByThemeManager", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.1
                        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                        public void run() {
                            try {
                                Logger.i("LVBA.AliveModule.AliveStartupUtils", "start bg activity by theme");
                                intent.addFlags(4096);
                                Intent intent2 = new Intent(configValue);
                                intent2.putExtra("EXTRA_ACTIVITY_TARGET_INTENT", intent);
                                intent2.setPackage("com.android.thememanager");
                                frameworkContext.sendBroadcast(intent2);
                            } catch (Exception e2) {
                                Logger.e("LVBA.AliveModule.AliveStartupUtils", "send broadcast error", e2);
                            }
                        }
                    }, parseLong, TimeUnit.MILLISECONDS);
                    if (contentProviderClient != null) {
                        contentProviderClient.release();
                    }
                    if (contentProviderClient2 != null) {
                        contentProviderClient2.release();
                    }
                    if (contentProviderClient3 == null) {
                        return;
                    }
                    contentProviderClient3.release();
                } catch (Exception e2) {
                    Logger.e("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByThemeManager error", e2);
                    if (contentProviderClient != null) {
                        contentProviderClient.release();
                    }
                    if (contentProviderClient2 != null) {
                        contentProviderClient2.release();
                    }
                    if (contentProviderClient3 == null) {
                        return;
                    }
                    contentProviderClient3.release();
                }
            } catch (Throwable th) {
                if (contentProviderClient != null) {
                    contentProviderClient.release();
                }
                if (contentProviderClient2 != null) {
                    contentProviderClient2.release();
                }
                if (contentProviderClient3 != null) {
                    contentProviderClient3.release();
                }
                throw th;
            }
        }
    }

    public static void b(int i2, int i3) {
        if (!m && m() && l.contains(Integer.valueOf(i2))) {
            Message obtain = Message.obtain();
            obtain.what = i2;
            obtain.arg1 = i3;
            g().removeMessages(i2);
            g().sendMessage("AliveStartupUtils#reportHwQFullScreenIntentResult", obtain);
        }
    }

    public static boolean a(Context context, String str) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (notificationManager == null) {
            return false;
        }
        NotificationChannel notificationChannel = notificationManager.getNotificationChannel(str);
        if (notificationChannel != null) {
            return a(notificationChannel);
        }
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "channel is empty");
        return true;
    }

    public static boolean b(Intent intent) {
        if (intent == null) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not check null intent");
            return false;
        }
        String packageName = StrategyFramework.getFrameworkContext().getPackageName();
        String str = intent.getPackage();
        ComponentName component = intent.getComponent();
        String str2 = "";
        if (component != null) {
            str2 = component.getPackageName();
        }
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "targetPkg: %s, targetComponentPackageName: %s", new Object[]{str, str2});
        return TextUtils.equals(packageName, str) || TextUtils.equals(str2, packageName);
    }

    private static boolean k(Intent intent) {
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", "intent package is null");
        }
        Application application = BaseApplication.instance().getApplication();
        PendingIntent pendingIntent = null;
        try {
            pendingIntent = PendingIntent.getActivity(application, 0, intent, 2048);
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
        }
        if (pendingIntent == null) {
            Logger.w("LVBA.AliveModule.AliveStartupUtils", "getActivity fail");
            return false;
        }
        NotificationCompat.Builder fullScreenIntent = new NotificationCompat.Builder(application, "regular").setSmallIcon(ResourceHelper.getNotifySmallIconTransparent()).setContentTitle("").setContentText("").setCustomContentView(new RemoteViews(application.getPackageName(), ResourceHelper.getAliveFullScreenNotification())).setVibrate(new long[]{0}).setSound((Uri) null).setPriority(0).setCategory("call").setContentIntent(pendingIntent).setVisibility(-1).setFullScreenIntent(pendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) application.getSystemService("notification");
        if (null == notificationManager) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "notify manage is null");
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26 && null == a(notificationManager, "regular")) {
            NotificationChannel notificationChannel = new NotificationChannel("regular", "常用通知", 4);
            notificationChannel.setVibrationPattern(new long[]{0});
            notificationChannel.setSound(null, null);
            notificationChannel.enableVibration(false);
            notificationChannel.enableLights(false);
            try {
                if ((RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture()) && Build.VERSION.SDK_INT >= 29 && l()) {
                    return a(application, intent, notificationManager, fullScreenIntent, notificationChannel);
                }
                notificationManager.createNotificationChannel(notificationChannel);
            } catch (Throwable th2) {
                Logger.e("LVBA.AliveModule.AliveStartupUtils", th2);
                return false;
            }
        }
        return b(application, intent, notificationManager, fullScreenIntent);
    }

    public static boolean f(Intent intent) {
        if (a(intent)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBackgroundByFullScreenNotification false when in lock box");
            return false;
        } else if (intent == null) {
            return false;
        } else {
            int screenState = ScreenUtils.instance().getScreenState();
            ScreenUtils.instance();
            if (screenState != 1 || !RomOsUtil.instance().isXiaomiManufacture()) {
                return k(intent);
            }
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "xiaomi will wakeup screen by fsn, skip it");
            return false;
        }
    }

    private static int o() {
        int i2 = (MMKVCompat.module("alive_strategy", true).getInt("fsn_auto_increment_id", 0) + 1) % 10;
        MMKVCompat.module("alive_strategy", true).putInt("fsn_auto_increment_id", i2);
        return AliveModule.instance().getNotificationID() + i2;
    }

    public static void c(Intent intent) {
        if (intent == null) {
            return;
        }
        if (a(intent)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not startBackgroundActivity when in lock box, return");
            return;
        }
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(frameworkContext, "OVERLAY");
        boolean hasPermission2 = DeviceCompatPermission.instance().hasPermission(frameworkContext, "BACKGROUND_START_ACTIVITY");
        int screenState = ScreenUtils.instance().getScreenState();
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBackgroundActivity: hasOverlayPermission: %s, hasBgStartActivityPermission: %s, screenState: %s", new Object[]{Boolean.valueOf(hasPermission), Boolean.valueOf(hasPermission2), Integer.valueOf(screenState)});
        boolean z = false;
        if (a(hasPermission, hasPermission2)) {
            z = j(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startActivity result: %s", new Object[]{Boolean.valueOf(z)});
        }
        if (!z && c()) {
            z = d(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByRumble result: %s", new Object[]{Boolean.valueOf(z)});
        }
        boolean a2 = e.a();
        if (!z && a2) {
            z = e.a(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "canStartBgActivityByFloatView result: %s", new Object[]{Boolean.valueOf(z)});
        }
        if (!z && a(screenState, hasPermission2)) {
            z = k(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByFullScreenNotification result: %s", new Object[]{Boolean.valueOf(z)});
        }
        if (!z && d()) {
            z = e(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startActivityByRyze result: %s", new Object[]{Boolean.valueOf(z)});
        }
        if (!z && b(screenState, hasPermission2)) {
            z = h(intent);
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBgActivityByAlarm result: %s", new Object[]{Boolean.valueOf(z)});
        }
        if (z) {
            return;
        }
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "startActivity again");
        if (DeprecatedAb.instance().isFlowControl("ab_alive_impl_not_satrt_without_ability_6040", false)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not support to start background");
            return;
        }
        k();
        j(intent);
    }

    public static int h() {
        String version = RomOsUtil.instance().getVersion();
        if (version != null && version.length() > 1) {
            return NumberUtils.instance().parseInt(version.substring(1));
        }
        return -1;
    }

    public static boolean a(int i2, boolean z) {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_android.ka_start_by_ntf_max_patch_ver_64600", "");
        if (!TextUtils.isEmpty(configValue) && com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a(configValue)) {
            if (!(RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_startup_notification_oppo_fix_20221001_6470", false) && !com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("oppo_fix_ntf_221001_23Q1") && !com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.a.a())) {
                Logger.i("LVBA.AliveModule.AliveStartupUtils", "can't start bg act by notify as secure patch version big than maxVersion:" + configValue);
                return false;
            }
        }
        if (!NotificationUtils.instance().isNotifyOpen(StrategyFramework.getFrameworkContext()) && DeprecatedAb.instance().isFlowControl("fsn_check_notify_perm_5250", true)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "can't start bg act by notify when notify switch off");
            return false;
        } else if (Build.VERSION.SDK_INT >= 26 && DeprecatedAb.instance().isFlowControl("fsn_check_notification_channel_5930", true) && !a(StrategyFramework.getFrameworkContext(), "regular")) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "can't start bg act by notify when channel switch off");
            return false;
        } else {
            boolean z2 = true;
            if (RomOsUtil.instance().isXiaomiManufacture() && i()) {
                z2 = z;
            } else if ((RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) && Build.VERSION.SDK_INT >= 29) {
                z2 = l();
            }
            ScreenUtils.instance();
            if (i2 == 2 && !n()) {
                if (!p() || Build.VERSION.SDK_INT < 26) {
                    Logger.i("LVBA.AliveModule.AliveStartupUtils", "skip start bg act by notify when screen present");
                    z2 = false;
                } else {
                    Logger.i("LVBA.AliveModule.AliveStartupUtils", "for notification optimize, no need to judge screen state");
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
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "defaultAb: %s", new Object[]{Boolean.valueOf(z2)});
            return DeprecatedAb.instance().isFlowControl("start_bg_act_by_notify_5230", z2);
        }
    }

    private static boolean j(Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        intent.addFlags(4096);
        if (TextUtils.isEmpty(intent.getPackage())) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", "intent package is null");
        }
        try {
            PendingIntent.getActivity(frameworkContext, 0, intent, 2048).send();
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "startActivityDirectly");
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
            return false;
        }
    }

    private static boolean n() {
        return DeprecatedAb.instance().isFlowControl("support_fsn_on_present_5300", RomOsUtil.instance().isVivoManufacture() || RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture() || (RomOsUtil.instance().isXiaomiManufacture() && !i()));
    }

    public static boolean b() {
        if (!DeprecatedAb.instance().isFlowControl("ab_alive_impl_check_limit_app_55700", false)) {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "ab not hit, isFilterByLimitApp false");
            return false;
        } else if (!RomOsUtil.instance().isXiaomiManufacture() || Build.VERSION.SDK_INT < 28) {
            return false;
        } else {
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "check xm");
            return StrategyFramework.getFrameworkContext().getPackageManager().isPackageSuspended();
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b$2, java.lang.Runnable] */
    private static boolean a(final Application application, final Intent intent, final NotificationManager notificationManager, final NotificationCompat.Builder builder, final NotificationChannel notificationChannel) {
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "create high importance channel");
        AliveNativeInterface.instance().duoduo002(notificationManager, notificationChannel);
        AliveNativeInterface.instance().duoduo001(notificationManager, "regular");
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("AliveStartupUtils#hwNotify", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.2
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                AliveNativeInterface.instance().duoduo002(notificationManager, notificationChannel);
                b.b(application, intent, notificationManager, builder);
            }
        }, NumberUtils.instance().parseInt(Configuration.instance().getConfiguration("x.whole_wheat_create_delay", "1000")));
        return true;
    }

    private static boolean p() {
        String configValue = RemoteConfig.instance().getConfigValue("enable_full_screen_notification_optimize_5920", "false");
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "enableFullScreenNotificationOptimize :" + configValue);
        return TextUtils.equals("true", configValue) || AppBuildInfo.instance().isDEBUG();
    }

    public static boolean d() {
        try {
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_by_ryze_action_61700", false)) {
                return AliveUnifyAbility.instance().isSupport("RyzePopupAbility");
            }
            Logger.i("LVBA.AliveModule.AliveStartupUtils", "not hit start_bg_by_ryze_action");
            return false;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
            return false;
        }
    }

    private static boolean l() {
        Boolean bool = i;
        if (bool == null) {
            Boolean valueOf = Boolean.valueOf(AppBuildInfo.instance().isDEBUG() || DeprecatedAb.instance().isFlowControl("fc_intent_compat_hwQ_5240", false));
            i = valueOf;
            bool = valueOf;
        }
        return bool.booleanValue();
    }

    public static boolean f() {
        boolean hasPermission = DeviceCompatPermission.instance().hasPermission(StrategyFramework.getFrameworkContext(), "BACKGROUND_START_ACTIVITY");
        int screenState = ScreenUtils.instance().getScreenState();
        Logger.i("LVBA.AliveModule.AliveStartupUtils", "startBackgroundActivity: hasBgStartActivityPermission: %s, screenState: %s", new Object[]{Boolean.valueOf(hasPermission), Integer.valueOf(screenState)});
        return a(screenState, hasPermission);
    }

    private static NotificationChannel a(NotificationManager notificationManager, String str) {
        List<NotificationChannel> list = null;
        try {
            list = notificationManager.getNotificationChannels();
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.AliveStartupUtils", th);
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
