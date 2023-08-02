package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IHwHiBoardProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IHwSelfStartProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IOppoAuProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IOppoLauncherProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IOppoLockDisplayProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IOppoLockPullProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IXmBehaviorWhiteProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IntentWrapper;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IntentWrapperList;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IFPUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.hss.IHssLocalDataManager;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.kael.IKaelDbOperate;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.permQuery.IPermQuery;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.IoUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: d.class */
public class d implements a.AnonymousClass1 {
    private static IXmBehaviorWhiteProvider C = null;
    private static IHwSelfStartProvider D = null;
    private static IOppoAuProvider E = null;
    private static IHwHiBoardProvider F = null;
    private static IOppoLauncherProvider G = null;
    private static IFPUtils H = null;
    private static IPermQuery I = null;
    private static IOppoLockDisplayProvider J = null;
    private static IOppoLockPullProvider K = null;
    private static IHssLocalDataManager L = null;
    private static IKaelDbOperate M = null;
    private static final Lock N = new ReentrantLock();
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final String k = null;
    private static final String l = null;
    private static final String m = null;
    private static final String n = null;
    private static final String o = null;
    private static final String p = null;
    private static final String q = null;
    private static final String r = null;
    private static final String s = null;
    private static final String t = null;
    private static final String u = null;
    private static final String v = null;
    private static final String w = null;
    private static final String x = null;
    private static final String y = null;
    private final IMMKV B = MMKVCompat.module("df_fp_provider", false);
    private final AtomicBoolean O = new AtomicBoolean(false);
    private HashMap z = b();
    private final HashMap A = c();

