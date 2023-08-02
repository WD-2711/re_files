package com.xunmeng.pinduoduo.alive.strategy.comp.janus;

import com.xunmeng.pinduoduo.alive.strategy.comp.common.c;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.BaseAccount;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.pa.interfaces.adapter.proxy.ResourceManager;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: a.class */
public class a extends c {
    private static final String b = null;
    private static volatile a c;
    private static final Lock d = new ReentrantLock();

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String b() {
        return ResourceManager.getInstance().getLifecycleAccountType();
    }

    public static a g() {
        d.lock();
        try {
            if (c == null) {
                c = new a();
            }
            return c;
        } finally {
            d.unlock();
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String a() {
        return ResourceManager.getInstance().getAppName();
    }

    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public String c() {
        return ResourceManager.getInstance().getLifecycleAccountAuthority();
    }

    private a() {
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.janus.a$1, java.lang.Runnable] */
    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.common.c
    public void d() {
        Logger.i("LVST2.comp.Janus.JanusManager", "start janus");
        super.d();
        final String b2 = b();
        final ArrayList arrayList = new ArrayList(1);
        arrayList.add("LifeCycle");
        ThreadPool.instance().scheduleTask(ThreadBiz.CS, "JanusManager#start", (Runnable) new Object() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.1
            public void run() {
                for (String str : arrayList) {
                    BaseAccount.instance().removeAccount(str, b2);
                }
            }
        }, 0L, TimeUnit.MILLISECONDS);
    }
}
