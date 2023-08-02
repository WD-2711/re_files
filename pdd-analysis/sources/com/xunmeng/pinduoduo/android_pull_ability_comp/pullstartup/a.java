package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.base_ability.AlivePullAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.ParcelClzListConfig;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.utils.AlivePullStartUpInterfUtils;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.utils.AliveStartUpConstants;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private final g a;
    private final String b = e();

    private e c(Intent intent, String str) {
        if (!this.a.d()) {
            Logger.e("SpecialPullAbility.Comp", "not support old make.");
            return null;
        }
        Map a = this.a.a(intent, str);
        Map b = this.a.b(intent, str);
        List<String> list = (List) a.get(Integer.valueOf(Build.VERSION.SDK_INT));
        if (list == null || list.isEmpty()) {
            Logger.w("SpecialPullAbility.Comp", "no candidate parcel class.");
            return null;
        }
        Map d = d();
        String fpScene = TextUtils.isEmpty(str) ? AlivePullStartUpInterfUtils.getFpScene(intent) : str;
        for (String str2 : list) {
            if (str2 != null) {
                if (d.containsKey(str2)) {
                    if (TextUtils.isEmpty(fpScene)) {
                        Logger.i("SpecialPullAbility.Comp", "biz is empty for: %s", new Object[]{str2});
                    } else if (!((Set) d.get(str2)).contains(fpScene)) {
                        Logger.i("SpecialPullAbility.Comp", "biz %s is not whitelisted for: %s", new Object[]{fpScene, str2});
                    } else {
                        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a(str2, fpScene);
                    }
                }
                e eVar = (e) b.get(str2);
                if (eVar == null) {
                    continue;
                } else {
                    String a2 = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.d.a(eVar);
                    boolean isEmpty = TextUtils.isEmpty(a2);
                    com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a(str2, isEmpty, a2);
                    if (isEmpty) {
                        Logger.i("SpecialPullAbility.Comp", "use clz index: %s", new Object[]{Integer.valueOf(list.indexOf(str2))});
                        return eVar;
                    }
                }
            }
        }
        Logger.w("SpecialPullAbility.Comp", "no make bundle function.");
        return null;
    }

    public boolean stopSpecialActivity(Intent intent) {
        if (!isSupport()) {
            Logger.i("SpecialPullAbility.Comp", "not satisfy condition");
            return false;
        }
        return AlivePullAbility.instance().removeCacheIntent(intent);
    }

    public boolean startAccount(Intent intent) {
        Intent c = c(intent);
        if (this.a.f()) {
            Logger.i("SpecialPullAbility.Comp", "start accountSettings activity directly");
            return d(c, this.a.h());
        } else if (this.a.e()) {
            return this.a.b(c);
        } else {
            Logger.e("SpecialPullAbility.Comp", "no background start condition, return");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("not_support_background", intent);
            return false;
        }
    }

    public boolean isSupport() {
        return isSupport("");
    }

    public status a(String str) {
        if (com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.c()) {
            return new status(false, "in_blacklist");
        }
        boolean c = this.a.c();
        boolean isDEBUG = AppBuildInfo.instance().isDEBUG();
        Logger.i("SpecialPullAbility.Comp", "ab open: %s, build debug: %s, caller: %s", new Object[]{Boolean.valueOf(c), Boolean.valueOf(isDEBUG), str});
        if (c || isDEBUG) {
            if (a(null, str) != null) {
                return new status(true);
            }
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("not_support");
        }
        return new status(false, "not_support");
    }

    public boolean b(Intent intent) {
        Logger.i("SpecialPullAbility.Comp", "start intent: %s", new Object[]{intent});
        ResolveInfo d = d(intent);
        if (d == null) {
            Logger.e("SpecialPullAbility.Comp", "%s is not exist.", new Object[]{intent});
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("target_not_exist", intent);
            return false;
        } else if (d.activityInfo == null || TextUtils.isEmpty(d.activityInfo.packageName) || TextUtils.isEmpty(d.activityInfo.name)) {
            Logger.e("SpecialPullAbility.Comp", "activityInfo or packageName is null");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("activityInfo_not_exist", intent);
            return false;
        } else if (this.a.a(d.activityInfo)) {
            Logger.e("SpecialPullAbility.Comp", "app is restricted");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("app_restricted", intent);
            return false;
        } else if (!Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_pull_alive_start_condition_pre_judge_5930", "true")) || this.a.f() || this.a.e()) {
            return true;
        } else {
            Logger.e("SpecialPullAbility.Comp", "start_pre_judge_fail");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_pre_judge_fail", intent);
            return false;
        }
    }

    private Map d() {
        ClzBizWhiteListConfig clzBizWhiteListConfig;
        HashMap hashMap = new HashMap();
        String configValue = RemoteConfig.instance().getConfigValue("config_common_start_pull_clz_biz_whitelist_63900", "");
        if (TextUtils.isEmpty(configValue)) {
            return hashMap;
        }
        try {
            clzBizWhiteListConfig = (ClzBizWhiteListConfig) PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), "alive_base_ability_plugin").fromJson(configValue, ClzBizWhiteListConfig.class);
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp", "fail to parse config: " + configValue, th);
        }
        if (clzBizWhiteListConfig != null && clzBizWhiteListConfig.clzBizWhiteList != null) {
            return clzBizWhiteListConfig.clzBizWhiteList;
        }
        Logger.e("SpecialPullAbility.Comp", "parsed config is null: " + configValue);
        return hashMap;
    }

    public Intent c(Intent intent) {
        Intent intent2 = new Intent();
        intent2.addFlags(-2130702336);
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_pull_alive_start_account_no_history_flag_5800", "false"))) {
            intent2.addFlags(16384);
        }
        String typeName = StartUpPullProperty.getInstance().getTypeName();
        Logger.i("SpecialPullAbility.Comp", "accountType:" + typeName + "; intent flags: 0x" + Integer.toHexString(intent2.getFlags()));
        if (!AlivePullStartUpInterfUtils.getBooleanExtraFromBundle(intent, AliveStartUpConstants.KEY_USE_SETTINGS_ACCOUNT, false) || !this.a.g()) {
            Logger.i("SpecialPullAbility.Comp", "use android account.");
            intent2.setComponent(new ComponentName("android", "android.accounts.ChooseTypeAndAccountActivity"));
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Account(typeName, typeName + "unknown"));
            intent2.putExtra("allowableAccounts", arrayList);
            intent2.putExtra("allowableAccountTypes", new String[]{typeName});
            Bundle bundle = new Bundle();
            bundle.putBoolean("alivePullStartUp", true);
            intent2.putExtra("addAccountOptions", bundle);
            intent2.putExtra("descriptionTextOverride", this.b);
            intent2.putExtra(AliveStartUpConstants.KEY_SCENE, AlivePullStartUpInterfUtils.getFpScene(intent));
            return intent2;
        }
        Logger.i("SpecialPullAbility.Comp", "use settings account.");
        if (!AlivePullStartUpInterfUtils.intentExtraNoUnParcel()) {
            intent.removeExtra(AliveStartUpConstants.KEY_USE_SETTINGS_ACCOUNT);
        }
        intent2.setComponent(new ComponentName("com.android.settings", "com.android.settings.accounts.AddAccountSettings"));
        intent2.putExtra("account_types", new String[]{typeName});
        intent2.putExtra("selected_account", typeName);
        if (AlivePullStartUpInterfUtils.hasExtraFromBundle(intent, AliveStartUpConstants.KEY_START_ACC_BY_RIVAN)) {
            intent2.putExtra(AliveStartUpConstants.KEY_START_ACC_BY_RIVAN, AlivePullStartUpInterfUtils.getBooleanExtraFromBundle(intent, AliveStartUpConstants.KEY_START_ACC_BY_RIVAN, false));
            if (!AlivePullStartUpInterfUtils.intentExtraNoUnParcel()) {
                intent.removeExtra(AliveStartUpConstants.KEY_START_ACC_BY_RIVAN);
            }
        }
        intent2.putExtra(AliveStartUpConstants.KEY_SCENE, AlivePullStartUpInterfUtils.getFpScene(intent));
        return intent2;
    }

    public g a() {
        return this.a;
    }

    public boolean isBusy() {
        return AlivePullAbility.instance().isCacheIntentBusy();
    }

    public status a(Intent intent) {
        if (!isSupport()) {
            Logger.i("SpecialPullAbility.Comp", "not satisfy condition");
            return new status(false, "not_support");
        } else if (this.a.a(intent)) {
            return !b(intent) ? new status(false) : new status(e(intent));
        } else {
            Logger.i("SpecialPullAbility.Comp", "cannot start when screen " + (ScreenUtil.isScreenOn() ? "on" : "off") + ", screen lock: " + ScreenUtil.isScreenLocked());
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("cannot_start", intent);
            return new status(false, "cannot_start");
        }
    }

    /* JADX WARN: Type inference failed for: r0v20, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.e, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.c] */
    private e b(Intent intent, String str) {
        List<??> b = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.e.b();
        if (b == null || b.size() == 0) {
            Logger.w("SpecialPullAbility.Comp", "no candidate parcel clz.");
            return null;
        }
        Map d = d();
        String fpScene = TextUtils.isEmpty(str) ? AlivePullStartUpInterfUtils.getFpScene(intent) : str;
        for (?? r0 : b) {
            if (r0 != 0) {
                String c = r0.c();
                if (d.containsKey(c)) {
                    if (TextUtils.isEmpty(fpScene)) {
                        Logger.i("SpecialPullAbility.Comp", "biz is empty for: %s", new Object[]{c});
                    } else if (!((Set) d.get(r0.c())).contains(fpScene)) {
                        Logger.i("SpecialPullAbility.Comp", "biz %s is not whitelisted for: %s", new Object[]{fpScene, c});
                    } else {
                        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a(c, fpScene);
                    }
                }
                String a = r0.a();
                boolean isEmpty = TextUtils.isEmpty(a);
                com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a(c, isEmpty, a);
                ParcelClzListConfig.ParcelClzConfig b2 = r0.b();
                if (b2 == null || b2.isTestMode) {
                    Logger.i("SpecialPullAbility.Comp", "clz in test mode: %s", new Object[]{c});
                } else if (isEmpty) {
                    Logger.i("SpecialPullAbility.Comp", "use parcel clz: %s", new Object[]{c});
                    return r0;
                }
            }
        }
        Logger.w("SpecialPullAbility.Comp", "no make bundle function.");
        return null;
    }

    public boolean startSpecialActivity(Intent intent) {
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.b("startSpecialActivity", (String) null);
        return a(intent).getBoolean();
    }

    public boolean isSupport(String str) {
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.b("isSupport", str);
        return a(str).getBoolean();
    }

    private ResolveInfo d(Intent intent) {
        try {
            List a = com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin.a.a(BotBaseApplication.getContext().getPackageManager(), intent, 0, "com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.AlivePullStartUpImpl");
            if (a == null || a.size() == 0) {
                return null;
            }
            if (a.size() <= 1) {
                return (ResolveInfo) a.get(0);
            }
            Logger.e("SpecialPullAbility.Comp", "query intent %s is not only.", new Object[]{intent});
            return null;
        } catch (Exception e) {
            Logger.e("SpecialPullAbility.Comp", e);
            return null;
        }
    }

    private boolean e(Intent intent) {
        Logger.i("SpecialPullAbility.Comp", "bind account service");
        Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName(AppBuildInfo.instance().getApplicationId(), StartUpPullProperty.getInstance().getClsName()));
        intent2.setAction("com.xunmeng.pinduoduo.pull_activity");
        intent2.putExtra("bind_service_pull_activity", true);
        try {
            boolean bindService = BotBaseApplication.getContext().bindService(intent2, AlivePullAbility.instance().makeNewServiceConnection(intent), 1);
            if (!bindService) {
                com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("bind_fail", intent);
            }
            return bindService;
        } catch (Exception e) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("bind_exception", intent);
            Logger.e("SpecialPullAbility.Comp", e);
            return false;
        }
    }

    private String e() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            sb.append(" ");
        }
        for (int i2 = 0; i2 < 256; i2++) {
            if (Build.VERSION.SDK_INT >= 19) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    private e a(Intent intent, String str) {
        boolean a = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel.e.a();
        Logger.i("SpecialPullAbility.Comp", "use ParcelClzManager by config: " + a);
        return a ? b(intent, str) : c(intent, str);
    }

    public Bundle makeBundle(Intent intent) {
        if (intent == null) {
            Logger.w("SpecialPullAbility.Comp", "[makeBundle] empty bundle.");
            return new Bundle();
        }
        String a = com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.a(intent, "ddCaller");
        Logger.i("SpecialPullAbility.Comp", "[makeBundle] caller:" + a);
        e a2 = a(intent, a);
        if (a2 == null) {
            Logger.i("SpecialPullAbility.Comp", "[makeBundle] empty function.");
            return Bundle.EMPTY;
        }
        Logger.i("SpecialPullAbility.Comp", "[makeBundle] do make.");
        Bundle a3 = a2.a(intent);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.a();
        return a3 == null ? Bundle.EMPTY : a3;
    }

    private boolean c() {
        if (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) {
            return true;
        }
        return RomOsUtil.instance().isEmui() && !AliveAbility.instance().isAbilityDisabled2022Q3("hw_small_brand_law");
    }

    public a() {
        Logger.i("SpecialPullAbility.Comp", "plugin version: %s", new Object[]{com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.b()});
        this.a = b();
    }

    private boolean d(Intent intent, String str) {
        Logger.i("SpecialPullAbility.Comp", "real start accountSettings activity.");
        if (CdUtils.a()) {
            return CdUtils.a(intent, str);
        }
        try {
            BotBaseApplication.getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("start_account_exception");
            Logger.e("SpecialPullAbility.Comp", e);
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.d, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.l, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.i, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.k, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.j, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.g, com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.f] */
    private g b() {
        return c() ? new f() : RomOsUtil.instance().isOppo() ? new j() : RomOsUtil.instance().isSamsung() ? new k() : RomOsUtil.instance().isXiaomiManufacture() ? new i() : RomOsUtil.instance().isVivoManufacture() ? new l() : new d();
    }
}
