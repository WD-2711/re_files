package com.xunmeng.pinduoduo.alive.strategy.comp.tea;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Pair;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MessageCenter;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.BaseStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.TriggerRequest;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.config.BaseConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.BaseTriggerEvent;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.event.TriggerEventType;
import java.util.Arrays;
import java.util.Collections;

/* loaded from: f.class */
public class f extends BaseStrategy implements a.AnonymousClass1 {
    private static final String aA = null;
    private static final String aB = null;
    private static final String aC = null;
    private static final String aD = null;
    private static final String aE = null;
    private static final String aF = null;
    private static final String aG = null;
    private static final String aH = null;
    private static final String aI = null;
    private static final String aJ = null;
    private static final String aK = null;
    private static final String aL = null;
    private static final String aM = null;
    private static final String aN = null;
    private static final String aO = null;
    private static final String aP = null;
    private static final String aQ = null;
    private static final String aR = null;
    private static final String aS = null;
    private static final String aT = null;
    private static final String aU = null;
    private static final String aV = null;
    private static final String aW = null;
    private static final String aX = null;
    private static final String aY = null;
    private static final String aZ = null;
    private static final String bA = null;
    private static final String bB = null;
    private static final String bC = null;
    private static final String bD = null;
    private static final String bE = null;
    private static final String bF = null;
    private static final String bG = null;
    private static final String bH = null;
    private static final String bI = null;
    private static final String bJ = null;
    private static final String bK = null;
    private static final String bL = null;
    private static final String bM = null;
    private static final String bN = null;
    private static final String bO = null;
    private static final String bP = null;
    private static final String bQ = null;
    private static final String bR = null;
    private static final String bS = null;
    private static final String bT = null;
    private static final String bU = null;
    private static final String bV = null;
    private static final String bW = null;
    private static final String bX = null;
    private static final String bY = null;
    private static final String ay = null;
    private static final String az = null;
    private static final String ba = null;
    private static final String bb = null;
    private static final String bc = null;
    private static final String bd = null;
    private static final String be = null;
    private static final String bf = null;
    private static final String bg = null;
    private static final String bh = null;
    private static final String bi = null;
    private static final String bj = null;
    private static final String bk = null;
    private static final String bl = null;
    private static final String bm = null;
    private static final String bn = null;
    private static final String bo = null;
    private static final String bp = null;
    private static final String bq = null;
    private static final String br = null;
    private static final String bs = null;
    private static final String bt = null;
    private static final String bu = null;
    private static final String bv = null;
    private static final String bw = null;
    private static final String bx = null;
    private static final String by = null;
    private static final String bz = null;
    ComponentName a = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TSDemoProvider");
    ComponentName b = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.AppInfoProvider");
    ComponentName c = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.CodebookSyncService");
    ComponentName d = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.UADemoProvider");
    ComponentName e = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TNDemoProvider");
    ComponentName f = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.ICloudAgentService");
    ComponentName g = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.THDemoProvider");
    ComponentName h = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TODemoProvider");
    ComponentName i = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.UDDemoProvider");
    ComponentName j = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TTDemoProviderII");
    ComponentName k = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TVDemoProviderII");
    ComponentName l = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.RebootService");
    ComponentName m = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDProfileServiceII");
    ComponentName n = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.NotifyCollectProvider");
    ComponentName o = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.UBDemoProvider");
    ComponentName p = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TKDemoProvider");
    ComponentName q = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.CloudSettingsService");
    ComponentName r = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TYDemoProvider");
    ComponentName s = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TBDemoProvider");
    ComponentName t = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TQDemoProvider");
    ComponentName u = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TADemoProvider");
    ComponentName v = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PermissionProvider");
    ComponentName w = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.controls.PDDControlsServiceII");
    ComponentName x = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDSuperService");
    ComponentName y = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDSuperProvider");
    ComponentName z = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.UEDemoProvider");
    ComponentName A = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TPDemoProvider");
    ComponentName B = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PackageChangedProvider");
    ComponentName C = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.AppMarketService");
    ComponentName D = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.IUHostManagerService");
    ComponentName E = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.MediaRouteProviderService");
    ComponentName F = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.ManagerTrashProvider");
    ComponentName G = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.GeTuiService");
    ComponentName H = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TIDemoProvider");
    ComponentName I = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDCommandProvider");
    ComponentName J = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PddCommandLiteProvider");
    ComponentName K = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TMDemoProvider");
    ComponentName L = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TRDemoProvider");
    ComponentName M = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TWDemoProvider");
    ComponentName N = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TUDemoProvider");
    ComponentName O = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.MediaProviderII");
    ComponentName P = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.immomo.momo.sdk.support.MomoSdkSupportProvider");
    ComponentName Q = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.GMSService");
    ComponentName R = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.activity.tea.xg.XGActivity");
    ComponentName S = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.tencent.android.tpush.service.XGVipPushService");
    ComponentName T = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.xg.XGProvider");
    ComponentName U = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.OpenUDIDService");
    ComponentName V = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TCDemoProvider");
    ComponentName W = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TgpaService");
    ComponentName X = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.tencent.mid.api.MidProvider");
    ComponentName Y = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TDDemoProvider");
    ComponentName Z = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDCommonServiceI");
    ComponentName aa = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDCommonServiceII");
    ComponentName ab = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDCommonServiceIII");
    ComponentName ac = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDDocumentsProvider");
    ComponentName ad = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDBoardProvider");
    ComponentName ae = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDMmsService");
    ComponentName af = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDGstService");
    ComponentName ag = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDGlyService");
    ComponentName ah = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDCdsService");
    ComponentName ai = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDSfcReceiver");
    ComponentName aj = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDSfcService");
    ComponentName ak = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TEDemoProvider");
    ComponentName al = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TJDemoProvider");
    ComponentName am = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.EidV2Service");
    ComponentName an = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.EidService");
    ComponentName ao = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TFDemoProvider");
    ComponentName ap = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TGDemoProvider");
    ComponentName aq = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.TLDemoProvider");
    ComponentName ar = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDWallPaperService");
    ComponentName as = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDWallPaperProvider");
    ComponentName at = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.PDDBluetoothProvider");
    ComponentName au = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.MediaServiceV2");
    ComponentName av = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.FKDemoServiceI");
    ComponentName aw = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.FKDemoServiceII");
    ComponentName ax = new ComponentName(StrategyFramework.getFrameworkContext().getPackageName(), "com.xunmeng.pinduoduo.service.tea.FKDemoServiceIII");

