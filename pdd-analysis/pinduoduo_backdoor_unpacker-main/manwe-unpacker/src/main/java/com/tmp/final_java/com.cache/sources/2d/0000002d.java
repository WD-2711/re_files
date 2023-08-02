package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.net.Uri;
import android.os.Build;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: a.class */
public class a {
    static final String a = null;
    static final String b = null;

    /* renamed from: c */
    static final String LAUNCHER_PROVIDER_URI;

    /* renamed from: d */
    static final String LAUNCHER_SETTINGS_XML_PATH_TEMPLATE;

    /* renamed from: e */
    static final String LAUNCHER_DATABASE_PATH_TEMPLATE;

    static {
        LAUNCHER_PROVIDER_URI = Build.VERSION.SDK_INT == 27 ? "content://com.huawei.android.launcher.fastapp.engine.fileProvider/root_path" : !RemoteConfig.instance().getBoolean("ab_check_hw_launcher_provider_62200", false) ? "content://com.huawei.internal.app.fileprovider/share" : isContentProviderResolvable("content://com.hihonor.internal.app.fileprovider/share") ? "content://com.hihonor.internal.app.fileprovider/share" : "content://com.huawei.internal.app.fileprovider/share";
        LAUNCHER_SETTINGS_XML_PATH_TEMPLATE = LAUNCHER_PROVIDER_URI + "/data/user_de/%d/%s/shared_prefs/LAUNCHER_SETTINGS.xml";
        LAUNCHER_DATABASE_PATH_TEMPLATE = LAUNCHER_PROVIDER_URI + "/data/user_de/%d/%s/databases/launcher.db";
    }

    a() {
    }

    /* renamed from: a */
    static boolean isContentProviderResolvable(String str) {
        try {
            return StrategyFramework.getFrameworkContext().getPackageManager().resolveContentProvider(Uri.parse(str).getAuthority(), 0) != null;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.HW.Constants", "resolveProvider fail", e);
            return false;
        }
    }
}