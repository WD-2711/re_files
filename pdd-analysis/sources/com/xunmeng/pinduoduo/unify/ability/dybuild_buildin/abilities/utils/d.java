package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.os.Build;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;

/* loaded from: d.class */
public class d {
    private static final String a = null;

    public static String a() {
        String str;
        if (Build.VERSION.SDK_INT >= 23) {
            if (RomOsUtil.instance().isEmui()) {
                String str2 = PddSystemProperties.instance().get("ro.huawei.build.version.security_patch", "");
                if (!TextUtils.isEmpty(str2)) {
                    return str2;
                }
            }
            str = Build.VERSION.SECURITY_PATCH;
        } else {
            str = PddSystemProperties.instance().get("ro.build.version.security_patch", "");
        }
        return str;
    }

    public static boolean a(String str) {
        String a2 = a();
        Logger.i("LVUA.SecPatchUtil", "nowSecPatchVersion:%s, configSecPatchVersion:%s", new Object[]{a2, str});
        try {
            if (TextUtils.isEmpty(a2) || TextUtils.isEmpty(str)) {
                Logger.e("LVUA.SecPatchUtil", "now pat ver:" + a2 + " config pat ver:" + str);
                return true;
            } else if (a2.length() != str.length()) {
                Logger.e("LVUA.SecPatchUtil", "the length of now and config is not equal");
                return true;
            } else {
                String[] split = a2.split("-");
                String[] split2 = str.split("-");
                if (split.length != split2.length) {
                    Logger.e("LVUA.SecPatchUtil", "now arr length and config arr length is not equal");
                    return true;
                }
                int length = split.length;
                for (int i = 0; i < length; i++) {
                    int parseInt = Integer.parseInt(split[i]);
                    int parseInt2 = Integer.parseInt(split2[i]);
                    if (parseInt > parseInt2) {
                        return true;
                    }
                    if (parseInt < parseInt2) {
                        return false;
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.SecPatchUtil", "sec patch version compare exception", th);
            return true;
        }
    }
}
