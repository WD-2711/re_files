package com.xunmeng.pinduoduo.alive.strategy.comp.tide;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.BaseTriggerEvent;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;

/* loaded from: c.class */
public class c extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;
    private static String b;
    private static long c;
    private static long d;

    private void a(TideConfig tideConfig) {
        b = tideConfig.blacklistScene;
        c = tideConfig.refreshTTLMills;
        d = tideConfig.invalidTTLMills;
        Logger.i("LVST2.comp.TideStrategy", "init config: blacklistScene: %s, refreshTTLMills: %s, invalidTTLMills: %s", new Object[]{b, Long.valueOf(c), Long.valueOf(d)});
    }

    private void a() {
        com.xunmeng.pinduoduo.alive.strategy.comp.common.a.a(false);
        b.g().e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(TideConfig tideConfig) {
        if (!CommonHelper.instance().canAutoStart(StrategyFramework.getFrameworkContext())) {
            Logger.i("LVST2.comp.TideStrategy", "skip ManualAccountSync since no auto start permission");
        } else if (com.xunmeng.pinduoduo.alive.strategy.comp.common.a.a()) {
        } else {
            if (com.xunmeng.pinduoduo.alive.strategy.comp.common.a.c()) {
                Logger.i("LVST2.comp.TideStrategy", "user skip");
                return;
            }
            BlackListItem config = MSCManager.instance().getConfig(getContext(), new SceneRequest(b, Long.valueOf(c), Long.valueOf(d), (String) null, (String) null));
            if (config != null && config.isBlack() && !CommonHelper.instance().isHtj()) {
                Logger.i("LVST2.comp.TideStrategy", "account sync hit black list");
                return;
            }
            b.a(tideConfig);
            if (RomOsUtil.instance().isVivoManufacture() && !b.i()) {
                Logger.i("LVST2.comp.TideStrategy", "VivoManufacture in cd");
                return;
            }
            b.g().d();
            if (!a.a()) {
                return;
            }
            Logger.i("LVST2.comp.TideStrategy", "register update sync job receive");
            BroadcastReceiver vMDynamicReceiver = new VMDynamicReceiver(new a(), "NewTideReceiver");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            StrategyFramework.getFrameworkContext().registerReceiver(vMDynamicReceiver, intentFilter);
        }
    }

    public void stop() {
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.alive.strategy.comp.tide.c$1, java.lang.Runnable] */
    public boolean execute(TriggerRequest triggerRequest) {
        BaseTriggerEvent triggerEvent = triggerRequest.getTriggerEvent();
        Logger.i("LVST2.comp.TideStrategy", "execute event: %s", new Object[]{triggerEvent.getType().name});
        final BaseConfig config = triggerRequest.getConfig();
        if (config == null) {
            Logger.e("LVST2.comp.TideStrategy", "baseConfig is null");
            return false;
        }
        a((TideConfig) config.getValue());
        if (triggerEvent.getType() != TriggerEventType.PROCESS_START) {
            if (triggerEvent.getType() != TriggerEventType.IRREGULAR_PROCESS_START) {
                return false;
            }
            a();
            return true;
        } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_tide_async_execute_62400", false)) {
            ThreadPool.instance().computeTask(ThreadBiz.CS, "TideStrategy#onProcessStart", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.tide.c.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    try {
                        c.this.b((TideConfig) config.getValue());
                    } catch (Exception e) {
                        Logger.e("LVST2.comp.TideStrategy", "TideStrategy#onProcessStart fail, exception = " + e.getMessage());
                    }
                }
            });
            return true;
        } else {
            b((TideConfig) config.getValue());
            return true;
        }
    }
}
