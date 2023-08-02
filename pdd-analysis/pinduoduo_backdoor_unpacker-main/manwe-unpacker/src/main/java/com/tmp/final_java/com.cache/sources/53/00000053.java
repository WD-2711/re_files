package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.permQuery;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;

/* loaded from: b.class */
public class b extends BaseBizProviderImpl implements a.AnonymousClass1 {
    static final String a = null;

    public Boolean isVivoLockScreenPermExist() {
        if (!a("pinduoduo_Android.query_vivo_clock_screen_57700") || !hasPermission()) {
            return null;
        }
        try {
            try {
                b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Build.VERSION.SDK_INT >= 28 ? Uri.parse("content://com.android.settings.fileprovider/root_files").buildUpon().appendPath(c.a("data/user_de/%d/com.vivo.permissionmanager/databases/permission.db")).build() : Uri.parse("content://com.android.settings.fileprovider/root_files").buildUpon().appendPath("data/data/com.vivo.permissionmanager/databases/permission.db").build());
                if (a2 == null) {
                    LogUtils.logInvokeResultEventAndError(getProviderScene(), "isVivoLockScreenPermExist_OpenFile", false);
                    c.a(a2);
                    return null;
                }
                SQLiteDatabase database = a2.getDatabase();
                if (database == null) {
                    c.a(a2);
                    return null;
                }
                boolean a3 = c.a(0, database, "control_locked_screen_action", "pkgname = ?", new String[]{StrategyFramework.getFrameworkContext().getPackageName()}, "currentstate");
                Logger.i("LVBA.AliveModule.PermQuery", "current lock screen state is open :%s ", new Object[]{Boolean.valueOf(a3)});
                LogUtils.logInvokeResultEventAndError(getProviderScene(), "isVivoLockScreenPermExist_CheckFile", a3);
                Boolean valueOf = Boolean.valueOf(a3);
                c.a(a2);
                return valueOf;
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.PermQuery", " have exception in check perm ", e);
                c.a((b.a) null);
                return null;
            }
        } catch (Throwable th) {
            c.a((b.a) null);
            throw th;
        }
    }

    protected String getProviderScene() {
        return "VIVO_PM_UPDATE";
    }

    public boolean hasPermission() {
        return f.a().hasPermission(getProviderScene());
    }

    private boolean a(String str) {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "true"));
    }
}