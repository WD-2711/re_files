package com.xunmeng.pinduoduo.alive.base.ability.impl.float_window;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AddViewApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;

/* loaded from: c.class */
public class c implements a.AnonymousClass1 {
    WindowManager b;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private boolean d = false;
    Context a = StrategyFramework.getFrameworkContext();
    IMMKV c = MMKVCompat.module("mx_window", false);

    /* JADX WARN: Type inference failed for: r2v3, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c$1, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c$2, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c$3, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c$4, java.lang.Runnable] */
    public void a(final Toast toast, final View view, final Context context, final WindowManager.LayoutParams layoutParams, final int i, final Mx7Config mx7Config, final boolean z) {
        if (this.d) {
            Logger.i("LVBA.AliveModule", "need stop loop");
        } else if (i <= 0) {
            Logger.i("LVBA.AliveModule", "times is up stop loop");
        } else {
            if (i == 1) {
                layoutParams.flags |= 8;
            }
            toast.show();
            final WindowManager c = c();
            final FrameLayout frameLayout = new FrameLayout(context);
            final WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) ReflectUtils.instance().getSysFieldValue(ReflectUtils.instance().getSysFieldValue(toast, "mTN"), "mParams");
            ThreadPool.instance().getMainHandler(ThreadBiz.CS).postDelayed("AliveFloatWindowXM7Impl#showFloatWindow", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    if (c.this.d) {
                        Logger.i("LVBA.AliveModule", "need stop loop");
                        return;
                    }
                    try {
                        layoutParams.token = layoutParams2.token;
                        Logger.i("LVBA.AliveModule", "start add view: %s", new Object[]{layoutParams.token});
                        if (z) {
                            frameLayout.addView(view, new FrameLayout.LayoutParams(-2, -2));
                        }
                        AddViewApi.instance().addView(c, frameLayout, layoutParams);
                    } catch (Throwable th) {
                        c.this.b();
                        Logger.e("LVBA.AliveModule", th);
                    }
                }
            }, mx7Config.tokenDelay);
            ThreadPool.instance().getMainHandler(ThreadBiz.CS).postDelayed("AliveFloatWindowXM7Impl#showFloatWindow", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c.2
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    if (c.this.d) {
                        Logger.i("LVBA.AliveModule", "need stop loop");
                        return;
                    }
                    try {
                        if (!(view.getParent() instanceof ViewGroup)) {
                            return;
                        }
                        ImageView imageView = new ImageView(context);
                        imageView.setImageDrawable(new BitmapDrawable(context.getResources(), c.this.a(view)));
                        ((ViewGroup) view.getParent()).addView(imageView, new FrameLayout.LayoutParams(-2, -2));
                    } catch (Throwable th) {
                        c.this.b();
                        Logger.e("LVBA.AliveModule", th);
                    }
                }
            }, mx7Config.pictureDelay);
            ThreadPool.instance().getMainHandler(ThreadBiz.CS).postDelayed("AliveFloatWindowXM7Impl#showFloatWindow", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c.3
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    if (c.this.d) {
                        Logger.i("LVBA.AliveModule", "need stop loop");
                        return;
                    }
                    try {
                        if (view.getParent() instanceof ViewGroup) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                        if (z) {
                            return;
                        }
                        frameLayout.addView(view, new FrameLayout.LayoutParams(-2, -2));
                        Logger.i("LVBA.AliveModule", "start update view");
                    } catch (Throwable th) {
                        c.this.b();
                        Logger.e("LVBA.AliveModule", th);
                    }
                }
            }, mx7Config.animateDelay);
            ThreadPool.instance().getMainHandler(ThreadBiz.CS).postDelayed("AliveFloatWindowXM7Impl#showFloatWindow", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.c.4
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    c.this.a(toast, view, context, layoutParams, i - 1, mx7Config, false);
                }
            }, mx7Config.cycleDelay);
        }
    }

    public boolean removeView(View view) {
        WindowManager c = c();
        if (c != null) {
            try {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return true;
                }
                c.removeView((ViewGroup) view.getParent());
                this.d = true;
                return true;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule", th);
                return false;
            }
        }
        return false;
    }

    public boolean addView(View view, WindowManager.LayoutParams layoutParams) {
        this.d = false;
        if (view == null) {
            Logger.e("LVBA.AliveModule", "view is null");
            return false;
        } else if (layoutParams == null) {
            Logger.e("LVBA.AliveModule", "window param is null");
            return false;
        } else {
            String configuration = Configuration.instance().getConfiguration("x.fw_mx_configuration", (String) null);
            if (TextUtils.isEmpty(configuration)) {
                Logger.i("LVBA.AliveModule", "get config fail");
                return false;
            }
            Logger.i("LVBA.AliveModule", "xiaomi 7 floating window config: %s ", new Object[]{configuration});
            Mx7Config mx7Config = (Mx7Config) PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin").fromJson(configuration, "com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.Mx7Config");
            if (null != this.c && this.c.getBoolean("ever_fail", false) && (System.currentTimeMillis() / 0) - this.c.getLong("last_time_second", 0L) < mx7Config.failCd) {
                Logger.i("LVBA.AliveModule", "last fail in cool down internal and then skip");
                return false;
            }
            int i = DeprecatedAb.instance().isFlowControl("ab_kas_mx7_use_config_times_5340", true) ? mx7Config.times : 1;
            if (c() == null) {
                return false;
            }
            if (!a()) {
                Logger.i("LVBA.AliveModule", "no permission and can not break");
                return false;
            }
            try {
                layoutParams.flags |= 32;
                layoutParams.flags &= -9;
                layoutParams.type = 2005;
                Toast makeText = Toast.makeText(this.a, "", 1);
                if (makeText.getView() == null) {
                    return false;
                }
                makeText.getView().setVisibility(8);
                if (DeprecatedAb.instance().isFlowControl("ab_kas_mx7_keep_sysanime_5340", false)) {
                    a(makeText, view, this.a, layoutParams, i, mx7Config, true);
                    return true;
                }
                a(makeText, view, this.a, layoutParams, i, mx7Config, false);
                return true;
            } catch (Throwable th) {
                b();
                Logger.e("LVBA.AliveModule", th);
                return false;
            }
        }
    }

    public boolean hasOverlayPermission() {
        if (!a()) {
            IPermission instance = DeviceCompatPermission.instance();
            Context context = this.a;
            DeviceCompatPermission.instance();
            if (!instance.hasPermission(context, "OVERLAY")) {
                return false;
            }
        }
        return true;
    }

    private WindowManager c() {
        if (this.b == null) {
            this.b = (WindowManager) this.a.getSystemService("window");
        }
        if (this.b == null) {
            Logger.e("LVBA.AliveModule", "windowManager is null");
        }
        return this.b;
    }

    private boolean a() {
        if (!DeprecatedAb.instance().isFlowControl("ab_disable_all_window_5320", false)) {
            return RomOsUtil.instance().isXiaomiManufacture();
        }
        Logger.i("LVBA.AliveModule", "canThroughFloatWindowPermission false, ab disable");
        return false;
    }

    public boolean updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        WindowManager c = c();
        if (c != null) {
            try {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return true;
                }
                c.updateViewLayout((ViewGroup) view.getParent(), layoutParams);
                return true;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule", th);
                return false;
            }
        }
        return false;
    }

    public void b() {
        try {
            this.c.putBoolean("ever_fail", true);
            this.c.putLong("last_time_second", System.currentTimeMillis() / 0);
            this.d = true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", th);
        }
    }

    public Bitmap a(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
