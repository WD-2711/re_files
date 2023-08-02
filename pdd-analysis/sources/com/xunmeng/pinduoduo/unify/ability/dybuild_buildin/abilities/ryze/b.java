package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    public static final String a = null;
    private static final String b = null;

    public void onStop() {
    }

    public void onNewIntent() {
    }

    public void onStart() {
    }

    public void onCreate(Activity activity, Bundle bundle) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        c.a("ryze_activity_start_success");
        try {
            if (ScreenUtils.instance().isScreenLocked()) {
                Logger.i("LVUA.Buildin.RyzeStubActivity", "screen locked");
                c.a("ryze_activity_screen_locked");
            } else if (!ScreenUtils.instance().isScreenOn()) {
                Logger.i("LVUA.Buildin.RyzeStubActivity", "screen off");
                c.a("ryze_activity_screen_off");
            } else {
                Uri data = activity.getIntent().getData();
                if (data == null) {
                    Logger.e("LVUA.Buildin.RyzeStubActivity", "uri is null");
                    return;
                }
                String queryParameter = data.getQueryParameter("intent");
                Logger.i("LVUA.Buildin.RyzeStubActivity", "intentUri=" + queryParameter);
                if (!"com.ryze.activity".equals(data.getAuthority()) || !"ryze".equals(data.getScheme()) || TextUtils.isEmpty(queryParameter)) {
                    Logger.i("LVUA.Buildin.RyzeStubActivity", "uri error");
                    return;
                }
                Intent parseUri = Intent.parseUri(queryParameter, 1);
                if (parseUri == null) {
                    Logger.i("LVUA.Buildin.RyzeStubActivity", "parse intent error");
                    return;
                }
                Logger.i("LVUA.Buildin.RyzeStubActivity", "targetIntent=" + parseUri);
                parseUri.setPackage(frameworkContext.getPackageName());
                parseUri.addFlags(4096);
                com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a("com.xunmeng.pinduoduo.unify.ability.dybuild.abilities.ryze.RyzeProxyActivity", frameworkContext, parseUri);
                c.a("start_by_uri", queryParameter);
            }
        } catch (Throwable th) {
            try {
                Logger.e("LVUA.Buildin.RyzeStubActivity", th);
            } finally {
                Logger.i("LVUA.Buildin.RyzeStubActivity", "disable and finish");
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
