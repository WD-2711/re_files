package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IFileProviderV2;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: f.class */
public class f {
    private static IFileProviderV2 a = null;
    private static final Lock b = new ReentrantLock();

    public static IFileProviderV2 a() {
        b.lock();
        try {
            if (a == null) {
                a = new d();
            }
            return a;
        } finally {
            b.unlock();
        }
    }
}