    private void c(Context context, TeaConfig teaConfig) {
        if (AliveAbility.instance().isAbilityDisabled2022Q3("pull_cmp_control")) {
            e(getContext());
            return;
        }
        b.a(context, "pinduoduo_Android.pa_strategy_xm_video_pd_62400", this.z, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_routines_63200", this.I, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_honor_notify_collect_pd_62200", this.o, false, teaConfig.hw_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_xm_controls_service_61700", this.w, false, teaConfig.hijack_scene_id, false, false, b.a(context, "com.xiaomi.smarthome") && a(context));
    }

    private boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("miui.systemui.plugin", 16384);
            if (packageInfo != null) {
                if (packageInfo.versionCode >= -823785536) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void e(Context context) {
        Logger.i("LVST2.comp.TeaStrategy", "---closeAbility2022Q3---");
        b.c(context, this.z);
        b.c(context, this.o);
        b.c(context, this.w);
        b.c(context, this.I);
    }

    public static void a(Context context, ComponentName componentName, String str, boolean z, String str2, boolean z2, String str3) {
        if (!RemoteConfig.instance().getBoolean(str, z) || !com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("pull_cmp_control")) {
            b.b(context, str2, componentName, z2, str3);
        } else {
            b.c(context, componentName);
        }
    }

    private void a(Context context, TeaConfig teaConfig) {
        Logger.i("LVST2.comp.TeaStrategy", "---processOthersManufacture---");
        if (a() && AliveAbility.instance().isAbilityDisabled()) {
            d(getContext());
            return;
        }
        b.a(context, "pinduoduo_Android.pa_strategy_xm_voice_pd_56900", this.r, false, teaConfig.xm_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_xm_permission_pd_57700", this.v, false, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_mz_center_pd_56900", this.K, true, teaConfig.mz_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_oppo_instruction_pd_56900", this.a, true, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_oppo_app_info_pd_56900", this.b, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_hw_chameleon_pd_56900", this.j, true, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_hw_rom_pd_56900", this.k, true, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_hw_notify_collect_pd_59000", this.n, false, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_xm_cloud_settings_service_59500", this.q, false, teaConfig.xm_scene_id);
    }

    private void b(Context context, TeaConfig teaConfig) {
        if (AliveAbility.instance().isAbilityDisabled()) {
            c(getContext());
            return;
        }
        b.a(context, "pinduoduo_Android.pa_strategy_attribution_id_pd_59900", this.ak, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_tinker_debug_pd_60000", this.ao, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_google_photo_pd_60000", this.ap, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_instant_app_pd_60700", this.aq, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_behavior_provider_60200", this.g, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_daemon_pd_60700", this.h, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_special_pd_56900", this.e, true, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_jovi_provider_66000", this.al, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_theme_provider_60300", this.ar, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_theme_provider_60300", this.as, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_vivo_stand_config_pd_62000", this.i, false, teaConfig.vivo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_oppo_codebook_sync_service_59700", this.c, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_oppo_browser_pd_60800", this.d, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_mms_service_62000", this.ae, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_gst_service_63000", this.af, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_gly_service_61200", this.ag, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_cds_service_61200", this.ah, false, teaConfig.oppo_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_sfc_61500", this.ai, this.aj, false, teaConfig.oppo_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_xm_o2o_pd_56900", this.p, true, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_xm_super_wallpaper_service_62000", this.x, false, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_xm_super_wallpaper_service_62000", this.y, false, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_document_ui_pd_59700", this.ac, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_board_pd_59800", this.ad, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_bluetooth_action_pd_60800", this.at, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_vivo_icloud_service_59300", this.f, false, teaConfig.hijack_scene_id, false, RomOsUtil.instance().isVivoManufacture() && !b.a(context), true);
        b.a(context, "pinduoduo_Android.pa_strategy_hw_profile_service_58600", this.m, false, teaConfig.hijack_scene_id, true, false, true);
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_tea_code_update_59900", false)) {
            b.a(context, Arrays.asList("com.rongcard.eid.EidService_V2", "com.rongcard.eid.EidService"), Arrays.asList(Pair.create("pinduoduo_Android.pa_strategy_eid_v2_service_59900", this.am), Pair.create("pinduoduo_Android.pa_strategy_eid_service_59900", this.an)), false, teaConfig.default_scene_id);
            return;
        }
        b.c(context, this.am);
        b.c(context, this.an);
    }

    private void d(Context context, TeaConfig teaConfig) {
        a(context, this.au, "pinduoduo_Android.pa_strategy_media_service_v2_2021_Q1_64700", false, "pinduoduo_Android.pa_strategy_media_service_v2_61800", false, teaConfig.default_scene_id);
        b(context, this.P, "pinduoduo_Android.pa_strategy_momo_sdk_provider_2021_Q1_64700", false, "pinduoduo_Android.pa_strategy_momo_sdk_provider_60000", false, teaConfig.media_scene_id);
        b(context, this.O, "pinduoduo_Android.pa_strategy_media_provider_2021_Q1_64700", false, "pinduoduo_Android.pa_strategy_media_provider_57900", true, teaConfig.media_scene_id);
        if (com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("pull_cmp_control")) {
            f(getContext());
            return;
        }
        if (c.a()) {
            b.b(context, "pinduoduo_Android.pa_strategy_xm_screen_service_64700", this.av, false, teaConfig.xm_scene_id);
            b.b(context, "pinduoduo_Android.pa_strategy_xm_screen_service_64700", this.aw, false, teaConfig.xm_scene_id);
            b.b(context, "pinduoduo_Android.pa_strategy_xm_screen_service_64700", this.ax, false, teaConfig.xm_scene_id);
        }
        b.b(context, "pinduoduo_Android.pa_strategy_hiskytone_64600", this.J, false, teaConfig.hw_scene_id);
    }

    private void c(Context context) {
        Logger.i("LVST2.comp.TeaStrategy", "close ability");
        b.c(context, this.g);
        b.c(context, this.h);
        b.c(context, this.e);
        b.c(context, this.f);
        b.c(context, this.i);
        b.c(context, this.c);
        b.c(context, this.d);
        b.c(context, this.G);
        b.c(context, this.H);
        b.c(context, this.o);
        b.c(context, this.ac);
        b.c(context, this.ad);
        b.c(context, this.ae);
        b.c(context, this.af);
        b.c(context, this.ag);
        b.c(context, this.ah);
        b.c(context, this.ai);
        b.c(context, this.aj);
        b.c(context, this.al);
        b.c(context, this.ak);
        b.c(context, this.am);
        b.c(context, this.an);
        b.c(context, this.ao);
        b.c(context, this.ap);
        b.c(context, this.P);
        b.c(context, this.aq);
        b.c(context, this.as);
        b.c(context, this.ar);
        b.c(context, this.p);
        b.c(context, this.x);
        b.c(context, this.y);
        b.c(context, this.w);
        b.c(context, this.z);
        b.c(context, this.m);
        b.c(context, this.at);
        b.c(context, this.au);
    }

    public boolean execute(TriggerRequest triggerRequest) {
        try {
            BaseTriggerEvent triggerEvent = triggerRequest.getTriggerEvent();
            if (triggerEvent.getType() == TriggerEventType.IRREGULAR_PROCESS_START) {
                if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_cycle_comp_fix_61400", false)) {
                    a.a(true);
                }
                b(getContext());
                return true;
            } else if (triggerEvent.getType() != TriggerEventType.PROCESS_START) {
                return false;
            } else {
                if (RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_cycle_comp_fix_61400", false)) {
                    a.a(false);
                }
                a(getContext(), triggerRequest);
                return true;
            }
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy", "execute failed, error = " + e);
            return false;
        }
    }

