package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import java.util.List;

/* loaded from: e.class */
public class e {
    private static final String a = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Boolean a(String str) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_oppo_double_open_62000", true)) {
            Logger.i("LVUA.Dybuild.OppoSystemDoubleOpen", "oppo isDoubleOpen hit ab ");
            return false;
        }
        try {
            Object invokeSysMethod = ReflectUtils.instance().invokeSysMethod(ReflectUtils.instance().getSysMethod(Class.forName("oppo.util.OppoMultiLauncherUtil"), "getInstance").invoke(null, new Object[0]), "getCreatedMultiApp", new Object[0]);
            Logger.i("LVUA.Dybuild.OppoSystemDoubleOpen", "mutil:" + invokeSysMethod);
            return Boolean.valueOf(a(invokeSysMethod, str));
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.OppoSystemDoubleOpen", th);
            try {
                Object invokeSysMethod2 = ReflectUtils.instance().invokeSysMethod(ReflectUtils.instance().getSysMethod(Class.forName("com.oplus.multiapp.OplusMultiAppManager"), "getInstance").invoke(null, new Object[0]), "getMultiAppList", new Object[]{0});
                Logger.i("LVUA.Dybuild.OppoSystemDoubleOpen", "mutil:" + invokeSysMethod2);
                return Boolean.valueOf(a(invokeSysMethod2, str));
            } catch (Exception e) {
                Logger.e("LVUA.Dybuild.OppoSystemDoubleOpen", e);
                try {
                    Object invokeSysMethod3 = ReflectUtils.instance().invokeSysMethod(ReflectUtils.instance().getSysMethod(Class.forName("com.color.multiapp.ColorMultiAppManager"), "getInstance").invoke(null, new Object[0]), "getCreatedMultiApp", new Object[0]);
                    Logger.i("LVUA.Dybuild.OppoSystemDoubleOpen", "mutil:" + invokeSysMethod3);
                    return Boolean.valueOf(a(invokeSysMethod3, str));
                } catch (Exception e2) {
                    Logger.e("LVUA.Dybuild.OppoSystemDoubleOpen", e2);
                    return null;
                }
            }
        }
    }

    private static boolean a(Object obj, String str) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof List)) {
            throw new IllegalStateException("instance of error");
        }
        boolean contains = ((List) obj).contains(str);
        b.a("oppo_double_open_status", contains ? "1" : "0");
        return contains;
    }
}
