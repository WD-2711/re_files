package com.xunmeng.pinduoduo.launcher_detect_comp.impl;

import com.xunmeng.pinduoduo.launcher_detect_comp_interf.interf.IVivoBindServiceComp;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: a.class */
public class a {
    private static final Lock a = new ReentrantLock();
    private static IVivoBindServiceComp b;

    public static IVivoBindServiceComp a() {
        if (b == null) {
            a.lock();
            try {
                if (b == null) {
                    b = new b();
                }
            } finally {
                a.unlock();
            }
        }
        return b;
    }
}
