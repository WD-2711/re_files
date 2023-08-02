package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.FileProviderV2;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.api.AliveSionAbility;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* loaded from: j.class */
public class j extends b {
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private long f = 0;
    private final Lock g = new ReentrantLock();
    private static final String h = null;

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public String h() {
        return "dd.oppo";
    }

    private boolean a(ComponentName componentName) {
        boolean z = false;
        boolean z2 = true;
        try {
            JSONObject jSONObject = new JSONObject(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_special_activity_app_restrict_list_check_5920", "{}"));
            z = jSONObject.optBoolean("enable_list_check", false);
            z2 = jSONObject.optBoolean("restrict_default_when_disable_check", true);
            Logger.i("SpecialPullAbility.Comp", "enableCheck list: %s, default when disable checkList: %s", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2)});
        } catch (Exception e2) {
            Logger.e("SpecialPullAbility.Comp", e2.toString());
        }
        if (!z) {
            return z2;
        }
        Boolean bool = null;
        try {
            bool = FileProviderV2.instance().oppoLockPullProvider().canLockPull(componentName);
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", th);
        }
        if (bool == null) {
            Logger.w("SpecialPullAbility.Comp", "oppo lock pull provider return null, maybe no granted.");
            return true;
        }
        Object[] objArr = new Object[2];
        objArr[0] = componentName;
        objArr[1] = Boolean.valueOf(!bool.booleanValue());
        Logger.i("SpecialPullAbility.Comp", "%s is restricted: %s", objArr);
        return !bool.booleanValue();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j$4, java.lang.Runnable] */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean b(final Intent intent) {
        if (CdUtils.b()) {
            return CdUtils.b(intent, "dd.oppo");
        }
        long j = 0;
        this.g.lock();
        try {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f;
            if (elapsedRealtime < StartUpPullProperty.getInstance().getBackStartDelayTime()) {
                j = StartUpPullProperty.getInstance().getBackStartDelayTime() - elapsedRealtime;
            }
            this.f = SystemClock.elapsedRealtime() + j;
            Logger.i("SpecialPullAbility.Comp", "background start activity by notification after %s ms", new Object[]{Long.valueOf(j)});
            ThreadPool.instance().uiTaskDelay(ThreadBiz.CS, "start_pull_oppo_activity", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j.4
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    Logger.i("SpecialPullAbility.Comp", "use sion notification ability start");
                    AliveSionAbility.instance().start(new SionRequest(intent, "dd.oppo", UUID.randomUUID().toString(), Collections.singletonList("NotificationSubAbility"), (Map) null));
                }
            }, j);
            return true;
        } finally {
            this.g.unlock();
        }
    }

    private Boolean a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_special_activity_app_notification_start_5980", "{}"));
            boolean optBoolean = jSONObject.optBoolean("enable_list_check", true);
            String optString = jSONObject.optString("wl_notification_start", "com.coloros.athena;com.oplus.athena");
            String[] split = optString.split(";");
            Logger.i("SpecialPullAbility.Comp", "enableCheck list: %s, wl apps : %s", new Object[]{Boolean.valueOf(optBoolean), optString});
            if (optBoolean) {
                return Boolean.valueOf(Arrays.asList(split).contains(str));
            }
            return null;
        } catch (Exception e2) {
            Logger.e("SpecialPullAbility.Comp", e2.toString());
            return false;
        }
    }

    public static Bundle d(Intent intent) {
        Bundle bundle = new Bundle();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        Parcel obtain3 = Parcel.obtain();
        obtain2.writeInt(49);
        obtain2.writeString("11");
        obtain2.writeInt(4);
        obtain2.writeString("com.oplus.orms.info.OrmsNotifyParam");
        obtain2.writeInt(3);
        obtain2.writeInt(855638067);
        obtain2.writeInt(0);
        obtain2.writeInt(6);
        obtain2.writeInt(0);
        obtain2.writeInt(0);
        obtain2.writeInt(3);
        obtain2.writeInt(855638065);
        obtain2.writeInt(6);
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
        for (int i = 0; i < 49 - 3; i++) {
            obtain2.writeString("44" + i);
            obtain2.writeInt(1);
            obtain2.writeInt(i);
        }
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
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_activity_screen_lock_and_on_5810", "true")) && ScreenUtil.isScreenLocked() && e(intent)) {
            return true;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_activity_screen_on_5770", "false"));
    }

    private boolean e(Intent intent) {
        String str = intent.getPackage();
        if (TextUtils.isEmpty(str) && intent.getComponent() != null) {
            str = intent.getComponent().getPackageName();
        }
        if (TextUtils.isEmpty(str)) {
            Logger.i("SpecialPullAbility.Comp", "can get pkg name in intent: %s", new Object[]{intent});
            return false;
        }
        boolean contains = StartUpPullProperty.getInstance().getCanStartWhitePkgListWhenLock().contains(str);
        Logger.i("SpecialPullAbility.Comp", "%s is in white list of screen lock start: %s", new Object[]{str, Boolean.valueOf(contains)});
        return contains;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void a() {
        super.a();
        this.a.put("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j.1
            public Bundle a(Intent intent) {
                return j.this.c(intent, "com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
            }
        });
        this.a.put("com.android.internal.telephony.OperatorInfo", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j.2
            public Bundle a(Intent intent) {
                return j.this.c(intent, "com.android.internal.telephony.OperatorInfo");
            }
        });
        this.a.put("com.oplus.orms.info.OrmsNotifyParam", new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j.3
            public Bundle a(Intent intent) {
                return j.d(intent);
            }
        });
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(ActivityInfo activityInfo) {
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_special_activity_app_restrict_5920", "false")) && !ScreenUtil.isScreenOn()) {
            String str = activityInfo.packageName;
            if (!f() && e()) {
                Boolean a = a(str);
                Logger.i("SpecialPullAbility.Comp", "target pkg: %s is in notification start wl: %s", new Object[]{str, a});
                if (a != null) {
                    return !a.booleanValue();
                }
            }
            if (TextUtils.equals(AppBuildInfo.instance().getApplicationId(), str)) {
                Logger.i("SpecialPullAbility.Comp", "%s is not restrict", new Object[]{AppBuildInfo.instance().getApplicationId()});
                return false;
            }
            try {
                ApplicationInfo applicationInfo = BotBaseApplication.getContext().getPackageManager().getApplicationInfo(str, 0);
                if (applicationInfo == null) {
                    Logger.w("SpecialPullAbility.Comp", "no found pkg: %s", new Object[]{str});
                    return true;
                } else if ((applicationInfo.flags & 1) == 0) {
                    return a(new ComponentName(str, activityInfo.name));
                } else {
                    Logger.i("SpecialPullAbility.Comp", "%s is system app, no restrict", new Object[]{str});
                    return false;
                }
            } catch (Throwable th) {
                Logger.e("SpecialPullAbility.Comp", th);
                return true;
            }
        }
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean e() {
        if (CdUtils.b()) {
            return CdUtils.b("dd.oppo");
        }
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_oppo_background_start_pull_special_activity_by_notification_5980", "false"))) {
            return AliveSionAbility.instance().isSupport(Collections.singletonList("NotificationSubAbility"), "dd.oppo");
        }
        Logger.i("SpecialPullAbility.Comp", "ab false when background start by notification in oppo");
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean c() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_oppo_start_pull_special_activity", "false"));
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean f() {
        if (CdUtils.a()) {
            return CdUtils.a("dd.oppo");
        }
        if (RemoteConfig.instance().getBoolean("ab_oppo_start_pull_special_activity_direct_start_6160", false)) {
            return true;
        }
        return (ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext())) || DeviceCompatPermission.instance().hasPermission(BotBaseApplication.getContext(), "OVERLAY");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bundle c(Intent intent, String str) {
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
        if ("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse".equals(str)) {
            obtain2.writeString("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse");
            for (int i = 0; i < 9; i++) {
                obtain2.writeInt(0);
            }
            obtain2.writeInt(1);
            obtain2.writeString(null);
        } else {
            obtain2.writeString("com.android.internal.telephony.OperatorInfo");
            obtain2.writeString("");
            obtain2.writeString("");
            obtain2.writeString("");
            obtain2.writeSerializable(null);
        }
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void b() {
        List asList = Arrays.asList("com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse", "com.android.internal.telephony.OperatorInfo");
        for (int i = 26; i <= 29; i++) {
            this.b.put(Integer.valueOf(i), asList);
        }
        this.b.put(30, Arrays.asList("com.oplus.orms.info.OrmsNotifyParam", "com.mediatek.internal.telephony.ims.MtkDedicateDataCallResponse"));
        super.b();
    }
}