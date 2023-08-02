package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import android.content.Context;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;

/* loaded from: e.class */
public class e {
    static String a = "LVUA.Buildin.StepsInfoGainer";
    protected final Context b = StrategyFramework.getFrameworkContext();

    public KarmaResult b() {
        return new KarmaResult("default_result");
    }

    public boolean a() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(String str) {
        try {
            return this.b.getPackageManager().getPackageInfo(str, 0) != null;
        } catch (Exception e) {
            Logger.i(a, "package : %s not found", new Object[]{str});
            return false;
        }
    }
}
