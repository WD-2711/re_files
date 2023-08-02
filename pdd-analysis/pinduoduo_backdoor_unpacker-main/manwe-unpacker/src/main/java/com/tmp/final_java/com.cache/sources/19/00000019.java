package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.EventLogger;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.IoUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: b.class */
public class b {
    private static final String a = null;
    private static final Lock b = new ReentrantLock();
    private static final String c = null;
    private static final String d = null;

    /* loaded from: b$a.class */
    public class a extends k.setValue implements a.AnonymousClass1 {
        private SQLiteDatabase a;
        private boolean b;

        public SQLiteDatabase getDatabase() {
            return this.a;
        }

        a(SQLiteDatabase sQLiteDatabase, Uri uri, String str, String str2, boolean z) {
            super(uri, str, str2);
            this.a = sQLiteDatabase;
            this.b = z;
        }

        @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k.setValue
        public boolean close() {
            this.a.close();
            return super.close();
        }

        /* renamed from: getWalStreamHandle */
        public IStreamHandle mo0getWalStreamHandle() {
            return null;
        }

        /* renamed from: getShmStreamHandle */
        public IStreamHandle mo1getShmStreamHandle() {
            return null;
        }

        public boolean isWalDB() {
            return this.b;
        }

        public a(SQLiteDatabase sQLiteDatabase, k.setValue setvalue, boolean z) {
            this(sQLiteDatabase, setvalue.getOriginStreamUri(), setvalue.getTempStreamFilePath(), setvalue.getOriginStreamMd5(), z);
        }
    }

    /* renamed from: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b$b */
    /* loaded from: b$b.class */
    public class C0000b extends a {
        private k.setValue a;
        private k.setValue b;

        @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a
        /* renamed from: a */
        public k.setValue mo0getWalStreamHandle() {
            return this.a;
        }

        @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a
        /* renamed from: b */
        public k.setValue mo1getShmStreamHandle() {
            return this.b;
        }

        @Override // com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a, com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k.setValue
        public boolean close() {
            if (this.a != null) {
                this.a.close();
            }
            if (this.b != null) {
                this.b.close();
            }
            return super.close();
        }

        public C0000b(SQLiteDatabase sQLiteDatabase, k.setValue setvalue, boolean z) {
            super(sQLiteDatabase, setvalue, z);
            this.b = k.a(setvalue, "-shm");
            this.a = k.a(setvalue, "-wal");
        }
    }

    private static boolean c(IDBHandle iDBHandle) {
        Logger.i("LVBA.AliveModule.Provider.DBUtils", "start write wal database");
        boolean z = true;
        boolean z2 = true;
        IStreamHandle walStreamHandle = iDBHandle.getWalStreamHandle();
        if (walStreamHandle != null) {
            z = k.b(walStreamHandle);
        }
        IStreamHandle shmStreamHandle = iDBHandle.getShmStreamHandle();
        if (shmStreamHandle != null) {
            z2 = k.b(shmStreamHandle);
        }
        boolean b2 = k.b((IStreamHandle) iDBHandle);
        Logger.i("LVBA.AliveModule.Provider.DBUtils", "writeWalSuccess: %s, writeShmSuccess: %s, writeSuccess: %s", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(b2)});
        return z && z2 && b2;
    }

    public static a a(Uri uri) {
        k.setValue a2 = k.a(uri, true, true);
        if (a2 == null) {
            return null;
        }
        try {
            SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(a2.getTempStreamFilePath(), null, 0);
            return (!a("pinduoduo_Android.write_database_in_android_pie_58000") || !a(openDatabase)) ? new a(openDatabase, a2, false) : new C0000b(openDatabase, a2, true);
        } catch (Exception e) {
            a2.close();
            Logger.e("LVBA.AliveModule.Provider.DBUtils", "fail to openDB", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(File file) {
        return StorageApi.instance().delete(file);
    }

    public static boolean a(IDBHandle iDBHandle) {
        b.lock();
        try {
            return b(iDBHandle);
        } finally {
            b.unlock();
        }
    }

    private static boolean b(IDBHandle iDBHandle) {
        if (iDBHandle.isWalDB()) {
            return c(iDBHandle);
        }
        Logger.i("LVBA.AliveModule.Provider.DBUtils", "start write database");
        boolean b2 = k.b((IStreamHandle) iDBHandle);
        Logger.i("LVBA.AliveModule.Provider.DBUtils", "writeSuccess: %s", new Object[]{Boolean.valueOf(b2)});
        return b2;
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("pragma journal_mode;", null);
                cursor.moveToNext();
                String string = cursor.getString(0);
                Logger.i("LVBA.AliveModule.Provider.DBUtils", "journal mode is %s", new Object[]{string});
                boolean equals = "wal".equals(string);
                if (cursor != null) {
                    IoUtils.instance().closeQuietly(cursor);
                }
                return equals;
            } catch (Exception e) {
                EventLogger.logExceptionEvent("query journal_mode", e);
                Logger.i("LVBA.AliveModule.Provider.DBUtils", e.getMessage());
                if (cursor == null) {
                    return false;
                }
                IoUtils.instance().closeQuietly(cursor);
                return false;
            }
        } catch (Throwable th) {
            if (cursor != null) {
                IoUtils.instance().closeQuietly(cursor);
            }
            throw th;
        }
    }

    private static boolean a(String str) {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue(str, "false"));
    }
}