    Boolean a(Intent intent) {
        try {
            ResolveInfo resolveActivity = StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 0);
            return Boolean.valueOf((resolveActivity == null || resolveActivity.activityInfo == null) ? false : true);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "resolveActivity fail", e2);
            return null;
        }
    }

    private boolean a(String str, String str2, Intent intent) {
        if (!a.a("fd.provider." + str)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission since bg start not support");
            j.a(str, str2, false, "bg_start_not_support");
            return false;
        }
        boolean a2 = a.a("fd.provider." + str, intent);
        j.a(str, str2, a2, "bg_start");
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene %s grant permission success : %s", new Object[]{str, Boolean.valueOf(a2)});
        return a2;
    }

    private boolean b(String str, String str2, Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        if (!AppUtils.instance().isAppOnForeground(frameworkContext)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission by service since app not foreground");
            j.a(str, str2, false, "service_not_foreground");
            return false;
        }
        intent.setClassName(frameworkContext, "com.xunmeng.pinduoduo.alive.impl.provider.component.HFPService");
        frameworkContext.startService(intent);
        j.a(str, str2, true, "service");
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene %s grant permission success", new Object[]{str});
        return true;
    }

    private boolean f(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip check ab for null");
            return false;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "false"));
    }

    public IOppoAuProvider oppoAuProvider() {
        N.lock();
        try {
            if (E == null) {
                E = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu.d();
            }
            return E;
        } finally {
            N.unlock();
        }
    }

    private String[] b(Intent intent) {
        if (intent == null || !intent.hasExtra("fp_batch_scenes")) {
            return null;
        }
        String[] stringArrayExtra = intent.getStringArrayExtra("fp_batch_scenes");
        if (stringArrayExtra == null || stringArrayExtra.length != 0) {
            return stringArrayExtra;
        }
        Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "bad batch scenes from intent: %s", new Object[]{intent.toString()});
        return null;
    }

    private boolean a(Uri uri, Boolean bool) {
        int i2 = 0;
        if (bool == null || bool.booleanValue()) {
            i2 = 0 | 1;
        }
        if (bool == null || !bool.booleanValue()) {
            i2 |= 2;
        }
        int checkCallingOrSelfUriPermission = StrategyFramework.getFrameworkContext().checkCallingOrSelfUriPermission(uri, i2);
        boolean z = checkCallingOrSelfUriPermission == 0;
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "permission mode for: %s, modeFlag: %d, result: %d, isGranted: %s", new Object[]{uri.toString(), Integer.valueOf(i2), Integer.valueOf(checkCallingOrSelfUriPermission), Boolean.valueOf(z)});
        return z;
    }

    public IOppoLauncherProvider oppoLauncherProvider() {
        N.lock();
        try {
            if (G == null) {
                G = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher.c();
            }
            return G;
        } finally {
            N.unlock();
        }
    }

    public void persistPermission(Intent intent) {
        String[] strArr;
        String a2 = a(intent, "fp_scene");
        String a3 = a(intent, "fp_src");
        String a4 = a(intent, "fp_request_id");
        String a5 = a(intent, "tracker_data");
        if (!TextUtils.isEmpty(a5)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "persistPermission tracker_data: %s.", new Object[]{a5});
        }
        try {
            if (h.a()) {
                h.a(intent);
            }
            if (e.b()) {
                e.c(intent);
            }
            long currentTimeMillis = System.currentTimeMillis() - NumberUtils.instance().parseLong(a(intent, "fp_start_ts"), 0L);
            String[] b2 = b(intent);
            if (b2 == null) {
                b2 = new String[]{a2};
            }
            String arrays = Arrays.toString(b2);
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "persistPermission invoked, scene: %s, batch scenes: %s, requestId: %s, duration: %d", new Object[]{a2, arrays, a4, Long.valueOf(currentTimeMillis)});
            if (b2.length > 1) {
                a(a2 + "-" + arrays, a3, a4);
            }
            for (String str : b2) {
                boolean hasPermission = hasPermission(str);
                j.a(str, a3, currentTimeMillis, hasPermission, a5);
                if (hasPermission) {
                    this.B.putLong(str, System.currentTimeMillis());
                    a(str, a3, a4);
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "broadcast permission ready for: %s", new Object[]{str});
                } else {
                    Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no permission in persistPermission callback");
                }
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "persistPermission fail: ", e2);
            j.a(a2 == null ? "null" : a2, "persistPermission", e2);
        }
    }

    public boolean startGrantPermission(String str, String str2, Intent intent, String str3) {
        String uuid = UUID.randomUUID().toString();
        boolean booleanExtra = intent.getBooleanExtra("keep_extra", false);
        if (intent.getBooleanExtra("rebuild_intent", false)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "startGrantPermission rebuild intent, keep extra: %s", new Object[]{Boolean.valueOf(booleanExtra)});
            Bundle bundle = null;
            if (booleanExtra) {
                bundle = intent.getExtras();
                bundle.remove("rebuild_intent");
                bundle.remove("keep_extra");
            }
            return a(str, str2, bundle);
        }
        String[] b2 = b(intent);
        if (b2 == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "startGrantPermission not batch scenes");
            return a(str, str2, intent, str3, null);
        }
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission for scene: %s, batch scenes: %s", new Object[]{str, Arrays.toString(b2)});
        Intent a2 = a(str, b2, str2, uuid);
        if (a2 == null) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "batch intent is null, stop grant permission");
            j.a(str, str2, false, "batch_intent_null");
            return false;
        }
        if (booleanExtra) {
            Bundle extras = intent.getExtras();
            extras.remove("keep_extra");
            a2.putExtras(extras);
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "batch intent fill kept extra keys: %s", new Object[]{extras.keySet()});
        }
        return a(str, str2, a2, str3, null);
    }

    public IOppoLockPullProvider oppoLockPullProvider() {
        N.lock();
        try {
            if (K == null) {
                K = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLockPull.c();
            }
            return K;
        } finally {
            N.unlock();
        }
    }

    public IHwSelfStartProvider hwSelfStartProvider() {
        N.lock();
        try {
            if (D == null) {
                D = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hwselfstart.b();
            }
            return D;
        } finally {
            N.unlock();
        }
    }

    public IOppoLockDisplayProvider oppoLockDisplayProvider() {
        N.lock();
        try {
            if (J == null) {
                J = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu.e();
            }
            return J;
        } finally {
            N.unlock();
        }
    }

    private boolean e(String str, String str2, Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        PendingIntent pendingIntent = null;
        try {
            List<ResolveInfo> queryIntentServices = frameworkContext.getPackageManager().queryIntentServices(new Intent("android.view.InputMethod"), 33554432);
            ActivityManager activityManager = (ActivityManager) frameworkContext.getSystemService("activity");
            Iterator<ResolveInfo> it = queryIntentServices.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ResolveInfo next = it.next();
                pendingIntent = activityManager.getRunningServiceControlPanel(new ComponentName(next.serviceInfo.packageName, next.serviceInfo.name));
                if (pendingIntent != null) {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "default input method : %s", new Object[]{next});
                    break;
                }
            }
            if (pendingIntent == null) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission scene pending intent is null");
                j.a(str, str2, false, "ime_pending_intent_null");
                return false;
            }
            intent.setDataAndType(intent.getData(), "text/com.xunmeng.pinduoduo.introduction.html");
            pendingIntent.send(frameworkContext, 0, intent);
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene %s grant permission success", new Object[]{str});
            j.a(str, str2, true, "ime");
            return true;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by input error", e2);
            j.a(str, str2, false, "ime_fail");
            j.a(str, "startGrantPermissionByInput", e2);
            return false;
        }
    }

    public IHwHiBoardProvider hwHiBoardProvider() {
        N.lock();
        try {
            if (F == null) {
                F = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.d.1
                    public boolean setServiceByTitle(List list) {
                        return false;
                    }

                    public boolean hasAbility(String str) {
                        return false;
                    }

                    public boolean setServiceByJson(List list) {
                        return false;
                    }

                    public List getServiceJsonList() {
                        return new ArrayList();
                    }

                    public List getServiceTitleList() {
                        return new ArrayList();
                    }
                };
            }
            return F;
        } finally {
            N.unlock();
        }
    }

    private boolean c(String str) {
        InputStream inputStream = null;
        try {
            try {
                String a2 = a(str);
                inputStream = StorageApi.instance().openInputStream(StrategyFramework.getFrameworkContext().getContentResolver(), Uri.parse(a2));
                boolean z = inputStream != null;
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "has permission: %s, file path: %s", new Object[]{Boolean.valueOf(z), a2});
                IoUtils.instance().closeQuietly(inputStream);
                return z;
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "open file path fail", e2);
                IoUtils.instance().closeQuietly(inputStream);
                return false;
            }
        } catch (Throwable th) {
            IoUtils.instance().closeQuietly(inputStream);
            throw th;
        }
    }

    private Intent a(String str, String[] strArr, String str2, String str3) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        try {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "obtainBatchIntent");
            for (String str4 : strArr) {
                IntentWrapper b2 = b(str4);
                if (b2 == null) {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "batch scene ignore: %s since intent wrapper is null", new Object[]{str4});
                } else {
                    arrayList.add(b2);
                    arrayList2.add(str4);
                    arrayList3.add(b2.getTargetUri());
                }
            }
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "final batch scenes: %s, batch uris: %s", new Object[]{arrayList2.toString(), arrayList3.toString()});
            if (arrayList2.size() == 0) {
                Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "obtainBatchIntent fail since empty scene");
                return null;
            }
            IntentWrapper intentWrapper = (IntentWrapper) arrayList.get(0);
            if (arrayList2.size() == 1) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "obtainBatchIntent downgrade to single scene intent: %s", new Object[]{intentWrapper.getScene()});
                return a(intentWrapper, intentWrapper.getScene(), str2, str3, null, null);
            }
            Intent a2 = a(intentWrapper, str, str2, str3, (String[]) arrayList2.toArray(new String[arrayList2.size()]), arrayList3);
            if (a2 == null) {
                Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "obtainBatchIntent obtain final intent fail");
                return null;
            }
            a2.putExtra("fp_scene", str);
            return a2;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "build batch intent failed:", e2);
            j.a(str, "obtainBatchIntent", e2);
            return null;
        }
    }

    public boolean hasPermission(String str, String str2) {
        return a(str, str2, (Boolean) null);
    }

    private boolean c(String str, String str2, Intent intent) {
        if (!c.a("fd.provider." + str)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission since law not support");
            j.a(str, str2, false, "law_not_support");
            return false;
        }
        boolean a2 = c.a("fd.provider." + str, intent);
        if (a2 && e.b() && !e.d(intent)) {
            e.a();
            e.a(intent);
        }
        j.a(str, str2, a2, "law");
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene %s grant permission success: %s", new Object[]{str, Boolean.valueOf(a2)});
        return a2;
    }

    private boolean d(String str, String str2, Intent intent) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        try {
            PendingIntent runningServiceControlPanel = ((ActivityManager) frameworkContext.getSystemService("activity")).getRunningServiceControlPanel(new ComponentName("com.android.systemui", "com.android.systemui.ImageWallpaper"));
            if (runningServiceControlPanel == null) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission scene pending intent is null");
                j.a(str, str2, false, "systemUi_pending_intent_null");
                return false;
            }
            intent.setDataAndType(intent.getData(), "text/com.xunmeng.pinduoduo.introduction.html");
            runningServiceControlPanel.send(frameworkContext, 0, intent);
            j.a(str, str2, true, "systemUi");
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene %s grant permission success", new Object[]{str});
            return true;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by system ui error", e2);
            j.a(str, str2, false, "SystemUi_fail");
            j.a(str, "startGrantPermissionBySystemUi", e2);
            return false;
        }
    }

    private boolean a(String str, String str2, Boolean bool) {
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "hasPermission invoked, file path: %s, scene : %s", new Object[]{str2, str});
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_fp_v2_check_permission_by_access_file_61400_" + str, "");
        if (!e("pinduoduo_Android.ka_fp_v2_check_permission_by_api_57600") || !TextUtils.isEmpty(configValue)) {
            if (!TextUtils.isEmpty(str2)) {
                return c(str2 + configValue);
            }
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no permission since empty file path");
            return false;
        } else if (TextUtils.isEmpty(str2)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no permission since empty uri");
            return false;
        } else {
            boolean a2 = a(Uri.parse(str2), bool);
            if (!a2) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "last permission granted timestamp: %d", new Object[]{Long.valueOf(this.B.getLong(str, 0L))});
            }
            return a2;
        }
    }

    public IFPUtils fileProviderUtils() {
        N.lock();
        try {
            if (H == null) {
                H = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.fpUtils.a();
            }
            return H;
        } finally {
            N.unlock();
        }
    }

    private boolean d(String str) {
        try {
            return StrategyFramework.getFrameworkContext().getPackageManager().resolveContentProvider(Uri.parse(str).getAuthority(), 0) != null;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "resolveProvider fail", e2);
            return false;
        }
    }

    private boolean a(String str, String str2, Intent intent, String str3, Boolean bool) {
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "startGrantPermission invoked, scene : %s, src : %s , target intent : %s", new Object[]{str, str2, intent});
        if (!e("pinduoduo_Android.ka_providerV2_grant_perm_57500")) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for ab not enable");
            j.a(str, str2, false, "ab_disable");
            return false;
        } else if (a(str, str3, bool)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for already has perm");
            j.a(str, str2, false, "has_permission");
            return false;
        } else if (com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.a()) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for box lock");
            j.a(str, str2, false, "box_lock");
            return false;
        } else if (intent == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant permission since intent from config is null");
            j.a(str, str2, false, "intent_null");
            return false;
        } else {
            if (intent.hasExtra("fp_flag")) {
                int intExtra = intent.getIntExtra("fp_flag", 0);
                intent.addFlags(intExtra);
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "add fp flag: %x", new Object[]{Integer.valueOf(intExtra)});
            }
            String stringExtra = intent.getStringExtra("fp_launch_method");
            if (stringExtra == null) {
                if (f("pinduoduo_Android.ka_fp_v2_grant_perm_by_system_ui_60000")) {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by system ui");
                    return d(str, str2, intent);
                } else if (h.a()) {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by iqoo secure");
                    return h.a(str, str2, intent);
                } else if (f("pinduoduo_Android.ka_fp_v2_grant_perm_by_input_60000")) {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by input");
                    return e(str, str2, intent);
                } else {
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by law");
                    return c(str, str2, intent);
                }
            }
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "fpLauncherMethod: %s", new Object[]{stringExtra});
            if (TextUtils.equals(stringExtra, "bg")) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by bg start");
                return a(str, str2, intent);
            } else if (TextUtils.equals(stringExtra, "service")) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by service");
                return b(str, str2, intent);
            } else if (!TextUtils.equals(stringExtra, "law")) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no fpLauncherMethod for: %s", new Object[]{stringExtra});
                j.a(str, str2, false, "invalid_fpLauncherMethod_" + stringExtra);
                return false;
            } else if (h.a()) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant germission by iqoo secure");
                return h.a(str, str2, intent);
            } else {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "start grant permission by law");
                return c(str, str2, intent);
            }
        }
    }

    private IntentWrapper a(IntentWrapper intentWrapper) {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_providerV2_refactor_intent_uri_62200", "");
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        if (this.O.compareAndSet(false, true)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "refactor intent config : " + configValue);
        }
        try {
            HashMap json2Map = JSONFormatUtils.instance().json2Map(new JSONObject(configValue));
            if (json2Map == null || json2Map.isEmpty()) {
                return null;
            }
            String targetUri = intentWrapper.getTargetUri();
            String str = (String) json2Map.get(targetUri);
            if (str == null || TextUtils.isEmpty(str) || !d(str)) {
                return null;
            }
            return new IntentWrapper(intentWrapper.getScene(), intentWrapper.getTargetIntent().replace(targetUri, str), str, intentWrapper.isNeedSpecialFlags(), intentWrapper.isNeedTransit(), intentWrapper.getFlags(), intentWrapper.getTransitIntent(), intentWrapper.getTransitIntentKey());
        } catch (JSONException e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "parse json error", e2);
            return null;
        }
    }

    private void a() {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_fp_v2_fetch_remote_config_by_callback_61700", false)) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "register listener for remote config call is off !");
            return;
        }
        try {
            RemoteConfig.instance().registerKeyChangeListener("pinduoduo_Android.ka_providerV2_config_57700", false, new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.d.2
                public void onConfigChanged() {
                    d.this.z = d.this.b();
                    Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "finish update remote config by callback!");
                }
            });
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "failed to register remote config callback !");
        }
    }

    public IKaelDbOperate kaelDbOperate() {
        N.lock();
        try {
            if (M == null) {
                M = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.kael.a();
            }
            return M;
        } finally {
            N.unlock();
        }
    }

    public boolean isSupport(String str, String str2) {
        if (!RemoteConfig.instance().getBoolean("ab_fp_enable_isSupport_method_6490", false)) {
            return true;
        }
        if (!RomOsUtil.instance().isNewHuaweiManufacture() && !RomOsUtil.instance().isHonerManufacture() && (!RomOsUtil.instance().isOppoManufacture() || 30 > Build.VERSION.SDK_INT || Build.VERSION.SDK_INT > 31)) {
            return true;
        }
        return c.a("fd.provider." + str2);
    }

    private boolean a(String str, String str2, Bundle bundle) {
        Boolean a2;
        String uuid = UUID.randomUUID().toString();
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "startGrantPermission invoked, scene: %s, src: %s, requestId: %s", new Object[]{str, str2, uuid});
        IntentWrapper b2 = b(str);
        if (b2 == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for scene not valid");
            j.a(str, str2, false, "no_intent_wrapper");
            return false;
        }
        Intent a3 = a(b2, str, str2, uuid, null, null);
        if (a3 == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for intent not valid");
            j.a(str, str2, false, "intent_invalid");
            return false;
        } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_fp_v2_resolve_transit_activity_61600", false) && b2.isNeedTransit() && ((a2 = a(a3)) == null || !a2.booleanValue())) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip grant perm for transit activity resolve fail");
            j.a(str, str2, false, "transit_activity_resolve_fail");
            return false;
        } else {
            if (bundle != null) {
                a3.putExtras(bundle);
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "startGrantPermission fill extra keys: %s", new Object[]{Arrays.toString(bundle.keySet().toArray(new String[bundle.keySet().size()]))});
            }
            return a(str, str2, a3, b2.getTargetUri(), b2.getIsReadWriteOnly());
        }
    }

    public IXmBehaviorWhiteProvider xmBehaviorWhiteProvider() {
        N.lock();
        try {
            if (C == null) {
                C = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.xmBW.c();
            }
            return C;
        } finally {
            N.unlock();
        }
    }

    public boolean hasPermission(String str) {
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "hasPermission invoked, scene: %s", new Object[]{str});
        IntentWrapper b2 = b(str);
        if (b2 == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", " %s is not a valid scene !", new Object[]{str});
            return false;
        }
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_fp_v2_check_permission_by_access_file_61400_" + str, "");
        if (!TextUtils.isEmpty(configValue)) {
            if (TextUtils.isEmpty(b2.getTargetUri())) {
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no permission since empty uri");
                return false;
            }
            boolean c2 = c(b2.getTargetUri() + configValue);
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene: %s, hasPermission: %s, last granted timestamp: %d", new Object[]{str, Boolean.valueOf(c2), Long.valueOf(this.B.getLong(str, 0L))});
            return c2;
        } else if (TextUtils.isEmpty(b2.getTargetUri())) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "no permission since empty uri");
            return false;
        } else {
            boolean a2 = a(Uri.parse(b2.getTargetUri()), b2.getIsReadWriteOnly());
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "scene: %s, hasPermission: %s, last granted timestamp: %d", new Object[]{str, Boolean.valueOf(a2), Long.valueOf(this.B.getLong(str, 0L))});
            return a2;
        }
    }

    public IHssLocalDataManager hssLocalDataManager() {
        N.lock();
        try {
            if (L == null) {
                L = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hss.a();
            }
            return L;
        } finally {
            N.unlock();
        }
    }

    private void a(String str, String str2, String str3) {
        try {
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            Intent intent = new Intent(frameworkContext.getPackageName() + ".intent.fp_permission_ready");
            intent.putExtra("fp_scene", str);
            intent.putExtra("fp_src", str2);
            intent.putExtra("fp_request_id", str3);
            intent.setPackage(frameworkContext.getPackageName());
            frameworkContext.sendBroadcast(intent);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "broadcastPermissionReady failed: ", e2);
            j.a(str, "broadcastPermissionReady", e2);
        }
    }

    private Intent a(IntentWrapper intentWrapper, String str, String str2, String str3, String[] strArr, List list) {
        try {
            Boolean isReadWriteOnly = intentWrapper.getIsReadWriteOnly();
            Intent parseUri = Intent.parseUri(intentWrapper.getTargetIntent(), 0);
            parseUri.putExtra("fp_scene", str);
            parseUri.putExtra("fp_src", str2);
            parseUri.putExtra("fp_request_id", str3);
            parseUri.putExtra("fp_start_ts", String.valueOf(System.currentTimeMillis()));
            parseUri.addFlags(64);
            if (isReadWriteOnly == null || isReadWriteOnly.booleanValue()) {
                parseUri.addFlags(1);
            }
            if (isReadWriteOnly == null || !isReadWriteOnly.booleanValue()) {
                parseUri.addFlags(2);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                parseUri.addFlags(128);
            }
            if (strArr != null && strArr.length > 1) {
                parseUri.putExtra("fp_batch_scenes", strArr);
                ClipData newRawUri = ClipData.newRawUri("", Uri.parse((String) list.get(0)));
                for (int i2 = 1; i2 < list.size(); i2++) {
                    newRawUri.addItem(new ClipData.Item(Uri.parse((String) list.get(i2))));
                }
                parseUri.setClipData(newRawUri);
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "obtainIntent batch clip item cnt: %d", new Object[]{Integer.valueOf(list.size())});
            }
            String str4 = null;
            if (j.e) {
                str4 = this.B.getString("fp_env_black_list_tracker_data_6470", "");
                if (!TextUtils.isEmpty(str4)) {
                    parseUri.putExtra("tracker_data", str4);
                }
            }
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_fp_v2_remove_get_extra_value_in_vivo_64600", false) && RomOsUtil.instance().isVivo()) {
                HashMap hashMap = new HashMap();
                hashMap.put("fp_scene", str);
                hashMap.put("fp_src", str2);
                hashMap.put("fp_request_id", str3);
                hashMap.put("fp_start_ts", String.valueOf(System.currentTimeMillis()));
                if (!TextUtils.isEmpty(str4)) {
                    hashMap.put("tracker_data", str4);
                }
                parseUri.putExtra("extra", JSONFormatUtils.instance().toJson(hashMap));
                boolean isUseService = intentWrapper.getIsUseService();
                parseUri.putExtra("useService", isUseService);
                Object[] objArr = new Object[1];
                objArr[0] = isUseService ? "service" : "activity";
                Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "will use %s to grant this time !", objArr);
            }
            if (intentWrapper.isNeedSpecialFlags()) {
                parseUri.addFlags(intentWrapper.getFlags());
            }
            Intent intent = null;
            if (intentWrapper.isNeedTransit()) {
                try {
                    intent = Intent.parseUri(intentWrapper.getTransitIntent(), 0);
                    intent.putExtra("fp_scene", intentWrapper.getScene());
                    if (intentWrapper.getIsUseIntentChooser() == null || !intentWrapper.getIsUseIntentChooser().booleanValue()) {
                        intent.putExtra(intentWrapper.getTransitIntentKey(), parseUri);
                    } else {
                        intent.putExtra(intentWrapper.getTransitIntentKey(), Intent.createChooser(parseUri, ""));
                        if (intentWrapper.isNeedRemoveFlags() && Build.VERSION.SDK_INT >= 26) {
                            intent.removeFlags(195);
                        }
                    }
                    if (TextUtils.equals(str, "provider_hw_launcher")) {
                        intent.putExtra("request_permission", new String[]{"android.permission.INTERNET"});
                    }
                } catch (Exception e2) {
                    Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "parse wrapper Intent failed : ", e2);
                    j.a(str, "parseTransitIntent", e2);
                }
            } else {
                intent = TextUtils.equals(str, "oppo_launcher_update") ? com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher.d.a(parseUri) : parseUri;
            }
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "parse Intent success : %s ", new Object[]{intent == null ? "null" : intent.toString()});
            return intent;
        } catch (Exception e3) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "parse Intent failed, so return new Intent :", e3);
            j.a(str, "obtainIntent", e3);
            return null;
        }
    }

    private boolean e(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "skip check ab for null");
            return false;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "true"));
    }

    public IPermQuery permQuery() {
        N.lock();
        try {
            if (I == null) {
                I = new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.permQuery.b();
            }
            return I;
        } finally {
            N.unlock();
        }
    }

    public HashMap b() {
        List<String> intentWrapperList;
        IntentWrapper intentWrapper;
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_providerV2_config_57700", "");
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "Obtain intent wrapper ab list: %s ", new Object[]{configValue});
        HashMap hashMap = new HashMap();
        if (TextUtils.isEmpty(configValue)) {
            return hashMap;
        }
        try {
            IntentWrapperList intentWrapperList2 = (IntentWrapperList) JSONFormatUtils.instance().fromJson(configValue, IntentWrapperList.class);
            if (intentWrapperList2 != null && (intentWrapperList = intentWrapperList2.getIntentWrapperList()) != null) {
                for (String str : intentWrapperList) {
                    String configValue2 = RemoteConfig.instance().getConfigValue(str, "");
                    if (!TextUtils.isEmpty(configValue2) && (intentWrapper = (IntentWrapper) JSONFormatUtils.instance().fromJson(configValue2, IntentWrapper.class)) != null && !TextUtils.isEmpty(intentWrapper.getScene())) {
                        if (!TextUtils.equals(intentWrapper.getScene(), "oppo_launcher_update")) {
                            hashMap.put(intentWrapper.getScene(), intentWrapper);
                            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "Obtain intent wrapper : %s ", new Object[]{intentWrapper});
                        } else {
                            IntentWrapper a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher.d.a(intentWrapper);
                            hashMap.put(intentWrapper.getScene(), a2);
                            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "Obtain intent wrapper : %s ", new Object[]{a2});
                        }
                        IntentWrapper a3 = a(intentWrapper);
                        if (a3 != null) {
                            hashMap.put(a3.getScene(), a3);
                            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "Refactor intent wrapper : %s", new Object[]{a3});
                        }
                    }
                }
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "parse remote config failed: ", e2);
        }
        return hashMap;
    }

    static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()));
    }

    private IntentWrapper b(String str) {
        return this.z.get(str) != null ? (IntentWrapper) this.z.get(str) : (IntentWrapper) this.A.get(str);
    }

    private HashMap c() {
        HashMap hashMap = new HashMap();
        hashMap.put("XM_BW_UPDATE_DB", new IntentWrapper("XM_BW_UPDATE_DB", "content://com.miui.securitycore.fileProvider/root_files#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;B.need_transit=true;end", "content://com.miui.securitycore.fileProvider/root_files", true, false, -2147450685, "", ""));
        hashMap.put("HW_SS_UPDATE_DB", new IntentWrapper("HW_SS_UPDATE_DB", "content://com.android.settings.files/my_root#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.android.settings.files/my_root", false, false, 0, "", ""));
        hashMap.put("provider_oppo_au", new IntentWrapper("provider_oppo_au", "content://com.coloros.phonemanager.files/clear_share#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.coloros.phonemanager.files/clear_share", false, false, 0, "", ""));
        hashMap.put("hw_hwid", new IntentWrapper("hw_hwid", "content://com.huawei.hms.install.apkInstallProvider/hwid#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.huawei.hms.install.apkInstallProvider/hwid", false, true, 0, "#Intent;component=com.huawei.hwid/com.tencent.connect.common.AssistActivity;S.appid=ac7qdi5y2op;end", "openSDK_LOG.AssistActivity.ExtraIntent"));
        hashMap.put("HW_HB_UPDATE_SP", new IntentWrapper("HW_HB_UPDATE_SP", "content://com.huawei.intelligent.fastapp.engine.fileProvider/root_path#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.huawei.intelligent.fastapp.engine.fileProvider/root_path", false, true, 0, "#Intent;component=com.huawei.intelligent/com.huawei.hms.activity.BridgeActivity;S.intent.extra.DELEGATE_CLASS_OBJECT=com.huawei.hms.adapter.ui.BaseResolutionAdapter;end", "resolution"));
        hashMap.put("oppo_launcher_update", com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher.d.j());
        hashMap.put("HSS_UPDATE_XML", new IntentWrapper("HSS_UPDATE_XML", "content://com.android.settings.files/my_root#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.android.settings.files/my_root", false, false, 0, "", ""));
        hashMap.put("provider_oppo_ld", new IntentWrapper("provider_oppo_ld", "content://com.coloros.phonemanager.files/clear_share#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.coloros.phonemanager.files/clear_share", false, false, 0, "", ""));
        return hashMap;
    }

    public static String a(Intent intent, String str) {
        HashMap hashMap;
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_fp_v2_remove_get_extra_value_in_vivo_64600", false) || !RomOsUtil.instance().isVivo()) {
            return intent.getStringExtra(str);
        }
        String stringExtra = intent.getStringExtra("extra");
        if (TextUtils.isEmpty(stringExtra) || (hashMap = (HashMap) JSONFormatUtils.instance().fromJson(stringExtra, HashMap.class)) == null) {
            return "";
        }
        String str2 = (String) hashMap.get(str);
        return TextUtils.isEmpty(str2) ? "" : str2;
    }

    public boolean startGrantPermission(String str, String str2) {
        return a(str, str2, (Bundle) null);
    }

    public d() {
        a();
        Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "success init DefaultFileProviderImpl, remote intent : %s . local default intent : %s .", new Object[]{this.z, this.A});
    }

    public Uri getValidUriByScene(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", " get valid uri failed for invalid scene !", new Object[]{str});
            return null;
        }
        IntentWrapper b2 = b(str);
        if (b2 == null) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", " get valid uri failed for null intentWrapper !", new Object[]{str});
            return null;
        } else if (TextUtils.isEmpty(b2.getTargetUri())) {
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", " get valid uri failed for empty uri !", new Object[]{str});
            return null;
        } else {
            String targetUri = b2.getTargetUri();
            Logger.i("LVBA.AliveModule.Provider.DefaultFileProviderImpl", "success obtain valid uri %s by %s !", new Object[]{targetUri, str});
            return Uri.parse(targetUri);
        }
    }
}
