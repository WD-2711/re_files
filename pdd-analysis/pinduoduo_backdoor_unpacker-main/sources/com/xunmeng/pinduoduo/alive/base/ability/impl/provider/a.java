package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.content.Intent;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.api.AliveSionAbility;
import java.util.UUID;

/* loaded from: a.class */
public class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str, Intent intent) {
        return AliveSionAbility.instance().start(new SionRequest(intent, str, UUID.randomUUID().toString())).isSuccess();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        return AliveSionAbility.instance().isSupport(str);
    }
}
