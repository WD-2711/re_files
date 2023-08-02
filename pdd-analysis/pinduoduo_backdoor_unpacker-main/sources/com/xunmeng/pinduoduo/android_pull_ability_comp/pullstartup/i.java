package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.AlivePullAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ReflectUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.ISionAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionResult;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.api.AliveSionAbility;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.utils.AlivePullStartUpInterfUtils;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.utils.AliveStartUpConstants;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* loaded from: i.class */
public class i extends b {
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private Object f;
    private Method g;

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public String h() {
        return "dd.xiaomi";
    }

    public Bundle e(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(3);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(4);
        obtain2.writeString("android.content.pm.parsing.component.ParsedIntentInfo");
        new IntentFilter().writeToParcel(obtain2, 0);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(7);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(1);
        obtain2.writeInt(1);
        obtain2.writeInt(13);
        obtain2.writeInt(32);
        obtain2.writeInt(1);
        obtain2.writeInt(-1);
        obtain2.writeInt(0);
        obtain2.writeInt(8);
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

    private boolean b(String str) {
        PackageManager packageManager = BotBaseApplication.getApplication().getPackageManager();
        try {
            if (Build.VERSION.SDK_INT < 29 || !packageManager.isPackageSuspended(str)) {
                return false;
            }
            Logger.i("SpecialPullAbility.Comp", "%s is filter by limit app", new Object[]{str});
            return true;
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
            return false;
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean b(Intent intent) {
        return CdUtils.b() ? CdUtils.b(intent, "dd.xiaomi") : f(intent);
    }

    private boolean f(Intent intent) {
        ISionAbility instance = AliveSionAbility.instance();
        SionRequest sionRequest = new SionRequest(intent, "dd.xiaomi", UUID.randomUUID().toString());
        if (RemoteConfig.instance().getBoolean("ab_mi_start_pull_activity_by_rivan_6200", false) && intent.getComponent() != null && TextUtils.equals(intent.getComponent().getPackageName(), "com.android.settings") && intent.getBooleanExtra(AliveStartUpConstants.KEY_START_ACC_BY_RIVAN, false) && instance.isSupport(Collections.singletonList("RivanSmartSubAbility"), "dd.xiaomi")) {
            sionRequest.setSubAbilityNames(Collections.singletonList("RivanSmartSubAbility"));
            SionResult start = instance.start(sionRequest);
            Logger.i("SpecialPullAbility.Comp", "statusResult:" + start.isSuccess() + " msg:" + start.getErrorMsg());
            if (!start.isSuccess()) {
                com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("rivan_fail#" + start.getErrorMsg(), intent);
            }
            return start.isSuccess();
        } else if (TextUtils.equals(AlivePullStartUpInterfUtils.getFpScene(intent), "miui_backup") && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext())) {
            Logger.i("SpecialPullAbility.Comp", "start activity directly by miui_backup");
            return g(intent);
        } else {
            List firstWayVersions = StartUpPullProperty.getInstance().getFirstWayVersions();
            if (RemoteConfig.instance().getBoolean("ab_mi_start_pull_activity_by_background_start_6170", false) && instance.isSupport("dd.xiaomi")) {
                Logger.i("SpecialPullAbility.Comp", "start Activity by background activity");
                instance.start(sionRequest);
                return true;
            }
            String version = RomOsUtil.instance().getVersion();
            if (TextUtils.isEmpty(version) || !firstWayVersions.contains(version.toLowerCase())) {
                if (Build.VERSION.SDK_INT >= 26) {
                    Logger.i("SpecialPullAbility.Comp", "start Activity by miui flags");
                    ReflectUtils.instance().invokeSysMethod(intent, "setMiuiFlags", new Object[]{2});
                }
                return g(intent);
            }
            Logger.i("SpecialPullAbility.Comp", "start Activity by alarm");
            sionRequest.setSubAbilityNames(Collections.singletonList("AlarmSubAbility"));
            instance.start(sionRequest);
            return true;
        }
    }

    private boolean g(Intent intent) {
        Logger.i("SpecialPullAbility.Comp", "real start mi accountSettings activity.");
        try {
            BotBaseApplication.getContext().startActivity(intent);
            return true;
        } catch (Exception e2) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_account_exception");
            Logger.e("SpecialPullAbility.Comp", e2);
            return false;
        }
    }

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
        obtain2.writeString("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
        for (int i = 0; i < 9; i++) {
            obtain2.writeInt(0);
        }
        obtain2.writeInt(1);
        obtain2.writeString(null);
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
    public boolean a(Intent intent) {
        if (!ScreenUtil.isScreenOn()) {
            return true;
        }
        return (!AlivePullStartUpInterfUtils.getBooleanExtraFromBundle(intent, AliveStartUpConstants.KEY_USE_SETTINGS_ACCOUNT, false) || !g()) ? Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_mi_start_pull_activity_screen_on_5790", "false")) : Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_mi_start_pull_screen_on_settings_account_6060", "false"));
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void a() {
        super.a();
        this.a.put("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.i.1
            public Bundle a(Intent intent) {
                return i.this.d(intent);
            }
        });
        this.a.put("android.content.pm.parsing.component.ParsedIntentInfo", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.i.2
            public Bundle a(Intent intent) {
                return i.this.e(intent);
            }
        });
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(ActivityInfo activityInfo) {
        String str = activityInfo.packageName;
        return a(str) || b(str);
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean e() {
        if (CdUtils.b()) {
            return CdUtils.b("dd.xiaomi");
        }
        return true;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean c() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_mi_start_pull_special_activity_5790", "false"));
    }

    public i() {
        try {
            Class<?> cls = Class.forName("miui.security.SecurityManager");
            this.g = cls.getDeclaredMethod("getApplicationAccessControlEnabled", String.class);
            this.g.setAccessible(true);
            this.f = cls.cast(BotBaseApplication.getApplication().getSystemService("security"));
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean f() {
        return CdUtils.a() ? CdUtils.a("dd.xiaomi") : ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext());
    }

    private boolean a(String str) {
        if (AlivePullAbility.instance().getSecureInt(BotBaseApplication.getApplication().getContentResolver(), "access_control_lock_enabled", 0) == 0 || this.g == null || this.f == null) {
            return false;
        }
        try {
            if (!((Boolean) this.g.invoke(this.f, str)).booleanValue()) {
                return false;
            }
            Logger.i("SpecialPullAbility.Comp", "%s is in app lock", new Object[]{str});
            return true;
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
            return false;
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void b() {
        this.b.put(29, Arrays.asList("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse"));
        this.b.put(30, Arrays.asList("android.content.pm.parsing.component.ParsedIntentInfo", "com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse"));
        super.b();
    }
}
