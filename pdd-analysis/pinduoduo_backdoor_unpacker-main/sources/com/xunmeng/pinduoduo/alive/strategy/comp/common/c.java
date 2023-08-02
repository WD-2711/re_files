package com.xunmeng.pinduoduo.alive.strategy.comp.common;

import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;

/* loaded from: c.class */
public class c {
    protected String a = "BaseAccountManager";
    private boolean b = true;

    public String b() {
        return null;
    }

    public String a() {
        return null;
    }

    public String c() {
        return null;
    }

    public void a(boolean z) {
        Logger.i(this.a, "set account sync enable:" + z);
        this.b = z;
    }

    public boolean f() {
        return this.b;
    }

    public void e() {
        b.a(a(), b());
    }

    public void d() {
        try {
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            String a = a();
            String b = b();
            String c = c();
            Logger.i(this.a, "start. accountName: %s, accountType: %s, authority: %s", new Object[]{a, b, c});
            b.a(frameworkContext, a, b, c);
            b.a(a, b, c);
        } catch (Exception e) {
            Logger.e(this.a, e);
        }
    }
}
