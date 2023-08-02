package com.xunmeng.pinduoduo.alive.strategy.comp.raptor;

import android.content.Context;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.biz.pa.MediaServiceData;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: d.class */
public class d extends BaseStrategy implements a.AnonymousClass1 {
    private static final String a = null;
    private static AtomicBoolean b = new AtomicBoolean(false);

    private boolean b(Context context) {
        boolean localBlacklistActive = MediaServiceData.instance().getLocalBlacklistActive();
        if (b.a().b() == localBlacklistActive) {
            Logger.i("LVST2.comp.RaptorStrategy", "already report current state, just return");
            return localBlacklistActive;
        }
        if (localBlacklistActive) {
            c.a("local_blacklist_active", MediaServiceData.instance().getLocalBlacklistActiveTime());
        } else {
            c.a("local_blacklist_inactive");
        }
        b.a().a(localBlacklistActive);
        return localBlacklistActive;
    }

    public static int a(Context context, String str) {
        if ("skip_blacklist".equals(str)) {
            Logger.i("LVST2.comp.RaptorStrategy", "skip blacklist check");
            return 0;
        }
        int i = -1;
        SceneRequest sceneRequest = new SceneRequest(str);
        sceneRequest.setOutputDegradeInfo(true);
        BlackListItem config = MSCManager.instance().getConfig(context, sceneRequest);
        if (config != null) {
            i = (config.getDegrade() == null || !config.getDegrade().booleanValue()) ? config.isBlack() ? 1 : 0 : 2;
        }
        Logger.i("LVST2.comp.RaptorStrategy", "sceneId = " + str + "; blacklist result = " + i);
        return i;
    }

    private void b(Context context, RaptorConfig raptorConfig) {
        Logger.i("LVST2.comp.RaptorStrategy", "init params");
        if (MediaServiceData.instance().getMinTimeInterval() != raptorConfig.minTimeInterval * 0) {
            Logger.i("LVST2.comp.RaptorStrategy", "set minTimeInterval = " + (raptorConfig.minTimeInterval * 0));
            MediaServiceData.instance().setMinTimeInterval(raptorConfig.minTimeInterval * 0);
        }
        if (MediaServiceData.instance().getProcessStartCountLimit() != raptorConfig.processStartCountLimit) {
            Logger.i("LVST2.comp.RaptorStrategy", "set processStartCountLimit = " + raptorConfig.processStartCountLimit);
            MediaServiceData.instance().setProcessStartCountLimit(raptorConfig.processStartCountLimit);
        }
        if (MediaServiceData.instance().getOpenRecentTaskValidDuration() != raptorConfig.openRecentTaskValidDuration * 0) {
            Logger.i("LVST2.comp.RaptorStrategy", "set openRecentTaskValidDuration = " + (raptorConfig.openRecentTaskValidDuration * 0));
            MediaServiceData.instance().setOpenRecentTaskValidDuration(raptorConfig.openRecentTaskValidDuration * 0);
        }
        boolean d = a.d();
        if (MediaServiceData.instance().getAbKeyEnableRecentTaskOptimization() != d) {
            Logger.i("LVST2.comp.RaptorStrategy", "set enableRecentTaskOptimization = " + d);
            MediaServiceData.instance().setAbKeyEnableRecentTaskOptimization(d);
        }
        boolean e = a.e();
        if (MediaServiceData.instance().getAbKeyEnableSetPullTimeByConfig() != e) {
            Logger.i("LVST2.comp.RaptorStrategy", "set enableSetPullTimeByConfig = " + e);
            MediaServiceData.instance().setAbKeyEnableSetPullTimeByConfig(e);
        }
        boolean f = a.f();
        if (MediaServiceData.instance().getAbKeyEnableUseNewCode() != f) {
            Logger.i("LVST2.comp.RaptorStrategy", "set enableUseNewCode = " + f);
            MediaServiceData.instance().setAbKeyEnableUseNewCode(f);
        }
        if (!MediaServiceData.instance().getPullTitanTimeList().equals(raptorConfig.pullTitanTimeList)) {
            Logger.i("LVST2.comp.RaptorStrategy", "set pullTitanTimeList = " + raptorConfig.pullTitanTimeList);
            MediaServiceData.instance().setPullTitanTimeList(raptorConfig.pullTitanTimeList);
        }
        c(context, raptorConfig);
    }

    private boolean a(Context context, RaptorConfig raptorConfig) {
        if (!a.c()) {
            Logger.i("LVST2.comp.RaptorStrategy", "AB is false, no need to check blacklist");
            return false;
        }
        BlackListItem config = MSCManager.instance().getConfig(context, new SceneRequest(raptorConfig.blacklistSceneId));
        if (null == config) {
            Logger.i("LVST2.comp.RaptorStrategy", "blacklist config is null, return");
            return true;
        }
        boolean isBlack = config.isBlack();
        Logger.i("LVST2.comp.RaptorStrategy", "isBlack = " + isBlack);
        return isBlack;
    }

