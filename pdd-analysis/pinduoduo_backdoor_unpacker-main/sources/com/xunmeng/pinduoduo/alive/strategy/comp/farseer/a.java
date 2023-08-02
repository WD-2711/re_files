package com.xunmeng.pinduoduo.alive.strategy.comp.farseer;

import android.os.Build;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;

/* loaded from: a.class */
public class a extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;

    private void a(FarSeerConfig farSeerConfig) {
        Logger.i("LVST2.comp.FarSeer", "process start");
        if (!b.a()) {
            Logger.i("LVST2.comp.FarSeer", "unable start");
            return;
        }
        if (farSeerConfig != null) {
            if (AliveAbility.instance().isAbilityDisabled() || !RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_farseer_period_opt_60400", false)) {
                b.a = farSeerConfig.jobSchedulePeriodInSec;
                b.b = farSeerConfig.jobReschedulePeriodInSec;
            } else {
                b.a = farSeerConfig.optSchedulePeriodInSec;
                b.b = farSeerConfig.optReschedulePeriodInSec;
            }
            Logger.i("LVST2.comp.FarSeer", "jobSchedulePeriodInSec:%s, jobReschedulePeriodInSec:%s", new Object[]{Integer.valueOf(b.a), Integer.valueOf(b.b)});
        }
        b.b();
    }

    public void stop() {
    }

    public boolean execute(TriggerRequest triggerRequest) {
        if (Build.VERSION.SDK_INT < 21) {
            Logger.i("LVST2.comp.FarSeer", "unable start under LOLLIPOP");
            return false;
        } else if (triggerRequest.getTriggerEvent().getType() != TriggerEventType.PROCESS_START) {
            return false;
        } else {
            BaseConfig config = triggerRequest.getConfig();
            if (config == null) {
                Logger.e("LVST2.comp.FarSeer", "baseConfig is null");
                return false;
            }
            a((FarSeerConfig) config.getValue());
            return true;
        }
    }
}
