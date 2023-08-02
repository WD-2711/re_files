package com.xunmeng.pinduoduo.alive.strategy.comp.wheel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final String b = null;
    private static final int d = 0;
    private static final int e = 0;
    private static final int f = 0;
    private static final int g = 0;
    private static final IMMKV c = MMKVCompat.module("WheelStrategyAutoBind", false);
    private static final Random h = new Random();

    public static void a(Context context, ComponentName componentName) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            long b2 = a.a().b();
            a.a().a(currentTimeMillis);
            long j = b2 == 0 ? 0L : currentTimeMillis - b2;
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "now = %d, last = %d, interval = %d", new Object[]{Long.valueOf(currentTimeMillis), Long.valueOf(b2), Long.valueOf(j)});
            HashMap hashMap = new HashMap();
            for (int i = 1; i <= 4; i++) {
                ComponentName a2 = a(context, i);
                if (null == a2) {
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "no find component");
                } else {
                    int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(a2);
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "componentName = %s, status = %d", new Object[]{a2.getShortClassName(), Integer.valueOf(componentEnabledSetting)});
                    hashMap.put(a2.getShortClassName(), Integer.valueOf(a(componentEnabledSetting)));
                }
            }
            c.a(hashMap, j, componentName);
        } catch (Exception e2) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "uploadComponentStatus failed " + e2);
        }
    }

    public static ComponentName a(Context context, int i) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentServices(new Intent().setPackage(context.getPackageName()), 640)) {
            Bundle bundle = resolveInfo.serviceInfo.metaData;
            if (null != bundle && bundle.getInt("my_service_id") == i) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "found, component = %s, id = %d", new Object[]{resolveInfo.serviceInfo.name, Integer.valueOf(i)});
                return new ComponentName(context.getPackageName(), resolveInfo.serviceInfo.name);
            }
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "not found, id = %d", new Object[]{Integer.valueOf(i)});
        return null;
    }

    public static boolean a(Context context, ArrayList arrayList, int i) {
        int i2 = 0;
        for (int i3 = 1; i3 <= 4; i3++) {
            ComponentName a2 = a(context, i3);
            if (null == a2) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "no find component");
            } else {
                int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(a2);
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", a2.getShortClassName() + " status = " + componentEnabledSetting);
                if (componentEnabledSetting != 1) {
                    continue;
                } else if (!arrayList.contains(Integer.valueOf(i3))) {
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "enable component not in active component list");
                    return false;
                } else {
                    i2++;
                }
            }
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "enableComponentCount = " + i2);
        return i2 == i;
    }

    public static void a(Context context) {
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "close all roll component");
        for (int i = 1; i <= 4; i++) {
            a(context, a(context, i), false);
        }
    }

    public static List b(String str) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "invalid value, return");
            return arrayList;
        }
        for (String str2 : str.split(",")) {
            arrayList.add(Long.valueOf(str2));
        }
        return arrayList;
    }

    public static void a(Context context, ComponentName componentName, boolean z) {
        if (null == componentName) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "null component, something wrong");
            return;
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "try to " + (z ? "open " : "close ") + componentName.getShortClassName());
        if (z && !e(context, componentName)) {
            c(context, componentName);
        } else if (z || d(context, componentName)) {
        } else {
            b(context, componentName);
        }
    }

    public static void b(Context context, ComponentName componentName, boolean z) {
        if (null == componentName) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "null component, something wrong");
            return;
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "try to " + (z ? "open " : "close ") + componentName.getShortClassName());
        if (z && !e(context, componentName)) {
            c(context, componentName);
            b(componentName, 0L);
            a(componentName, 0L);
            a(componentName, 0);
        } else if (z || d(context, componentName)) {
        } else {
            b(context, componentName);
            b(componentName, TimeStamp.instance().getRealLocalTimeV2());
            a(componentName, 0);
            a(componentName, 0L);
        }
    }

    private static void a(ComponentName componentName, long j) {
        String str = "wheel_first_bind_time_" + componentName.getShortClassName();
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "set " + str + " = " + j);
        c.putLong(str, j);
    }

    private static boolean d(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 2;
        } catch (Exception e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", e2);
            return false;
        }
    }

    public static int a(Context context, String str) {
        if ("skip_blacklist".equals(str)) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "skip blacklist check");
            return 0;
        }
        int i = -1;
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_blacklist_update_61900", false)) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "check degrade");
            SceneRequest sceneRequest = new SceneRequest(str);
            sceneRequest.setOutputDegradeInfo(true);
            BlackListItem config = MSCManager.instance().getConfig(context, sceneRequest);
            if (config != null) {
                i = (config.getDegrade() == null || !config.getDegrade().booleanValue()) ? config.isBlack() ? 1 : 0 : 2;
            }
        } else {
            BlackListItem cachedConfig = MSCManager.instance().getCachedConfig(context, new SceneRequest(str));
            if (cachedConfig != null) {
                i = cachedConfig.isBlack() ? 1 : 0;
            }
        }
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sceneId = " + str + "; blacklist result = " + i);
        return i;
    }

    private static void a(ComponentName componentName, int i) {
        String str = "wheel_count_" + componentName.getShortClassName();
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "set " + str + " = " + i);
        c.putInt(str, i);
    }

    private static long a(ComponentName componentName) {
        String str = "wheel_sleep_start_time_" + componentName.getShortClassName();
        long j = c.getLong(str, 0L);
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "get " + str + " = " + j);
        return j;
    }

    private static void a(ArrayList arrayList, int i) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((Integer) it.next()).intValue() == i) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "delete value = " + i);
                it.remove();
            }
        }
    }

    public static void a(Context context, ArrayList arrayList, int i, int i2) {
        ArrayList a2 = a(arrayList, i, i2);
        for (int i3 = 1; i3 <= 4; i3++) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "process component id = " + i3);
            ComponentName a3 = a(context, i3);
            if (null == a3) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "no find component");
            } else if (a2.contains(Integer.valueOf(i3))) {
                a(context, a3, true);
            } else {
                a(context, a3, false);
            }
        }
    }

    public static void b(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", " close " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", e2);
        }
    }

    public static int a(ArrayList arrayList) {
        int nextInt = h.nextInt(arrayList.size());
        int intValue = ((Integer) arrayList.get(nextInt)).intValue();
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "get random service id = " + intValue);
        arrayList.remove(nextInt);
        return intValue;
    }

    private static int a(int i) {
        return i == 1 ? 1 : 0;
    }

    public static ArrayList a(String str) {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "invalid value, return");
            return arrayList;
        }
        for (String str2 : str.split(",")) {
            arrayList.add(Integer.valueOf(str2));
        }
        return arrayList;
    }

    public static ArrayList a(ArrayList arrayList, int i, int i2) {
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "getEnableComponentListByRandom");
        ArrayList arrayList2 = new ArrayList();
        try {
            ArrayList arrayList3 = new ArrayList();
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                arrayList3.add((Integer) arrayList.get(i3));
            }
            a(arrayList3, i);
            for (int i4 = 0; i4 < i2; i4++) {
                arrayList2.add(Integer.valueOf(a(arrayList3)));
            }
        } catch (Exception e2) {
            Logger.e("LVST2.comp.WheelStrategy.WheelHelper", "getEnableComponentListByRandom failed " + e2);
        }
        return arrayList2;
    }

    public static void a(Context context, List list) {
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "wakeupSleepingComponent");
        if (list == null) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sleepingTimes == null");
            return;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 644);
            if (packageInfo == null) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "pi == null");
                return;
            }
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            if (serviceInfoArr == null) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sis == null");
                return;
            }
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                Bundle bundle = serviceInfo.metaData;
                if (bundle != null && bundle.containsKey("wheel")) {
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "found, component = %s", new Object[]{serviceInfo.name});
                    ComponentName componentName = new ComponentName(context.getPackageName(), serviceInfo.name);
                    int i = bundle.getInt("my_service_id", 0);
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "id = " + i);
                    if (i <= 0) {
                        a(componentName, 0L);
                        a(componentName, 0);
                        b(componentName, 0L);
                    } else {
                        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "found, component = %s, id = %d", new Object[]{serviceInfo.name, Integer.valueOf(i)});
                        if (i > list.size()) {
                            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sleepingTimes.size()=" + list.size());
                        } else {
                            long longValue = ((Long) list.get(i - 1)).longValue();
                            if (longValue == 0) {
                                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "time == 0L");
                                b(context, componentName, false);
                            } else {
                                long a2 = a(componentName);
                                if (a2 == 0) {
                                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sleepStartTime == 0L");
                                    b(context, componentName, true);
                                } else if (TimeStamp.instance().getRealLocalTimeV2() - a2 < longValue) {
                                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "continue sleeping");
                                    b(context, componentName, false);
                                } else {
                                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "wake it up");
                                    b(context, componentName, true);
                                }
                            }
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "NameNotFoundException", e2);
        }
    }

    private static void b(ComponentName componentName, long j) {
        String str = "wheel_sleep_start_time_" + componentName.getShortClassName();
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "set " + str + " = " + j);
        c.putLong(str, j);
    }

    private static boolean e(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 1;
        } catch (Exception e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", e2);
            return false;
        }
    }

    public static void c(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", " open " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", e2);
        }
    }

    public static void b(Context context) {
        Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "close all roll component new");
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 644);
            if (packageInfo == null) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "pi == null");
                return;
            }
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            if (serviceInfoArr == null) {
                Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "sis == null");
                return;
            }
            long realLocalTimeV2 = TimeStamp.instance().getRealLocalTimeV2();
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                Bundle bundle = serviceInfo.metaData;
                if (null != bundle && bundle.containsKey("wheel")) {
                    Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "found, component = %s", new Object[]{serviceInfo.name});
                    ComponentName componentName = new ComponentName(context.getPackageName(), serviceInfo.name);
                    b(context, componentName, false);
                    b(componentName, realLocalTimeV2);
                    a(componentName, 0);
                    a(componentName, 0L);
                }
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Logger.i("LVST2.comp.WheelStrategy.WheelHelper", "NameNotFoundException", e2);
        }
    }
}
