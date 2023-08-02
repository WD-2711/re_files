package com.xunmeng.pinduoduo.alive.strategy.biz.plugin.common;

import android.os.Handler;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.SubThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import java.util.concurrent.TimeUnit;

/* loaded from: b.class */
public class b {
    public static final String a = null;
    public static final String b = null;

    private static void h(String str, String str2, Runnable runnable) {
        ThreadPool.instance().smartExecutorExecute(SubThreadBiz.valueOf(str), str2, runnable);
    }

    public static void a(String str, String str2, Runnable runnable) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            e(str, str2, runnable);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().addTask(runnable);
        }
    }

    private static void e(String str, String str2, Runnable runnable, long j) {
        ThreadPool.instance().ioTaskDelay(ThreadBiz.valueOf(str), str2, runnable, j);
    }

    public static void c(String str, String str2, Runnable runnable) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            g(str, str2, runnable);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().addTask(runnable);
        }
    }

    public static void d(String str, String str2, Runnable runnable) {
        h(str, str2, runnable);
    }

    public static void b(String str, String str2, Runnable runnable) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            f(str, str2, runnable);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().addTask(runnable);
        }
    }

    private static void d(String str, String str2, Runnable runnable, long j) {
        ThreadPool.instance().uiTaskDelay(ThreadBiz.valueOf(str), str2, runnable, j);
    }

    public static void a(String str, String str2, Runnable runnable, long j) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            d(str, str2, runnable, j);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().postDelayed(runnable, j);
        }
    }

    private static void e(String str, String str2, Runnable runnable) {
        ThreadPool.instance().uiTask(ThreadBiz.valueOf(str), str2, runnable);
    }

    public static Handler b(String str) {
        return StrategyFramework.hasAdapterInterface("ThreadPoolV2") ? d(str) : com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().getUiHandler();
    }

    private static void a(String str, String str2, Runnable runnable, long j, TimeUnit timeUnit) {
        ThreadPool.instance().scheduleTask(ThreadBiz.valueOf(str), str2, runnable, j, timeUnit);
    }

    public static Handler a(String str) {
        return StrategyFramework.hasAdapterInterface("ThreadPoolV2") ? c(str) : com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().getWorkerHandler();
    }

    private static void f(String str, String str2, Runnable runnable) {
        ThreadPool.instance().ioTask(ThreadBiz.valueOf(str), str2, runnable);
    }

    private static Handler d(String str) {
        return ThreadPool.instance().getMainHandler2(ThreadBiz.valueOf(str));
    }

    private static Handler c(String str) {
        return ThreadPool.instance().getWorkerHandler2(ThreadBiz.valueOf(str));
    }

    public static void c(String str, String str2, Runnable runnable, long j) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            a(str, str2, runnable, j, TimeUnit.MILLISECONDS);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().postDelayed(runnable, j);
        }
    }

    public static void b(String str, String str2, Runnable runnable, long j) {
        if (StrategyFramework.hasAdapterInterface("ThreadPoolV2")) {
            e(str, str2, runnable, j);
        } else {
            com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.ThreadPool.instance().postDelayed(runnable, j);
        }
    }

    private static void g(String str, String str2, Runnable runnable) {
        ThreadPool.instance().computeTask(ThreadBiz.valueOf(str), str2, runnable);
    }
}
