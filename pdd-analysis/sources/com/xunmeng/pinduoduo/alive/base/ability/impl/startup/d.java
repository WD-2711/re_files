package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import org.json.JSONObject;

/* loaded from: d.class */
public class d {
    private int a;
    private int b;
    private int c;
    private int d;

    private d(int i, int i2, int i3, int i4) {
        this.a = 3000;
        this.b = 1000;
        this.c = 2101;
        this.d = 5;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
    }

    private static d f() {
        try {
            String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.lvba_float_view_61400", "");
            Logger.i("LVBA.AliveModule.FloatViewHelper", "config value: %s", new Object[]{configValue});
            if (TextUtils.isEmpty(configValue)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(configValue);
            return new d(jSONObject.getInt("autoCloseDelayInMs"), jSONObject.getInt("startActivityDelayInMs"), jSONObject.getInt("windowType"), jSONObject.getInt("pixelSize"));
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.FloatViewHelper", "fail to parse config", e);
            return null;
        }
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    private d() {
        this.a = 3000;
        this.b = 1000;
        this.c = 2101;
        this.d = 5;
    }

    public static d e() {
        d f = f();
        if (f == null) {
            f = new d();
        }
        return f;
    }
}
