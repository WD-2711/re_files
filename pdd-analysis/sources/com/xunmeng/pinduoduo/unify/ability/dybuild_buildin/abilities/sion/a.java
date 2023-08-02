package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionInternalRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.sion.SionResult;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.EliseSubAbility;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.NotificationSubAbility;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.RyzeSubAbility;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.g;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.h;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.i;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.j;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private final SionConfig d;
    private static final Map c = new HashMap();
    private static final List e = Arrays.asList("RyzeSubAbility", "VarusCommonSubAbility", "VarusMiuiSubAbility");

    /* renamed from: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.a$a */
    /* loaded from: a$a.class */
    public class C0002a extends c {
        private final String b;
        private final String c;
        private final SionRequest d;
        private boolean e = false;

        @Override // com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.c
        public void onActivityCreated(Activity activity, Bundle bundle) {
            try {
                super.onActivityCreated(activity, bundle);
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "onActivityCreated:" + activity.getClass().getName());
                if (!TextUtils.equals(activity.getClass().getName(), this.c)) {
                    return;
                }
                Intent intent = activity.getIntent();
                HashMap hashMap = new HashMap(a.this.a(this.d, a("sion_cd_name", intent)));
                hashMap.put("msg", "success");
                hashMap.put("sub_ability_name", this.b);
                hashMap.put("callback_request_id", a("sion_req_id", intent));
                f.a("sion_start_success", hashMap);
                f.a("sion_start_success", (Map) hashMap);
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", this.c + " start success");
                this.e = true;
            } catch (Throwable th) {
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", th);
                f.a("onActivityStarted", th);
            }
        }

        void a(String str) {
            try {
                if (this.e) {
                    Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "activity start success, not report");
                    return;
                }
                HashMap hashMap = new HashMap(a.this.a(this.d, str));
                hashMap.put("msg", "fail");
                hashMap.put("callback_request_id", a("sion_req_id", null));
                f.a("sion_start_fail", hashMap);
                f.a("sion_start_fail", (Map) hashMap);
                this.e = false;
            } catch (Throwable th) {
                Logger.e("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", th);
                f.a("reportActivityStartFail", th);
            }
        }

        public C0002a(String str, String str2, SionRequest sionRequest) {
            a.this = r4;
            this.c = str2;
            this.d = sionRequest;
            this.b = str;
        }

        private String a(String str, Intent intent) {
            String str2 = "default";
            if (intent != null) {
                str2 = intent.getStringExtra(str);
                if (TextUtils.isEmpty(str2) && intent.getData() != null) {
                    Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "get " + str + "data from uri");
                    str2 = intent.getData().getQueryParameter(str);
                }
            }
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "key=" + str + ", value = " + str2);
            return str2;
        }
    }

    private void a(String str) {
        if ((AppBuildInfo.instance().isDEBUG() || (CommonHelper.instance().isHtjReady() && CommonHelper.instance().isHtj())) && Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("can't use " + str + " in main thread");
        }
    }

    private SionResult b(SionRequest sionRequest) {
        boolean booleanValue;
        if (sionRequest == null) {
            return new SionResult(false, "request is null");
        }
        String a2 = a(sionRequest.getCaller(), false);
        if (!TextUtils.isEmpty(a2)) {
            return new SionResult(false, a2);
        }
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "sion ability real do start");
        StatusResult a3 = a(sionRequest);
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "check intent isSuccess:" + a3.isSuccess() + ", error msg:" + a3.getErrorMsg());
        if (!a3.isSuccess()) {
            return new SionResult(false, a3.getErrorMsg());
        }
        List subAbilityNames = sionRequest.getSubAbilityNames();
        boolean z = false;
        if (subAbilityNames == null) {
            subAbilityNames = a();
            z = true;
        }
        ActivityInfo a4 = a(sionRequest.getIntent());
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "defaultSubAbilitySeq: %s, useDefaultSeq: %s", new Object[]{subAbilityNames, Boolean.valueOf(z)});
        String str = "";
        boolean a5 = a(a4);
        C0002a c0002a = null;
        HashMap hashMap = new HashMap();
        for (int i = 1; i <= subAbilityNames.size(); i++) {
            String str2 = (String) subAbilityNames.get(i - 1);
            ISubAbility iSubAbility = (ISubAbility) c.get(str2);
            if (iSubAbility == null) {
                Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "subAbility not exist: %s, seq: %d", new Object[]{str2, Integer.valueOf(i)});
            } else {
                if (RemoteConfig.instance().getBoolean("pinduoduo_android.dybuild_sion_check_double_open_inner_64600", false)) {
                    if (a4 == null) {
                        Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "[doStart] activityInfo is null");
                    } else if (this.d.checkDOPkgNamesByAbility != null && this.d.checkDOPkgNamesByAbility.containsKey(str2)) {
                        String str3 = (String) this.d.checkDOPkgNamesByAbility.get(str2);
                        String str4 = a4.packageName;
                        if (!TextUtils.isEmpty(str3) && str3.contains(str4)) {
                            Boolean bool = (Boolean) hashMap.get(str4);
                            if (bool == null) {
                                booleanValue = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a(str4);
                                hashMap.put(str4, Boolean.valueOf(booleanValue));
                            } else {
                                booleanValue = bool.booleanValue();
                            }
                            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "[doStart] doubel open check for:" + str4 + ", ret:" + booleanValue);
                            if (booleanValue) {
                                continue;
                            }
                        }
                    }
                }
                e isSupport = iSubAbility.isSupport();
                a(isSupport);
                if (!isSupport.isSuccess()) {
                    Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "subAbility not support: %s, seq: %d, msg: %s", new Object[]{str2, Integer.valueOf(i), isSupport.getErrorMsg()});
                } else {
                    a(sionRequest, str2, a4);
                    if (a5) {
                        c0002a = a(str2, sionRequest, a4);
                    }
                    Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "start execute subAbility: %s, seq: %d", new Object[]{str2, Integer.valueOf(i)});
                    StatusResult a6 = a(iSubAbility, str2, sionRequest);
                    if (a5 && c0002a != null) {
                        a(c0002a, str2, a6.isSuccess());
                    }
                    boolean isSuccess = a6.isSuccess();
                    str = a6.getErrorMsg();
                    Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "execute subAbility: %s, seq: %d, success: %s, error msg: %s", new Object[]{str2, Integer.valueOf(i), Boolean.valueOf(isSuccess), str});
                    if (isSuccess) {
                        Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "finish start: success");
                        return new SionResult(true, (String) null, str2, Integer.valueOf(i));
                    }
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("msg", a6.getErrorMsg());
                    hashMap2.put("subAbilityName", str2);
                    f.a("sub_execute_fail", hashMap2);
                }
            }
        }
        Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "finish start: fail");
        return new SionResult(false, "not support, reason:" + str);
    }

    private void a(e eVar) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.dybuild_sion_report_support_63600", false)) {
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "reportSionSupport hit gray");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("is_support", eVar.isSuccess() ? "1" : "0");
        hashMap.put("msg", eVar.getErrorMsg());
        hashMap.put("is_main_thread", String.valueOf(Looper.getMainLooper().getThread() == Thread.currentThread()));
        if (eVar.a != null && !eVar.a.isEmpty()) {
            hashMap.putAll(eVar.a);
        }
        f.a("support_result", hashMap);
    }

    private List a() {
        return this.d.subAbilitySeq;
    }

    private boolean b(Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.HOME");
    }

    private StatusResult a(SionRequest sionRequest) {
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "start check intent");
        Intent intent = sionRequest.getIntent();
        List b2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.b(intent);
        if (b2 == null || b2.size() == 0) {
            return new StatusResult(false, "Illegal intent, target activity not found");
        }
        if (b(intent)) {
            return new StatusResult(true, "home intent");
        }
        ActivityInfo activityInfo = ((ResolveInfo) b2.get(0)).activityInfo;
        if (activityInfo == null) {
            return new StatusResult(false, "Illegal intent, activity info is null");
        }
        if (b2.size() != 1) {
            ResolveInfo c2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.c(intent);
            if (c2 == null) {
                return new StatusResult(false, "Illegal intent, resolveActivity is null");
            }
            ActivityInfo activityInfo2 = c2.activityInfo;
            if (activityInfo2 == null) {
                return new StatusResult(false, "Illegal intent, resolveActivityInfo is null");
            }
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "resolveActivityInfo:name=" + activityInfo2.name + ",pkgName=" + activityInfo2.packageName + "; activityInfo:name=" + activityInfo.name + ",pkgName=" + activityInfo.packageName);
            if (!TextUtils.equals(activityInfo.packageName, activityInfo2.packageName) || !TextUtils.equals(activityInfo.name, activityInfo2.name)) {
                return new StatusResult(false, "Illegal intent, exist more activity");
            }
        }
        return (RemoteConfig.instance().getBoolean("pinduoduo_android.dybuild_sion_check_double_open_inner_64600", false) || !this.d.checkDOPkgNames.contains(activityInfo.packageName) || !com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a(activityInfo.packageName)) ? new StatusResult(true, "intent valid pass") : new StatusResult(false, "Illegal intent, " + activityInfo.packageName + " double open");
    }

    private StatusResult a(ISubAbility iSubAbility, String str, SionRequest sionRequest) {
        try {
            return iSubAbility.start(sionRequest);
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "execute subAbility exception: " + str, th);
            f.a("execute_exception", th);
            return new StatusResult(false, th.getMessage());
        }
    }

    private String b(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split.length <= 2 ? str : split[0] + "." + split[1];
    }

    private String a(String str, boolean z) {
        if (!this.d.callerWhiteList.contains(b(str))) {
            if (z) {
                f.a("not_support", " not in white list", str);
            }
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "[commonSupportCheck] callerWhiteList not contain " + str);
            return "callerWhiteList not contain " + str;
        } else if (!com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a()) {
            return "";
        } else {
            if (z) {
                f.a("not_support", "SystemFilter", str);
            }
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "[commonSupportCheck] system filter block");
            return "system filter";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [android.app.Application$ActivityLifecycleCallbacks, com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.a$a] */
    private C0002a a(String str, SionRequest sionRequest, ActivityInfo activityInfo) {
        Application a2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a();
        if (a2 == 0 || activityInfo == null || !activityInfo.packageName.equals(StrategyFramework.getFrameworkContext().getPackageName())) {
            return null;
        }
        String str2 = activityInfo.name;
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "activity name = " + str2);
        ?? c0002a = new C0002a(str, str2, sionRequest);
        a2.registerActivityLifecycleCallbacks(c0002a);
        return c0002a;
    }

    private void a(SionRequest sionRequest, String str, ActivityInfo activityInfo) {
        Intent intent = sionRequest.getIntent();
        if (activityInfo == null) {
            return;
        }
        if (!StrategyFramework.getFrameworkContext().getPackageName().equals(activityInfo.packageName)) {
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "target activity is not pdd");
            return;
        }
        Map extra = sionRequest.getExtra();
        Uri data = intent.getData();
        if (extra != null && !"true".equalsIgnoreCase(String.valueOf(extra.get("ignore_sion_req_id")))) {
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "ignore add intent");
            if (!intent.hasExtra("sion_req_id")) {
                intent.putExtra("sion_req_id", sionRequest.getRequestId());
            }
            if (e.contains(str) && data != null && !data.toString().contains("sion_req_id")) {
                data = data.buildUpon().appendQueryParameter("sion_req_id", sionRequest.getRequestId()).build();
            }
        }
        if (extra != null && "true".equals(String.valueOf(extra.get("carry_ability_name_to_intent")))) {
            intent.putExtra("sion_cd_name", str);
            if (e.contains(str) && data != null) {
                intent.setData(data.toString().contains("sion_cd_name") ? Uri.parse(data.toString().replaceAll("(sion_cd_name=[^&]*)", "sion_cd_name=" + str)) : data.buildUpon().appendQueryParameter("sion_cd_name", str).build());
            }
        }
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "final intent:" + intent.toUri(1));
    }

    public boolean isSupport(String str) {
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "enter isSupport()");
        return isSupport(new ArrayList(this.d.subAbilitySeq), str);
    }

    public boolean isSupport(List list, String str) {
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "enter isSupport, subAbilityNames=" + list + ",caller=" + str);
        a("isSupport");
        if (!TextUtils.isEmpty(a(str, true))) {
            return false;
        }
        Map map = this.d.secPatchMaxVersion;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            ISubAbility iSubAbility = (ISubAbility) c.get(str2);
            if (iSubAbility == null) {
                Logger.w("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "subAbility not exist: " + str2);
            } else if ("ForceDirectSubAbility".equals(str2)) {
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "ignore ForceDirectSubAbility isSupport");
            } else {
                if (map != null && map.containsKey(str2)) {
                    String str3 = (String) map.get(str2);
                    if (!TextUtils.isEmpty(str3) && com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.d.a(str3)) {
                        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "ability %s big than max secure patch version", new Object[]{str2});
                    }
                }
                e isSupport = iSubAbility.isSupport();
                a(isSupport);
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "exit isSupport= " + isSupport.isSuccess() + " , subAbility=" + str2);
                if (isSupport.isSuccess()) {
                    return true;
                }
            }
        }
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "exit isSupport=false");
        return false;
    }

    public a(SionInternalRequest sionInternalRequest, SionConfig sionConfig) {
        this.d = sionConfig;
    }

    private ActivityInfo a(Intent intent) {
        List b2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.b(intent);
        if (b2 == null || b2.isEmpty()) {
            return null;
        }
        return ((ResolveInfo) b2.get(0)).activityInfo;
    }

    static {
        c.put("DirectSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.b());
        c.put("RumbleSubAbility", new g());
        c.put("FloatSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.c());
        c.put("RyzeSubAbility", new RyzeSubAbility());
        c.put("NotificationSubAbility", new NotificationSubAbility());
        c.put("AlarmSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.a());
        c.put("RivanSmartSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.e());
        c.put("RivanSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.f());
        c.put("VarusCommonSubAbility", new h());
        c.put("VarusMiuiSubAbility", new i());
        c.put("EliseSubAbility", new EliseSubAbility());
        c.put("ForceDirectSubAbility", new com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.subAbility.d());
        c.put("VivoOverlaySubAbility", new j());
    }

    private boolean a(ActivityInfo activityInfo) {
        return AppBuildInfo.instance().getRealVersionCode() >= 15532160 && activityInfo != null && TextUtils.equals(activityInfo.packageName, StrategyFramework.getFrameworkContext().getPackageName()) && RemoteConfig.instance().getBoolean("pinduoduo_Android.dybuild_sion_listen_activity_lifecycle_63600", false);
    }

    public SionResult start(SionRequest sionRequest) {
        SionResult sionResult;
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "enter start, request=" + sionRequest);
        a("start");
        HashMap hashMap = new HashMap();
        try {
            sionResult = b(sionRequest);
            hashMap.put("execute_result", sionResult.isSuccess() ? "success" : "fail");
            hashMap.put("msg", sionResult.getErrorMsg());
            hashMap.put("sub_ability_seq", String.valueOf(sionResult.getSubAbilitySeq()));
            hashMap.putAll(a(sionRequest, sionResult.getSubAbilityName()));
            hashMap.put("is_main_thread", String.valueOf(Looper.getMainLooper().getThread() == Thread.currentThread()));
        } catch (Throwable th) {
            try {
                Logger.e("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "start exception: ", th);
                sionResult = new SionResult(false, "start exception");
                f.a("start_exception", th);
                f.a("sion_execute_result", hashMap);
                f.a("sion_execute_result", (Map) hashMap);
            } finally {
                f.a("sion_execute_result", hashMap);
                f.a("sion_execute_result", (Map) hashMap);
            }
        }
        Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "SionResult:" + sionResult.toString());
        return sionResult;
    }

    public Map a(SionRequest sionRequest, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("caller", sionRequest.getCaller());
        hashMap.put("request_id", sionRequest.getRequestId());
        hashMap.put("target_intent", sionRequest.getIntent().toUri(1));
        hashMap.put("ability_name", str);
        Map extra = sionRequest.getExtra();
        if (extra != null) {
            for (String str2 : extra.keySet()) {
                hashMap.put(str2, String.valueOf(extra.get(str2)));
            }
        }
        ActivityInfo a2 = a(sionRequest.getIntent());
        if (a2 != null) {
            hashMap.put("target_component", a2.name + "/" + a2.packageName);
            hashMap.put("is_listen_activity", a(a2) ? "1" : "0");
        }
        List subAbilityNames = sionRequest.getSubAbilityNames();
        if (subAbilityNames != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < subAbilityNames.size(); i++) {
                sb.append((String) subAbilityNames.get(i));
                if (i != subAbilityNames.size() - 1) {
                    sb.append(",");
                }
            }
            hashMap.put("sub_ability_names", sb.toString());
        }
        return hashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.a$1, java.lang.Runnable] */
    private void a(final C0002a c0002a, final String str, boolean z) {
        final Application a2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a();
        if (a2 == null) {
            return;
        }
        if (!z) {
            Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "unregisterActivityLifecycle by fail");
            a2.unregisterActivityLifecycleCallbacks(c0002a);
            return;
        }
        int i = this.d.delayRemoveLifecycleTime;
        if ("AlarmSubAbility".equals(str)) {
            i += 5000;
        }
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("DefaultSionAbilityImpl", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.a.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v4, types: [android.app.Application$ActivityLifecycleCallbacks, com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.a$a] */
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                Logger.i("LVUA.Dybuild.Sion.DefaultSionAbilityImpl", "delay unregisterActivityLifecycle");
                c0002a.a(str);
                a2.unregisterActivityLifecycleCallbacks(c0002a);
            }
        }, i);
    }
}
