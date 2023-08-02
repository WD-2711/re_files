package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;

/* loaded from: b.class */
public class b extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private String e = "";
    private String f = "";
    private String g = "";

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public Bundle a(Intent intent) {
        return b(intent);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public String c() {
        return "COIMINS";
    }

    private boolean e() {
        try {
            ParcelParaArr a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("CoImiForNSClz", "config_pull_ability_dd_coimifornsclz_64200");
            if (a2 == null || TextUtils.isEmpty(a2.paraIntArr)) {
                Logger.i("CoImiForNSClz", "can't get para arr.");
                return false;
            }
            String[] split = a2.paraIntArr.split(":");
            this.e = 0 < split.length ? split[0] : "";
            this.f = 1 < split.length ? split[1] : "";
            this.g = 2 < split.length ? split[2] : "";
            if (TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.f) || TextUtils.isEmpty(this.g)) {
                Logger.i("CoImiForNSClz", "para is empty.");
                return false;
            }
            String a3 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.e);
            String a4 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.f);
            String a5 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.g);
            if (Build.VERSION.SDK_INT <= 30) {
                if (a3 == null || a4 == null || a5 == null) {
                    Logger.i("CoImiForNSClz", "clz name is null.");
                    return false;
                }
                Class.forName(a4);
            } else if (a3 == null || a5 == null) {
                Logger.i("CoImiForNSClz", "clz name is null.");
                return false;
            }
            Class.forName(a3);
            Class.forName(a5);
            Class.forName("android.preference.Preference$BaseSavedState");
            return true;
        } catch (Throwable th) {
            Logger.i("CoImiForNSClz", "pass check exception:", th);
            return false;
        }
    }

    public Bundle b(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain2.writeInt(2);
        obtain2.writeString("intena");
        obtain2.writeInt(17);
        obtain2.writeInt(4);
        obtain2.writeInt(4);
        obtain2.writeString("android.preference.Preference$BaseSavedState");
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.e);
        if (Build.VERSION.SDK_INT <= 30) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.f);
        }
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.g);
        obtain2.writeInt(-1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        if (Build.VERSION.SDK_INT >= 28) {
            obtain2.writeInt(0);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            obtain2.writeInt(0);
        }
        if (Build.VERSION.SDK_INT > 30) {
            obtain2.writeInt(0);
            obtain2.writeInt(0);
        }
        new ResolveInfo().writeToParcel(obtain2, 0);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(18);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeParcelable(intent, 0);
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName(BotBaseApplication.getContext().getPackageName(), "com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity"));
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeParcelable(intent2, 0);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(1145982018);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        obtain.recycle();
        obtain2.recycle();
        return bundle;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public boolean d() {
        return e() && f();
    }

    private boolean f() {
        try {
            return com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.d.a(b(new Intent()));
        } catch (Throwable th) {
            Logger.i("CoImiForNSClz", "report check exception");
            return false;
        }
    }
}
