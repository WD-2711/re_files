package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.status;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: b.class */
public class b implements a.AnonymousClass1 {
    public static final String a = null;
    public static final String b = null;
    public static final String c = null;
    public static final String d = null;
    public static final String e = null;
    public static final String f = null;
    private static final String g = null;
    private static final AtomicInteger h = new AtomicInteger(0);
    private int i;
    private long j;
    private long k;
    private final c l = new c(this);
    private WeakReference m;
    private SonaRequest n;

    public void onStop() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onStop");
        HashMap hashMap = new HashMap();
        hashMap.put("request_code", String.valueOf(this.i));
        a(hashMap);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_stop", this.n, null, hashMap);
    }

    public void onStart() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onStart");
    }

    public void onCreate(Activity activity, Bundle bundle) {
        Intent intent = activity.getIntent();
        this.m = new WeakReference(activity);
        this.i = h.incrementAndGet();
        this.j = intent.getLongExtra("intent_build_time", System.currentTimeMillis());
        this.k = System.currentTimeMillis();
        this.n = d.a(intent.getBundleExtra("sona_request"));
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(this.i);
        objArr[1] = this.n == null ? null : this.n.toString();
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onCreate, requestCode: %d, request: %s", objArr);
        if (this.n == null) {
            Logger.e("SpecialPullAbility.Comp.SonaActivity", "invalid sona request");
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_create", null, new status(false, "invalid_request"), null);
            a();
        } else if (this.n == null) {
        } else {
            this.l.a(activity.getWindow(), activity.getIntent());
            HashMap hashMap = new HashMap();
            hashMap.put("request_code", String.valueOf(this.i));
            hashMap.put("intent_build_time", String.valueOf(this.j));
            hashMap.put("intent_build_cost_time", String.valueOf(System.currentTimeMillis() - this.j));
            com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_create", this.n, new status(true), hashMap);
            if (e.a() == null) {
                return;
            }
            e.a().a(activity, this.n, this.i);
        }
    }

    public int c() {
        return this.i;
    }

    public SonaRequest b() {
        return this.n;
    }

    private void a(Map map) {
        map.put("intent_build_time", String.valueOf(this.j));
        map.put("intent_build_cost_time", String.valueOf(System.currentTimeMillis() - this.j));
        map.put("activity_create_time", String.valueOf(this.k));
        map.put("activity_create_cost_time", String.valueOf(System.currentTimeMillis() - this.k));
    }

    public void onNewIntent() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onNewIntent");
        this.l.a();
        HashMap hashMap = new HashMap();
        hashMap.put("request_code", String.valueOf(this.i));
        a(hashMap);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_new_intent", this.n, null, hashMap);
    }

    public void onResume() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onResume");
    }

    public void a() {
        try {
            Logger.i("SpecialPullAbility.Comp.SonaActivity", "safeFinishActivity");
            if (this.m == null) {
                Logger.i("SpecialPullAbility.Comp.SonaActivity", "dexActivityReference is null");
                return;
            }
            Activity activity = (Activity) this.m.get();
            if (activity == null || activity.isFinishing()) {
                Logger.i("SpecialPullAbility.Comp.SonaActivity", "activity is already finishing");
            } else {
                activity.finish();
            }
        } catch (Throwable th) {
            Logger.e("SpecialPullAbility.Comp.SonaActivity", "fail to finish activity", th);
        }
    }

    public void onDestroy() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onDestroy");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        String uri = intent == null ? null : new Intent(intent).toUri(0);
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onActivityResult called for: requestCode: %d, resultCode: %d, intent: %s", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), uri});
        HashMap hashMap = new HashMap();
        hashMap.put("request_code", String.valueOf(i));
        hashMap.put("result_code", String.valueOf(i2));
        hashMap.put("result_data", uri);
        hashMap.put("expected_request_code", String.valueOf(this.i));
        hashMap.put("request_code_match", String.valueOf(this.i == i));
        a(hashMap);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_result", this.n, null, hashMap);
        this.l.a();
    }

    public void onPause() {
        Logger.i("SpecialPullAbility.Comp.SonaActivity", "onPause");
        HashMap hashMap = new HashMap();
        hashMap.put("request_code", String.valueOf(this.i));
        a(hashMap);
        com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.track.a.a("sona_activity_on_pause", this.n, null, hashMap);
    }
}