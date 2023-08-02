package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import android.content.ComponentName;
import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.message.Message0;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;

/* loaded from: d.class */
public class d implements a.AnonymousClass1 {
    public static final String a = null;
    private static final String b = null;

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.tea.d$1, java.lang.Runnable] */
    public void onReceive(final Message0 message0) {
        ThreadPool.instance().computeTask(ThreadBiz.CS, "TeaMessageReceiver#onReceive", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.tea.d.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                try {
                    Logger.i("LVST2.comp.TeaStrategy.TeaMessageReceiver", "receive message = " + JSONFormatUtils.instance().toJson(message0));
                    if (message0.name == null || !message0.name.equals("ANT_ONLINE_STATE_CHANGED") || message0.payload == null) {
                        Logger.e("LVST2.comp.TeaStrategy.TeaMessageReceiver", "error message");
                    } else if (!message0.payload.getBoolean("online")) {
                        Logger.i("LVST2.comp.TeaStrategy.TeaMessageReceiver", "titan offline");
                    } else {
                        a.c();
                        Context frameworkContext = StrategyFramework.getFrameworkContext();
                        b.c(frameworkContext, new ComponentName(frameworkContext.getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDProfileServiceII"));
                    }
                } catch (Exception e) {
                    Logger.e("LVST2.comp.TeaStrategy.TeaMessageReceiver", "fail to process message, exception = " + e);
                }
            }
        });
    }
}
