package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import java.lang.reflect.Method;

/* loaded from: f.class */
public class f {
    private static final String a = null;

    public static Object a(String str) {
        try {
            Method declaredMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            declaredMethod.setAccessible(true);
            IBinder iBinder = (IBinder) declaredMethod.invoke(null, str);
            if (iBinder == null) {
                return null;
            }
            Class<?> cls = (Build.VERSION.SDK_INT > 25 || !TextUtils.equals(str, "activity")) ? Class.forName(iBinder.getInterfaceDescriptor() + "$Stub") : Class.forName("android.app.ActivityManagerNative");
            Method declaredMethod2 = Class.class.getDeclaredMethod("getMethod", String.class, Class[].class);
            declaredMethod2.setAccessible(true);
            Method method = (Method) declaredMethod2.invoke(cls, "asInterface", new Class[]{IBinder.class});
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(null, iBinder);
        } catch (Exception e) {
            Logger.e("LVUA.Dybuild.SystemManagerInvoker", e);
            return null;
        }
    }
}
