package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    public static final String a = null;
    public static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static boolean j = false;

    private boolean b(Uri uri) {
        Logger.i("LVUA.Buildin.RyzePopupAbility", "validate uri = " + uri);
        if (getContext().getPackageManager() == null) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "package manager is null");
            return false;
        }
        Intent intent = new Intent();
        intent.setData(uri);
        List<ResolveInfo> queryIntentActivitiesForComponentUtilsWithFilter = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.queryIntentActivitiesForComponentUtilsWithFilter(intent);
        if (queryIntentActivitiesForComponentUtilsWithFilter == null || queryIntentActivitiesForComponentUtilsWithFilter.isEmpty()) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "resolveInfo is null");
            return false;
        } else if (queryIntentActivitiesForComponentUtilsWithFilter.size() == 1) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivitiesForComponentUtilsWithFilter.get(0);
            if (resolveInfo == null || resolveInfo.activityInfo == null) {
                c.a("schema_error");
                return false;
            }
            Logger.i("LVUA.Buildin.RyzePopupAbility", "uri is correct");
            return true;
        } else {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "more intent");
            for (ResolveInfo resolveInfo2 : queryIntentActivitiesForComponentUtilsWithFilter) {
                if (resolveInfo2 != null && !TextUtils.isEmpty(resolveInfo2.resolvePackageName)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("schema_package", resolveInfo2.resolvePackageName);
                    c.a("exist_repeat_schema", hashMap);
                }
            }
            return false;
        }
    }

    /* renamed from: a */
    public StatusResult execute(IntentRequest intentRequest) {
        Uri data;
        try {
            Logger.d("LVUA.Buildin.RyzePopupAbility", "execute");
            if (intentRequest == null) {
                return new StatusResult(false, "request is null.");
            }
            if (!isSupport()) {
                return new StatusResult(false, "ryze not support");
            }
            if (!"RyzeProxyActivity".equals(intentRequest.getExtra())) {
                data = intentRequest.getIntent().getData();
                if (data == null) {
                    return new StatusResult(false, "uri is null");
                }
            } else if (ScreenUtils.instance().isScreenLocked()) {
                Logger.i("LVUA.Buildin.RyzePopupAbility", "screen locked");
                return new StatusResult(false, "screen locked");
            } else if (!ScreenUtils.instance().isScreenOn()) {
                Logger.i("LVUA.Buildin.RyzePopupAbility", "screen off");
                return new StatusResult(false, "screen off");
            } else {
                data = new Uri.Builder().scheme("ryze").authority("com.ryze.activity").appendPath("61700").appendQueryParameter("intent", intentRequest.getIntent().toUri(1)).appendQueryParameter("component_name", "RyzeProxyActivity").build();
            }
            Logger.i("LVUA.Buildin.RyzePopupAbility", "final uri:" + data.toString());
            RyzeConfig a2 = a();
            if (a2 == null) {
                return new StatusResult(false, "config error");
            }
            a(a2.aliveUri);
            return TextUtils.equals("broadcast", a2.type) ? a(data, a2.extra) : TextUtils.equals("service", a2.type) ? a(data) : new StatusResult(false, "type error");
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.RyzePopupAbility", th);
            c.a(th);
            return new StatusResult(false, "failed");
        }
    }

    private void a(String str) {
        try {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "isPulledAdSolution:" + j);
            if (j) {
                return;
            }
            Logger.i("LVUA.Buildin.RyzePopupAbility", "pullAliveSolution");
            j = true;
            getContext().getContentResolver().getType(Uri.parse(str));
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.RyzePopupAbility", th);
        }
    }

    public boolean isSupport() {
        HashMap hashMap = new HashMap();
        try {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "isSupport");
            RyzeConfig a2 = a();
            if (a2 == null) {
                hashMap.put("msg", "solutionConfig is null");
                c.a("ad_solution_not_support", hashMap);
                return false;
            } else if (e.a(StrategyFramework.getFrameworkContext().getPackageName())) {
                Logger.i("LVUA.Buildin.RyzePopupAbility", "pdd double open");
                hashMap.put("msg", "pdd_double_open");
                c.a("ad_solution_not_support", hashMap);
                return false;
            } else if (e.a()) {
                Logger.i("LVUA.Buildin.RyzePopupAbility", "system filter block");
                hashMap.put("msg", "system_filter");
                c.a("ad_solution_not_support", hashMap);
                return false;
            } else if (a(a2)) {
                Logger.i("LVUA.Buildin.RyzePopupAbility", "hit black list");
                hashMap.put("msg", "hit black list");
                c.a("ad_solution_not_support", hashMap);
                return false;
            } else {
                int i2 = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo("com.miui.systemAdSolution", 0).versionCode;
                Logger.i("LVUA.Buildin.RyzePopupAbility", "AdSolutionVersionCode:" + i2);
                hashMap.put("msg", "ad_solution_version_not_support");
                hashMap.put("adSolutionVersion", String.valueOf(i2));
                if (a2.maxSupportVersion >= a2.minSupportVersion) {
                    boolean z = ((long) i2) >= a2.minSupportVersion && ((long) i2) <= a2.maxSupportVersion;
                    if (!z) {
                        c.a("ad_solution_not_support", hashMap);
                    }
                    return z;
                } else if (a2.minSupportVersion > 0) {
                    boolean z2 = ((long) i2) >= a2.minSupportVersion;
                    if (!z2) {
                        c.a("ad_solution_not_support", hashMap);
                    }
                    return z2;
                } else {
                    List list = a2.banVersions;
                    if (list == null || list.isEmpty() || !list.contains(Integer.valueOf(i2))) {
                        return false;
                    }
                    Logger.i("LVUA.Buildin.RyzePopupAbility", "ban version " + i2);
                    return false;
                }
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.RyzePopupAbility", th);
            hashMap.put("msg", Log.getStackTraceString(th));
            c.a("ad_solution_not_support", hashMap);
            return false;
        }
    }

    private StatusResult a(Uri uri, String str) {
        Logger.i("LVUA.Buildin.RyzePopupAbility", "popupActivityByBroadcast");
        Intent intent = new Intent("miui.intent.miad.APP_INSTALL_NOTIFICATION.click");
        intent.putExtra("deeplink", uri.toString());
        if ("ryze".equals(uri.getScheme())) {
            com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(1);
        }
        if (!b(uri)) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "validate intent failed");
            return new StatusResult(false, "exist more intent activity");
        }
        try {
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("packageName")) {
                    intent.putExtra("packageName", jSONObject.optString("packageName", ""));
                }
                if (jSONObject.has("mediaType")) {
                    intent.putExtra("mediaType", jSONObject.optString("mediaType", ""));
                }
                if (jSONObject.has("uuid")) {
                    intent.putExtra("uuid", jSONObject.optString("uuid", ""));
                }
            }
        } catch (Throwable th) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", th);
        }
        Logger.i("LVUA.Buildin.RyzePopupAbility", "send broad cast:" + intent.toString());
        StrategyFramework.getFrameworkContext().sendBroadcast(intent);
        return new StatusResult(true, "success");
    }

    private RyzeConfig a() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.build_in_miui_ad_solution_key_61800", "");
        Logger.i("LVUA.Buildin.RyzePopupAbility", "raw config:" + configValue);
        if (TextUtils.isEmpty(configValue)) {
            return null;
        }
        Logger.i("LVUA.Buildin.RyzePopupAbility", "parse json, plugin name = " + AbilityFramework.getPluginName());
        IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
        RyzeConfig ryzeConfig = (RyzeConfig) pluginJSONFormatUtils.fromJson(configValue, RyzeConfig.class);
        Logger.i("LVUA.Buildin.RyzePopupAbility", "format json:" + pluginJSONFormatUtils.toJson(ryzeConfig));
        return ryzeConfig;
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze.a$1, android.content.ServiceConnection] */
    private StatusResult a(final Uri uri) {
        Logger.i("LVUA.Buildin.RyzePopupAbility", "popupActivityByService");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.miui.systemAdSolution", "com.miui.msa.internal.preinstall.v1.InternalPreInstallService"));
        com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(1);
        if (!b(uri)) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "validate intent failed");
            com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.setComponentEnabledState(2);
            return new StatusResult(false, "schema exist more");
        }
        ResolveInfo resolveService = getContext().getPackageManager().resolveService(intent, 0);
        if (resolveService != null && resolveService.serviceInfo != null) {
            c.a("bind_service_start");
            return getContext().bindService(intent, new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze.a.1
                /* JADX WARN: Multi-variable type inference failed */
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Logger.i("LVUA.Buildin.RyzePopupAbility", "onServiceConnected");
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken("com.miui.msa.internal.preinstall.v1.IInternalPreInstallAd");
                        obtain.writeInt(1);
                        obtain.writeInt(1);
                        obtain.writeLong(0L);
                        obtain.writeString("");
                        obtain.writeString("");
                        obtain.writeString("");
                        obtain.writeString(uri.toString());
                        obtain.writeString("");
                        obtain.writeString("");
                        obtain.writeLong(0L);
                        obtain.writeString("");
                        obtain.writeString("");
                        obtain.writeString("");
                        obtain.writeString(uri.toString());
                        obtain.writeInt(0);
                        obtain.writeLong(0L);
                        obtain.writeLong(0L);
                        obtain.writeStringList(new ArrayList());
                        obtain.writeStringList(new ArrayList());
                        obtain.writeInt(0);
                        obtain.writeLong(0L);
                        iBinder.transact(4, obtain, obtain2, 0);
                        c.a("bind_service_success");
                        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.build_in_miui_ad_solution_unbind_service_61700", true)) {
                            a.this.getContext().unbindService(this);
                        }
                        Logger.i("LVUA.Buildin.RyzePopupAbility", "onServiceConnected: scc");
                    } catch (Exception e2) {
                        c.a(e2);
                        Logger.e("LVUA.Buildin.RyzePopupAbility", e2);
                    } finally {
                        obtain.recycle();
                        obtain2.recycle();
                    }
                }

                public void onServiceDisconnected(ComponentName componentName) {
                    Logger.i("LVUA.Buildin.RyzePopupAbility", "onServiceDisconnected");
                }

                public void onBindingDied(ComponentName componentName) {
                }

                public void onNullBinding(ComponentName componentName) {
                }
            }, 1) ? new StatusResult(true, "success") : new StatusResult(false, "bind fail");
        }
        Logger.i("LVUA.Buildin.RyzePopupAbility", "service not exist.");
        c.a("service_not_exist");
        return new StatusResult(false, "service not exist.");
    }

    private boolean a(RyzeConfig ryzeConfig) {
        if (TextUtils.isEmpty(ryzeConfig.blackSceneId)) {
            return false;
        }
        SceneRequest sceneRequest = new SceneRequest(ryzeConfig.blackSceneId, Long.valueOf(ryzeConfig.refreshTTLMills), Long.valueOf(ryzeConfig.invalidTTLMills), (String) null, (String) null);
        BlackListItem config = ryzeConfig.mscUseSyncApi ? MSCManager.instance().getConfig(getContext(), sceneRequest) : MSCManager.instance().getCachedConfig(getContext(), sceneRequest);
        if (ryzeConfig.isIgnoreNoneBlack && config == null) {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "ignore null black");
            return false;
        } else if (config != null && !config.isBlack()) {
            return false;
        } else {
            Logger.i("LVUA.Buildin.RyzePopupAbility", "hit black list: %s, ability not support", new Object[]{ryzeConfig.blackSceneId});
            return true;
        }
    }
}