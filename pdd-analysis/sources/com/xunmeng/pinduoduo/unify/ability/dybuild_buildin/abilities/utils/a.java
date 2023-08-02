package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.database.Cursor;
import android.net.Uri;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeviceUtil;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    public static String b() {
        return AppBuildInfo.instance().getRealVersionCode() >= 16580740 ? c() : "";
    }

    public static boolean a() {
        return a(false);
    }

    private static String c() {
        return DeviceUtil.instance().getRomBuildId();
    }

    public static boolean a(boolean z) {
        Cursor cursor = null;
        try {
            cursor = c.a(Uri.parse("content://com.oplus.customize.coreapp.configmanager.configprovider.AppFeatureProvider").buildUpon().appendPath("app_feature").build(), null, "featurename=?", new String[]{"com.android.systemui.apply_light_os_qs_tile"}, null);
            boolean z2 = cursor != null && cursor.getCount() > 0;
            Logger.i("LVUA.Dybuild.Sion.CommonSystemUtils", "[isLightOs] " + z2);
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return z2;
        } catch (Throwable th) {
            try {
                Logger.e("LVUA.Dybuild.Sion.CommonSystemUtils", "[isLightOs] error", th);
                if (z) {
                    com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.f.a("isOppoLightOs_error", th.getMessage());
                }
                return false;
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
    }
}
