package com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck;

import android.provider.Settings;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.message.Message0;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MessageCenter;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IContentObserver;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMContentObserver;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    protected static final int a = 0;
    protected static final int b = 0;

    public boolean a(String str, IContentObserver iContentObserver) {
        try {
            StrategyFramework.getFrameworkContext().getContentResolver().registerContentObserver(Settings.System.getUriFor(str), false, new VMContentObserver(ThreadPool.instance().getWorkerHandler2(ThreadBiz.CS), iContentObserver, "ScreenRecordCheckImpl"));
            return true;
        } catch (Throwable th) {
            Logger.e("ScreenRecordCheckImpl", "register screen record detect failed ", th);
            return false;
        }
    }

    public Boolean isRecording() {
        Logger.i("ScreenRecordCheckImpl", "dummy impl is record");
        return null;
    }

    public boolean isSupportOfDetectScreenRecord() {
        Logger.i("ScreenRecordCheckImpl", "dummy impl no support start record");
        return false;
    }

    /*  JADX ERROR: Failed to set jump: 0x0002 -> 0x0008
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.InsnNode.addAttr(jadx.api.plugins.input.data.attributes.IJadxAttrType, Object)" because "insnByOffset[target]" is null
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.addJump(ProcessInstructionsVisitor.java:144)
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.initJumps(ProcessInstructionsVisitor.java:57)
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.visit(ProcessInstructionsVisitor.java:40)
        */
    private java.lang.String b(int r3) {
        /*
            r2 = this;
            r0 = r3
            switch(r0) {
                case 0: goto L1c;
                case 1: goto L20;
                default: goto L8;
            }
        L1c:
            java.lang.String r0 = "screen_record_start_60700"
            return r0
        L20:
            java.lang.String r0 = "screen_record_stop_60700"
            return r0
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.alive.base.ability.impl.screenRecordCheck.a.b(int):java.lang.String");
    }

    public void a(int i) {
        String b2 = b(i);
        Logger.i("ScreenRecordCheckImpl", "notify record state: %s", new Object[]{b2});
        MessageCenter.instance().send(new Message0(b2), true);
    }

    public int a(String str, int i) {
        try {
            return Settings.System.getInt(StrategyFramework.getFrameworkContext().getContentResolver(), str, i);
        } catch (Exception e) {
            Logger.e("ScreenRecordCheckImpl", e);
            return i;
        }
    }

    public boolean startDetectScreenRecord() {
        Logger.i("ScreenRecordCheckImpl", "dummy impl start record");
        return false;
    }

    public boolean isSupportOfGetRecordState() {
        Logger.i("ScreenRecordCheckImpl", "dummy impl no support record");
        return false;
    }
}
