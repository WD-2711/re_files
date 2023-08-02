package com.xunmeng.pinduoduo.alive.base.ability.impl.float_window;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    Context a = StrategyFramework.getFrameworkContext();
    Object b;

    private boolean a() {
        if (DeprecatedAb.instance().isFlowControl("ab_disable_all_window_5320", false)) {
            Logger.i("LVBA.AliveModule", "canThroughFloatWindowPermission false, ab disable");
            return false;
        }
        return true;
    }

    public boolean removeView(View view) {
        if (null == this.b) {
            Logger.i("LVBA.AliveModule", "mTN is null can't remove view");
        }
        try {
            ReflectUtils.instance().invokeSysMethod(this.b, "hide", new Object[0]);
            Logger.i("LVBA.AliveModule", "mTN remove view success");
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", th);
            return false;
        }
    }

    public boolean updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        Logger.i("LVBA.AliveModule", "toast can't update and return false directly");
        return false;
    }

    public boolean addView(View view, WindowManager.LayoutParams layoutParams) {
        if (view == null) {
            Logger.e("LVBA.AliveModule", "view is null");
            return false;
        } else if (layoutParams == null) {
            Logger.e("LVBA.AliveModule", "window param is null");
            return false;
        } else if (!a()) {
            Logger.i("LVBA.AliveModule", "no permission and can not break");
            return false;
        } else {
            try {
                layoutParams.flags |= 32;
                Toast toast = new Toast(this.a);
                toast.setView(view);
                this.b = ReflectUtils.instance().getSysFieldValue(toast, "mTN");
                WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) ReflectUtils.instance().getSysFieldValue(this.b, "mParams");
                layoutParams2.flags = layoutParams.flags;
                layoutParams2.flags |= 32;
                layoutParams2.windowAnimations = 0;
                layoutParams2.format = layoutParams.format;
                layoutParams2.gravity = layoutParams.gravity;
                layoutParams2.width = layoutParams.width;
                layoutParams2.height = layoutParams.height;
                layoutParams2.x = layoutParams.x;
                layoutParams2.y = layoutParams.y;
                toast.setGravity(layoutParams.gravity, layoutParams.x, layoutParams.y);
                ReflectUtils.instance().setSysFieldValue(this.b, "mNextView", view);
                ReflectUtils.instance().setSysFieldValue(this.b, "mView", (Object) null);
                ReflectUtils.instance().getSysMethod(this.b.getClass(), "show").invoke(this.b, new Object[0]);
                return true;
            } catch (Throwable th) {
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
}
