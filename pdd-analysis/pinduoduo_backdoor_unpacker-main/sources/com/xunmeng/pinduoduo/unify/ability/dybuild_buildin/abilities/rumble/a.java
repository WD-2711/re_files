package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.aepm.activity.ActivityUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    static final String a = null;
    static final String b = null;

    public void onStop() {
    }

    public void onNewIntent() {
    }

    public void onStart() {
    }

    public void onCreate(Activity activity, Bundle bundle) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        b.a("rumble_activity_start_success", "");
        try {
            if (ScreenUtils.instance().isScreenLocked()) {
                Logger.i("LVUA.Buildin.RumbleProxyActivity", "screen locked");
                b.a("rumble_activity_screen_locked", "");
            } else if (!ScreenUtils.instance().isScreenOn()) {
                Logger.i("LVUA.Buildin.RumbleProxyActivity", "screen off");
                b.a("rumble_activity_screen_off", "");
            } else {
                Intent intent = activity.getIntent();
                Intent intent2 = (Intent) intent.getParcelableExtra("realIntent");
                if (intent2 == null) {
                    Logger.i("LVUA.Buildin.RumbleProxyActivity", "targetIntent is null");
                    return;
                }
                Uri data = intent.getData();
                if (data == null) {
                    Logger.e("LVUA.Buildin.RumbleProxyActivity", "uri is null");
                } else if (!"com.ryze.activity".equals(data.getAuthority()) || !"ryze".equals(data.getScheme())) {
                    Logger.i("LVUA.Buildin.RumbleProxyActivity", "uri error");
                } else {
                    Logger.i("LVUA.Buildin.RumbleProxyActivity", "targetIntent=" + intent2);
                    intent2.setPackage(frameworkContext.getPackageName());
                    intent2.addFlags(4096);
                    ActivityUtils.instance().startActivity("cs_alive_rumble", StrategyFramework.getFrameworkContext(), intent2);
                }
            }
        } catch (Throwable th) {
            try {
                Logger.e("LVUA.Buildin.RumbleProxyActivity", th);
            } finally {
                Logger.i("LVUA.Buildin.RumbleProxyActivity", "disable and finish");
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a(2);
                activity.finish();
            }
        }
    }

    public void onResume() {
    }

    public void onDestroy() {
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onPause() {
    }
}
