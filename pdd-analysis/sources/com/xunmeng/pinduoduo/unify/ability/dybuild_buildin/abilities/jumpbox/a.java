package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox;

import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.BlackListItem;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.msc.SceneRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MSCManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.PluginStrategyService;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.g;
import java.util.HashMap;
import java.util.List;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private Integer b;

    private boolean b(JumpBoxInfo jumpBoxInfo) {
        if (jumpBoxInfo.minSupportVersion == 32768 && jumpBoxInfo.maxSupportVersion == -32769) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "Varus do not check version");
            return true;
        }
        int d = d(jumpBoxInfo);
        if (d < 0) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "version less zero");
            return false;
        }
        List list = jumpBoxInfo.banVersions;
        if (list != null && !list.isEmpty() && list.contains(Integer.valueOf(d))) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "ban version " + d);
            return false;
        } else if (d >= jumpBoxInfo.minSupportVersion && d <= jumpBoxInfo.maxSupportVersion) {
            return true;
        } else {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "version do not support");
            return false;
        }
    }

    /* renamed from: a */
    public StatusResult execute(IntentRequest intentRequest) {
        try {
            JumpBoxInfo b = b();
            if (intentRequest == null || b == null) {
                a("execute", "fail_jumpBoxInfo_None", null);
                return new StatusResult(false, "request or jumpBoxInfo is null");
            }
            String caller = intentRequest.getCaller();
            a("execute_start", "start", caller);
            StatusResult a2 = a(intentRequest, b);
            Logger.i("LVUA.Buildin.JumpBoxAbility", "isSuccess:" + a2.isSuccess() + ", error msg:" + a2.getErrorMsg() + ",caller:" + caller);
            if (a2.isSuccess()) {
                a("execute_success", "success", caller);
            } else {
                a("execute_failed", a2.getErrorMsg(), caller);
            }
            return a2;
        } catch (Throwable th) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", th);
            a("error", Logger.getStackTraceString(th), null);
            return new StatusResult(false, th.getMessage());
        }
    }

    protected void a(String str, String str2, String str3) {
        try {
            JumpBoxInfo b = b();
            if (b == null) {
                return;
            }
            if (!RemoteConfig.instance().getBoolean(b.trackerAbKey, false)) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "tracker hit gray");
                return;
            }
            HashMap hashMap = new HashMap();
            if (!TextUtils.isEmpty(str2)) {
                hashMap.put("msg", str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                hashMap.put("caller", str3);
            }
            hashMap.put("jumpBoxAppVersion", Integer.valueOf(this.b == null ? -1 : this.b.intValue()));
            hashMap.put("log_action", str);
            hashMap.put("business", b.abilityName);
            hashMap.put("romOsVersion", RomOsUtil.instance().getVersion());
            String pluginName = AbilityFramework.getPluginName();
            if (pluginName != null) {
                hashMap.put("plugin_version", PluginStrategyService.instance(pluginName).getRunningPluginVersion());
                hashMap.put("plugin_name", pluginName);
            }
            g.a("", 23199779, new TrackEventOption(hashMap, "perf", "alive", 0));
            Logger.i("LVUA.Buildin.JumpBoxAbility", "tracker pmm end");
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.JumpBoxAbility", th);
        }
    }

    private JumpBoxInfo b() {
        if (TextUtils.isEmpty(a())) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "getJumpBoxKey is null");
            return null;
        }
        IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
        String configValue = RemoteConfig.instance().getConfigValue(a(), "");
        if (TextUtils.isEmpty(configValue)) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "jumBoxInfoStr is none");
            return null;
        }
        JumpBoxInfo jumpBoxInfo = (JumpBoxInfo) pluginJSONFormatUtils.fromJson(configValue, JumpBoxInfo.class);
        if (jumpBoxInfo == null || TextUtils.isEmpty(jumpBoxInfo.abilityName)) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "jumpBoxInfo is null");
            return null;
        }
        Logger.i("LVUA.Buildin.JumpBoxAbility", "format json:" + pluginJSONFormatUtils.toJson(jumpBoxInfo));
        return jumpBoxInfo;
    }

    protected boolean a(JumpBoxInfo jumpBoxInfo) {
        return true;
    }

    public boolean isSupport() {
        try {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "isSupport");
            JumpBoxInfo b = b();
            if (b == null) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "jumpBoxInfo is null");
                return false;
            } else if (e.a(StrategyFramework.getFrameworkContext().getPackageName())) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "pdd double open");
                a("not_support", "pdd_double_open", null);
                return false;
            } else if (e.a()) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "system filter block");
                a("not_support", "system_block", null);
                return false;
            } else if (!b(b)) {
                a("not_support", "version_not_match", null);
                return false;
            } else if (!a(b)) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "isSupportBySpecialAbility is false");
                a("not_support", "special_ability_not_support", null);
                return false;
            } else if (!c(b)) {
                return true;
            } else {
                a("not_support", "hit_black", null);
                Logger.i("LVUA.Buildin.JumpBoxAbility", "hit black list");
                return false;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.JumpBoxAbility", th);
            return false;
        }
    }

    protected String a() {
        return null;
    }

    private boolean c(JumpBoxInfo jumpBoxInfo) {
        try {
            if (TextUtils.isEmpty(jumpBoxInfo.blackSceneId)) {
                return false;
            }
            SceneRequest sceneRequest = new SceneRequest(jumpBoxInfo.blackSceneId, Long.valueOf(jumpBoxInfo.refreshTTLMills), Long.valueOf(jumpBoxInfo.invalidTTLMills), (String) null, (String) null);
            BlackListItem config = jumpBoxInfo.mscUseSyncApi ? MSCManager.instance().getConfig(getContext(), sceneRequest) : MSCManager.instance().getCachedConfig(getContext(), sceneRequest);
            if (jumpBoxInfo.isIgnoreNoneBlack && config == null) {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "ignore null black");
                return false;
            } else if (config != null && !config.isBlack()) {
                return false;
            } else {
                Logger.i("LVUA.Buildin.JumpBoxAbility", "hit black list: %s, ability not support", new Object[]{jumpBoxInfo.blackSceneId});
                return true;
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Buildin.JumpBoxAbility", th);
            a("error_black", Logger.getStackTraceString(th), null);
            return false;
        }
    }

    protected StatusResult a(IntentRequest intentRequest, JumpBoxInfo jumpBoxInfo) {
        return new StatusResult(false, "execute end");
    }

    private int d(JumpBoxInfo jumpBoxInfo) {
        if (this.b != null) {
            return this.b.intValue();
        }
        PackageInfo a2 = b.a(jumpBoxInfo.jumpBoxPkgName);
        if (a2 == null) {
            Logger.i("LVUA.Buildin.JumpBoxAbility", "pkg not installed, ability not support");
            return -1;
        }
        this.b = Integer.valueOf(a2.versionCode);
        Logger.i("LVUA.Buildin.JumpBoxAbility", "ability pkg version code: %d, version name: %s", new Object[]{this.b, a2.versionName});
        return this.b.intValue();
    }
}
