package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public class b {
    public static Map a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (HashMap) JSONFormatUtils.instance().fromJson(str, HashMap.class);
        }
        return null;
    }
}
