package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.DeviceCompatPermission;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AppUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.CdUtils;
import com.xunmeng.plugin.adapter_sdk.utils.ScreenUtil;

/* loaded from: l.class */
public class l extends b {
    private static final String c = null;

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public String h() {
        return "dd.vivo";
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(Intent intent) {
        if (!ScreenUtil.isScreenOn()) {
            return true;
        }
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_vivo_start_pull_activity_screen_on_5930", "false"));
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean b(Intent intent) {
        if (CdUtils.b()) {
            return CdUtils.b(intent, "dd.vivo");
        }
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void a() {
        super.a();
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean a(ActivityInfo activityInfo) {
        return false;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean e() {
        if (CdUtils.b()) {
            return CdUtils.b("dd.vivo");
        }
        if (!Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_vivo_start_pull_activity_by_other_5930", "false"))) {
            Logger.i("SpecialPullAbility.Comp", "start activity by other ab false, return");
            return false;
        }
        Logger.i("SpecialPullAbility.Comp", "support other background start activity.");
        return true;
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean c() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_vivo_start_pull_special_activity_5930", "false"));
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public boolean f() {
        if (CdUtils.a()) {
            return CdUtils.a("dd.vivo");
        }
        if (ScreenUtil.isScreenOn() && AppUtils.instance().isAppOnForeground(BotBaseApplication.getContext())) {
            Logger.i("SpecialPullAbility.Comp", "pdd is foreground and screen on.");
            return true;
        } else if (DeviceCompatPermission.instance().hasPermission(BotBaseApplication.getContext(), "BACKGROUND_START_ACTIVITY")) {
            Logger.i("SpecialPullAbility.Comp", "pdd has background start activity permission.");
            return true;
        } else if (!DeviceCompatPermission.instance().hasPermission(BotBaseApplication.getContext(), "OVERLAY") || !Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_vivo_back_start_pull_activity_when_overlay_on_6020", "true"))) {
            return false;
        } else {
            Logger.i("SpecialPullAbility.Comp", "pdd has overlay permission.");
            return true;
        }
    }

    @Override // com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.b
    public void b() {
        super.b();
    }
}
