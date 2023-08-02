package com.xunmeng.pinduoduo.alive.strategy.comp.janus;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.BaseTriggerEvent;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;

/* loaded from: b.class */
public class b extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;

    private void a() {
        if (!CommonHelper.instance().canAutoStart(StrategyFramework.getFrameworkContext())) {
            Logger.i("LVST2.comp.JanusStrategy", "skip ManualAccountSync since no auto start permission");
        } else if (com.xunmeng.pinduoduo.alive.strategy.comp.common.a.a()) {
        } else {
            if (!com.xunmeng.pinduoduo.alive.strategy.comp.common.a.d()) {
                a.g().d();
                return;
            }
            Logger.i("LVST2.comp.JanusStrategy", "user skip");
            com.xunmeng.pinduoduo.alive.strategy.comp.common.a.b(false);
        }
    }

    public void stop() {
    }

    public boolean execute(TriggerRequest triggerRequest) {
        BaseTriggerEvent triggerEvent = triggerRequest.getTriggerEvent();
        Logger.i("LVST2.comp.JanusStrategy", "execute event: %s", new Object[]{triggerEvent.getType().name});
        if (triggerEvent.getType() == TriggerEventType.PROCESS_START) {
            a();
            return true;
        } else if (triggerEvent.getType() != TriggerEventType.IRREGULAR_PROCESS_START) {
            return false;
        } else {
            b();
            return true;
        }
    }

    private void b() {
        com.xunmeng.pinduoduo.alive.strategy.comp.common.a.b(false);
        a.g().e();
    }
}
