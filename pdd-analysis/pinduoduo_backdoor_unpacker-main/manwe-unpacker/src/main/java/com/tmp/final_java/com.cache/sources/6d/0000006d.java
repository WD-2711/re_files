package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.PddHandler;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ResourceHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e */
/* loaded from: e.class */
public class FloatViewHelper {
    static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static View d;
    private static volatile WindowManager e;
    private static final AtomicBoolean f = new AtomicBoolean(false);
    private static final Runnable g = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e.1
        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            FloatViewHelper.c();
        }
    };

    /* renamed from: a */
    public static boolean canStartBgActivityByFloatView() {
        boolean z = RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_by_float_view_61400", false) && (RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture()) && !AliveAbility.instance().isAbilityDisabled();
        Logger.i("LVBA.AliveModule.FloatViewHelper", "canStartBgActivityByFloatView: %s", new Object[]{Boolean.valueOf(z)});
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e$3, java.lang.Runnable] */
    /* renamed from: b */
    public static boolean startBgActivityByFloatView(d dVar, final Intent intent) {
        if (addFloatView(dVar)) {
            ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("lvba_start_activity_delay", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e.3
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    try {
                        intent.addFlags(4096);
                        StrategyFramework.getFrameworkContext().startActivity(intent);
                        Logger.i("LVBA.AliveModule.FloatViewHelper", "activity start finish");
                    } catch (Exception e2) {
                        Logger.e("LVBA.AliveModule.FloatViewHelper", "fail to start activity");
                    }
                }
            }, dVar.b());
            return true;
        }
        return false;
    }

    /* renamed from: a */
    private static void trackFloatViewError(String str, String str2) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.lvba_float_view_track_error_61400", false)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("error", str2);
        StrategyFramework.trackError("FloatViewStrategy", new TrackErrorOption(31301, 30069, (String) null, hashMap, (Integer) null));
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e$2, java.lang.Runnable] */
    /* renamed from: a */
    public static boolean startBgActivityWithFloatView(final Intent intent) {
        if (!canStartBgActivityByFloatView()) {
            return false;
        }
        final d e2 = d.e();
        PddHandler mainHandler = ThreadPool.instance().getMainHandler(ThreadBiz.CS);
        mainHandler.removeCallbacks(g);
        boolean z = true;
        if (ThreadPool.instance().isMainThread()) {
            z = startBgActivityByFloatView(e2, intent);
        } else {
            mainHandler.post("lvba_add_float_view", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.startup.e.2
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    FloatViewHelper.startBgActivityByFloatView(d.this, intent);
                }
            });
        }
        mainHandler.postDelayed("lvba_close_float_view_delay", g, e2.a());
        return z;
    }

    /* renamed from: a */
    private static boolean addFloatView(d dVar) {
        try {
            if (f.get()) {
                Logger.i("LVBA.AliveModule.FloatViewHelper", "has shown view, don't show again util removed");
                return true;
            }
            Logger.i("LVBA.AliveModule.FloatViewHelper", "show view");
            trackFloatViewError("addViewStart", (String) null);
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            e = (WindowManager) frameworkContext.getSystemService("window");
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.height = dVar.d();
            layoutParams.width = dVar.d();
            layoutParams.format = 1;
            layoutParams.dimAmount = 0.0f;
            layoutParams.flags = 40;
            layoutParams.gravity = 51;
            layoutParams.type = dVar.c();
            d = LayoutInflater.from(frameworkContext.getApplicationContext()).inflate(ResourceHelper.instance().getLvbaFloatViewResId(), (ViewGroup) null);
            com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.a.a(e, d, layoutParams);
            f.set(true);
            return true;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.FloatViewHelper", "show window failed ", e2);
            trackFloatViewError("addViewFail", e2.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c() {
        try {
            if (null == e || null == d) {
                Logger.i("LVBA.AliveModule.FloatViewHelper", "no need close window");
            } else {
                Logger.i("LVBA.AliveModule.FloatViewHelper", "close system window");
                e.removeView(d);
                f.set(false);
                e = null;
                d = null;
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.FloatViewHelper", e2);
        }
    }
}