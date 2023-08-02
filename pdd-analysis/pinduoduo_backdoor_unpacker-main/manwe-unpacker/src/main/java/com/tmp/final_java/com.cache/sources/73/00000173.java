package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin;

import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen.f;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.d;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public final class b {
    private b() {
    }

    public static Map a() {
        HashMap hashMap = new HashMap();
        hashMap.put("VarusCommonAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.1
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.varus.b();
            }
        });
        hashMap.put("RumbleProxyActivity", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.5
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.a();
            }
        });
        hashMap.put("RyzeProxyActivity", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.6
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze.b();
            }
        });
        hashMap.put("VarusAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.7
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.varus.a();
            }
        });
        hashMap.put("RyzePopupAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.8
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze.a();
            }
        });
        hashMap.put("RivanStartShortcutAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.9
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan.c();
            }
        });
        hashMap.put("SionAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.10
            public Object createObject() {
                return new d();
            }
        });
        hashMap.put("RumbleStartAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.11
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.b();
            }
        });
        hashMap.put("TeemoAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.12
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.widget.a();
            }
        });
        hashMap.put("SystemDoubleOpen", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.2
            public Object createObject() {
                return new f();
            }
        });
        hashMap.put("RivanStartSmartShortcutAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.3
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan.d();
            }
        });
        hashMap.put("JumpBoxAbility", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.4
            public Object createObject() {
                return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.a();
            }
        });
        return hashMap;
    }
}