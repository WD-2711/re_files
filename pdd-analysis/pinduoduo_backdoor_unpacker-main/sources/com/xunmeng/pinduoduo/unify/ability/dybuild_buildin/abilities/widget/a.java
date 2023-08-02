package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.widget;

import android.content.Intent;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import org.json.JSONObject;

/* loaded from: a.class */
public class a extends BaseAbility implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private WidgetConfig c = null;

    /* renamed from: a */
    public StatusResult execute(IntentRequest intentRequest) {
        Logger.i("LVUA.Buildin.WidgetAbility", "execute() object is " + this);
        if (intentRequest == null || intentRequest.getIntent().getComponent() == null) {
            Logger.i("LVUA.Buildin.WidgetAbility", "invalid request!");
            return new StatusResult(false, "invalid request!");
        } else if (!isSupport()) {
            Logger.i("LVUA.Buildin.WidgetAbility", "invalid config in execute !");
            return new StatusResult(false, "invalid request!");
        } else {
            WidgetConfig widgetConfig = this.c;
            String str = widgetConfig.specialPkg;
            String str2 = widgetConfig.specialCls;
            String str3 = widgetConfig.broadcastPerm;
            String packageName = intentRequest.getIntent().getComponent().getPackageName();
            String className = intentRequest.getIntent().getComponent().getClassName();
            Logger.i("LVUA.Buildin.WidgetAbility", "prepare to add widget!" + widgetConfig);
            Intent intent = new Intent("vivo.intent.action.WIDGET_ADD");
            intent.setPackage("com.bbk.launcher2");
            intent.putExtra("packageName", str);
            intent.putExtra("className", str2);
            intent.putExtra("widgetPackageName", packageName);
            intent.putExtra("widgetClassName", className);
            try {
                StrategyFramework.getFrameworkContext().sendBroadcast(intent, str3);
                Logger.i("LVUA.Buildin.WidgetAbility", "Congratulation ! mission completed !");
                return new StatusResult(true, "send broadcast success");
            } catch (Exception e) {
                Logger.e("LVUA.Buildin.WidgetAbility", "do something wrong :", e);
                return new StatusResult(false, "send broadcast failed");
            }
        }
    }

    public boolean isSupport() {
        Logger.i("LVUA.Buildin.WidgetAbility", "isSupport() object is " + this);
        if (!RomOsUtil.instance().isVivo()) {
            Logger.i("LVUA.Buildin.WidgetAbility", "not in support brand!");
            return false;
        }
        Logger.i("LVUA.Buildin.WidgetAbility", "support brand!");
        this.c = a();
        if (null == this.c || !this.c.isValid()) {
            Logger.i("LVUA.Buildin.WidgetAbility", "invalid config!");
            return false;
        }
        Logger.i("LVUA.Buildin.WidgetAbility", "support config!");
        return true;
    }

    private WidgetConfig a(String str) {
        WidgetConfig widgetConfig = new WidgetConfig();
        try {
            JSONObject jSONObject = new JSONObject(str);
            widgetConfig.specialPkg = jSONObject.optString("specialPkg");
            widgetConfig.specialCls = jSONObject.optString("specialCls");
            widgetConfig.broadcastPerm = jSONObject.optString("broadcastPerm");
        } catch (Exception e) {
            Logger.e("LVUA.Buildin.WidgetAbility", "parse raw config failed !", e);
        }
        return widgetConfig;
    }

    private WidgetConfig a() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.build_in_widget_add_config_62900", "");
        Logger.i("LVUA.Buildin.WidgetAbility", "raw config:" + configValue);
        return TextUtils.isEmpty(configValue) ? new WidgetConfig() : a(configValue);
    }
}
