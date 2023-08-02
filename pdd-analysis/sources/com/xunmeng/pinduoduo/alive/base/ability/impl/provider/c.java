package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.content.Intent;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaAbility;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaResult;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import java.util.UUID;

/* loaded from: c.class */
public class c {
    public static boolean a(String str, Intent intent) {
        SonaResult start = SonaAbility.getInstance().start(new SonaRequest(intent, str, UUID.randomUUID().toString()));
        Logger.i("LVBA.AliveModule", "sona result : " + start.toString());
        return start.isSuccess();
    }

    public static boolean a(String str) {
        return SonaAbility.getInstance().isSupport(str);
    }
}
