package com.xunmeng.pinduoduo.launcher_detect_comp.impl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.ConfigItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MRCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.launcher_detect_comp_interf.aidl.ISecureDaemonservice;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.json.JSONObject;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    private static final String a = null;
    private Object b;
    private Method c;
    private Field d;
    private Field e;
    private Method i;
    private Object j;
    private Object l;
    private Method m;
    private Object n;
    private Method o;
    private String p;
    private boolean g = true;
    private boolean h = true;
    private boolean k = true;
    private final boolean f = i();

    private boolean i() {
        if (Boolean.parseBoolean(RemoteConfig.instance().getExpValue("home_detect_vivo_bind_service_disable_black_5950", "false"))) {
            Logger.i("Pdd.HomeDetect.Comp", "disable black list");
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(RemoteConfig.instance().getExpValue("home_detect_vivo_bind_service_request_config_5950", "{}"));
            SceneRequest sceneRequest = new SceneRequest(jSONObject.optString("scene_id", "3035"));
            sceneRequest.setValidTTLInMs(Long.valueOf(jSONObject.optLong("valid_ttl_mills", 0L)));
            sceneRequest.setRefreshTTLInMs(Long.valueOf(jSONObject.optLong("refresh_ttl_mills", 0L)));
            ConfigItem config = MRCManager.instance().getConfig(BotBaseApplication.getContext(), sceneRequest);
            if (config == null || TextUtils.isEmpty(config.getConfig())) {
                Logger.i("Pdd.HomeDetect.Comp", "bind is in black list, configItem or configItem's config is null.");
                return true;
            } else if (!new JSONObject(config.getConfig()).optBoolean("is_black", true)) {
                return false;
            } else {
                Logger.i("Pdd.HomeDetect.Comp", "bind is in black list");
                return true;
            }
        } catch (Throwable th) {
            Logger.e("Pdd.HomeDetect.Comp", th);
            return true;
        }
    }

    private String d() {
        if (h() && this.i != null && this.h) {
            try {
                String str = (String) this.i.invoke(this.j, new Object[0]);
                return str != null ? str : "";
            } catch (Exception e) {
                if (e.getCause() instanceof SecurityException) {
                    Logger.w("Pdd.HomeDetect.Comp", "top pkg by night mode invalid");
                    this.h = false;
                }
                Logger.e("Pdd.HomeDetect.Comp", e);
                return "";
            }
        }
        return "";
    }

    private ComponentName e() {
        if (g() && !ScreenUtil.isScreenLocked() && this.g) {
            try {
                List list = (List) this.c.invoke(this.b, new Object[0]);
                if (list == null) {
                    return null;
                }
                for (Object obj : list) {
                    String str = (String) this.d.get(obj);
                    if (!TextUtils.isEmpty(str) && ((Integer) this.e.get(obj)).intValue() == 1) {
                        return ComponentName.unflattenFromString(str);
                    }
                }
                return null;
            } catch (Throwable th) {
                if (th.getCause() instanceof SecurityException) {
                    Logger.w("Pdd.HomeDetect.Comp", "top pkg by finger invalid");
                    this.g = false;
                }
                Logger.e("Pdd.HomeDetect.Comp", th);
                return null;
            }
        }
        return null;
    }

    public ComponentName getTopComponent() {
        if (this.f) {
            return null;
        }
        ComponentName a2 = a();
        if (a2 != null) {
            return a2;
        }
        ComponentName e = e();
        if (e != null) {
            return e;
        }
        ComponentName b = b();
        return b != null ? b : new ComponentName("", d());
    }

    public boolean isSupportOfGetTopComponent() {
        if (!AliveAbility.instance().isAbilityDisabled() || !Boolean.parseBoolean(RemoteConfig.instance().getExpValue("home_detect_new_get_top_pkg_use_disable_salt_6070", "false"))) {
            return g() || h() || c() || f();
        }
        return false;
    }

    private ComponentName b() {
        if (!c() || this.m == null || ScreenUtil.isScreenLocked() || !this.k) {
            return null;
        }
        try {
            String str = (String) this.m.invoke(this.l, Integer.valueOf(ScreenUtil.getDisplayWidth() >> 1), Integer.valueOf(ScreenUtil.getDisplayHeight() >> 1));
            if (str == null) {
                return null;
            }
            int indexOf = str.indexOf("{");
            int indexOf2 = str.indexOf("}");
            if (indexOf == -1 || indexOf2 == -1) {
                return null;
            }
            String[] split = str.substring(indexOf + 1, indexOf2).split(" ");
            if (split.length == 3) {
                return ComponentName.unflattenFromString(split[2]);
            }
            return null;
        } catch (Throwable th) {
            if (th.getCause() instanceof SecurityException) {
                Logger.w("Pdd.HomeDetect.Comp", "top pkg by wm invalid");
                this.k = false;
            }
            Logger.e("Pdd.HomeDetect.Comp", th);
            return null;
        }
    }

    private ComponentName a() {
        if (!f() || this.o == null) {
            return null;
        }
        try {
            return (ComponentName) this.o.invoke(this.n, 0);
        } catch (Throwable th) {
            Logger.e("Pdd.HomeDetect.Comp", th);
            return null;
        }
    }

    private boolean c() {
        return ((!DeprecatedAb.instance().isFlowControl("home_detect_vivo_new_method_v3_6050", false) && !AppBuildInfo.instance().isDEBUG()) || this.m == null || this.l == null) ? false : true;
    }

    public ISecureDaemonservice getIService(IBinder iBinder) {
        ISecureDaemonservice.Stub.setDescriptor("com.vivo.securedaemonservice.ISecureDaemonservice");
        return ISecureDaemonservice.Stub.asInterface(iBinder);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0093 -> B:72:0x009e). Please submit an issue!!! */
    public b() {
        this.p = "";
        try {
            this.b = a("fingerprintui");
            if (this.b != null) {
                this.c = ReflectUtils.instance().getSysMethod(this.b.getClass(), "getWindowList");
                Class<?> cls = Class.forName("com.vivo.fingerprint.WindowStatus");
                this.d = ReflectUtils.instance().getSysField(cls, "title");
                this.e = ReflectUtils.instance().getSysField(cls, "type");
            } else {
                Logger.w("Pdd.HomeDetect.Comp", "cannot get fingerui service.");
            }
        } catch (Exception e) {
            Logger.e("Pdd.HomeDetect.Comp", e.toString());
        }
        try {
            Class<?> cls2 = Class.forName("vivo.app.nightmode.NightModeController");
            Method sysMethod = ReflectUtils.instance().getSysMethod(cls2, "getInstance");
            if (sysMethod != null) {
                this.j = sysMethod.invoke(null, new Object[0]);
            }
            this.i = ReflectUtils.instance().getSysMethod(cls2, "getResumedActivityName");
        } catch (Exception e2) {
            Logger.e("Pdd.HomeDetect.Comp", e2.toString());
        }
        try {
            this.l = a("window");
            if (this.l != null) {
                this.m = ReflectUtils.instance().getSysMethod(this.l.getClass(), "getTouchableWindowTitleAtPoint");
            } else {
                Logger.w("Pdd.HomeDetect.Comp", "cannot get window manager service.");
            }
        } catch (Exception e3) {
            Logger.e("Pdd.HomeDetect.Comp", e3.toString());
        }
        try {
            this.p = PddSystemProperties.instance().get("ro.vivo.product.overseas", "no");
            Logger.i("Pdd.HomeDetect.Comp", "prop ro.vivo.product.overseas: %s", new Object[]{this.p});
            this.n = a("vivo_cast_service");
            if (this.n != null) {
                this.o = ReflectUtils.instance().getSysMethod(this.n.getClass(), "getForegroundApp");
            } else {
                Logger.w("Pdd.HomeDetect.Comp", "cannot get vivo cast service.");
            }
        } catch (Exception e4) {
            Logger.e("Pdd.HomeDetect.Comp", e4.toString());
        }
    }

    private boolean f() {
        return RemoteConfig.instance().getBoolean("home_detect_vivo_new_method_of_castservice_6200", false) && TextUtils.equals("no", this.p) && this.n != null && this.o != null;
    }

    private boolean g() {
        return ((!DeprecatedAb.instance().isFlowControl("home_detect_vivo_new_method_6000", false) && !AppBuildInfo.instance().isDEBUG()) || this.c == null || this.b == null || this.d == null || this.e == null) ? false : true;
    }

    private Object a(String str) {
        try {
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            declaredMethod.setAccessible(true);
            IBinder iBinder = (IBinder) declaredMethod.invoke(null, str);
            if (iBinder == null) {
                return null;
            }
            Class<?> cls = Class.forName(iBinder.getInterfaceDescriptor() + "$Stub");
            Method declaredMethod2 = Class.class.getDeclaredMethod("getMethod", String.class, Class[].class);
            declaredMethod2.setAccessible(true);
            Method method = (Method) declaredMethod2.invoke(cls, "asInterface", new Class[]{IBinder.class});
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(null, iBinder);
        } catch (Exception e) {
            Logger.e("Pdd.HomeDetect.Comp", e.toString());
            return null;
        }
    }

    private boolean h() {
        return ((!DeprecatedAb.instance().isFlowControl("home_detect_vivo_new_method_v2_6030", false) && !AppBuildInfo.instance().isDEBUG()) || this.j == null || this.i == null) ? false : true;
    }

    public boolean bindService(ServiceConnection serviceConnection, int i) {
        Logger.i("Pdd.HomeDetect.Comp", "bind vivo service");
        if (i()) {
            return false;
        }
        Intent intent = new Intent("vivo.intent.action.SECURE_DAEMON_SERVICE");
        intent.setPackage("com.vivo.upslide");
        try {
            return BotBaseApplication.getApplication().bindService(intent, serviceConnection, 1);
        } catch (Throwable th) {
            Logger.e("Pdd.HomeDetect.Comp", th);
            return false;
        }
    }
}