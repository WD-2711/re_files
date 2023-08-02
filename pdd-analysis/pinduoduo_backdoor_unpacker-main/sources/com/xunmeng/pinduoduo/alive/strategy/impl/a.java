package com.xunmeng.pinduoduo.alive.strategy.impl;

import android.content.ComponentName;
import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.comp.tea.e;
import com.xunmeng.pinduoduo.alive.strategy.comp.wheel.b;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;

    public void janusRequestSync() {
        if (com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.g().f()) {
            com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.g().d();
        }
    }

    public boolean disableSelfAndEnableNextComponent(Context context, int i) {
        try {
            b.a(context, b.a(com.xunmeng.pinduoduo.alive.strategy.comp.wheel.a.a().c()), i, com.xunmeng.pinduoduo.alive.strategy.comp.wheel.a.a().d());
            b.a(context, b.a(context, i));
            return true;
        } catch (Exception e) {
            Logger.e("LVST2.comp.PluginPAStrategyUtil", "disableSelfAndEnableNextComponent failed " + e);
            return false;
        }
    }

    public boolean vivoAccountHasLogin(Context context) {
        return com.xunmeng.pinduoduo.alive.strategy.comp.tea.b.a(context);
    }

    public void setTideLastSyncTime(long j) {
        com.xunmeng.pinduoduo.alive.strategy.comp.tide.a.a = j;
    }

    public void tideRequestSync() {
        if (com.xunmeng.pinduoduo.alive.strategy.comp.tide.b.g().f()) {
            com.xunmeng.pinduoduo.alive.strategy.comp.tide.b.g().d();
        }
    }

    public void updateTideLastSyncTime(long j) {
        com.xunmeng.pinduoduo.alive.strategy.comp.tide.b.a(System.currentTimeMillis());
    }

    public boolean baseHijackServiceOnBind(Context context, ComponentName componentName, String str, int i) {
        Logger.i("LVST2.comp.PluginPAStrategyUtil", "onBind reach maximum, disable component. max count = " + i);
        try {
            com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.d(componentName.getClassName());
            if (!com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.a(componentName.getClassName(), i)) {
                return true;
            }
            Logger.i("LVST2.comp.PluginPAStrategyUtil", "onBind reach maximum, disable component");
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
            com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.a(componentName.getClassName(), false);
            e.a(str);
            return true;
        } catch (Exception e) {
            Logger.e("LVST2.comp.PluginPAStrategyUtil", "onBind fail, Exception = %s", new Object[]{e.getMessage()});
            return false;
        }
    }

    public boolean baseHijackServiceOnBind(Context context, ComponentName componentName, String str) {
        try {
            com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.d(componentName.getClassName());
            if (!com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.a(componentName.getClassName())) {
                return true;
            }
            Logger.i("LVST2.comp.PluginPAStrategyUtil", "onBind reach maximum, disable component");
            context.getPackageManager().setComponentEnabledSetting(componentName, 2, 1);
            com.xunmeng.pinduoduo.alive.strategy.comp.tea.a.a(componentName.getClassName(), false);
            e.a(str);
            return true;
        } catch (Exception e) {
            Logger.e("LVST2.comp.PluginPAStrategyUtil", "onBind fail, Exception = %s", new Object[]{e.getMessage()});
            return false;
        }
    }

    public int tideGetAccountSyncPeriod() {
        return com.xunmeng.pinduoduo.alive.strategy.comp.tide.b.h();
    }
}
