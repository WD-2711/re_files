package com.xunmeng.pinduoduo.alive.strategy.comp.mcgrady;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.BaseTriggerEvent;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: b.class */
public class b extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;

    public boolean a() {
        boolean z = RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_ab_mcgrady_enable_5710", false);
        boolean isHtj = CommonHelper.instance().isHtj();
        Logger.i("LVST2.comp.McGradyStrategy", "enabled:" + z + " test:" + isHtj);
        return z || isHtj;
    }

    private boolean d(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.McGradyStrategy", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 2;
        } catch (Exception e) {
            Logger.e("LVST2.comp.McGradyStrategy", e);
            return false;
        }
    }

    public void a(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.McGradyStrategy", " close " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
        } catch (Exception e) {
            Logger.e("LVST2.comp.McGradyStrategy", e);
        }
    }

    private boolean c(Context context, ComponentName componentName) {
        try {
            int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(componentName);
            Logger.i("LVST2.comp.McGradyStrategy", componentName + " enabled = " + componentEnabledSetting);
            return componentEnabledSetting == 1;
        } catch (Exception e) {
            Logger.e("LVST2.comp.McGradyStrategy", e);
            return false;
        }
    }

    private boolean c() {
        Context context = getContext();
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new Intent("com.huawei.android.powerkit.PowerKitService"), 0);
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            Logger.i("LVST2.comp.McGradyStrategy", "not implements");
        } else {
            for (ResolveInfo resolveInfo : queryIntentServices) {
                if (!resolveInfo.serviceInfo.packageName.equals(context.getPackageName())) {
                    Logger.i("LVST2.comp.McGradyStrategy", "find other components:" + resolveInfo);
                    HashMap hashMap = new HashMap();
                    hashMap.put("pkgName", resolveInfo.serviceInfo.packageName);
                    hashMap.put("cmpName", String.valueOf(resolveInfo));
                    a("findOtherComponent", hashMap);
                    return true;
                }
                Logger.i("LVST2.comp.McGradyStrategy", "find myself :" + resolveInfo);
            }
        }
        a("notFindOther", new HashMap());
        return false;
    }

    public void b(Context context, ComponentName componentName) {
        try {
            Logger.i("LVST2.comp.McGradyStrategy", " open " + componentName);
            context.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
        } catch (Exception e) {
            Logger.e("LVST2.comp.McGradyStrategy", e);
        }
    }

    public void stop() {
    }

    private void a(String str, Map map) {
        if (a.a().b().getBoolean(str, false)) {
            return;
        }
        Logger.i("LVST2.comp.McGradyStrategy", "report data:" + str);
        StrategyFramework.trackError("McGradyStrategy", new TrackErrorOption(15002, 30069, str, map, (Integer) null));
        a.a().b().putBoolean(str, true);
    }

    public void b() {
        Logger.i("LVST2.comp.McGradyStrategy", "detect work");
        Context context = getContext();
        ComponentName componentName = new ComponentName(context.getPackageName(), "com.xunmeng.pinduoduo.alive.strategy.biz.mcgrady.McGradyPeService");
        boolean a2 = a();
        if (c() || !a2) {
            if (d(context, componentName)) {
                return;
            }
            Logger.i("LVST2.comp.McGradyStrategy", "disable component");
            a(context, componentName);
        } else if (c(context, componentName)) {
        } else {
            Logger.i("LVST2.comp.McGradyStrategy", "enable component");
            b(context, componentName);
        }
    }

    public boolean execute(TriggerRequest triggerRequest) {
        BaseTriggerEvent triggerEvent = triggerRequest.getTriggerEvent();
        if (triggerEvent.getType() == TriggerEventType.PROCESS_START) {
            Logger.i("LVST2.comp.McGradyStrategy", "work on :" + triggerEvent.getType());
            b();
            return true;
        }
        return false;
    }
}
