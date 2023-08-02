package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.AlivePullAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelParaArr;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: f.class */
public class f extends b {
    String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;

    private boolean i() {
        try {
            ParcelParaArr a = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a("SpecialPullAbility.Comp", "ab_start_pull_hw_au_addsc_parcel_64900");
            if (a == null || TextUtils.isEmpty(a.paraIntArr)) {
                Logger.i("SpecialPullAbility.Comp", "[checkHWSNew1] can't get para arr.");
                return false;
            }
            this.c = a.paraIntArr;
            String a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(this.c);
            if (a2 == null) {
                Logger.i("SpecialPullAbility.Comp", "[checkHWSNew1] clz name is null.");
                return false;
            }
            Class.forName(a2);
            Class.forName("android.preference.Preference$BaseSavedState");
            Logger.i("SpecialPullAbility.Comp", "[checkHWSNew1] pass.");
            return true;
        } catch (Throwable th) {
            Logger.i("SpecialPullAbility.Comp", "[checkHWSNew1] pass check exception:", th);
            return false;
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public String h() {
        return "dd.hw";
    }

    public static Bundle e(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(8);
        obtain2.writeInt(8);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
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
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(0);
        obtain2.writeInt(8);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
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
        return bundle;
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f$4, java.lang.Runnable] */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean b(final Intent intent) {
        if (CdUtils.b()) {
            return CdUtils.b(intent, "dd.hw");
        }
        AlivePullAbility.instance().addAutoCloseWindowAndStartForeActivity();
        long backStartDelayTime = StartUpPullProperty.getInstance().getBackStartDelayTime();
        Logger.i("SpecialPullAbility.Comp", "background start activity after %s ms", new Object[]{Long.valueOf(backStartDelayTime)});
        ThreadPool.instance().getMainHandler(ThreadBiz.CS).postDelayed("start_pull_hw_activity", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f.4
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                f.this.g(intent);
            }
        }, backStartDelayTime);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean g(Intent intent) {
        Logger.i("SpecialPullAbility.Comp", "real start hw accountSettings activity.");
        try {
            BotBaseApplication.getContext().startActivity(intent);
            return true;
        } catch (Exception e2) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_account_exception");
            Logger.e("SpecialPullAbility.Comp", e2);
            return false;
        }
    }

    public static Bundle d(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(3);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("com.huawei.recsys.aidl.HwObjectContainer");
        obtain2.writeSerializable(null);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(36);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(4);
        obtain2.writeInt(13);
        obtain2.writeInt(66);
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
    public boolean a(Intent intent) {
        if (!ScreenUtil.isScreenOn()) {
            return true;
        }
        return TextUtils.equals(RemoteConfig.instance().getConfigValue("ab_hw_start_pull_activity_screen_on_5770", "true"), "true");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void a() {
        super.a();
        this.a.put("com.huawei.recsys.aidl.HwObjectContainer", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f.1
            public Bundle a(Intent intent) {
                return f.d(intent);
            }
        });
        this.a.put("com.android.internal.widget.VerifyCredentialResponse", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f.2
            public Bundle a(Intent intent) {
                return f.e(intent);
            }
        });
        this.a.put("ddws.hw1", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f.3
            public Bundle a(Intent intent) {
                return f.this.f(intent);
            }
        });
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(ActivityInfo activityInfo) {
        String str = activityInfo.packageName;
        if (AlivePullAbility.instance().getSecureInt(BotBaseApplication.getContext().getContentResolver(), "app_lock_func_status", 0) == 0) {
            return false;
        }
        String secureString = AlivePullAbility.instance().getSecureString(BotBaseApplication.getContext().getContentResolver(), "app_lock_list");
        if (TextUtils.isEmpty(secureString)) {
            return false;
        }
        try {
            String[] split = secureString.split(";");
            if (Arrays.asList(split).contains(AppBuildInfo.instance().getApplicationId())) {
                Logger.i("SpecialPullAbility.Comp", "pdd is in hw lock list ");
                return true;
            } else if (!Arrays.asList(split).contains(str)) {
                return false;
            } else {
                Logger.i("SpecialPullAbility.Comp", "%s is in hw lock list ", new Object[]{str});
                return true;
            }
        } catch (Exception e2) {
            Logger.e("SpecialPullAbility.Comp", e2);
            return false;
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean e() {
        Logger.i("SpecialPullAbility.Comp", "start accountSettings activity by window");
        if (CdUtils.b()) {
            return CdUtils.b("dd.hw");
        }
        if (TextUtils.equals(RemoteConfig.instance().getConfigValue("ab_hw_start_pull_activity_by_window", "true"), "true")) {
            return true;
        }
        Logger.i("SpecialPullAbility.Comp", "start activity by window ab false, return");
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean c() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_hw_start_pull_special_activity", "false"));
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean f() {
        return CdUtils.a() ? CdUtils.a("dd.hw") : ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext());
    }

    public Bundle f(Intent intent) {
        Logger.e("SpecialPullAbility.Comp", "makeBundleForHWSNew1");
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain2.writeInt(2);
        obtain2.writeString("intena");
        obtain2.writeInt(17);
        obtain2.writeInt(4);
        obtain2.writeInt(4);
        obtain2.writeString("android.preference.Preference$BaseSavedState");
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.b.a(obtain2, this.c);
        obtain2.writeInt(0);
        obtain2.writeInt(-1);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(18);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void b() {
        List asList = Arrays.asList("com.huawei.recsys.aidl.HwObjectContainer");
        for (int i = 26; i <= 30; i++) {
            this.b.put(Integer.valueOf(i), asList);
        }
        List asList2 = Arrays.asList("com.android.internal.widget.VerifyCredentialResponse");
        for (int i2 = 24; i2 <= 25; i2++) {
            this.b.put(Integer.valueOf(i2), asList2);
        }
        super.b();
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public Map a(Intent intent, String str) {
        Map a = super.a(intent, str);
        if (TextUtils.equals(str, "au.addShortcut")) {
            boolean z = RemoteConfig.instance().getBoolean("ab_start_pull_hw_au_addsc_64900", false) && i();
            Logger.e("SpecialPullAbility.Comp", "[getCandidateParcelableClzMap] is au.addShortcut, enable:" + z);
            for (int i = 28; i <= 31; i++) {
                ArrayList arrayList = new ArrayList();
                if (z) {
                    arrayList.add("ddws.hw1");
                }
                a.put(Integer.valueOf(i), arrayList);
            }
        }
        return a;
    }
}
