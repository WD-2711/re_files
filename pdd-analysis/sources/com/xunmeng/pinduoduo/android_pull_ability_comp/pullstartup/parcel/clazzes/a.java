package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;

/* loaded from: a.class */
public class a extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private String d = "";
    private String e = "";
    private String f = "";

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public Bundle a(Intent intent) {
        return b(intent);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public String c() {
        return "COIMI";
    }

    private boolean e() {
        try {
            ParcelParaArr a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("CoImiClz", "config_pull_ability_dd_coimiclz_64100");
            if (a2 == null || TextUtils.isEmpty(a2.paraIntArr)) {
                Logger.i("CoImiClz", "can't get para arr.");
                return false;
            }
            String[] split = a2.paraIntArr.split(":");
            this.d = 0 < split.length ? split[0] : "";
            this.e = 1 < split.length ? split[1] : "";
            this.f = 2 < split.length ? split[2] : "";
            if (TextUtils.isEmpty(this.d) || TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.f)) {
                Logger.i("CoImiClz", "para is empty.");
                return false;
            }
            String a3 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.d);
            String a4 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.e);
            String a5 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.f);
            if (Build.VERSION.SDK_INT <= 30) {
                if (a3 == null || a4 == null || a5 == null) {
                    Logger.i("CoImiClz", "clz name is null.");
                    return false;
                }
                Class.forName(a4);
            } else if (a3 == null || a5 == null) {
                Logger.i("CoImiClz", "clz name is null.");
                return false;
            }
            Class.forName(a3);
            Class.forName(a5);
            return true;
        } catch (Throwable th) {
            Logger.i("CoImiClz", "pass check exception:", th);
            return false;
        }
    }

    private Bundle b(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("android.preference.Preference$BaseSavedState");
        for (int i = 0; i < 20; i++) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.d);
        }
        if (Build.VERSION.SDK_INT <= 30) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.e);
        }
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.f);
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
        obtain2.writeInt(13);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(44);
        obtain2.writeInt(-1);
        obtain2.writeInt(13);
        obtain2.writeInt(22);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(-1);
        int dataPosition = obtain2.dataPosition();
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeString("android.content.Intent");
        intent.writeToParcel(obtain3, 0);
        obtain2.appendFrom(obtain3, 0, obtain3.dataSize());
        int dataPosition2 = obtain2.dataPosition();
        obtain2.setDataPosition(dataPosition - 4);
        obtain2.writeInt(dataPosition2 - dataPosition);
        obtain2.setDataPosition(dataPosition2);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(1145982018);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        obtain.recycle();
        obtain2.recycle();
        obtain3.recycle();
        return bundle;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public boolean d() {
        return e();
    }
}
