package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;

/* loaded from: d.class */
public class d extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private String d = "";

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public Bundle a(Intent intent) {
        return b(intent);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public String c() {
        return "COUENS";
    }

    private boolean e() {
        try {
            ParcelParaArr a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("CoUeForNSClz", "config_pull_ability_dd_couefornsclz_64600");
            if (a2 == null || TextUtils.isEmpty(a2.paraIntArr)) {
                Logger.i("CoUeForNSClz", "can't get para arr.");
                return false;
            }
            this.d = a2.paraIntArr;
            String a3 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.d);
            if (a3 == null) {
                Logger.i("CoUeForNSClz", "clz name is null.");
                return false;
            }
            Class.forName(a3);
            Class.forName("android.preference.Preference$BaseSavedState");
            return true;
        } catch (Throwable th) {
            Logger.i("CoUeForNSClz", "pass check exception:", th);
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
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.d);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(4);
        obtain2.writeInt(0);
        obtain2.writeInt(18);
        obtain2.writeInt(19);
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeParcelable(intent, 0);
        obtain2.writeString("intent");
        obtain2.writeInt(4);
        obtain2.writeParcelable(null, 0);
        int dataSize = obtain2.dataSize();
        obtain.writeInt(dataSize);
        obtain.writeInt(1145982018);
        obtain.appendFrom(obtain2, 0, dataSize);
        obtain.setDataPosition(0);
        bundle.readFromParcel(obtain);
        obtain2.recycle();
        obtain.recycle();
        return bundle;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public boolean d() {
        return Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 27 && e();
    }
}
