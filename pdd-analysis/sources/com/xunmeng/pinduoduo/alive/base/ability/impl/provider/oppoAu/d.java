package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu;

import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.i;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.j;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: d.class */
public class d extends BaseBizProviderImpl implements a.AnonymousClass1 {
    private final String a = "LVBA.AliveModule.Provider.OppoAuProviderImpl";
    private static final String b = null;

    public Boolean isUninstallable() {
        if (!hasAbility("isUninstallable")) {
            return null;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "start isUninstallable");
        if (!hasPermission()) {
            if (i.a().b("pinduoduo_Android.ka_providerV2_oppo_au_cache_57700")) {
                Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "get isUninstallable from mmkv");
                return i.a().a(getProviderScene());
            }
            Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "skip isUninstallable since no permission");
            return null;
        }
        Boolean a = f.a();
        j.a(getProviderScene(), "isUninstallable", a != null);
        if (a != null) {
            Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "read database success, start update mmkv, enableUninstall: %s", new Object[]{a});
            i.a().a(getProviderScene(), a);
        } else {
            a = i.a().a(getProviderScene());
            Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "read database failed, get isUninstallable from mmkv, enableUninstall: %s", new Object[]{a});
        }
        return a;
    }

    public boolean a() {
        if (!hasAbility("restartLauncher")) {
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "start restartLauncher");
        boolean b2 = f.b();
        j.a(getProviderScene(), b2);
        return b2;
    }

    protected String getProviderScene() {
        return "provider_oppo_au";
    }

    public boolean hasPermission() {
        return com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f.a().hasPermission(getProviderScene());
    }

    public boolean setUninstall(boolean z) {
        if (!hasAbility("setUninstall")) {
            return false;
        }
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "skip setUninstall since no permission");
            return false;
        }
        Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "start setUninstall, enableUninstall: %s", new Object[]{Boolean.valueOf(z)});
        boolean a = f.a(z);
        if (a) {
            Logger.i("LVBA.AliveModule.Provider.OppoAuProviderImpl", "write database success, start update mmkv, enableUninstall: %s", new Object[]{Boolean.valueOf(z)});
            i.a().a(getProviderScene(), Boolean.valueOf(z));
        }
        j.a(getProviderScene(), "setUninstall", a);
        return a;
    }
}
