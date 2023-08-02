package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.PddHandler;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ResourceHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AddViewApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* loaded from: c.class */
public class c implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static View c;
    private static volatile WindowManager d;
    private static final AtomicBoolean e = new AtomicBoolean(false);
    private static final Runnable f = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c.1
        @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
        public void run() {
            c.c();
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: c$a.class */
    public class a {
        private int a;
        private int b;
        private int c;
        private int d;

        private a(int i, int i2, int i3, int i4) {
            this.a = 3000;
            this.b = 1000;
            this.c = 2101;
            this.d = 5;
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }

        public static a e() {
            a f = f();
            if (f == null) {
                f = new a();
            }
            return f;
        }

        private static a f() {
            try {
                String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.lvba_float_view_61400", "");
                Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "config value: %s", new Object[]{configValue});
                if (TextUtils.isEmpty(configValue)) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject(configValue);
                return new a(jSONObject.getInt("autoCloseDelayInMs"), jSONObject.getInt("startActivityDelayInMs"), jSONObject.getInt("windowType"), jSONObject.getInt("pixelSize"));
            } catch (Exception e) {
                Logger.e("LVUA.Dybuild.Sion.FloatSubAbility", "fail to parse config", e);
                return null;
            }
        }

        public int c() {
            return this.c;
        }

        public int d() {
            return this.d;
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        private a() {
            this.a = 3000;
            this.b = 1000;
            this.c = 2101;
            this.d = 5;
        }
    }

    public com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e isSupport() {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15663320) {
            return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(b(), null);
        }
        Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "app version low");
        return new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.e(false, "app version low");
    }

    private static View a(Context context) {
        return LayoutInflater.from(context.getApplicationContext()).inflate(ResourceHelper.instance().getLvbaFloatViewResId(), (ViewGroup) null);
    }

    private static void a(WindowManager.LayoutParams layoutParams) {
        AddViewApi.instance().addView(d, c, layoutParams);
    }

    private static boolean a(a aVar) {
        try {
            if (e.get()) {
                Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "has shown view, don't show again util removed");
                return true;
            }
            Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "show view");
            Context frameworkContext = StrategyFramework.getFrameworkContext();
            d = (WindowManager) frameworkContext.getSystemService("window");
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.height = aVar.d();
            layoutParams.width = aVar.d();
            layoutParams.format = 1;
            layoutParams.dimAmount = 0.0f;
            layoutParams.flags = 40;
            layoutParams.gravity = 51;
            layoutParams.type = aVar.c();
            c = a(frameworkContext);
            a(layoutParams);
            e.set(true);
            return true;
        } catch (Exception e2) {
            Logger.e("LVUA.Dybuild.Sion.FloatSubAbility", "show window failed ", e2);
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c$2, java.lang.Runnable] */
    private static boolean a(final Intent intent) {
        final a e2 = a.e();
        PddHandler mainHandler = ThreadPool.instance().getMainHandler(ThreadBiz.CS);
        mainHandler.removeCallbacks(f);
        boolean z = true;
        if (ThreadPool.instance().isMainThread()) {
            z = b(e2, intent);
        } else {
            mainHandler.post("lvba_add_float_view", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c.2
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    c.b(a.this, intent);
                }
            });
        }
        mainHandler.postDelayed("lvba_close_float_view_delay", f, e2.a());
        return z;
    }

    public StatusResult start(SionRequest sionRequest) {
        boolean a2 = a(sionRequest.getIntent());
        return new StatusResult(a2, a2 ? "success" : "failed");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c$3, java.lang.Runnable] */
    public static boolean b(a aVar, final Intent intent) {
        if (a(aVar)) {
            ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("lvba_start_activity_delay", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c.3
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    try {
                        com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a("com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.sion.subAbility.FloatSubAbility", StrategyFramework.getFrameworkContext(), intent);
                        Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "activity start finish");
                    } catch (Exception e2) {
                        Logger.e("LVUA.Dybuild.Sion.FloatSubAbility", "fail to start activity");
                    }
                }
            }, aVar.b());
            return true;
        }
        return false;
    }

    private boolean b() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.start_bg_by_float_view_63400", true) && (RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isNewHuaweiManufacture());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c() {
        try {
            if (null == d || null == c) {
                Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "no need close window");
            } else {
                Logger.i("LVUA.Dybuild.Sion.FloatSubAbility", "close system window");
                d.removeView(c);
                e.set(false);
                d = null;
                c = null;
            }
        } catch (Exception e2) {
            Logger.e("LVUA.Dybuild.Sion.FloatSubAbility", e2);
        }
    }
}
