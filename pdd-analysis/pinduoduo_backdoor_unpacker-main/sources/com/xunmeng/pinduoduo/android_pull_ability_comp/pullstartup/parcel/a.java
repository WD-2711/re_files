package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel;

import android.content.Intent;
import android.os.Bundle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelClzListConfig;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private ParcelClzListConfig.ParcelClzConfig a;

    public ParcelClzListConfig.ParcelClzConfig b() {
        return this.a;
    }

    public Bundle a(Intent intent) {
        return null;
    }

    public void a(ParcelClzListConfig.ParcelClzConfig parcelClzConfig) {
        this.a = parcelClzConfig;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String a() {
        return d.a((com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.e) this);
    }

    public String c() {
        if (AppBuildInfo.instance().isDEBUG()) {
            throw new RuntimeException("no implementation");
        }
        return "";
    }

    public boolean d() {
        return false;
    }
}
