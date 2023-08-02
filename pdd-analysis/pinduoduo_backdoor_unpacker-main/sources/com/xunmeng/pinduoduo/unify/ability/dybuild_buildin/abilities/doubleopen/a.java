package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import android.content.pm.LauncherApps;
import android.os.Build;
import android.os.UserHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.List;

/* loaded from: a.class */
public class a {
    private static final String a = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        try {
            if (Build.VERSION.SDK_INT < 26) {
                return false;
            }
            LauncherApps launcherApps = (LauncherApps) StrategyFramework.getFrameworkContext().getSystemService("launcherapps");
            List<UserHandle> profiles = launcherApps.getProfiles();
            if (profiles == null || profiles.isEmpty()) {
                Logger.i("LVUA.Dybuild.CommonDoubleOpen", "userHandles is empty");
                return false;
            }
            for (UserHandle userHandle : profiles) {
                boolean isPackageEnabled = launcherApps.isPackageEnabled(str, userHandle);
                Logger.i("LVUA.Dybuild.CommonDoubleOpen", "userHandle: " + userHandle + " " + str + " enable " + isPackageEnabled);
                if (userHandle.hashCode() != 0 && isPackageEnabled) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.CommonDoubleOpen", th);
            return false;
        }
    }
}
