package com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;

/* loaded from: e.class */
public class e extends a {
    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public Boolean isRecording() {
        return null;
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean isSupportOfDetectScreenRecord() {
        return true;
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean startDetectScreenRecord() {
        Logger.i("ScreenRecordCheckImpl", "start xiaomi detect screen record.");
        return a((BroadcastReceiver) new VMDynamicReceiver(new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.e.1
            /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.e$1$1, java.lang.Runnable] */
            public void onReceive(Context context, Intent intent) {
                Logger.i("ScreenRecordCheckImpl", "receive action: %s", new Object[]{intent.toUri(0)});
                ThreadPool.instance().computeTask(ThreadBiz.CS, "ScreenRecordCheckImpl#onReceive", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.e.1.1
                    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                    public void run() {
                        e.this.a(1);
                    }
                });
            }
        }, "ScreenRecordCheckImpl.XM"), new IntentFilter("miui.screenrecorder.record.sucess"));
    }

    @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a
    public boolean isSupportOfGetRecordState() {
        return false;
    }

    private boolean a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        try {
            StrategyFramework.getFrameworkContext().registerReceiver(broadcastReceiver, intentFilter);
            Logger.i("ScreenRecordCheckImpl", "finish register record detect!");
            return true;
        } catch (Throwable th) {
            Logger.e("ScreenRecordCheckImpl", "register record detect failed ", th);
            return false;
        }
    }
}
