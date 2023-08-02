package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.kael;

import android.content.ContentValues;
import android.database.Cursor;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.impl.EmptyFileProviderImpl;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IFileProvider;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.kael.KaelOperateResult;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.NumberUtils;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    static final String a = null;
    public static final String b = null;
    private final IFileProvider c;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private final AtomicBoolean i = new AtomicBoolean(false);
    private b.a j = null;
    private final int g = NumberUtils.instance().parseInt(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_strategy_vivo_db_grant_time_out_57300", "2"));
    private final int h = NumberUtils.instance().parseInt(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_strategy_vivo_db_grant_block_57300", "1000"));

    public KaelOperateResult grantPermission(String str) {
        Logger.i("LVBA.AliveModule.KaelDbOperate", "prepare grant !");
        if (!this.c.hasAbility("startGrantPermission")) {
            return new KaelOperateResult(-92, "no ability");
        }
        this.i.set(this.c.hasPermission());
        Logger.i("LVBA.AliveModule.KaelDbOperate", "check permission before grant : %s ", new Object[]{Boolean.valueOf(this.i.get())});
        if (this.i.get()) {
            return new KaelOperateResult(-100, "already has permission");
        }
        this.c.startGrantPermission(str);
        try {
            Thread.sleep(this.h);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.KaelDbOperate", "wait for block time failed !", e2);
        }
        boolean hasPermission = this.c.hasPermission();
        Logger.i("LVBA.AliveModule.KaelDbOperate", "grant permission success : " + hasPermission);
        this.i.set(hasPermission);
        return this.i.get() ? new KaelOperateResult(-100, "obtain permission success") : new KaelOperateResult(-90, "grant permission failed");
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.kael.a$1, java.util.concurrent.Callable] */
    public KaelOperateResult startOperate(final int i, final String str, final String str2) {
        Logger.i("LVBA.AliveModule.KaelDbOperate", "prepare start operate !");
        KaelOperateResult kaelOperateResult = new KaelOperateResult(-92, "get future task failed");
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "didn't obtain permission !");
            return new KaelOperateResult(-93, "no permission");
        }
        try {
            KaelOperateResult kaelOperateResult2 = (KaelOperateResult) ThreadPool.instance().obtainIoExecutor().submit(ThreadBiz.CS, "dbOperate", (Callable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.kael.a.1
                /* renamed from: a */
                public KaelOperateResult m2call() {
                    return a.this.b(i, str, str2);
                }
            }).get(this.g, TimeUnit.SECONDS);
            if (kaelOperateResult2 != null) {
                kaelOperateResult = kaelOperateResult2;
            }
            Logger.i("LVBA.AliveModule.KaelDbOperate", "operate db result :" + kaelOperateResult.toString());
        } catch (TimeoutException e2) {
            Logger.e("LVBA.AliveModule.KaelDbOperate", "operate task time out, so return time out exception !");
            kaelOperateResult = new KaelOperateResult(-98, "task time out");
        } catch (Exception e3) {
            Logger.e("LVBA.AliveModule.KaelDbOperate", "start operate exception : ", e3);
        } finally {
            c();
        }
        return kaelOperateResult;
    }

    private KaelOperateResult b() {
        b.a a2 = b.a(com.xunmeng.pinduoduo.alive.base.ability.impl.provider.vivo.a.a.buildUpon().appendPath("data/user_de/0/com.vivo.abe/databases/unifiedconfig.db").build());
        this.j = a2;
        if (a2 == null || a2.getDatabase() == null) {
            return new KaelOperateResult(-97, "no database");
        }
        try {
            try {
                Cursor query = a2.getDatabase().query("config_list", null, "module = ?", new String[]{"BBKLauncher2"}, null, null, null);
                if (query == null || query.getCount() <= 0) {
                    Logger.i("LVBA.AliveModule.KaelDbOperate", "not record !");
                    KaelOperateResult kaelOperateResult = new KaelOperateResult(-91, "no record");
                    a(query);
                    return kaelOperateResult;
                }
                int i = -1;
                while (query.moveToNext()) {
                    int parseInt = NumberUtils.instance().parseInt(query.getString(0));
                    if (i < parseInt) {
                        i = parseInt;
                    }
                }
                Logger.i("LVBA.AliveModule.KaelDbOperate", "max id record :" + i);
                KaelOperateResult kaelOperateResult2 = new KaelOperateResult(i, "get newest record");
                a(query);
                return kaelOperateResult2;
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.KaelDbOperate", "query db failed :", e2);
                a((Cursor) null);
                return new KaelOperateResult(-97, "no database");
            }
        } catch (Throwable th) {
            a((Cursor) null);
            throw th;
        }
    }

    private boolean a(b.a aVar) {
        if (aVar.getOriginStreamUri() == null || aVar.getTempStreamFilePath() == null) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "origin stream uri or temp file path is null !");
            return false;
        }
        return b.a((IDBHandle) aVar);
    }

    private ContentValues a(int i, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(i));
        contentValues.put("module", "BBKLauncher2");
        contentValues.put("url", str);
        contentValues.put("key", str2);
        contentValues.put("engine_version", "1.0");
        contentValues.put("identifier", "com.bbk.launcher2_configs");
        contentValues.put("type", "1");
        return contentValues;
    }

    public KaelOperateResult getNewestDbRecord() {
        Logger.i("LVBA.AliveModule.KaelDbOperate", "prepare get newest record !");
        if (!hasPermission()) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "didn't obtain permission !");
            return new KaelOperateResult(-93, "no permission");
        }
        KaelOperateResult b2 = b();
        if (b2.getErrorCode() != -91) {
            c();
        }
        return b2;
    }

    private b.a a() {
        if (this.j == null || this.j.getOriginStreamMd5() == null) {
            return null;
        }
        return this.j;
    }

    private void a(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.KaelDbOperate", "close cursor failed !");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public KaelOperateResult b(int i, String str, String str2) {
        b.a a2 = a();
        if (null == a2) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "save DB failed !");
            return new KaelOperateResult(-94, "save DB failed");
        } else if (!a(a(i, str, str2), a2)) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "modify db failed !");
            return new KaelOperateResult(-95, "modify db failed");
        } else if (!a(a2)) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "push DB failed !");
            return new KaelOperateResult(-96, "push DB failed");
        } else {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "change db success !");
            return new KaelOperateResult(-100, "change db success");
        }
    }

    private boolean a(ContentValues contentValues, b.a aVar) {
        if (aVar.getDatabase() == null) {
            Logger.i("LVBA.AliveModule.KaelDbOperate", "db is not valid");
            return false;
        }
        try {
            aVar.getDatabase().insert("config_list", null, contentValues);
            return true;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.KaelDbOperate", "modify db failed !");
            return false;
        }
    }

    public a() {
        boolean parseBoolean = Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_strategy_vivo_db_grant_57300", "true"));
        this.c = parseBoolean ? new com.xunmeng.pinduoduo.alive.base.ability.impl.provider.vivo.a() : new EmptyFileProviderImpl();
        Logger.i("LVBA.AliveModule.KaelDbOperate", "init KaelDbOperateImpl success, ab enable:%s, task time out:%s, block time out:%s !", new Object[]{Boolean.valueOf(parseBoolean), Integer.valueOf(this.g), Integer.valueOf(this.h)});
    }

    public boolean hasPermission() {
        boolean z = this.i.get();
        Logger.i("LVBA.AliveModule.KaelDbOperate", "newest permission state: " + z);
        return z;
    }

    private void c() {
        try {
            if (this.j == null) {
                return;
            }
            this.j.close();
            this.j = null;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.KaelDbOperate", "release OriDBHandle failed :", e2);
        }
    }
}