    public void stop() {
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.raptor.d$1, java.lang.Runnable] */
    public boolean execute(final TriggerRequest triggerRequest) {
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_raptor_async_execute_62400", false)) {
            ThreadPool.instance().computeTask(ThreadBiz.CS, "RaptorStrategy#executeInner", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.raptor.d.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    try {
                        d.this.a(triggerRequest);
                    } catch (Exception e) {
                        Logger.e("LVST2.comp.RaptorStrategy", "RaptorStrategy#executeInner fail, exception = " + e.getMessage());
                    }
                }
            });
            return true;
        }
        a(triggerRequest);
        return true;
    }

    public void a(TriggerRequest triggerRequest) {
        BaseConfig config = triggerRequest.getConfig();
        if (config == null) {
            Logger.e("LVST2.comp.RaptorStrategy", "baseConfig is null");
            return;
        }
        RaptorConfig raptorConfig = (RaptorConfig) config.getValue();
        if (raptorConfig == null) {
            Logger.e("LVST2.comp.RaptorStrategy", "config is null");
        } else if (!b.compareAndSet(false, true)) {
            Logger.i("LVST2.comp.RaptorStrategy", "busy handling, return");
        } else {
            try {
                Logger.i("LVST2.comp.RaptorStrategy", "start to execute event");
                Context context = getContext();
                if (triggerRequest.getTriggerEvent().getType() == TriggerEventType.IRREGULAR_PROCESS_START) {
                    Logger.i("LVST2.comp.RaptorStrategy", "we have IRREGULAR_PROCESS_START event, need to disable media service");
                    e.b(getContext());
                    b.set(false);
                    return;
                }
                if (AliveAbility.instance().isAbilityDisabled()) {
                    Logger.i("LVST2.comp.RaptorStrategy", "not allow to optimize, use old time list");
                    raptorConfig.pullTitanTimeList = "7,12,18,23,0";
                }
                b(context, raptorConfig);
                if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_blacklist_update_61900", false)) {
                    if (!a.b() || a(context, raptorConfig) || b(context)) {
                        Logger.i("LVST2.comp.RaptorStrategy", "ready to disable media service");
                        e.b(getContext());
                    } else {
                        Logger.i("LVST2.comp.RaptorStrategy", "ready to enable media service");
                        e.a(getContext());
                    }
                    b.set(false);
                    return;
                }
                Logger.i("LVST2.comp.RaptorStrategy", "use new code");
                if (!a.b() || b(context)) {
                    Logger.i("LVST2.comp.RaptorStrategy", "ready to disable media service");
                    e.b(getContext());
                    b.set(false);
                    return;
                }
                int a2 = a(context, raptorConfig.blacklistSceneId);
                if (a2 == 2) {
                    Logger.i("LVST2.comp.RaptorStrategy", "request degrade, do nothing");
                    b.set(false);
                } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_comp_raptor_process_blacklist_null_61900", false) && a2 == -1) {
                    Logger.i("LVST2.comp.RaptorStrategy", "ab is true, do nothing");
                    b.set(false);
                } else if (a2 != 0) {
                    Logger.i("LVST2.comp.RaptorStrategy", "hit blacklist, ready to disable media service");
                    e.b(getContext());
                    b.set(false);
                } else {
                    Logger.i("LVST2.comp.RaptorStrategy", "ready to enable media service");
                    e.a(getContext());
                    b.set(false);
                }
            } catch (Exception e) {
                Logger.e("LVST2.comp.RaptorStrategy", "failed to execute: %s", new Object[]{e.getMessage()});
                b.set(false);
            }
        }
    }

    public static void a(Context context) {
        try {
            if (!a.h()) {
                Logger.i("LVST2.comp.RaptorStrategy", "ab is false, not allow to report");
                return;
            }
            String recentTenPullTitanTime = MediaServiceData.instance().getRecentTenPullTitanTime();
            Logger.i("LVST2.comp.RaptorStrategy", "recentTenPullTitanTime = " + recentTenPullTitanTime);
            if (TextUtils.isEmpty(recentTenPullTitanTime)) {
                Logger.i("LVST2.comp.RaptorStrategy", "recentTenPullTitanTime is empty");
                return;
            }
            String c = b.a().c();
            Logger.i("LVST2.comp.RaptorStrategy", "lastReportPullTitanTimeValue = " + c);
            if (!TextUtils.isEmpty(c) && c.equals(recentTenPullTitanTime)) {
                Logger.i("LVST2.comp.RaptorStrategy", "already report");
                return;
            }
            c.a("pull_titan_time", recentTenPullTitanTime);
            b.a().a(recentTenPullTitanTime);
        } catch (Exception e) {
            Logger.e("LVST2.comp.RaptorStrategy", "tryToReportPullTitanMsg fail, exception = " + e);
        }
    }

    private void c(Context context, RaptorConfig raptorConfig) {
        long localBlacklistActiveTime = MediaServiceData.instance().getLocalBlacklistActiveTime();
        Logger.i("LVST2.comp.RaptorStrategy", "localBlacklistActiveTime = " + localBlacklistActiveTime);
        Logger.i("LVST2.comp.RaptorStrategy", "psRecentTenPidRecord = " + MediaServiceData.instance().getRecentTenProcessPid());
        Logger.i("LVST2.comp.RaptorStrategy", "psRecentTenProcessStartTime = " + MediaServiceData.instance().getRecentTenProcessStartTime());
        Logger.i("LVST2.comp.RaptorStrategy", "recentTenOpenRecentTaskTime = " + MediaServiceData.instance().getRecentTenOpenRecentTaskTime());
        Logger.i("LVST2.comp.RaptorStrategy", "psRecentTenIgnoredProcessStartRecord = " + MediaServiceData.instance().getRecentTenIgnoredProcessStartRecord());
        if (System.currentTimeMillis() - localBlacklistActiveTime > raptorConfig.localBlacklistActiveDuration * 0 * 0 * 0 * 0) {
            Logger.i("LVST2.comp.RaptorStrategy", "local blacklist is invalid");
            MediaServiceData.instance().setLocalBlacklistActive(false);
        }
        a(context);
    }
}
