package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.debugCheck.IDebugDetectTransmission;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import java.util.Map;

/* loaded from: c.class */
public class c implements a.AnonymousClass1 {
    static final String a = null;
    private final IDebugDetectTransmission b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;

    public void onReceive(Context context, Intent intent) {
        if (null == intent || !TextUtils.equals(this.e, intent.getAction())) {
            Logger.d("LogStatusReceiver", "receive invalid action ! ");
            return;
        }
        String stringExtra = intent.getStringExtra(this.d);
        if (TextUtils.isEmpty(stringExtra)) {
            Logger.d("LogStatusReceiver", "system debug status is null ?!");
            return;
        }
        this.b.send(this.f, TextUtils.equals(stringExtra, this.c));
        f.a(this.f, stringExtra);
        Logger.d("LogStatusReceiver", "system debug status : %s !", new Object[]{stringExtra});
    }

    public c(Map map, IDebugDetectTransmission iDebugDetectTransmission) {
        this.b = iDebugDetectTransmission;
        this.c = (String) map.get("openStatus");
        this.d = (String) map.get("statusKey");
        this.e = (String) map.get("action");
        this.f = TextUtils.isEmpty((CharSequence) map.get("logType")) ? "apLog" : (String) map.get("logType");
    }
}
