package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.FileProviderV2;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Proguard;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.MiUIUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;

    private static boolean a(ComponentName componentName, boolean z) {
        if (z) {
            try {
                if (a.b()) {
                    Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "titan has online today");
                    return false;
                }
            } catch (Exception e) {
                Logger.e("LVST2.comp.TeaStrategy.TeaHelper", "checkConditionForHijackComponent fail, exception = " + e.getMessage());
                return false;
            }
        }
        String className = componentName.getClassName();
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "targetComponentClassName = " + className);
        if (a.a(className)) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "service onBind count has reached maximum");
            return false;
        }
        return true;
    }

    public static boolean a(Context context) {
        try {
            String string = Settings.System.getString(context.getContentResolver(), "settingAvatarPath");
            String string2 = Settings.System.getString(context.getContentResolver(), "accountNickname");
            if (!TextUtils.isEmpty(string) || !TextUtils.isEmpty(string2)) {
                Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "vivo account has login ");
                return true;
            }
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "not login vivo account");
            e.b("not_login_vivo");
            return false;
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", e);
            return true;
        }
    }

    public static void a(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", " close " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception e) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }

    private static int d(Context context, String str) {
        BlackListItem cachedConfig;
        if ("skip_blacklist".equals(str)) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "skip blacklist check");
            return 0;
        }
        int i = -1;
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_tea_new_blacklist_api_60600", false)) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "use new api");
            cachedConfig = MSCManager.instance().getConfig(context, new SceneRequest(str));
        } else {
            cachedConfig = MSCManager.instance().getCachedConfig(context, new SceneRequest(str));
        }
        if (cachedConfig != null) {
            i = cachedConfig.isBlack() ? 1 : 0;
        }
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "sceneId = " + str + "; blacklist result = " + i);
        return i;
    }

    public static boolean a(Context context, String str) {
        boolean b2 = b(context, str);
        e.b(str + (b2 ? "_installed" : "_uninstalled"));
        return b2;
    }

    public static void a(Context context, String str, String str2, ComponentName componentName, boolean z, String str3) {
        try {
            if (!c(context, str)) {
                b(context, str2, componentName, z, str3);
                return;
            }
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", "find other component, need to disable our component");
            if (d(context, componentName)) {
                return;
            }
            a(context, componentName);
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Runnable, com.xunmeng.pinduoduo.alive.strategy.comp.tea.b$1] */
    public static void a(final Context context, final String str, final ComponentName componentName, final ComponentName componentName2, final boolean z, final String str2) {
        ThreadPool.instance().scheduleTask(ThreadBiz.CS, "PDDControlOppoSfc", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.tea.b.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                try {
                    Logger.i("LVST2.comp.TeaStrategy.TeaHelper", Proguard.marks("start to control component for oppo PDDSfc"));
                    b.b(context, str, componentName, z, str2);
                    b.b(context, str, componentName2, z, str2);
                } catch (Exception e) {
                    Logger.e("LVST2.comp.TeaStrategy.TeaHelper", Proguard.marks("control component for oppo PDDSfc failed ") + e);
                }
            }
        }, 0L, TimeUnit.MILLISECONDS);
    }

    public static void a(Context context, String str, ComponentName componentName, boolean z, String str2, int i) {
        try {
            if (a(componentName, i)) {
                b(context, str, componentName, z, str2);
                return;
            }
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "try to disable component");
            c(context, componentName);
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", "controlTransitActionComponent fail", e);
        }
    }

    private static void a(String str, Map map) {
        if (a.a().getBoolean(str, false)) {
            return;
        }
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "report data:" + str);
        StrategyFramework.trackError("TeaStrategy", new TrackErrorOption(10485900, 30069, str, map, (Integer) null));
        a.a().putBoolean(str, true);
    }

    private static boolean a(ComponentName componentName, int i) {
        try {
            String className = componentName.getClassName();
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "targetComponentClassName = " + className);
            if (!a.a(className, i)) {
                return true;
            }
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "service onBind count has reached maximum");
            return false;
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", "checkConditionForHijackComponent fail", e);
            return false;
        }
    }

    public static void a(Context context, String str, ComponentName componentName, boolean z, String str2) {
        try {
            boolean z2 = RemoteConfig.instance().getBoolean("pinduoduo_Android.pa_strategy_enable_whitelist_scene_check_58300", false);
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "pinduoduo_Android.pa_strategy_enable_whitelist_scene_check_58300 ab is " + z2);
            if (RemoteConfig.instance().getBoolean(str, z) && z2 && d(context, "1106") == 0) {
                if (e(context, componentName)) {
                    return;
                }
                b(context, componentName);
            } else if (!b() || a()) {
                b(context, str, componentName, z, str2);
            } else if (d(context, componentName)) {
            } else {
                a(context, componentName);
            }
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }

    private static boolean a() {
        boolean z = RemoteConfig.instance().getBoolean("pinduoduo_Android.pa_strategy_enable_whitelist_check_58000", false);
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "pinduoduo_Android.pa_strategy_enable_whitelist_check_58000 ab is " + z);
        if (!z) {
            return false;
        }
        Boolean isInBehaviorWhiteList = FileProviderV2.instance().xmBehaviorWhiteProvider().isInBehaviorWhiteList();
        if (isInBehaviorWhiteList != null) {
            return isInBehaviorWhiteList.booleanValue();
        }
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "isInBehaviorWhitelist is null");
        return false;
    }

    private static boolean d(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 2;
        } catch (Exception e) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
        if (a(r8, r11) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r6, java.lang.String r7, android.content.ComponentName r8, boolean r9, java.lang.String r10, boolean r11, boolean r12, boolean r13) {
        /*
            r0 = r13
            if (r0 == 0) goto L14
            r0 = r12
            if (r0 != 0) goto L25
            r0 = r8
            r1 = r11
            boolean r0 = a(r0, r1)     // Catch: java.lang.Exception -> L35
            if (r0 != 0) goto L25
        L14:
            java.lang.String r0 = "LVST2.comp.TeaStrategy.TeaHelper"
            java.lang.String r1 = "try to disable component"
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.i(r0, r1)     // Catch: java.lang.Exception -> L35
            r0 = r6
            r1 = r8
            c(r0, r1)     // Catch: java.lang.Exception -> L35
            return
        L25:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            b(r0, r1, r2, r3, r4)     // Catch: java.lang.Exception -> L35
            goto L55
        L35:
            r14 = move-exception
            java.lang.String r0 = "LVST2.comp.TeaStrategy.TeaHelper"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = r1
            r2.<init>()
            java.lang.String r2 = "controlTransitActionComponent fail, exception e = "
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r14
            java.lang.String r2 = r2.getMessage()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.e(r0, r1)
        L55:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.alive.strategy.comp.tea.b.a(android.content.Context, java.lang.String, android.content.ComponentName, boolean, java.lang.String, boolean, boolean, boolean):void");
    }

    private static boolean e(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 1;
        } catch (Exception e) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", e);
            return false;
        }
    }

    private static boolean b() {
        if (!RomOsUtil.instance().isMiui()) {
            return false;
        }
        String versionName = MiUIUtils.instance().getVersionName();
        if (versionName == null) {
            return true;
        }
        try {
            String[] split = versionName.trim().split("\\.");
            if (split.length == 0) {
                return true;
            }
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "miui version code:%s", new Object[]{split[0]});
            return NumberUtils.instance().parseInt(split[0], 99) >= 12;
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", e);
            return true;
        }
    }

    public static void b(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", " open " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }

    private static boolean c(Context context, String str) {
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "detect action:" + str);
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new Intent(str), 0);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "not implements");
        } else {
            for (ResolveInfo resolveInfo : queryIntentServices) {
                if (!resolveInfo.serviceInfo.packageName.equals(context.getPackageName())) {
                    Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "find other components:" + resolveInfo);
                    HashMap hashMap = new HashMap();
                    hashMap.put("pkgName", resolveInfo.serviceInfo.packageName);
                    hashMap.put("cmpName", String.valueOf(resolveInfo));
                    a(str + "-findOtherComponent", hashMap);
                    return true;
                }
                Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "find myself :" + resolveInfo);
            }
        }
        a(str + "-notFindOther", new HashMap());
        return false;
    }

    public static void c(Context context, ComponentName componentName) {
        if (!d(context, componentName)) {
            a(context, componentName);
        }
    }

    public static boolean b(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", Proguard.marks("invalid pkgName"));
            return false;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", Proguard.marks("get packageInfo failed, exception = ") + e.getMessage());
        }
        boolean z = packageInfo != null;
        Logger.i("LVST2.comp.TeaStrategy.TeaHelper", str + Proguard.marks(" is installed: ") + z);
        return z;
    }

    public static void b(Context context, String str, ComponentName componentName, boolean z, String str2) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            boolean z2 = RemoteConfig.instance().getBoolean(str, z);
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", str + " ab is open : " + z2 + ";\n " + componentName + " enabled = " + componentEnabledSetting);
            if (!z2) {
                if (componentEnabledSetting == 2) {
                    return;
                }
                a(context, componentName);
                return;
            }
            int d2 = d(context, str2);
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_blacklist_update_61900", false) && d2 == -1) {
                Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "do nothing");
            } else if (d2 == 1) {
                if (componentEnabledSetting == 2) {
                    return;
                }
                a(context, componentName);
            } else if (componentEnabledSetting == 1) {
            } else {
                b(context, componentName);
            }
        } catch (Exception e) {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }

    public static void a(Context context, List list, List list2, boolean z, String str) {
        try {
            Logger.i("LVST2.comp.TeaStrategy.TeaHelper", "---controlMultiActionComponent---");
            boolean z2 = false;
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (c(context, (String) it.next())) {
                    Logger.e("LVST2.comp.TeaStrategy.TeaHelper", "find other component, need to disable our component");
                    z2 = true;
                    break;
                }
            }
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                Pair pair = (Pair) it2.next();
                if (z2) {
                    c(context, (ComponentName) pair.second);
                } else {
                    a(context, (String) pair.first, (ComponentName) pair.second, z, str);
                }
            }
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy.TeaHelper", e);
        }
    }
}
