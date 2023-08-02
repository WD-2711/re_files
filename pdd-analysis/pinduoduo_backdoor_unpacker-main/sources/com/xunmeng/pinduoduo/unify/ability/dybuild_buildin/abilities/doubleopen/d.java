package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: d.class */
public class d {
    private static final String a = null;
    private static final String b = null;

    private static boolean b(String str) {
        try {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDBbyServiceManager");
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            declaredMethod.setAccessible(true);
            IBinder iBinder = (IBinder) declaredMethod.invoke(null, "package");
            Method declaredMethod2 = Class.forName("android.content.pm.IPackageManager$Stub").getDeclaredMethod("asInterface", IBinder.class);
            declaredMethod2.setAccessible(true);
            Object invoke = declaredMethod2.invoke(null, iBinder);
            Method method = invoke.getClass().getMethod("getPackageInfo", String.class, Integer.TYPE, Integer.TYPE);
            method.setAccessible(true);
            PackageInfo packageInfo = (PackageInfo) method.invoke(invoke, str, 0, 999);
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "packageInfo:" + packageInfo);
            return packageInfo != null;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            b.a(th);
            return false;
        }
    }

    private static boolean a() {
        try {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOBySecureSettings");
            int i = Settings.Secure.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), "xspace_enabled", -1);
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOBySecureSettings xspaceEnabled:" + i);
            return i == 1;
        } catch (Throwable th) {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            b.a(th);
            return false;
        }
    }

    private static boolean e(String str) {
        try {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOByUserId");
            if (Build.VERSION.SDK_INT < 17 || !TextUtils.equals(str, StrategyFramework.getFrameworkContext().getPackageName())) {
                return false;
            }
            int hashCode = Process.myUserHandle().hashCode();
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "userId=" + hashCode);
            return hashCode == 999;
        } catch (Throwable th) {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            b.a(th);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Boolean a(String str) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_double_open_62000", true)) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDoubleOpen hit ab ");
                return false;
            }
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDoubleOpen");
            boolean a2 = a();
            boolean d = d(str);
            boolean c = c(str);
            boolean b2 = b(str);
            boolean a3 = a.a(str);
            boolean e = e(str);
            HashMap hashMap = new HashMap();
            hashMap.put("isDOBySecureSettings", a2 ? "1" : "0");
            hashMap.put("isDOByUid", d ? "1" : "0");
            hashMap.put("isDOByUserFile", c ? "1" : "0");
            hashMap.put("isDBbyServiceManager", b2 ? "1" : "0");
            hashMap.put("isDOByLauncherApps", a3 ? "1" : "0");
            hashMap.put("isDOByUserId", e ? "1" : "0");
            b.a("pdd_double_open_status", hashMap);
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_userId_double_open_61800", false) && e) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDOByUserId");
                return true;
            } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_isDOByLauncherApps_double_open_62200", false) && a3) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDOByLauncherApps");
                return true;
            } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_isDOByUserFile_double_open_61800", true) && c) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDOByUserFile");
                return true;
            } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_isDOBySecureSettings_double_open_61800", true) && !a2) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDOBySecureSettings");
                return false;
            } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_isDBbyServiceManager_double_open_61800", true) && "V130".equals(RomOsUtil.instance().getVersion()) && b2) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDBbyServiceManager");
                return true;
            } else if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_isDOByUid_double_open_61800", true) || !d) {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDoubleOpen not hit result");
                return false;
            } else {
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "return isDOByUid");
                return true;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            b.a(th);
            return null;
        }
    }

    private static boolean c(String str) {
        try {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOByUserFile");
            if (!new File("/data/user/999/" + str).exists()) {
                return false;
            }
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOByUserFile data exist double open");
            return true;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            return false;
        }
    }

    private static boolean d(String str) {
        try {
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOByUid");
            if (Build.VERSION.SDK_INT < 24) {
                return false;
            }
            Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "uid double open");
            String[] packagesForUid = StrategyFramework.getFrameworkContext().getPackageManager().getPackagesForUid(StrategyFramework.getFrameworkContext().getPackageManager().getPackageUid(str, 0) - 195426976);
            if (packagesForUid == null || packagesForUid.length <= 0) {
                return false;
            }
            boolean z = false;
            for (String str2 : packagesForUid) {
                if (str2 != null && str2.equals(str)) {
                    z = true;
                }
                Logger.i("LVUA.Dybuild.MiuiSystemDoubleOpen", "isDOByUid pkgName:" + str2);
            }
            return z;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.MiuiSystemDoubleOpen", th);
            return false;
        }
    }
}
