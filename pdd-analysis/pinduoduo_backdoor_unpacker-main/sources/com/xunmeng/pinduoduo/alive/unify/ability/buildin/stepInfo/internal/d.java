package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;

/* loaded from: d.class */
public class d {
    public static e a() {
        return (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture() || RomOsUtil.instance().isOppoManufacture()) ? new b() : RomOsUtil.instance().isXiaomiManufacture() ? new g() : RomOsUtil.instance().isVivoManufacture() ? new f() : new a();
    }
}
