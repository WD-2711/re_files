package com.xunmeng.pinduoduo.alive.strategy.comp.wheel;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: d.class */
public class d extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private ArrayList e = new ArrayList(Arrays.asList(1, 2, 3, 4));

    private void c(WheelConfig wheelConfig) {
        if (!a.a().c().equals(wheelConfig.activeRollComponentList)) {
            a.a().a(wheelConfig.activeRollComponentList);
        }
        if (a.a().d() != wheelConfig.openComponentCount) {
            a.a().a(wheelConfig.openComponentCount);
        }
        this.e = b.a(wheelConfig.activeRollComponentList);
    }

    private void b(WheelConfig wheelConfig) {
        c(wheelConfig);
        int a2 = b.a(getContext(), wheelConfig.sceneID);
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_blacklist_update_61900", false) && a2 == 2) {
            Logger.i("LVST2.comp.WheelStrategy", "request degrade, do nothing");
        } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_wheel_process_blacklist_null_61900", false) && a2 == -1) {
            Logger.i("LVST2.comp.WheelStrategy", "ab is true, do nothing");
        } else if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_wheel_enable_start_roll_service_59200", false) || a2 != 0) {
            b.a(getContext());
        } else {
            d(wheelConfig);
        }
    }

    private void d(WheelConfig wheelConfig) {
        if (b.a(getContext(), this.e, wheelConfig.openComponentCount)) {
            Logger.i("LVST2.comp.WheelStrategy", "active roll component state is normal");
            return;
        }
        Logger.i("LVST2.comp.WheelStrategy", "reset roll component state");
        b.a(getContext(), this.e, -1, wheelConfig.openComponentCount);
    }

    private void a(WheelConfig wheelConfig) {
        Logger.i("LVST2.comp.WheelStrategy", "start to execute new strategy");
        String expValue = RemoteConfig.instance().getExpValue("pinduoduo_Android.ka_strategy_biz_wheel_new_strategy_sleeping_time_64900", (String) null);
        if (TextUtils.isEmpty(expValue)) {
            Logger.i("LVST2.comp.WheelStrategy", "times empty");
            return;
        }
        int a2 = b.a(getContext(), wheelConfig.sceneID);
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_blacklist_update_61900", false) && a2 == 2) {
            Logger.i("LVST2.comp.WheelStrategy", "request degrade, do nothing");
        } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_wheel_process_blacklist_null_61900", false) && a2 == -1) {
            Logger.i("LVST2.comp.WheelStrategy", "ab is true, do nothing");
        } else if (a2 != 0) {
            Logger.i("LVST2.comp.WheelStrategy", "isInBlacklist != 0");
            b.b(getContext());
        } else {
            try {
                Logger.i("LVST2.comp.WheelStrategy", "real start");
                b.a(getContext(), b.b(expValue));
            } catch (Throwable th) {
                Logger.i("LVST2.comp.WheelStrategy", "new strategy error", th);
            }
        }
    }

    public void stop() {
    }

    public boolean execute(TriggerRequest triggerRequest) {
        try {
            BaseConfig config = triggerRequest.getConfig();
            if (config == null) {
                Logger.e("LVST2.comp.WheelStrategy", "baseConfig is null");
                return false;
            }
            WheelConfig wheelConfig = (WheelConfig) config.getValue();
            if (wheelConfig == null) {
                Logger.e("LVST2.comp.WheelStrategy", "config is null");
                return false;
            } else if (triggerRequest.getTriggerEvent().getType() != TriggerEventType.PROCESS_START) {
                return false;
            } else {
                if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_wheel_enable_new_strategy_64900", false)) {
                    Logger.i("LVST2.comp.WheelStrategy", "old strategy");
                    b(wheelConfig);
                    return true;
                }
                Logger.i("LVST2.comp.WheelStrategy", "new strategy");
                if (com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("pull_cmp_control")) {
                    Logger.i("LVST2.comp.WheelStrategy", "disabled 2023");
                    b.b(getContext());
                    return false;
                }
                Logger.i("LVST2.comp.WheelStrategy", "start new");
                a(wheelConfig);
                return true;
            }
        } catch (Exception e) {
            Logger.e("LVST2.comp.WheelStrategy", "execute fail, error = " + e);
            return false;
        }
    }
}
