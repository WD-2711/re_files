package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

/* loaded from: q.class */
public class q extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public Bundle a(Intent intent) {
        return com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j.d(intent);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public String c() {
        return "OPONP";
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public boolean d() {
        return Build.VERSION.SDK_INT == 30;
    }
}
