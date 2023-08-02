package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.xmBW;

import android.content.ContentValues;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.f;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.BaseBizProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;

/* loaded from: c.class */
public class c extends BaseBizProviderImpl implements a.AnonymousClass1 {
    static final String a = null;
    private final boolean b = f();

    public Boolean isInBehaviorWhiteList() {
        if (!hasAbility("queryWhite")) {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip query white list for no ability !");
            return null;
        } else if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip query white list for no permission !");
            return null;
        } else if (a()) {
            return b();
        } else {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip query white list for not reboot !");
            return null;
        }
    }

    private Boolean b() {
        long currentTimeMillis = System.currentTimeMillis();
        Boolean d = a.d();
        Logger.i("LVBA.AliveModule.Provider.XM", "query behavior white list spent : %s ms!", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)});
        return d;
    }

    public Integer getLocalConfigVer() {
        if (!hasAbility("getVer")) {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip get ver for no ability !");
            return null;
        } else if (hasPermission()) {
            return c();
        } else {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip get ver for no permission !");
            return null;
        }
    }

    private ContentValues e() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", AppBuildInfo.instance().getApplicationId() + "@0");
        contentValues.put("application_name", AppBuildInfo.instance().getApplicationId());
        contentValues.put("type", (Integer) 4);
        return contentValues;
    }

    private boolean a() {
        long a2 = a.a();
        long c = a.c();
        Logger.i("LVBA.AliveModule.Provider.XM", "this boot time %s , update reboot time %s !", new Object[]{Long.valueOf(c), Long.valueOf(a2)});
        if (a2 == 0) {
            Logger.i("LVBA.AliveModule.Provider.XM", "not set update boot time !");
            return false;
        } else if (c == a2) {
            Logger.i("LVBA.AliveModule.Provider.XM", "not reboot !");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.XM", "has reboot !");
            return true;
        }
    }

    public boolean hasAbility(String str) {
        if (!TextUtils.isEmpty(str) && this.b) {
            return DeprecatedAb.instance().isFlowControl("ab_xm_fp_ability_57500_" + str, true);
        }
        return false;
    }

    protected String getProviderScene() {
        return "XM_BW_UPDATE_DB";
    }

    public boolean setWhite() {
        if (!hasAbility("setWhite")) {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip set white for no ability !");
            return false;
        } else if (hasPermission()) {
            return d();
        } else {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip set white for no permission !");
            return false;
        }
    }

    private boolean d() {
        long currentTimeMillis = System.currentTimeMillis();
        boolean a2 = a.a(e());
        Logger.i("LVBA.AliveModule.Provider.XM", "set white spend total time : %s ms!", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)});
        return a2;
    }

    public c() {
        Logger.i("LVBA.AliveModule.Provider.XM", "finish init XmBehaviorWhiteImpl, file provider valid %s !", new Object[]{Boolean.valueOf(this.b)});
    }

    public boolean hasPermission() {
        return f.a().hasPermission(getProviderScene());
    }

    private boolean f() {
        PackageManager packageManager;
        boolean parseBoolean = Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_xm_provider_need_check_58200", "false"));
        Logger.i("LVBA.AliveModule.Provider.XM", "need check provider ? %s ", new Object[]{Boolean.valueOf(parseBoolean)});
        if (!parseBoolean) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 24) {
            Logger.i("LVBA.AliveModule.Provider.XM", "skip check for not valid sdk version!");
            return true;
        }
        int i = -1;
        try {
            packageManager = StrategyFramework.getFrameworkContext().getPackageManager();
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.XM", "check fp valid failed : ", e);
        }
        if (packageManager == null) {
            return false;
        }
        if (packageManager.resolveContentProvider("com.miui.securitycore.fileProvider", 512) != null) {
            Logger.i("LVBA.AliveModule.Provider.XM", "provider is valid!");
            return true;
        }
        PackageInfo packageInfo = packageManager.getPackageInfo("com.miui.securitycore", 0);
        if (packageInfo != null) {
            i = packageInfo.versionCode;
        }
        a.b("CheckFileProviderFailed", String.valueOf(i));
        return false;
    }

    private Integer c() {
        long currentTimeMillis = System.currentTimeMillis();
        Integer a2 = a.a("key_third_desktop_version", a.f());
        Logger.i("LVBA.AliveModule.Provider.XM", "get local config ver spend total time : %s ms!", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)});
        return a2;
    }
}
