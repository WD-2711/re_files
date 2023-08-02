package com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck;

import com.xunmeng.pinduoduo.alive.base.ability.interfaces.screenRecordCheck.IScreenRecordCheck;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;

/* loaded from: c.class */
public class c implements a.AnonymousClass1 {
    public static final String a = null;
    private IScreenRecordCheck b;

    public Boolean isRecording() {
        return this.b.isRecording();
    }

    public boolean isSupportOfDetectScreenRecord() {
        return this.b.isSupportOfDetectScreenRecord();
    }

    private boolean a() {
        return AliveAbility.instance().isAbilityDisabled() && RemoteConfig.instance().getBoolean("pinduoduo_Android.detect_screen_record_detect_use_salt_60700", false);
    }

    public c() {
        if (RemoteConfig.instance().getBoolean("pinduoduo_Android.detect_screen_record_detect_key_60700", false) && !a()) {
            if (RomOsUtil.instance().isXiaomiManufacture()) {
                this.b = new e();
                return;
            } else if (RomOsUtil.instance().isOppoManufacture()) {
                this.b = new b();
                return;
            } else if (RomOsUtil.instance().isVivoManufacture()) {
                this.b = new d();
                return;
            }
        }
        Logger.i("ScreenRecordCheckImpl", "use dummy instance.");
        this.b = new a();
    }

    public boolean startDetectScreenRecord() {
        return this.b.startDetectScreenRecord();
    }

    public boolean isSupportOfGetRecordState() {
        return this.b.isSupportOfGetRecordState();
    }
}