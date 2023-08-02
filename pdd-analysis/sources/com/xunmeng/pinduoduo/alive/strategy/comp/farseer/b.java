package com.xunmeng.pinduoduo.alive.strategy.comp.farseer;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackErrorOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.TrackEventOption;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.CommonHelper;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: b.class */
public class b {
    public static int a;
    public static int b;
    private static final String c = null;
    private static final int d = 0;
    private static Runnable e;
    private static Future f;
    private static final String g = null;
    private static final String h = null;

    private static void a(int i, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("mode", str);
        hashMap.put("job_size", Integer.valueOf(i));
        hashMap.put("business", "FarSeerStrategy");
        StrategyFramework.trackCsDataEvent("", 0L, new TrackEventOption(hashMap, "perf", "alive", 0));
    }

    private static void a(int i) {
        Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "track limit");
        a(i, "exceed_limit");
    }

    private static void a(Exception exc, String str) {
        if (RemoteConfig.instance().getBoolean("ab_alive_strategy_biz_farseer_track_error_60300", false)) {
            HashMap hashMap = new HashMap();
            hashMap.put("category", str);
            hashMap.put("exception", exc.getMessage());
            StrategyFramework.trackError("FarSeerStrategy", new TrackErrorOption(31200, 30069, (String) null, hashMap, (Integer) null));
        }
    }

    private static void b(int i) {
        if (RemoteConfig.instance().getBoolean("ab_live_strategy_biz_farseer_track_63000", false)) {
            Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "track normal");
            a(i, "normal");
        }
    }

    public static void c(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 21) {
                Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "unable scheduleJob under LOLLIPOP");
                return;
            }
            int i = 1000 * a;
            JobInfo.Builder builder = new JobInfo.Builder(3, new ComponentName(context, "com.xunmeng.pinduoduo.service.farseer.LifeCycleJobService"));
            builder.setPeriodic(i);
            builder.setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler == null) {
                return;
            }
            if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.ka_strategy_biz_farseer_check_job_size_63000", false)) {
                jobScheduler.schedule(builder.build());
            } else {
                List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
                int size = allPendingJobs.size();
                int i2 = RemoteConfig.instance().getInt("pinduoduo_Android.ka_strategy_biz_farseer_job_max_size_63000", 100);
                b(size);
                Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "pending jobs size : " + size + " , max limit size : " + i2);
                if (size <= i2) {
                    jobScheduler.schedule(builder.build());
                } else {
                    a(size);
                    for (JobInfo jobInfo : allPendingJobs) {
                        if (jobInfo != null) {
                            Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "Job : " + jobInfo.toString());
                        }
                    }
                }
            }
            int i3 = 1000 * b;
            if (f != null) {
                f.cancel(false);
            }
            f = ThreadPool.instance().scheduleTask(ThreadBiz.CS, "JobWakeupUtils#scheduleJob", e(), i3, TimeUnit.MILLISECONDS);
            Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "Job schedule success , period(ms) is " + i);
        } catch (Exception e2) {
            Logger.e("LVST2.comp.FarSeer.JobWakeupUtils", e2);
            a(e2, "scheduleJob");
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b$3, java.lang.Runnable] */
    private static Runnable e() {
        if (e == null) {
            e = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b.3
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    b.b();
                }
            };
        }
        return e;
    }

    public static boolean a() {
        if (!CommonHelper.instance().canAutoStart(StrategyFramework.getFrameworkContext())) {
            Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "skip JobScheduler since no auto start permission");
            return false;
        }
        return true;
    }

    private static boolean d() {
        return true;
    }

    public static void d(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 21) {
                Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "unable cancelJob under LOLLIPOP");
                return;
            }
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler == null) {
                return;
            }
            jobScheduler.cancel(3);
            Logger.i("LVST2.comp.FarSeer.JobWakeupUtils", "Job cancel success");
        } catch (Exception e2) {
            Logger.e("LVST2.comp.FarSeer.JobWakeupUtils", e2);
            a(e2, "cancelJob");
        }
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b$1, java.lang.Runnable] */
    public static void b() {
        if (!d()) {
            return;
        }
        final Context frameworkContext = StrategyFramework.getFrameworkContext();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            ThreadPool.instance().computeTask(ThreadBiz.CS, "JobWakeupUtils#start", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b.1
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    b.d(frameworkContext);
                    b.c(frameworkContext);
                }
            });
            return;
        }
        d(frameworkContext);
        c(frameworkContext);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b$2, java.lang.Runnable] */
    public static void c() {
        if (!d()) {
            return;
        }
        final Context frameworkContext = StrategyFramework.getFrameworkContext();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            ThreadPool.instance().computeTask(ThreadBiz.CS, "JobWakeupUtils#start", (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.strategy.comp.farseer.b.2
                @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                public void run() {
                    b.d(frameworkContext);
                }
            });
        } else {
            d(frameworkContext);
        }
    }
}
