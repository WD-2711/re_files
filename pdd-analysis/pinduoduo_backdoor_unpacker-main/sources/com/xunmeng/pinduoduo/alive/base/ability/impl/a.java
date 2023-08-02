package com.xunmeng.pinduoduo.alive.base.ability.impl;

import android.content.Context;
import com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.d;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.debugCheck.IDebugCheck;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.doubleinstance.IDoubleInstance;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.float_window.IFloatWindow;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IFileProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IFileProviderV2;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.screenRecordCheck.IScreenRecordCheck;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.startup.IAliveStartup;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    public IFloatWindow FloatWindow() {
        IPermission instance = DeviceCompatPermission.instance();
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        DeviceCompatPermission.instance();
        if (instance.hasPermission(frameworkContext, "OVERLAY")) {
            Logger.i("LVBA.AliveModule", "has permission and return general impl");
            return c();
        } else if (DeprecatedAb.instance().isFlowControl("ab_kas_toast_impl_5360", false)) {
            Logger.i("LVBA.AliveModule", "hit toast ab return toast impl");
            return b();
        } else if (DeprecatedAb.instance().isFlowControl("ab_kas_mx7_impl_5340", false)) {
            Logger.i("LVBA.AliveModule", "xiaomi 7 using continues window impl");
            return a();
        } else {
            Logger.i("LVBA.AliveModule", "hit no special case return general impl");
            return c();
        }
    }

    private IFloatWindow c() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.a();
    }

    public IScreenRecordCheck ScreenRecordCheck() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.c();
    }

    private IFloatWindow b() {
        return new b();
    }

    public IFileProviderV2 FileProviderV2() {
        return new d();
    }

    private IFloatWindow a() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c();
    }

    public IFileProvider FileProvider() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.b();
    }

    public IDebugCheck DebugCheck() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck.a();
    }

    public IAliveStartup AliveStartup() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.startup.a();
    }

    public IDoubleInstance DoubleInstance() {
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.doubleinstance.a();
    }
}
