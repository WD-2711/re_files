package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel;

import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;

/* loaded from: b.class */
public class b {
    private static final String a = null;

    public static void a(Parcel parcel, String str) {
        try {
            for (int i : b(str)) {
                parcel.writeInt(i);
            }
        } catch (Throwable th) {
            Logger.i("handlePU", "p write exception");
        }
    }

    public static String a(int[] iArr) {
        Parcel parcel = null;
        String str = null;
        try {
            parcel = Parcel.obtain();
            parcel.writeInt(iArr[0]);
            int length = iArr.length;
            for (int i = 0; i < length - 1; i++) {
                parcel.writeInt(iArr[i + 1]);
            }
            parcel.setDataPosition(0);
            str = parcel.readString();
            if (parcel != null) {
                parcel.recycle();
            }
        } catch (Throwable th) {
            try {
                Logger.i("handlePU", "handle exception");
            } finally {
                if (parcel != null) {
                    parcel.recycle();
                }
            }
        }
        return str;
    }

    public static int[] b(String str) {
        String[] split = str.split(",");
        int length = split.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = NumberUtils.instance().parseInt(split[i]);
        }
        return iArr;
    }

    public static ParcelParaArr a(String str, String str2) {
        String configValue = RemoteConfig.instance().getConfigValue(str2, "");
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        try {
            return (ParcelParaArr) PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin").fromJson(configValue, ParcelParaArr.class);
        } catch (Throwable th) {
            Logger.i(str, "fail to parse config: " + configValue, th);
            return null;
        }
    }

    public static String a(String str) {
        return a(b(str));
    }
}
