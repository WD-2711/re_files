package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: g.class */
public class g {
    private static final String a = null;

    /* renamed from: d */
    public static String getOPPODefaultLauncherVersion() {
        return getPackageVersion("com.oppo.launcher");
    }

    /* renamed from: f */
    public static String getCurrentSystemDefaultLauncherPackageName() {
        if (RomOsUtil.instance().isEmui()) {
            return "com.huawei.android.launcher";
        }
        if (RomOsUtil.instance().isOppo()) {
            return "com.oppo.launcher";
        }
        if (RomOsUtil.instance().isVivo()) {
            return "com.bbk.launcher2";
        }
        if (!RomOsUtil.instance().isMiui()) {
            return null;
        }
        return "com.miui.home";
    }

    /* renamed from: a */
    public static boolean compareVersion(String str, String str2) {
        try {
            ArrayList arrayList = new ArrayList(Arrays.asList(str.split("\\.")));
            Logger.i("LVBA.AliveModule.Provider.LauncherVersionUtils", " config version " + str2);
            String[] split = str2.split("\\.");
            if (TextUtils.isEmpty(str2)) {
                return true;
            }
            while (arrayList.size() < split.length) {
                Logger.i("LVBA.AliveModule.Provider.LauncherVersionUtils", " complete ..");
                arrayList.add("0");
            }
            for (int i = 0; i < split.length; i++) {
                int parseInt = NumberUtils.instance().parseInt((String) arrayList.get(i));
                int parseInt2 = NumberUtils.instance().parseInt(split[i]);
                if (parseInt < parseInt2) {
                    return false;
                }
                if (parseInt > parseInt2) {
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.LauncherVersionUtils", e);
            return true;
        }
    }

    /* renamed from: e */
    public static String getVivoDefaultLauncherVersion() {
        return getPackageVersion("com.bbk.launcher2");
    }

    /* renamed from: a */
    public static String getMIUIDefaultLauncherVersion() {
        return getPackageVersion("com.miui.home");
    }

    /* renamed from: c */
    public static String getHuaweiDefaultLauncherPackageName() {
        return RomOsUtil.instance().isHonerManufacture() ? getPackageVersion("com.hihonor.android.launcher") : getPackageVersion("com.huawei.android.launcher");
    }

    /* renamed from: g */
    private static String getCurrentSystemDefaultLauncherVersion() {
        if (RomOsUtil.instance().isMiui()) {
            return getMIUIDefaultLauncherVersion();
        }
        if (RomOsUtil.instance().isEmui()) {
            return getHuaweiDefaultLauncherPackageName();
        }
        if (RomOsUtil.instance().isVivo()) {
            return getVivoDefaultLauncherVersion();
        }
        if (RomOsUtil.instance().isOppo()) {
            return getOPPODefaultLauncherVersion();
        }
        Logger.e("LVBA.AliveModule.Provider.LauncherVersionUtils", "[getOVHMVer] unsupport brand.");
        return "";
    }

    /* renamed from: b */
    public static Integer getMIUIDefaultLauncherVersionCode() {
        return getPackageVersionCode("com.miui.home");
    }

    /* renamed from: b */
    private static Integer getPackageVersionCode(String str) {
        try {
            int i = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo(str, 16384).versionCode;
            Logger.i("LVBA.AliveModule.Provider.LauncherVersionUtils", "[getVerCode] pkgName:%s, version:%s", new Object[]{str, Integer.valueOf(i)});
            return Integer.valueOf(i);
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.LauncherVersionUtils", "[getVerCode] failed.", e);
            return null;
        }
    }

    /* renamed from: a */
    private static String getPackageVersion(String str) {
        try {
            String str2 = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo(str, 16384).versionName;
            Logger.i("LVBA.AliveModule.Provider.LauncherVersionUtils", "[getCommonVer] pkgName:%s, version:%s", new Object[]{str, str2});
            if (TextUtils.isEmpty(str2)) {
                return "";
            }
            int i = -1;
            for (int i2 = 0; i2 < str2.length() && (str2.charAt(i2) < '0' || str2.charAt(i2) > '9'); i2++) {
                i = i2;
            }
            return str2.substring(i + 1);
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.LauncherVersionUtils", "[getCommonVer] failed.", e);
            return "";
        }
    }
}