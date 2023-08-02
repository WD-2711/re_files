package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: b.class */
public class b extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;

    /* loaded from: b$a.class */
    public class a implements a.AnonymousClass1 {
        private final RumbleConfig b;
        private final Intent c;
        private final int d;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.b$a$1, java.lang.Runnable] */
        public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            if (this.b.delayTime <= 0) {
                b.this.a(this.b, this.c, this.d, this, iBinder);
            } else {
                ThreadPool.instance().ioTaskDelay(ThreadBiz.CS, "RumbleStartAbility$RumbleServiceConnect", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.b.a.1
                    /* JADX WARN: Type inference failed for: r4v1, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.b$a, android.content.ServiceConnection] */
                    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                    public void run() {
                        b.this.a(a.this.b, a.this.c, a.this.d, a.this, iBinder);
                    }
                }, this.b.delayTime);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }

        public void onBindingDied(ComponentName componentName) {
        }

        public a(RumbleConfig rumbleConfig, Intent intent, int i) {
            this.b = rumbleConfig;
            this.c = intent;
            this.d = i;
        }

        public void onNullBinding(ComponentName componentName) {
        }
    }

    private Intent b(RumbleConfig rumbleConfig) {
        Intent intent = new Intent(rumbleConfig.serviceAction);
        intent.setPackage(rumbleConfig.pkgName);
        intent.setComponent(new ComponentName(rumbleConfig.pkgName, rumbleConfig.serviceName));
        return intent;
    }

    private RumbleConfig a() {
        Integer[] numArr;
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.build_in_rumble_start_ability_key_62100", "");
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        RumbleConfig rumbleConfig = new RumbleConfig();
        try {
            JSONObject jSONObject = new JSONObject(configValue);
            if (jSONObject.has("pkgName")) {
                rumbleConfig.pkgName = jSONObject.getString("pkgName");
            }
            if (jSONObject.has("serviceName")) {
                rumbleConfig.serviceName = jSONObject.getString("serviceName");
            }
            if (jSONObject.has("serviceAction")) {
                rumbleConfig.serviceAction = jSONObject.getString("serviceAction");
            }
            if (jSONObject.has("descriptor")) {
                rumbleConfig.descriptor = jSONObject.getString("descriptor");
            }
            if (jSONObject.has("blackSceneId")) {
                rumbleConfig.blackSceneId = jSONObject.getString("blackSceneId");
            }
            if (jSONObject.has("mscUseSyncApi")) {
                rumbleConfig.mscUseSyncApi = jSONObject.getBoolean("mscUseSyncApi");
            }
            if (jSONObject.has("refreshTTLMills")) {
                rumbleConfig.refreshTTLMills = jSONObject.getLong("refreshTTLMills");
            }
            if (jSONObject.has("invalidTTLMills")) {
                rumbleConfig.invalidTTLMills = jSONObject.getLong("invalidTTLMills");
            }
            if (jSONObject.has("isIgnoreNoneBlack")) {
                rumbleConfig.isIgnoreNoneBlack = jSONObject.getBoolean("isIgnoreNoneBlack");
            }
            if (jSONObject.has("delayTime")) {
                rumbleConfig.delayTime = jSONObject.getInt("delayTime");
            }
            if (jSONObject.has("banKeyExtras")) {
                JSONArray jSONArray = jSONObject.getJSONArray("banKeyExtras");
                rumbleConfig.banKeyExtras = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    rumbleConfig.banKeyExtras.add(jSONArray.getString(i));
                }
            }
            if (jSONObject.has("transactCodeMap")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("transactCodeMap");
                Iterator<String> keys = jSONObject2.keys();
                rumbleConfig.transactCodeMap = new HashMap();
                while (keys.hasNext()) {
                    String next = keys.next();
                    JSONArray optJSONArray = jSONObject2.optJSONArray(next);
                    if (optJSONArray != null) {
                        numArr = new Integer[optJSONArray.length()];
                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                            numArr[i2] = Integer.valueOf(optJSONArray.getInt(i2));
                        }
                    } else {
                        numArr = new Integer[0];
                    }
                    rumbleConfig.transactCodeMap.put(Integer.valueOf(NumberUtils.instance().parseInt(next)), numArr);
                }
            }
        } catch (Throwable th) {
            Logger.i("LVUA.Buildin.RumbleStartAbility", th);
        }
        return rumbleConfig;
    }

    public boolean isSupport() {
        try {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "execute rumble isSupport");
            RumbleConfig a2 = a();
            if (a2 == null) {
                Logger.i("LVUA.Buildin.RumbleStartAbility", "rumble config is null");
                return false;
            }
            Map map = a2.transactCodeMap;
            if (map == null || map.isEmpty()) {
                Logger.e("LVUA.Buildin.RumbleStartAbility", "transact code map is none");
                return false;
            } else if (e.a()) {
                Logger.i("LVUA.Buildin.RumbleStartAbility", "system filter block");
                a("system_filter", "system filter block");
                return false;
            } else if (!com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.hasSingleServiceForIntent(b(a2))) {
                Logger.i("LVUA.Buildin.RumbleStartAbility", "exist more service");
                a("not_support", "exist more service");
                return false;
            } else {
                int d = d(a2);
                if (d <= 0) {
                    return false;
                }
                HashSet hashSet = new HashSet();
                for (Integer[] numArr : map.values()) {
                    if (numArr != null) {
                        hashSet.addAll(Arrays.asList(numArr));
                    }
                }
                if (!hashSet.contains(Integer.valueOf(d))) {
                    Logger.i("LVUA.Buildin.RumbleStartAbility", "not support version:" + d);
                    a("version_not_match", String.valueOf(d));
                    return false;
                } else if (!a(a2)) {
                    return true;
                } else {
                    Logger.i("LVUA.Buildin.RumbleStartAbility", "hit black list");
                    a("not_support", "hit black");
                    return false;
                }
            }
        } catch (Throwable th) {
            a("support_error", Logger.getStackTraceString(th));
            Logger.e("LVUA.Buildin.RumbleStartAbility", th);
            return false;
        }
    }

    private StatusResult a(RumbleConfig rumbleConfig, Intent intent) {
        List queryIntentActivitiesForComponentUtilsWithFilter = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.queryIntentActivitiesForComponentUtilsWithFilter(intent);
        if (queryIntentActivitiesForComponentUtilsWithFilter == null || queryIntentActivitiesForComponentUtilsWithFilter.size() != 1) {
            a("execute_failed", "target activity more one or not exist");
            return new StatusResult(false, "more activity or activity not exist");
        }
        ActivityInfo activityInfo = ((ResolveInfo) queryIntentActivitiesForComponentUtilsWithFilter.get(0)).activityInfo;
        if (activityInfo == null) {
            a("execute_failed", "target activity not exist");
            return new StatusResult(false, "activity error");
        } else if (!activityInfo.enabled) {
            a("execute_failed", "target activity disable");
            return new StatusResult(false, "target activity disable");
        } else if (!activityInfo.exported) {
            a("execute_failed", "target activity not exported");
            return new StatusResult(false, "activity exported is false");
        } else {
            PackageManager packageManager = getContext().getPackageManager();
            if (TextUtils.isEmpty(activityInfo.permission) || packageManager.checkPermission(activityInfo.permission, rumbleConfig.pkgName) == 0) {
                return null;
            }
            a("execute_failed", "target activity not support permission");
            return new StatusResult(false, "start activity need grant permission " + activityInfo.permission);
        }
    }

    private Integer c(RumbleConfig rumbleConfig) {
        Map map = rumbleConfig.transactCodeMap;
        if (map == null) {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "transact code map is none");
            return null;
        }
        PackageInfo packageInfoForPackageName = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.getPackageInfoForPackageName(rumbleConfig.pkgName);
        if (packageInfoForPackageName == null) {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "package info is none");
            return null;
        }
        int i = packageInfoForPackageName.versionCode;
        for (Map.Entry entry : map.entrySet()) {
            Integer num = (Integer) entry.getKey();
            Integer[] numArr = (Integer[]) entry.getValue();
            if (num != null && numArr != null) {
                for (Integer num2 : numArr) {
                    if (num2.intValue() == i) {
                        return num;
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble.b$a, android.content.ServiceConnection] */
    private StatusResult a(Intent intent, RumbleConfig rumbleConfig) {
        Intent b2 = b(rumbleConfig);
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        Integer c2 = c(rumbleConfig);
        if (c2 == null) {
            a("execute_failed", "get transactCode failed");
            return new StatusResult(false, "get transact code failed");
        }
        boolean bindService = frameworkContext.bindService(b2, new a(rumbleConfig, intent, c2.intValue()), 1);
        if (!bindService) {
            a("execute_failed", "bind failed");
        }
        return new StatusResult(bindService, bindService ? "bind service success" : "bind service failed");
    }

    private static void a(String str, Map map) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_rumble_track_key_62100", false)) {
            return;
        }
        HashMap hashMap = new HashMap();
        if (map != null) {
            hashMap.putAll(map);
        }
        Logger.i("LVUA.Buildin.RumbleStartAbility", "start track event");
        hashMap.put("log_action", str);
        hashMap.put("business", "RumbleStartAbility");
        g.a("", 23134263, new TrackEventOption(hashMap, "perf", "alive", 0));
        Logger.i("LVUA.Buildin.RumbleStartAbility", "tracker end");
    }

    /* renamed from: a */
    public StatusResult execute(IntentRequest intentRequest) {
        try {
            if (intentRequest == null) {
                return new StatusResult(false, "request is none");
            }
            RumbleConfig a2 = a();
            if (a2 == null) {
                return new StatusResult(false, "rumble config is none");
            }
            StringBuilder sb = new StringBuilder();
            for (String str : a2.banKeyExtras) {
                if (intentRequest.getIntent().hasExtra(str)) {
                    sb.append(str).append(",");
                }
            }
            if (!TextUtils.isEmpty(sb.toString())) {
                a("execute_failed", "extras illegal");
                return new StatusResult(false, "error extras:" + sb.toString());
            }
            Logger.e("LVUA.Buildin.RumbleStartAbility", "start intent is " + intentRequest.getIntent().toUri(1));
            if ("RumbleProxyActivity".equals(intentRequest.getExtra())) {
                Logger.i("LVUA.Buildin.RumbleStartAbility", "start rumble proxy activity");
                a("rumble_activity_proxy_ready", "");
                return a(a(intentRequest.getIntent()), a2);
            }
            Logger.i("LVUA.Buildin.RumbleStartAbility", "start target activity directly");
            StatusResult a3 = a(a2, intentRequest.getIntent());
            return a3 == null ? a(intentRequest.getIntent(), a2) : a3;
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.RumbleStartAbility", th);
            a("execute_error", Logger.getStackTraceString(th));
            return new StatusResult(false, th.getMessage());
        }
    }

    private boolean a(RumbleConfig rumbleConfig) {
        if (TextUtils.isEmpty(rumbleConfig.blackSceneId)) {
            return false;
        }
        SceneRequest sceneRequest = new SceneRequest(rumbleConfig.blackSceneId, Long.valueOf(rumbleConfig.refreshTTLMills), Long.valueOf(rumbleConfig.invalidTTLMills), (String) null, (String) null);
        BlackListItem config = rumbleConfig.mscUseSyncApi ? MSCManager.instance().getConfig(getContext(), sceneRequest) : MSCManager.instance().getCachedConfig(getContext(), sceneRequest);
        if (rumbleConfig.isIgnoreNoneBlack && config == null) {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "ignore null black");
            return false;
        } else if (config != null && !config.isBlack()) {
            return false;
        } else {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "hit black list: %s, ability not support", new Object[]{rumbleConfig.blackSceneId});
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(RumbleConfig rumbleConfig, Intent intent, int i, ServiceConnection serviceConnection, IBinder iBinder) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(rumbleConfig.descriptor);
            obtain.writeInt(1);
            intent.writeToParcel(obtain, 0);
            if (!iBinder.transact(i, obtain, obtain2, 0)) {
                a("execute_failed", "return status false");
                Logger.e("LVUA.Buildin.RumbleStartAbility", "start activity failed after connected");
                return;
            }
            obtain2.readException();
            boolean z = 0 != obtain2.readInt();
            Logger.i("LVUA.Buildin.RumbleStartAbility", "result:" + z);
            HashMap hashMap = new HashMap();
            hashMap.put("search_ver", String.valueOf(d(rumbleConfig)));
            if (intent.getData() != null) {
                Uri data = intent.getData();
                hashMap.put("target_uri", data.getScheme() + "://" + data.getAuthority());
            }
            if (intent.getComponent() != null) {
                hashMap.put("target_pkg", intent.getComponent().getPackageName());
                hashMap.put("target_component", intent.getComponent().getPackageName());
            } else if (intent.getPackage() != null) {
                hashMap.put("target_pkg", intent.getPackage());
            }
            if (z) {
                hashMap.put("msg", "rumble success");
                a("execute_success", hashMap);
            } else {
                hashMap.put("msg", "rumble failed");
                a("execute_failed", hashMap);
            }
        } catch (Throwable th) {
            try {
                Logger.e("LVUA.Buildin.RumbleStartAbility", th);
                a("connect_error", Logger.getStackTraceString(th));
            } finally {
                obtain2.recycle();
                obtain.recycle();
                getContext().unbindService(serviceConnection);
            }
        }
    }

    private int d(RumbleConfig rumbleConfig) {
        PackageInfo packageInfoForPackageName = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.getPackageInfoForPackageName(rumbleConfig.pkgName);
        if (packageInfoForPackageName == null) {
            Logger.i("LVUA.Buildin.RumbleStartAbility", "pkg not installed, ability not support");
            a("not_support", "pkg not installed");
            return -1;
        }
        int i = packageInfoForPackageName.versionCode;
        Logger.i("LVUA.Buildin.RumbleStartAbility", "ability pkg version code: %d, version name: %s", new Object[]{Integer.valueOf(i), packageInfoForPackageName.versionName});
        return i;
    }

    private Intent a(Intent intent) {
        ComponentName componentName = new ComponentName(getContext(), "com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.ryze.RyzeActivity");
        com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(1);
        Uri build = new Uri.Builder().scheme("ryze").authority("com.ryze.activity").appendPath("61700").appendQueryParameter("component_name", "RumbleProxyActivity").build();
        Intent intent2 = new Intent();
        intent2.putExtra("realIntent", intent);
        intent2.setData(build);
        intent2.setComponent(componentName);
        intent2.setPackage(getContext().getPackageName());
        return intent2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("msg", str2);
        a(str, hashMap);
    }
}