package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeviceUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelClzListConfig;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.f;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.g;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.h;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.i;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.j;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.k;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.l;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.m;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.n;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.o;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.p;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.q;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.r;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.s;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.t;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: e.class */
public class e {
    private static final String a = null;
    private static final String b = null;
    private static final Map c = new HashMap();

    public static boolean a(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                Logger.e("SpecialPullAbility.Comp.ParcelClzManager", "now pat ver:" + str + " config pat ver:" + str2);
                return true;
            } else if (str.length() != str2.length()) {
                Logger.e("SpecialPullAbility.Comp.ParcelClzManager", "the length of now and config is not equal");
                return true;
            } else {
                String[] split = str.split("-");
                String[] split2 = str2.split("-");
                if (split.length != split2.length) {
                    Logger.e("SpecialPullAbility.Comp.ParcelClzManager", "now arr length and config arr length is not equal");
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
            Logger.e("SpecialPullAbility.Comp.ParcelClzManager", "sec patch version compare exception");
            return true;
        }
    }

    public static boolean a() {
        return c() != null;
    }

    static {
        a[] aVarArr;
        for (a aVar : new a[]{new t(), new i(), new s(), new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.a(), new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.c(), new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.e(), new f(), new g(), new j(), new k(), new l(), new m(), new n(), new o(), new p(), new q(), new r(), new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.b(), new h(), new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes.d()}) {
            c.put(aVar.c(), aVar);
        }
    }

    private static ParcelClzListConfig c() {
        String configValue = RemoteConfig.instance().getConfigValue("config_pull_ability_clz_64100", "");
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        try {
            return (ParcelClzListConfig) PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin").fromJson(configValue, ParcelClzListConfig.class);
        } catch (Throwable th) {
            Logger.i("SpecialPullAbility.Comp.ParcelClzManager", "fail to parse config: " + configValue, th);
            return null;
        }
    }

    public static List b() {
        ArrayList arrayList = new ArrayList();
        ParcelClzListConfig c2 = c();
        if (c2 == null || c2.parcelClzList == null) {
            return arrayList;
        }
        for (ParcelClzListConfig.ParcelClzConfig parcelClzConfig : c2.parcelClzList) {
            if (TextUtils.isEmpty(parcelClzConfig.abKey) || TextUtils.isEmpty(parcelClzConfig.configName)) {
                Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "skip parcelClzItem with empty fields, abKey: %s, configName: %s", new Object[]{parcelClzConfig.abKey, parcelClzConfig.configName});
            } else if (!RemoteConfig.instance().getBoolean(parcelClzConfig.abKey, false)) {
                Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "skip parcelClzItem by ab, configName: %s", new Object[]{parcelClzConfig.configName});
            } else {
                a aVar = (a) c.get(parcelClzConfig.configName);
                if (aVar == null) {
                    Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "parcelClz not found, configName: %s", new Object[]{parcelClzConfig.configName});
                } else if (TextUtils.isEmpty(parcelClzConfig.configSecPatchVersion)) {
                    Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "skip parcelClzItem since configSecPatch is empty");
                } else if (a(DeviceUtil.instance().getSecurePatchVersion(), parcelClzConfig.configSecPatchVersion)) {
                    Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "skip parcelClzItem since now sec patch is big than config patch");
                } else if (!aVar.d()) {
                    Logger.w("SpecialPullAbility.Comp.ParcelClzManager", "skip parcelClz since not enabled, configName: %s", new Object[]{parcelClzConfig.configName});
                } else {
                    aVar.a(parcelClzConfig);
                    arrayList.add(aVar);
                }
            }
        }
        return arrayList;
    }
}
