package com.xunmeng.pinduoduo.alive.unify.ability.buildin;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public final class b {
    private b() {
    }

    public static Map a() {
        HashMap hashMap = new HashMap();
        hashMap.put("PowerSaveModeAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.1
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.a();
            }
        });
        hashMap.put("KayleAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.2
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.unify.ability.buildin.kayle.a();
            }
        });
        hashMap.put("PowerSuperSaveModeAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.3
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.unify.ability.buildin.powersave.c();
            }
        });
        hashMap.put("KarmaAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.4
            public Object createObject() {
                return new com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.a();
            }
        });
        return hashMap;
    }
}