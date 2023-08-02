package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: f.class */
public class f extends e {
    private static final String c = null;
    private static AtomicBoolean d = new AtomicBoolean(false);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [android.content.ServiceConnection, com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.f$1] */
    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public KarmaResult b() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Logger.i(a, "count down latch is " + countDownLatch.toString());
        final int[] iArr = {-1};
        ?? r0 = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.f.1
            /* JADX WARN: Multi-variable type inference failed */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                try {
                    Logger.i(e.a, "onServiceConnected");
                    iArr[0] = f.this.a(iBinder);
                    Logger.i(e.a, "steps is : %s", new Object[]{Integer.valueOf(iArr[0])});
                } catch (Throwable th) {
                    try {
                        Logger.e(e.a, "onServiceConnected error", th);
                    } finally {
                        Logger.i(e.a, "onServiceConnected finally");
                        f.d.set(false);
                        countDownLatch.countDown();
                        f.this.b.unbindService(this);
                    }
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                Logger.i(e.a, "onServiceDisconnected");
            }

            public void onBindingDied(ComponentName componentName) {
                Logger.i(e.a, "onBindingDied");
            }

            public void onNullBinding(ComponentName componentName) {
                Logger.i(e.a, "onNullBinding");
            }
        };
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.vivo.health", "com.vivo.health.sport.compat.SportCompatService"));
        if (!d.compareAndSet(false, true)) {
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("busy_query");
            return new KarmaResult("busy_query_steps");
        }
        try {
            this.b.bindService(intent, r0, 1);
            try {
                Logger.i(a, "query begin to wait until awakened.");
                countDownLatch.await(RemoteConfig.instance().getLong("pinduoduo_Android.build_in_vivo_step_info_over_time_62100", 1L), TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Logger.e(a, "query await exception:", e);
            }
            if (iArr[0] == -1) {
                Logger.i(a, "over time");
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("over_time");
            } else {
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("get_success");
            }
            return iArr[0] != -1 ? new KarmaResult(iArr[0], TimeStamp.instance().getRealLocalTimeV2()) : new KarmaResult("no_step_info");
        } catch (Throwable th) {
            try {
                Logger.e(a, "bindService error", th);
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("bind_service_error");
                return new KarmaResult("read_from_file_error");
            } finally {
                Logger.i(a, "reset handling event");
                d.set(false);
            }
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public boolean a() {
        return a("com.vivo.health");
    }

    int a(IBinder iBinder) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.vivo.health.sport.compat.ISportServiceBinder");
            iBinder.transact(2, obtain, obtain2, 0);
            obtain2.readException();
            return obtain2.readInt();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
