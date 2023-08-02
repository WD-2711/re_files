package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import java.util.Set;

/* loaded from: i.class */
public class i {
    private static final String a = null;
    private static final String b = null;
    private static final i c = new i();
    private static final String d = null;
    private static IMMKV e;

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.Provider.ProviderCacheUtils", "skip check ab for null");
            return false;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "true"));
    }

    private static IMMKV b() {
        if (e != null) {
            return e;
        }
        e = MMKVCompat.module("provider_cache", false);
        return e;
    }

    public void a(String str, Boolean bool) {
        d(str);
        b().putBoolean(str, bool.booleanValue());
    }

    public Boolean a(String str) {
        if (!c(str)) {
            return null;
        }
        return Boolean.valueOf(b().getBoolean(str));
    }

    private boolean c(String str) {
        return b().getStringSet("provider_scene_set").contains(str);
    }

    private void d(String str) {
        Set stringSet = b().getStringSet("provider_scene_set");
        stringSet.add(str);
        b().putStringSet("provider_scene_set", stringSet);
    }

    public static i a() {
        return c;
    }
}
