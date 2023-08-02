package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck.b */
/* loaded from: b.class */
public class JsonUtils {
    /* renamed from: a */
    public static Map parseJsonToMap(String str) {
        if (!TextUtils.isEmpty(str)) {
            return (HashMap) JSONFormatUtils.instance().fromJson(str, HashMap.class);
        }
        return null;
    }
}