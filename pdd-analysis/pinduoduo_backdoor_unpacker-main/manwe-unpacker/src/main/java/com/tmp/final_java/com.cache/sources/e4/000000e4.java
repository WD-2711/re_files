package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.Arrays;
import java.util.List;

/* loaded from: k.class */
public class k extends b {
    private static final String c = null;
    private static final String d = null;

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public String h() {
        return "dd.samsung";
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(Intent intent) {
        if (!ScreenUtil.isScreenOn()) {
            return true;
        }
        return TextUtils.equals(RemoteConfig.instance().getConfigValue("ab_samsung_start_pull_activity_screen_on_5770", "false"), "true");
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean b(Intent intent) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void a() {
        super.a();
        this.a.put("com.samsung.android.cepproxyks.CertByte", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.k.1
            public Bundle a(Intent intent) {
                return k.this.d(intent);
            }
        });
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(ActivityInfo activityInfo) {
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean e() {
        if (CdUtils.b()) {
            return CdUtils.b("dd.samsung");
        }
        Logger.i("SpecialPullAbility.Comp", "not support background start activity.");
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean c() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_samsung_start_pull_special_activity", "false"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle d(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(3);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("com.samsung.android.cepproxyks.CertByte");
        obtain2.writeInt(0);
        obtain2.writeByteArray(new byte[0]);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(53);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(72);
        obtain2.writeInt(48);
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
        return bundle;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean f() {
        return CdUtils.a() ? CdUtils.a("dd.samsung") : ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void b() {
        List asList = Arrays.asList("com.samsung.android.cepproxyks.CertByte");
        for (int i = 28; i <= 30; i++) {
            this.b.put(Integer.valueOf(i), asList);
        }
        super.b();
    }
}