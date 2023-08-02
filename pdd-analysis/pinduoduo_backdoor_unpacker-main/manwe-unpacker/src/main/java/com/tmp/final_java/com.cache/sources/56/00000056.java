package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.xmBW;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.LogUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.g;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.constant.AppBuildInfo;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* loaded from: a.class */
public class a {
    private static IMMKV a;
    private static final String b = null;
    private static final String c = null;

    static void a(String str) {
        if (!DeprecatedAb.instance().isFlowControl("ab_xm_fp_track_57500", false)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        LogUtils.logEventAndErrorWithTracker("XmBW", "XmBW", (Map) hashMap, true, false);
    }

    public static long a() {
        long j = g().getLong("updateDbTime", 0L);
        Logger.i("LVBA.AliveModule.Provider.XM", "get Update Boot Time %s !", new Object[]{Long.valueOf(j)});
        return j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Boolean d() {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(e());
        if (a2 == null || a2.getDatabase() == null) {
            Logger.i("LVBA.AliveModule.Provider.XM", "db is null !");
            return null;
        }
        Boolean a3 = a(a2.getDatabase());
        a2.close();
        return a3;
    }

    public static long c() {
        return (System.currentTimeMillis() - SystemClock.elapsedRealtime()) / 0;
    }

    private static Boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            try {
                try {
                    Cursor query = sQLiteDatabase.query("THIRD_DESKTOP", null, "package_name = ? and type = 4", new String[]{AppBuildInfo.instance().getApplicationId() + "@0"}, null, null, null);
                    if (query == null) {
                        Logger.i("LVBA.AliveModule.Provider.XM", "open db failed, cursor is null !");
                        a("openDBFailed");
                        a(query);
                        return null;
                    } else if (query.getCount() > 0) {
                        Logger.i("LVBA.AliveModule.Provider.XM", "app already in behavior white list !");
                        a(query);
                        return true;
                    } else {
                        Logger.i("LVBA.AliveModule.Provider.XM", "no app record in db !");
                        a(query);
                        return false;
                    }
                } catch (SQLiteException e) {
                    Logger.e("LVBA.AliveModule.Provider.XM", "query white list failed in sql exception !", e);
                    if (e.getMessage() != null && e.getMessage().contains("no such table")) {
                        b("queryWhiteListDbInner", "noTableException");
                    }
                    a((Cursor) null);
                    return null;
                }
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.Provider.XM", "query white list failed !", e2);
                a((Cursor) null);
                return null;
            }
        } catch (Throwable th) {
            a((Cursor) null);
            throw th;
        }
    }

