package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Proguard;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    protected final Map a = new HashMap();
    protected final Map b = new HashMap();
    private static final String c = null;

    public String h() {
        return null;
    }

    public boolean b(Intent intent) {
        return false;
    }

    public static Bundle c(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(2);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("android.os.WorkSource");
        obtain2.writeInt(-1);
        obtain2.writeInt(-1);
        obtain2.writeInt(-1);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(13);
        obtain2.writeInt(13);
        obtain2.writeInt(68);
        obtain2.writeInt(11);
        obtain2.writeInt(0);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
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
        return bundle;
    }

    public boolean a(Intent intent) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a() {
        this.a.put("android.os.WorkSource", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b.1
            public Bundle a(Intent intent) {
                return b.c(intent);
            }
        });
    }

    public boolean a(ActivityInfo activityInfo) {
        return false;
    }

    public boolean e() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public final boolean d() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_common_start_pull_old_make_64900", "true"));
    }

    public b() {
        try {
            a();
            b();
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
        }
    }

    public boolean f() {
        return false;
    }

    public final Map b(Intent intent, String str) {
        return this.a;
    }

    public boolean g() {
        return RemoteConfig.instance().getBoolean(Proguard.marks("ab_start_pull_use_settings_account_6060"), false) && !AliveAbility.instance().isAbilityDisabled();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void b() {
        if (!Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_common_start_pull_enable_5930", "false"))) {
            return;
        }
        Logger.i("SpecialPullAbility.Comp", "add common parcelable clz.");
        for (int i = 28; i <= 31; i++) {
            List list = (List) this.b.get(Integer.valueOf(i));
            ArrayList arrayList = new ArrayList();
            arrayList.add("android.os.WorkSource");
            if (list != null) {
                arrayList.addAll(list);
            }
            this.b.put(Integer.valueOf(i), arrayList);
        }
    }

    public Map a(Intent intent, String str) {
        return this.b;
    }
}
