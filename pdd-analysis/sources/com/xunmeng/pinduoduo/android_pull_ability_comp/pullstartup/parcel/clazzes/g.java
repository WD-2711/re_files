package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;

/* loaded from: g.class */
public class g extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
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
        return "HWAI";
    }

    private boolean e() {
        try {
            ParcelParaArr a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("HwAiClz", "config_pull_ability_dd_hwaiclz_64100");
            if (a2 == null || TextUtils.isEmpty(a2.paraIntArr)) {
                Logger.i("HwAiClz", "can't get para arr.");
                return false;
            }
            this.d = a2.paraIntArr;
            String a3 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.d);
            if (a3 == null) {
                Logger.i("HwAiClz", "clz name is null.");
                return false;
            }
            Class.forName(a3);
            Class.forName("android.preference.Preference$BaseSavedState");
            return true;
        } catch (Throwable th) {
            Logger.i("HwAiClz", "pass check exception:", th);
            return false;
        }
    }

    private Bundle b(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(1025);
        for (int i = 0; i < 509; i++) {
            obtain2.writeInt(-1);
        }
        obtain2.writeInt(6);
        obtain2.writeInt(122);
        obtain2.writeInt(119);
        obtain2.writeInt(6);
        obtain2.writeInt(4);
        obtain2.writeString("android.preference.Preference$BaseSavedState");
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.d);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(1025);
        for (int i2 = 0; i2 < 512; i2++) {
            obtain2.writeInt(-1);
        }
        obtain2.writeInt(6);
        obtain2.writeInt(13);
        obtain2.writeInt(2076);
        for (int i3 = 0; i3 < 512; i3++) {
            obtain2.writeInt(-1);
        }
        obtain2.writeInt(1);
        obtain2.writeInt(2);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(3);
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
