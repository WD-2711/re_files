package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;

/* loaded from: g.class */
public class g {
    public static void a(String str, int i, TrackEventOption trackEventOption) {
        if (AppBuildInfo.instance().getRealVersionCode() >= 15335520) {
            b(str, i, trackEventOption);
        }
    }

    private static void b(String str, int i, TrackEventOption trackEventOption) {
        StrategyFramework.trackCsDataEvent(str, i, trackEventOption);
    }
}
