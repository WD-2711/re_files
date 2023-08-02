package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.net.Uri;
import android.os.Build;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;

/* loaded from: a.class */
class a {
    static final String a = null;
    static final String b = null;
    static final String c;
    static final String d;
    static final String e;

    static {
        c = Build.VERSION.SDK_INT == 27 ? "content://com.huawei.android.launcher.fastapp.engine.fileProvider/root_path" : !RemoteConfig.instance().getBoolean("ab_check_hw_launcher_provider_62200", false) ? "content://com.huawei.internal.app.fileprovider/share" : a("content://com.hihonor.internal.app.fileprovider/share") ? "content://com.hihonor.internal.app.fileprovider/share" : "content://com.huawei.internal.app.fileprovider/share";
        d = c + "/data/user_de/%d/%s/shared_prefs/LAUNCHER_SETTINGS.xml";
        e = c + "/data/user_de/%d/%s/databases/launcher.db";
    }

    a() {
    }

    static boolean a(String str) {
        try {
            return StrategyFramework.getFrameworkContext().getPackageManager().resolveContentProvider(Uri.parse(str).getAuthority(), 0) != null;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.HW.Constants", "resolveProvider fail", e2);
            return false;
        }
    }
}
