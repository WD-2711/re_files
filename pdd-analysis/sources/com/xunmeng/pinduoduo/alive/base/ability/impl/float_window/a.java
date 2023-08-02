package com.xunmeng.pinduoduo.alive.base.ability.impl.float_window;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AddViewApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.Configuration;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DateUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import java.util.HashMap;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    Context a = StrategyFramework.getFrameworkContext();
    WindowManager b;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final IMMKV h = MMKVCompat.module("special_ability", false);

    private boolean i() {
        if (DeprecatedAb.instance().isFlowControl("ab_disable_meizu_window_5430", false)) {
            Logger.i("LVBA.AliveModule.FloatWindow", "enableMeizuWindowPermission false, ab disable");
            return false;
        }
        return j();
    }

    public boolean removeView(View view) {
        WindowManager b = b();
        if (b != null) {
            try {
                b.removeView(view);
                return true;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.FloatWindow", th);
                return false;
            }
        }
        return false;
    }

    public static void a(int i, boolean z, String str, String str2) {
        try {
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_track_add_view_62900", false)) {
                return;
            }
            Logger.i("LVBA.AliveModule.FloatWindow", "start track event");
            HashMap hashMap = new HashMap();
            hashMap.put("business", "AddView");
            hashMap.put("type", Integer.valueOf(i));
            hashMap.put("result", Boolean.valueOf(z));
            hashMap.put("action", str);
            hashMap.put("error_msg", str2);
            StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(hashMap, "perf", "alive", 0));
            Logger.i("LVBA.AliveModule.FloatWindow", "tracker end");
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.FloatWindow", th);
        }
    }

    public boolean addView(View view, WindowManager.LayoutParams layoutParams) {
        if (view == null) {
            Logger.e("LVBA.AliveModule.FloatWindow", "view is null");
            return false;
        } else if (layoutParams == null) {
            Logger.e("LVBA.AliveModule.FloatWindow", "window param is null");
            return false;
        } else {
            WindowManager b = b();
            if (b == null) {
                return false;
            }
            if (a()) {
                if (d()) {
                    if (!b(layoutParams)) {
                        Logger.i("LVBA.AliveModule.FloatWindow", "break hw window fail");
                        return false;
                    }
                } else if (RomOsUtil.instance().isXiaomiManufacture()) {
                    if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_add_view_use_special_window_62900", false)) {
                        return b(b, view, layoutParams);
                    }
                    if (!c(layoutParams)) {
                        Logger.i("LVBA.AliveModule.FloatWindow", "break xiaomi window fail");
                        return false;
                    }
                } else if (RomOsUtil.instance().isOppoManufacture()) {
                    if (!a(view, layoutParams)) {
                        Logger.i("LVBA.AliveModule.FloatWindow", "break oppo window fail");
                        return false;
                    }
                } else if (RomOsUtil.instance().isVivoManufacture()) {
                    if (!a(layoutParams)) {
                        Logger.i("LVBA.AliveModule.FloatWindow", "break vivo window fail");
                        return false;
                    }
                } else if (!RomOsUtil.instance().isFlyme()) {
                    a(layoutParams, 2037);
                } else if (!d(layoutParams)) {
                    Logger.i("LVBA.AliveModule.FloatWindow", "break meizu window fail");
                    return false;
                }
                Logger.i("LVBA.AliveModule.FloatWindow", "verify ok_api:id=%d,type=%d,flags=%s,title=%s,package=%s", new Object[]{Integer.valueOf(view.getId()), Integer.valueOf(layoutParams.type), Integer.valueOf(layoutParams.flags), layoutParams.getTitle(), layoutParams.packageName});
            }
            try {
                a(b, view, layoutParams);
                return true;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.FloatWindow", th);
                return false;
            }
        }
    }

    private boolean b(WindowManager.LayoutParams layoutParams) {
        return a(layoutParams, 2101);
    }

    private boolean d(WindowManager.LayoutParams layoutParams) {
        return a(layoutParams, 2037);
    }

    private boolean b(WindowManager windowManager, View view, WindowManager.LayoutParams layoutParams) {
        boolean z = false;
        if (0 == 0 && g()) {
            c(layoutParams);
            z = c(windowManager, view, layoutParams);
            Logger.i("LVBA.AliveModule.FloatWindow", "add 2037 window success : %s", new Object[]{Boolean.valueOf(z)});
        }
        if (!z && k()) {
            a(layoutParams, 2029);
            z = c(windowManager, view, layoutParams);
            Logger.i("LVBA.AliveModule.FloatWindow", "add 2029 window success : %s", new Object[]{Boolean.valueOf(z)});
        }
        return z;
    }

    private boolean a() {
        boolean z = DeprecatedAb.instance().isFlowControl("ab_lfs_skip_float_ability_5350", false) && !AliveAbility.instance().isAbilityDisabled2022Q3("hw_float_to_window");
        IPermission instance = DeviceCompatPermission.instance();
        Context context = this.a;
        DeviceCompatPermission.instance();
        boolean hasPermission = instance.hasPermission(context, "OVERLAY");
        Logger.i("LVBA.AliveModule.FloatWindow", "skipFloatAbility=%s, hasOverlayPermission=%s", new Object[]{Boolean.valueOf(z), Boolean.valueOf(hasPermission)});
        return c() && (z || !hasPermission);
    }

    private boolean c(WindowManager windowManager, View view, WindowManager.LayoutParams layoutParams) {
        try {
            Logger.i("LVBA.AliveModule.FloatWindow", "verify ok_api:id=%d,type=%d,flags=%s,title=%s,package=%s", new Object[]{Integer.valueOf(view.getId()), Integer.valueOf(layoutParams.type), Integer.valueOf(layoutParams.flags), layoutParams.getTitle(), layoutParams.packageName});
            a(windowManager, view, layoutParams);
            Logger.i("LVBA.AliveModule.FloatWindow", "add window success");
            a(layoutParams.type, true, "addSpecialTypeView", "success");
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.FloatWindow", "use special window error", th);
            a(layoutParams.type, false, "addSpecialTypeView", Logger.getStackTraceString(th));
            try {
                windowManager.removeView(view);
                return false;
            } catch (Throwable th2) {
                Logger.e("LVBA.AliveModule.FloatWindow", "remove window error", th2);
                return false;
            }
        }
    }

    public boolean updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        WindowManager b = b();
        if (b != null) {
            try {
                b.updateViewLayout(view, layoutParams);
                return true;
            } catch (Throwable th) {
                Logger.e("LVBA.AliveModule.FloatWindow", th);
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x0075, code lost:
        if (r0.hasPermission(r1, "COARSE_LOCATION") == false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean e() {
        /*
            r7 = this;
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IDeprecatedAb r0 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb.instance()
            java.lang.String r1 = "ab_disable_oppo_window_5320"
            r2 = 0
            boolean r0 = r0.isFlowControl(r1, r2)
            if (r0 == 0) goto L1a
            java.lang.String r0 = "LVBA.AliveModule.FloatWindow"
            java.lang.String r1 = "enableOppoWindowPermission false, ab disable"
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.i(r0, r1)
            r0 = 0
            return r0
        L1a:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L39
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 > r1) goto L39
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 23
            if (r0 == r1) goto L39
            r0 = 1
            goto L3a
        L39:
            r0 = 0
        L3a:
            r8 = r0
            r0 = r8
            if (r0 == 0) goto L84
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 != r1) goto L84
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission r0 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission.instance()
            r1 = r7
            android.content.Context r1 = r1.a
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission r2 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission.instance()
            java.lang.String r2 = "FINE_LOCATION"
            boolean r0 = r0.hasPermission(r1, r2)
            if (r0 == 0) goto L78
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission r0 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission.instance()
            r1 = r7
            android.content.Context r1 = r1.a
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IPermission r2 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission.instance()
            java.lang.String r2 = "COARSE_LOCATION"
            boolean r0 = r0.hasPermission(r1, r2)
            if (r0 != 0) goto L84
        L78:
            r0 = 0
            r8 = r0
            java.lang.String r0 = "LVBA.AliveModule.FloatWindow"
            java.lang.String r1 = "only support Android 10 with location permission"
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.i(r0, r1)
        L84:
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 23
            if (r0 != r1) goto La8
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IDeprecatedAb r0 = com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb.instance()
            java.lang.String r1 = "ab_enable_oppo_window_6_5321"
            r2 = 0
            boolean r0 = r0.isFlowControl(r1, r2)
            if (r0 == 0) goto La8
            java.lang.String r0 = "LVBA.AliveModule.FloatWindow"
            java.lang.String r1 = "enable oppo 6"
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.i(r0, r1)
            r0 = 1
            r8 = r0
        La8:
            java.lang.String r0 = "LVBA.AliveModule.FloatWindow"
            java.lang.String r1 = "enableOppoWindowPermission: %s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = r2
            r4 = 0
            r5 = r8
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            r3[r4] = r5
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger.i(r0, r1, r2)
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.alive.base.ability.impl.float_window.a.e():boolean");
    }

    private boolean c() {
        if (!DeprecatedAb.instance().isFlowControl("ab_disable_all_window_5320", false)) {
            return RomOsUtil.instance().isOppoManufacture() ? e() : RomOsUtil.instance().isXiaomiManufacture() ? g() || k() : d() ? f() : RomOsUtil.instance().isVivoManufacture() ? h() : RomOsUtil.instance().isFlyme() ? i() : j();
        }
        Logger.i("LVBA.AliveModule.FloatWindow", "canThroughFloatWindowPermission false, ab disable");
        return false;
    }

    private boolean g() {
        if (DeprecatedAb.instance().isFlowControl("ab_disable_xiaomi_window_5330", false)) {
            Logger.i("LVBA.AliveModule.FloatWindow", "enableXiaomiWindowPermission false, ab disable");
            return false;
        } else if (Build.VERSION.SDK_INT != 23) {
            return j();
        } else {
            boolean isFlowControl = DeprecatedAb.instance().isFlowControl("ab_enable_xiaomi_6_window_5330", false);
            Logger.i("LVBA.AliveModule.FloatWindow", "enableXiaomi6WindowPermission: %s", new Object[]{Boolean.valueOf(isFlowControl)});
            return isFlowControl;
        }
    }

    private boolean k() {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_add_view_use_special_window_62900", false)) {
            Logger.i("LVBA.AliveModule.FloatWindow", "add 2029 window disable by ab");
            return false;
        } else if (!AliveAbility.instance().isAbilityDisabled2022Q3("add_2029_view")) {
            return a(2029);
        } else {
            Logger.i("LVBA.AliveModule.FloatWindow", "add 2029 window disable by salt");
            return false;
        }
    }

    public boolean hasOverlayPermission() {
        if (!c()) {
            IPermission instance = DeviceCompatPermission.instance();
            Context context = this.a;
            DeviceCompatPermission.instance();
            if (!instance.hasPermission(context, "OVERLAY")) {
                return false;
            }
        }
        return true;
    }

    private boolean a(WindowManager.LayoutParams layoutParams) {
        if (DeprecatedAb.instance().isFlowControl("ab_enable_vivo_toast_window_5640", false)) {
            ReflectUtils.instance().setSysFieldValue(layoutParams, "traditonToast", 1);
            return a(layoutParams, 2005);
        }
        return a(layoutParams, 2037);
    }

    private boolean c(WindowManager.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT == 23) {
            int parseInt = NumberUtils.instance().parseInt(Configuration.instance().getConfiguration("x.broken_xiaomi_6_window_type", "2034"));
            Logger.i("LVBA.AliveModule.FloatWindow", "break xiaomi 6 window type: %s", new Object[]{Integer.valueOf(parseInt)});
            return a(layoutParams, parseInt);
        }
        return a(layoutParams, 2037);
    }

    private boolean a(WindowManager.LayoutParams layoutParams, int i) {
        layoutParams.type = NumberUtils.instance().parseInt(Configuration.instance().getConfiguration("x.broken_window_type", Integer.toString(i)));
        layoutParams.flags |= 32;
        return true;
    }

    public static void a(WindowManager windowManager, View view, WindowManager.LayoutParams layoutParams) {
        AddViewApi.instance().addView(windowManager, view, layoutParams);
    }

    private boolean a(View view, WindowManager.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT == 23) {
            Logger.i("LVBA.AliveModule.FloatWindow", "break oppo Android M");
            view.setId(33819897);
            layoutParams.type = 2005;
            return true;
        }
        Logger.i("LVBA.AliveModule.FloatWindow", "break oppo");
        if (Build.VERSION.SDK_INT >= 24) {
            layoutParams.type = 2009;
            Logger.i("LVBA.AliveModule.FloatWindow", "set param title SecurityInputMethodDialog");
            if (!TextUtils.isEmpty("SecurityInputMethodDialog")) {
                layoutParams.setTitle("SecurityInputMethodDialog");
            }
        } else {
            layoutParams.type = 2010;
        }
        layoutParams.flags |= 32;
        if (Build.VERSION.SDK_INT > 22) {
            return true;
        }
        Logger.i("LVBA.AliveModule.FloatWindow", "get SystemProperties oppo.dex.front.package");
        if (TextUtils.isEmpty("oppo.dex.front.package")) {
            return true;
        }
        String str = PddSystemProperties.instance().get("oppo.dex.front.package", (String) null);
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        layoutParams.packageName = str;
        return true;
    }

    private WindowManager b() {
        if (this.b == null) {
            this.b = (WindowManager) this.a.getSystemService("window");
        }
        if (this.b == null) {
            Logger.e("LVBA.AliveModule.FloatWindow", "windowManager is null");
        }
        return this.b;
    }

    private boolean a(int i) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        View view = new View(frameworkContext);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = i;
        layoutParams.width = 1;
        layoutParams.height = 1;
        layoutParams.flags |= 40;
        layoutParams.x = 1;
        layoutParams.y = 1;
        layoutParams.format = 1;
        WindowManager windowManager = (WindowManager) frameworkContext.getSystemService("window");
        try {
            AddViewApi.instance().addView(windowManager, view, layoutParams);
            windowManager.removeView(view);
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule.FloatWindow", th);
            a(i, false, "isWindowTypeAvailable_error", Logger.getStackTraceString(th));
            return false;
        }
    }

    private boolean d() {
        if (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) {
            return true;
        }
        return RemoteConfig.instance().getBoolean("ab_float_window_considered_hw_mfc_62800", false) && RomOsUtil.instance().isEmui() && !AliveAbility.instance().isAbilityDisabled2022Q3("hw_small_brand_float");
    }

    private boolean f() {
        if (DeprecatedAb.instance().isFlowControl("ab_disable_huawei_window_5320", false)) {
            Logger.i("LVBA.AliveModule.FloatWindow", "enableHuaweiWindowPermission false, ab disable");
            return false;
        }
        return true;
    }

    private boolean h() {
        if (DeprecatedAb.instance().isFlowControl("ab_disable_vivo_window_5320", false)) {
            Logger.i("LVBA.AliveModule.FloatWindow", "enableVivoWindowPermission false, ab disable");
            return false;
        } else if (Build.VERSION.SDK_INT <= 23) {
            return true;
        } else {
            return j();
        }
    }

    private boolean j() {
        if (!DeprecatedAb.instance().isFlowControl("ab_enable_detect_window_type_2037_5930", false)) {
            return true;
        }
        if (DateUtils.instance().isToday(h.getLong("last_check_special_window", 0L))) {
            return h.getBoolean("special_window_type_state", true);
        }
        boolean a = a(2037);
        h.putLong("last_check_special_window", TimeStamp.instance().getRealLocalTime().longValue());
        h.putBoolean("special_window_type_state", a);
        return a;
    }
}
