package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.LayoutProps;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.OppoIconInfo;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import java.util.List;

/* loaded from: c.class */
public class c extends BaseBizProviderImpl implements a.AnonymousClass1 {
    private static final String a = null;

    public LayoutProps getLayoutProps() {
        if (!a("getLayoutProps")) {
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "start get layout props !");
        LayoutProps b = d.b();
        LogUtils.logInvokeResultEventAndError("oppo_launcher_update", "getLauncherIcons", b != null);
        return b;
    }

    public boolean hasAbility(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return DeprecatedAb.instance().isFlowControl("ab_oppo_lp_ability_enable_57600_" + str, true);
    }

    protected String getProviderScene() {
        return "oppo_launcher_update";
    }

    public boolean addIcon(OppoIconInfo oppoIconInfo) {
        if (!a("addIcon")) {
            return false;
        }
        if (oppoIconInfo == null) {
            Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "skip add icon for icon info is null !");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "start add icon : %s", new Object[]{oppoIconInfo});
        boolean a2 = d.a(oppoIconInfo);
        LogUtils.logInvokeResultEventAndError("oppo_launcher_update", "addIcon", a2);
        return a2;
    }

    public boolean removeIcon(int i) {
        if (!a("removeIcon")) {
            return false;
        }
        if (i < 0) {
            Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "skip add icon for invalid icon id !");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "start remove icon : %s", new Object[]{Integer.valueOf(i)});
        boolean a2 = d.a(i);
        LogUtils.logInvokeResultEventAndError("oppo_launcher_update", "removeIcon", a2);
        return a2;
    }

    public boolean hasPermission() {
        return f.a().hasPermission(getProviderScene());
    }

    public List getLauncherIcons() {
        if (!a("getLauncherIcons")) {
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "start get icons list !");
        List a2 = d.a();
        LogUtils.logInvokeResultEventAndError("oppo_launcher_update", "getLauncherIcons", a2 != null);
        return a2;
    }

    private boolean a(String str) {
        if (!hasAbility(str)) {
            Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "no %s ability !", new Object[]{str});
            return false;
        } else if (hasPermission()) {
            return true;
        } else {
            Logger.i("LVBA.AliveModule.Provider.OppoLPImpl", "no fp perm !");
            return false;
        }
    }
}