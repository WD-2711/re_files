package com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck;

import android.net.Uri;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IContentObserver;

/* loaded from: b.class */
public class b extends a {
    private static final String c = null;

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public Boolean isRecording() {
        Logger.i("ScreenRecordCheckImpl", "record state: %d", new Object[]{Integer.valueOf(a())});
        return Boolean.valueOf(a() == 2);
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean isSupportOfDetectScreenRecord() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a() {
        return a("recorder_status", -1);
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean startDetectScreenRecord() {
        Logger.i("ScreenRecordCheckImpl", "start oppo detect screen record.");
        return a("recorder_status", (IContentObserver) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.b.1
            public void onChange(boolean z) {
                int a = b.this.a();
                Logger.i("ScreenRecordCheckImpl", "record state: %d", new Object[]{Integer.valueOf(a)});
                if (a == 2) {
                    b.this.a(0);
                } else if (a != 1 && a != 3) {
                } else {
                    b.this.a(1);
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
