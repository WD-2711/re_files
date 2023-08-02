package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.debugCheck.IDebugDetectTransmission;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import java.util.Map;

/* loaded from: d.class */
public class d implements a.AnonymousClass1 {
    static final String a = null;
    private final Map b;
    private final IDebugDetectTransmission c;

    public void onReceive(Context context, Intent intent) {
        if (null != this.b && a(intent)) {
            String stringExtra = intent.getStringExtra("extra_action");
            if (intent.getData() == null) {
                return;
            }
            String str = (String) this.b.get(intent.getData().getHost());
            if (str != null && stringExtra != null && this.c != null) {
                this.c.send(str, TextUtils.equals(stringExtra, "start"));
            }
            String str2 = (String) this.b.get("trackDelay");
            if (TextUtils.isEmpty(str2)) {
                f.a(str, stringExtra);
                return;
            }
            long parseLong = NumberUtils.instance().parseLong(str2);
            Logger.d("SecretCodeReceiver", "prepare to check %s debug after %d ms !", new Object[]{str, Long.valueOf(parseLong)});
            a(str, stringExtra, parseLong);
        }
    }

    public d(String str, IDebugDetectTransmission iDebugDetectTransmission) {
        this.b = b.a(str);
        this.c = iDebugDetectTransmission;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck.d$1, java.lang.Runnable] */
    private void a(final String str, final String str2, long j) {
        if (j <= 0) {
            return;
        }
        ThreadPool.instance().getWorkerHandler(ThreadBiz.CS).postDelayed("SecretCodeReceiver#trackAfterDelayCheck", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck.d.1
            @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
            public void run() {
                try {
                    if (d.this.c == null || !d.this.c.isDebugging()) {
                        Logger.d("SecretCodeReceiver", "system is not debugging !");
                    } else {
                        Logger.d("SecretCodeReceiver", "system is debugging !");
                        f.a(str, str2);
                    }
                } catch (Exception e) {
                    Logger.e("SecretCodeReceiver", "trackAfterDelayCheck failed ", e);
                }
            }
        }, j);
    }

    private boolean a(Intent intent) {
        if (intent == null) {
            Logger.d("SecretCodeReceiver", "obtain invalid intent since is Null");
            return false;
        } else if (intent.getData() == null) {
            Logger.d("SecretCodeReceiver", "obtain invalid intent since no Uri");
            return false;
        } else if (intent.getData().getHost() == null) {
            Logger.d("SecretCodeReceiver", "obtain invalid intent since no host");
            return false;
        } else if (!this.b.containsKey(intent.getData().getHost())) {
            Logger.d("SecretCodeReceiver", "obtain invalid intent since not in codeMap");
            return false;
        } else {
            String host = intent.getData().getHost();
            Logger.d("SecretCodeReceiver", "obtain valid intent : host %s , log type %s, extra action %s ", new Object[]{host, (String) this.b.get(host), intent.getStringExtra("extra_action")});
            return true;
        }
    }
}
