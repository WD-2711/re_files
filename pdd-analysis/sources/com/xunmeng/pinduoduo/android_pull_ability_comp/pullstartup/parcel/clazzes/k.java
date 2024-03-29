package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;

/* loaded from: k.class */
public class k extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
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
        return "MTBMSROM";
    }

    private boolean e() {
        try {
            ParcelParaArr a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("MtBmsrForOmClz", "config_pull_ability_dd_mtbmsrforomclz_64100");
            if (a2 == null || TextUtils.isEmpty(a2.paraIntArr)) {
                Logger.i("MtBmsrForOmClz", "can't get para arr.");
                return false;
            }
            this.d = a2.paraIntArr;
            String a3 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.d);
            if (a3 == null) {
                Logger.i("MtBmsrForOmClz", "clz name is null.");
                return false;
            }
            Class.forName(a3);
            Class.forName("android.preference.ListPreference$SavedState");
            return true;
        } catch (Throwable th) {
            Logger.i("MtBmsrForOmClz", "pass check exception:", th);
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
        obtain2.writeString("android.preference.ListPreference$SavedState");
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.d);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(68);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
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
