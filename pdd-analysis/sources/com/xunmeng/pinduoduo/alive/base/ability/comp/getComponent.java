package com.xunmeng.pinduoduo.alive.base.ability.comp;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.ObjectCreator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.comp.b */
/* loaded from: b.class */
public class getComponent {
    private static final String a = null;
    private static final Map[] b = {a.a(), com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.a(), com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.a(), com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.c.a()};
    // 可以看作c=b，它是一个map数组
    private static final Map c = changeComponentMap();
    private static final Map d = new ConcurrentHashMap();

    public static Set a() {
        return c.keySet();
    }

    private getComponent() {
    }

    /* renamed from: a */
    public static Object mainFunction(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.e("LVBA.Plugin.ComponentContainer", "input component name is null");
            return null;
        }
        if (!d.containsKey(str)) { // 若没有缓存此组件实例，则创建一个，且返回该组件实例
            ObjectCreator objectCreator = (ObjectCreator) c.get(str);
            if (objectCreator == null) {
                Logger.w("LVBA.Plugin.ComponentContainer", "creator not exist: " + str);
                return null;
            }
            d.put(str, objectCreator.createObject());
        } else {
            Logger.i("LVBA.Plugin.ComponentContainer", "instance exist: " + str);
        }
        return d.get(str); // 返回之前缓存过的组件实例
    }

    /* renamed from: b */
    private static Map changeComponentMap() {
        Logger.i("LVBA.Plugin.ComponentContainer", "creatorMapList length: %d", new Object[]{Integer.valueOf(b.length)});
        HashMap hashMap = new HashMap();
        for (int i = 0; i < b.length; i++) {
            Map map = b[i];
            if (map == null) {
                throw new RuntimeException("null creatorMap " + i);
            }
            Logger.i("LVBA.Plugin.ComponentContainer", "creatorMap %d keys: %d, %s", new Object[]{Integer.valueOf(i), Integer.valueOf(map.size()), Arrays.toString(map.keySet().toArray())});
            int size = hashMap.size();
            hashMap.putAll(map);
            Logger.i("LVBA.Plugin.ComponentContainer", "resultMap size: %d, resultSizeBeforeAdd: %d", new Object[]{Integer.valueOf(hashMap.size()), Integer.valueOf(size)});
            if (hashMap.size() != size + map.size()) {
                throw new RuntimeException("duplicate component name found");
            }
        }
        Logger.i("LVBA.Plugin.ComponentContainer", "creator map result: %d, %s", new Object[]{Integer.valueOf(hashMap.size()), Arrays.toString(hashMap.keySet().toArray())});
        return hashMap;
    }
}