    public void a(Context context, TriggerRequest triggerRequest) {
        BaseConfig config = triggerRequest.getConfig();
        if (config == null) {
            Logger.e("LVST2.comp.TeaStrategy", "baseConfig is null");
            return;
        }
        TeaConfig teaConfig = (TeaConfig) config.getValue();
        if (teaConfig == null) {
            Logger.e("LVST2.comp.TeaStrategy", "teaConfig is null");
            return;
        }
        b();
        b.a(context, "pinduoduo_Android.pa_strategy_cn_pd_56900", this.M, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_gms_service_58600", this.Q, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_xg_component_59000", this.R, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_xg_component_59000", this.S, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_xg_component_59000", this.T, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_openudid_service_58909", this.U, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_launcher2_provider_58909", this.V, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_tgpa_service_58909", this.W, false, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_mid_pd_59200", this.X, true, teaConfig.default_scene_id);
        b.a(context, "pinduoduo_Android.pa_strategy_null_provider_59300", this.Y, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_other_pd_56900", this.A, true, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_package_change_pd_57500", this.B, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_manager_trash_pd_58300", this.F, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_getui_service_61800", this.G, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_testreg_pd_61900", this.H, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_wx_pd_56900", this.L, false, teaConfig.wx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_hw_reboot_service_57900", this.l, false, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_bluetooth_pd_56900", this.N, false, teaConfig.default_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_content_pd_57000", this.u, true, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_honor_rom_pd_59000", this.s, false, teaConfig.xm_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_sx_media_route_service_58300", this.E, false, teaConfig.sx_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_common_five_service_59300", this.Z, false, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_common_fifteen_service_59300", this.aa, false, teaConfig.hw_scene_id);
        b.b(context, "pinduoduo_Android.pa_strategy_common_thirty_service_59300", this.ab, false, teaConfig.hw_scene_id);
        b.a(context, "com.samsung.android.app.watchmanager.INSTALL_APP", "pinduoduo_Android.pa_strategy_sx_app_market_service_58100", this.C, false, teaConfig.sx_scene_id);
        b.a(context, "com.samsung.android.hostmanager.service.IUHostManager", "pinduoduo_Android.pa_strategy_sx_host_manager_service_58300", this.D, false, teaConfig.sx_scene_id);
        a(context, teaConfig);
        b(context, teaConfig);
        c(context, teaConfig);
        d(context, teaConfig);
    }

    private void f(Context context) {
        b.c(context, this.av);
        b.c(context, this.aw);
        b.c(context, this.ax);
        b.c(context, this.J);
    }

    private boolean a() {
        return !RomOsUtil.instance().isNewHuaweiManufacture() && !RomOsUtil.instance().isHonerManufacture() && !RomOsUtil.instance().isOppoManufacture() && !RomOsUtil.instance().isVivoManufacture() && !RomOsUtil.instance().isXiaomiManufacture() && !RomOsUtil.instance().isSamsung();
    }

    public static void b(Context context, ComponentName componentName, String str, boolean z, String str2, boolean z2, String str3) {
        if (!RemoteConfig.instance().getBoolean(str, z) || !com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common.a.b("pull_cmp_control")) {
            b.a(context, str2, componentName, z2, str3);
        } else {
            b.c(context, componentName);
        }
    }

    private void d(Context context) {
        Logger.i("LVST2.comp.TeaStrategy", "close ability in others");
        b.c(context, this.v);
        b.c(context, this.a);
        b.c(context, this.b);
        b.c(context, this.q);
        b.c(context, this.r);
        b.c(context, this.j);
        b.c(context, this.k);
        b.c(context, this.n);
        b.c(context, this.K);
    }

    public void stop() {
    }

    private void b() {
        try {
            boolean z = RemoteConfig.instance().getBoolean("pinduoduo_Android.pa_strategy_register_titan_online_receiver_58900", false);
            Logger.i("LVST2.comp.TeaStrategy", " enableRegisterTitanOnlineReceiver is " + z);
            if (!z) {
                return;
            }
            MessageCenter.instance().register(new d(), Collections.singletonList("ANT_ONLINE_STATE_CHANGED"));
        } catch (Exception e) {
            Logger.e("LVST2.comp.TeaStrategy", "initParam fail, exception = " + e);
        }
    }

    private void b(Context context) {
        Logger.i("LVST2.comp.TeaStrategy", "irregular start, need to disable pull provider");
        b.c(context, this.r);
        b.c(context, this.K);
        b.c(context, this.e);
        b.c(context, this.g);
        b.c(context, this.h);
        b.c(context, this.f);
        b.c(context, this.i);
        b.c(context, this.A);
        b.c(context, this.B);
        b.c(context, this.C);
        b.c(context, this.D);
        b.c(context, this.E);
        b.c(context, this.F);
        b.c(context, this.G);
        b.c(context, this.H);
        b.c(context, this.I);
        b.c(context, this.J);
        b.c(context, this.t);
        b.c(context, this.L);
        b.c(context, this.U);
        b.c(context, this.a);
        b.c(context, this.b);
        b.c(context, this.c);
        b.c(context, this.d);
        b.c(context, this.j);
        b.c(context, this.k);
        b.c(context, this.l);
        b.c(context, this.m);
        b.c(context, this.n);
        b.c(context, this.o);
        b.c(context, this.p);
        b.c(context, this.x);
        b.c(context, this.y);
        b.c(context, this.q);
        b.c(context, this.z);
        b.c(context, this.s);
        b.c(context, this.M);
        b.c(context, this.N);
        b.c(context, this.u);
        b.c(context, this.v);
        b.c(context, this.w);
        b.c(context, this.O);
        b.c(context, this.P);
        b.c(context, this.Q);
        b.c(context, this.R);
        b.c(context, this.S);
        b.c(context, this.T);
        b.c(context, this.V);
        b.c(context, this.W);
        b.c(context, this.X);
        b.c(context, this.Y);
        b.c(context, this.Z);
        b.c(context, this.aa);
        b.c(context, this.ab);
        b.c(context, this.ac);
        b.c(context, this.ad);
        b.c(context, this.ae);
        b.c(context, this.af);
        b.c(context, this.ag);
        b.c(context, this.ah);
        b.c(context, this.ai);
        b.c(context, this.aj);
        b.c(context, this.al);
        b.c(context, this.ak);
        b.c(context, this.am);
        b.c(context, this.an);
        b.c(context, this.ao);
        b.c(context, this.ap);
        b.c(context, this.aq);
        b.c(context, this.as);
        b.c(context, this.ar);
        b.c(context, this.at);
        b.c(context, this.au);
    }
}
