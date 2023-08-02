package com.xunmeng.pinduoduo.alive.strategy.comp.raptor;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;

/* loaded from: a.class */
public class a {
    public static final String a = null;
    public static final String b = null;
    public static final String c = null;
    public static final String d = null;
    public static final String e = null;
    public static final String f = null;
    public static final String g = null;
    public static final String h = null;
    private static final String i = null;

    public static boolean a() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ab_pull_alive_strategy_raptor_5750", false);
    }

    public static boolean b() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ab_pull_alive_strategy_raptor_5750_enable_media_service", false);
    }

    public static boolean e() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_raptor_enable_set_pull_time_by_config_58600", false);
    }

    public static boolean c() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ab_pull_alive_strategy_raptor_5750_check_blacklist", true);
    }

    public static boolean d() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_raptor_enable_recent_task_optimization_58300", false);
    }

    public static boolean f() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_raptor_enable_use_new_code_60200", false);
    }

    public static boolean g() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_raptor_new_report_interface_59500", true);
    }

    public static boolean h() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_raptor_report_pull_msg_60100", false);
    }
}
