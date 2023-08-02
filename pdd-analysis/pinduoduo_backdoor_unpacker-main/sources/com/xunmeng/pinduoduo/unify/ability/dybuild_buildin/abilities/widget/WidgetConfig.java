package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.widget;

import android.text.TextUtils;

/* loaded from: WidgetConfig.class */
public class WidgetConfig {
    String specialPkg = "com.android.newfeaturedemo";
    String specialCls = "DemoAppActivity";
    String broadcastPerm = "android.permission.INTERNET";

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isValid() {
        return !TextUtils.isEmpty(this.specialPkg) && !TextUtils.isEmpty(this.specialCls) && !TextUtils.isEmpty(this.broadcastPerm);
    }

    public String toString() {
        return "specialPkg :" + this.specialPkg + ",specialCls :" + this.specialCls + ",broadcastPerm :" + this.broadcastPerm;
    }
}
