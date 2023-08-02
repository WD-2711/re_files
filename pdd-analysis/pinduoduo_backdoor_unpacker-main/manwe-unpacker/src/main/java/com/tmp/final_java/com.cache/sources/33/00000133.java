package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.utils.IPluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.AbilityFramework;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import java.util.List;

/* loaded from: b.class */
public class b extends BaseAbility implements a.AnonymousClass1 {
    static final String a = null;
    static final String b = null;
    static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private final a f = new a("shortcut".equals(a()));

    /* renamed from: a */
    public StatusResult execute(IntentRequest intentRequest) {
        try {
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "execute called");
            if (intentRequest == null) {
                return new StatusResult(false, "request intent is null");
            }
            String a2 = a(intentRequest.getIntent());
            if (TextUtils.isEmpty(a2)) {
                Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "target package name is null");
                return new StatusResult(false, "target package name is null");
            } else if (com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.e.a(a2)) {
                e.a("double_open", a2);
                return new StatusResult(false, a2 + "is double open app");
            } else if (!b(intentRequest.getIntent())) {
                Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "intent error");
                return new StatusResult(false, "uri error");
            } else if (TextUtils.isEmpty(a())) {
                Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "item type is null");
                return new StatusResult(false, "item type is null");
            } else {
                String uri = intentRequest.getIntent().toUri(0);
                String packageName = intentRequest.getIntent().getComponent() != null ? intentRequest.getIntent().getComponent().getPackageName() : getContext().getPackageName();
                Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "request intent uri: %s, pkg: %s", new Object[]{uri, packageName});
                RivanConfig b2 = b();
                if (b2 == null) {
                    return new StatusResult(false, "config is null");
                }
                String format = String.format(b2.itemExtraFormat, uri, packageName);
                Intent b3 = this.f.b(b2);
                b3.putExtra("itemType", a());
                b3.putExtra("item", format);
                Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "send broadcast: %s, item: %s", new Object[]{b3.toString(), format});
                getContext().sendBroadcast(b3);
                this.f.a();
                e.a("execute_success", null);
                return new StatusResult(true);
            }
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.PABgStartByPAAbility", "execute fail", th);
            e.a("execute_fail", th.getMessage());
            return new StatusResult(false, "failed");
        }
    }

    public boolean isSupport() {
        RivanConfig b2 = b();
        if (b2 == null) {
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "invalid config, ability not support");
            return false;
        }
        return this.f.a(b2);
    }

    private boolean b(Intent intent) {
        if (getContext().getPackageManager() == null) {
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "package manager is null");
            return false;
        }
        List<ResolveInfo> queryIntentActivitiesForComponentUtilsWithFilter = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.queryIntentActivitiesForComponentUtilsWithFilter(intent);
        if (queryIntentActivitiesForComponentUtilsWithFilter == null || queryIntentActivitiesForComponentUtilsWithFilter.isEmpty()) {
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "resolveInfo is null");
            return false;
        } else if (queryIntentActivitiesForComponentUtilsWithFilter.size() == 1) {
            return true;
        } else {
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "resolve intent size:" + queryIntentActivitiesForComponentUtilsWithFilter.size());
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "more intent");
            for (ResolveInfo resolveInfo : queryIntentActivitiesForComponentUtilsWithFilter) {
                if (resolveInfo != null && !TextUtils.isEmpty(resolveInfo.resolvePackageName)) {
                    e.a("exist_repeat_schema", resolveInfo.resolvePackageName);
                }
            }
            return false;
        }
    }

    protected String a() {
        return "";
    }

    public String a(Intent intent) {
        String str = intent.getPackage();
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        ComponentName component = intent.getComponent();
        String str2 = "";
        if (component != null) {
            str2 = component.getPackageName();
        }
        return str2;
    }

    private RivanConfig b() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.lvua_start_bg_by_pa_config_61900", "");
        if (TextUtils.isEmpty(configValue)) {
            Logger.w("LVUA.Dybuild.PABgStartByPAAbility", "empty config");
            return null;
        }
        try {
            IPluginJSONFormatUtils pluginJSONFormatUtils = PluginJSONFormatUtils.getInstance(StrategyFramework.getFrameworkContext(), AbilityFramework.getPluginName());
            RivanConfig rivanConfig = (RivanConfig) pluginJSONFormatUtils.fromJson(configValue, RivanConfig.class);
            Logger.i("LVUA.Dybuild.PABgStartByPAAbility", "raw config: %s, parsed config: %s", new Object[]{configValue, pluginJSONFormatUtils.toJson(rivanConfig)});
            return rivanConfig;
        } catch (Exception e2) {
            Logger.e("LVUA.Dybuild.PABgStartByPAAbility", "fail to parse config: " + configValue, e2);
            return null;
        }
    }
}