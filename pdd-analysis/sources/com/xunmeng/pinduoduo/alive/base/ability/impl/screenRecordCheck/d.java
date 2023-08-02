package com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck;

import android.net.Uri;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IContentObserver;

/* loaded from: d.class */
public class d extends a {
    private static final String c = null;

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public Boolean isRecording() {
        int a = a();
        Logger.i("ScreenRecordCheckImpl", "record state(fps): %d", new Object[]{Integer.valueOf(a)});
        return Boolean.valueOf(a > 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean isSupportOfDetectScreenRecord() {
        return true;
    }

    public int a() {
        return a("vivo_screen_record_switch_setting", 0);
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean startDetectScreenRecord() {
        Logger.i("ScreenRecordCheckImpl", "start vivo detect screen record.");
        return a("vivo_screen_record_switch_setting", (IContentObserver) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.d.1
            public void onChange(boolean z) {
                Logger.i("ScreenRecordCheckImpl", "onChange record state: %d", new Object[]{Integer.valueOf(d.this.a())});
                Boolean isRecording = d.this.isRecording();
                if (isRecording == null) {
                    Logger.e("ScreenRecordCheckImpl", "record state error.");
                } else {
                    d.this.a(isRecording.booleanValue() ? 0 : 1);
                }
            }

            public void onChange(boolean z, Uri uri, int i) {
            }

            public void onChange(boolean z, Uri uri) {
            }
        });
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean isSupportOfGetRecordState() {
        return true;
    }
}
