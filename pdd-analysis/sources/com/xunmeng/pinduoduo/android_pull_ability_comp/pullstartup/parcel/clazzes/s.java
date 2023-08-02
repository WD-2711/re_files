package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.clazzes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;

/* loaded from: s.class */
public class s extends com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a {
    private static final String a = null;

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public Bundle a(Intent intent) {
        return b(intent);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.a
    public String c() {
        return "VCR";
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
        obtain2.writeString("com.android.internal.widget.VerifyCredentialResponse");
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(8);
        obtain2.writeInt(8);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(5);
        obtain2.writeInt(10);
        obtain2.writeInt(0);
        obtain2.writeInt(8);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeInt(5);
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
        return Build.VERSION.SDK_INT >= 24 && Build.VERSION.SDK_INT <= 25;
    }
}