    public static Uri e() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_xm_provider_db_path_57800", "");
        return TextUtils.isEmpty(configValue) ? Uri.parse("content://com.miui.securitycore.fileProvider/root_files").buildUpon().appendPath("data/data/com.miui.securitycenter/databases/ThirdDesktop").build() : Uri.parse("content://com.miui.securitycore.fileProvider/root_files").buildUpon().appendPath(configValue).build();
    }

    private static IMMKV g() {
        if (a != null) {
            return a;
        }
        a = MMKVCompat.module("XM_BW_REBOOT_TIME", false);
        return a;
    }

    public static String f() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.ka_xm_provider_sp_path_57800", "");
        return TextUtils.isEmpty(configValue) ? "data/data/com.miui.securitycenter/shared_prefs/remote_provider_preferences.xml" : configValue;
    }

    private static void a(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.Provider.XM", "close cursor failed !");
            }
        }
    }

    private static long a(SQLiteDatabase sQLiteDatabase, ContentValues contentValues) {
        Cursor query;
        long j = 0;
        try {
            try {
                query = sQLiteDatabase.query("THIRD_DESKTOP", null, "package_name = ?", new String[]{AppBuildInfo.instance().getApplicationId() + "@0"}, null, null, null);
            } catch (SQLiteException e) {
                Logger.e("LVBA.AliveModule.Provider.XM", "insert white list failed in sql exception !", e);
                if (e.getMessage() != null && e.getMessage().contains("no such table")) {
                    b("insetWhiteList", "noTableException");
                }
                a((Cursor) null);
            } catch (Exception e2) {
                Logger.e("LVBA.AliveModule.Provider.XM", "insert white list failed !", e2);
                a((Cursor) null);
            }
            if (query == null) {
                Logger.i("LVBA.AliveModule.Provider.XM", "open db failed, cursor is null !");
                a("openDBFailed");
                a(query);
                return 0L;
            }
            if (query.getCount() > 0) {
                j = 0;
                Logger.i("LVBA.AliveModule.Provider.XM", "db already has record : %s ", new Object[]{Integer.valueOf(query.getCount())});
                if (a() == 0) {
                    b();
                }
            } else if (sQLiteDatabase.insert("THIRD_DESKTOP", null, contentValues) != 0) {
                j = 1;
                Logger.i("LVBA.AliveModule.Provider.XM", "success insert content : %s ", new Object[]{contentValues.toString()});
            } else {
                Logger.i("LVBA.AliveModule.Provider.XM", "insert content failed : %s ", new Object[]{contentValues.toString()});
            }
            a(query);
            return j;
        } catch (Throwable th) {
            a((Cursor) null);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b(String str, String str2) {
        if (!DeprecatedAb.instance().isFlowControl("ab_xm_fp_track_57500", false)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("result", str2);
        LogUtils.logEventAndErrorWithTracker("XmBW", "XmBW", (Map) hashMap, true, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(ContentValues contentValues) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(e());
        if (a2 == null) {
            Logger.i("LVBA.AliveModule.Provider.XM", "db is null !");
            return false;
        } else if (a2.getDatabase() == null || a2.getDatabase().getPageSize() == 0) {
            Logger.i("LVBA.AliveModule.Provider.XM", "db is empty !");
            a2.close();
            return false;
        } else {
            long a3 = a(a2.getDatabase(), contentValues);
            boolean z = true;
            if (a3 > 0) {
                z = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
                if (z) {
                    b();
                }
            }
            a2.close();
            boolean z2 = a3 >= 0 && z;
            b("setWhite", String.valueOf(z2));
            Logger.i("LVBA.AliveModule.Provider.XM", "success add to bw ? insert :%s, rewrite :%s !", new Object[]{Long.valueOf(a3), Boolean.valueOf(z)});
            return z2;
        }
    }

    public static void b() {
        long c2 = c();
        Logger.i("LVBA.AliveModule.Provider.XM", "set Update Boot Time %s !", new Object[]{Long.valueOf(c2)});
        g().putLong("updateDbTime", c2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Integer a(String str, String str2) {
        k.setValue a2;
        Integer num = null;
        k.setValue setvalue = null;
        try {
            try {
                a2 = k.a(Uri.parse("content://com.miui.securitycore.fileProvider/root_files").buildUpon().appendPath(str2).build(), true, false);
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.Provider.XM", "InquireSpNote failed !", e);
                if (0 != 0) {
                    setvalue.close();
                }
            }
            if (a2 == null || a2.getTempStreamFilePath() == null) {
                if (a2 != null) {
                    a2.close();
                }
                return null;
            }
            SharedPreferences a3 = g.a(StrategyFramework.getFrameworkContext(), new File(a2.getTempStreamFilePath()));
            if (a3 == null) {
                a("getSharedPreferencesByFileFailed");
                if (a2 != null) {
                    a2.close();
                }
                return null;
            }
            num = Integer.valueOf(Integer.parseInt(String.valueOf(a3.getLong(str, 0L))));
            if (a2 != null) {
                a2.close();
            }
            return num;
        } catch (Throwable th) {
            if (0 != 0) {
                setvalue.close();
            }
            throw th;
        }
    }
}