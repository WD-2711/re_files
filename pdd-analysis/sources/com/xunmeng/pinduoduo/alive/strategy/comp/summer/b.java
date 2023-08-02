package com.xunmeng.pinduoduo.alive.strategy.comp.summer;

import android.content.ComponentName;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.FileProviderV2;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.HssRegisterServiceManager;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.FpPermReadyEvent;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: b.class */
public class b extends BaseStrategy implements a.AnonymousClass1 {
    ComponentName a = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.summer.ScreenOnService");
    ComponentName b = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.summer.StatusBarService");
    ComponentName c = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.summer.PackageChangedService");
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;
    private static final String k = null;

    public void a(SummerConfig summerConfig) {
        if (!FileProviderV2.instance().hasPermission("HSS_UPDATE_XML")) {
            Logger.e("LVST2.comp.SummerStrategy", "not permission");
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("versionId", summerConfig.versionId);
        int b = a.b(getContext(), summerConfig.targetPackage);
        hashMap.put("targetUid", String.valueOf(b));
        if (b != 1000) {
            Logger.e("LVST2.comp.SummerStrategy", "target package uid is not 1000");
            a(10485805, "openSummer_check_uid_fail", hashMap, 0);
        } else if (a() && RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_summer_clear_xml_57600", false)) {
            if (!HssRegisterServiceManager.instance().clearAllServicesInXml()) {
                a(10485804, "closeSummer_clear_xml_fail", hashMap, 0);
                return;
            }
            c.a().a(null);
            a(10485803, "closeSummer_clear_xml_success", hashMap, 0);
        } else if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_summer_write_xml_57600", false) || a.a(getContext(), summerConfig.xmlDataSceneId) != 0) {
        } else {
            if (c.a().b().equals(summerConfig.versionId)) {
                Logger.i("LVST2.comp.SummerStrategy", "broadcast_version_id is not change");
                return;
            }
            if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_check_summer_origin_xml_file_57900", false)) {
                Logger.i("LVST2.comp.SummerStrategy", "pinduoduo_Android.ka_strategy_biz_check_summer_origin_xml_file_57900 is open, check origin xml file");
                String a = a.a(HssRegisterServiceManager.instance().getAllServicesExceptPackage(getContext().getPackageName()));
                if (!TextUtils.isEmpty(a)) {
                    Logger.e("LVST2.comp.SummerStrategy", "origin xml file is not empty, services = " + a);
                    hashMap.put("originServices", a);
                    a(10485806, "openSummer_xml_not_empty", hashMap, 0);
                    return;
                }
            }
            if (!HssRegisterServiceManager.instance().addServicesToXml(summerConfig.serviceNameList, summerConfig.actionNameList, summerConfig.dataSchemeList)) {
                a(10485801, "openSummer_write_xml_fail", hashMap, 0);
            } else if (!HssRegisterServiceManager.instance().checkServicesInXml(summerConfig.serviceNameList, summerConfig.actionNameList, summerConfig.dataSchemeList)) {
                a(10485802, "openSummer_check_xml_fail", hashMap, 0);
            } else {
                c.a().a(summerConfig.versionId);
                a(10485800, "openSummer_check_xml_success", hashMap, 0);
            }
        }
    }

    public void b(SummerConfig summerConfig) {
        a.a(getContext(), "pinduoduo_Android.ka_strategy_biz_summer_screen_on_service_57600", this.a, false, summerConfig.serviceComponentSceneId);
        a.a(getContext(), "pinduoduo_Android.ka_strategy_biz_summer_status_bar_service_57600", this.b, false, summerConfig.serviceComponentSceneId);
        a.a(getContext(), "pinduoduo_Android.ka_strategy_biz_summer_package_changed_service_57600", this.c, false, summerConfig.serviceComponentSceneId);
    }

    private boolean a() {
        return !c.a().b().isEmpty();
    }

    public void stop() {
    }

    public boolean execute(TriggerRequest triggerRequest) {
        try {
            BaseConfig config = triggerRequest.getConfig();
            if (config == null) {
                Logger.e("LVST2.comp.SummerStrategy", "baseConfig is null");
                return false;
            }
            SummerConfig summerConfig = (SummerConfig) config.getValue();
            if (summerConfig == null) {
                Logger.e("LVST2.comp.SummerStrategy", "config is null");
                return false;
            }
            if (triggerRequest.getTriggerEvent() instanceof FpPermReadyEvent) {
                FpPermReadyEvent triggerEvent = triggerRequest.getTriggerEvent();
                if (triggerEvent.getScene().equals("HSS_UPDATE_XML")) {
                    Logger.i("LVST2.comp.SummerStrategy", "permission ready: %s, %s", new Object[]{triggerEvent.getScene(), triggerEvent.getSrc()});
                    a(summerConfig);
                    return true;
                }
            }
            if (triggerRequest.getTriggerEvent().getType() == TriggerEventType.PROCESS_START) {
                a(summerConfig);
                b(summerConfig);
                return true;
            } else if (triggerRequest.getTriggerEvent().getType() != TriggerEventType.IRREGULAR_PROCESS_START) {
                return true;
            } else {
                b();
                return true;
            }
        } catch (Exception e2) {
            Logger.e("LVST2.comp.SummerStrategy", "excute fail, exception = " + e2);
            return false;
        }
    }

    private void b() {
        a.c(getContext(), this.a);
        a.c(getContext(), this.b);
        a.c(getContext(), this.c);
    }

    private void a(int i2, String str, Map map, int i3) {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_summer_upload_data_57600", true)) {
            Logger.i("LVST2.comp.SummerStrategy", "not report, errorId: %s", new Object[]{Integer.valueOf(i2)});
            return;
        }
        Logger.i("LVST2.comp.SummerStrategy", "report, errorId: %s", new Object[]{Integer.valueOf(i2)});
        trackError(new TrackErrorOption(i2, 30069, str, map, Integer.valueOf(i3)));
    }
}
