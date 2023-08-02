package com.xunmeng.pinduoduo.alive.strategy.comp;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.comp.raptor.d;
import com.xunmeng.pinduoduo.alive.strategy.comp.summer.b;
import com.xunmeng.pinduoduo.alive.strategy.comp.tea.f;
import com.xunmeng.pinduoduo.alive.strategy.comp.tide.c;
import java.util.HashMap;
import java.util.Map;

/* loaded from: a.class */
public final class a {
    private a() {
    }

    public static Map a() {
        HashMap hashMap = new HashMap();
        hashMap.put("SummerStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.1
            public Object createObject() {
                return new b();
            }
        });
        hashMap.put("JanusStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.2
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.strategy.comp.janus.b();
            }
        });
        hashMap.put("McGradyStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.3
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.strategy.comp.mcgrady.b();
            }
        });
        hashMap.put("TideStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.4
            public Object createObject() {
                return new c();
            }
        });
        hashMap.put("TeaStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.5
            public Object createObject() {
                return new f();
            }
        });
        hashMap.put("FarSeerStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.6
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.strategy.comp.farseer.a();
            }
        });
        hashMap.put("RaptorStrategyV3", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.7
            public Object createObject() {
                return new d();
            }
        });
        hashMap.put("WheelStrategyV2", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.a.8
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.strategy.comp.wheel.d();
            }
        });
        return hashMap;
    }
}