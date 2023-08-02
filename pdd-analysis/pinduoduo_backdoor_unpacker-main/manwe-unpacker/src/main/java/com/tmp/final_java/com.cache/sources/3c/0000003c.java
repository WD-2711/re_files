package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hwselfstart;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Process;
import android.os.SystemClock;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.g;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.IMMKV;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.MMKVCompat;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.io.File;

/* loaded from: c.class */
public class c {
    private static final String a = null;
    private static final String b = null;
    private static IMMKV c;

    private static boolean a(SQLiteDatabase sQLiteDatabase, boolean z, String str) {
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        String str6 = "";
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.query("smartAppsControlCloudTable", null, "packageName = ?", new String[]{str}, null, null, null);
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.HwSelfStartProvider", "rw db error" + e);
                if (cursor != null) {
                    cursor.close();
                }
            }
            if (cursor == null || cursor.getCount() <= 0) {
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            }
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                str2 = cursor.getString(cursor.getColumnIndex("operation"));
                str3 = cursor.getString(cursor.getColumnIndex("autoStart"));
                str4 = cursor.getString(cursor.getColumnIndex("autoAwakedBy"));
                str5 = cursor.getString(cursor.getColumnIndex("background"));
                str6 = cursor.getString(cursor.getColumnIndex("isShow"));
            }
            if (cursor != null) {
                cursor.close();
            }
            boolean equals = b() ? "0".equals(str5) : "1".equals(str5);
            if ("0".equals(str2) && "0".equals(str3) && "0".equals(str4) && equals) {
                if ((z ? "0" : "1").equals(str6)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static boolean b(String str) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        String d = d(str);
        if (g.a(frameworkContext, Uri.parse(d)) == 1) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "file is not exist, default true!");
            return true;
        }
        k.setValue b2 = k.b(Uri.parse(d));
        if (b2 == null) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "open failed!");
            return false;
        }
        SharedPreferences a2 = g.a(frameworkContext, new File(b2.getTempStreamFilePath()));
        if (a2 == null) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "get sp failed!");
            b2.close();
            return false;
        }
        boolean z = a2.getBoolean("init_app_control_config", true);
        b2.close();
        return !z;
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str) {
        long j = 0;
        a(sQLiteDatabase, str);
        try {
            j = sQLiteDatabase.insert("smartAppsControlCloudTable", null, contentValues);
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "rw db error" + e);
        }
        return j != 0;
    }

    private static boolean c(String str) {
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        String d = d(str);
        if (g.a(frameworkContext, Uri.parse(d)) == 1) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "file is not exist, default true!");
            return true;
        }
        k.setValue b2 = k.b(Uri.parse(d));
        if (b2 == null) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "open failed!");
            return false;
        }
        SharedPreferences a2 = g.a(frameworkContext, new File(b2.getTempStreamFilePath()));
        if (a2 == null) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "get sp failed!");
            b2.close();
            return false;
        } else if (!a2.getBoolean("init_app_control_config", true)) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "update switch already open!");
            b2.close();
            return true;
        } else {
            SharedPreferences.Editor edit = a2.edit();
            edit.putBoolean("init_app_control_config", false);
            edit.commit();
            boolean a3 = k.a((IStreamHandle) b2);
            b2.close();
            return a3;
        }
    }

    private static String d(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(boolean z, String str) {
        long currentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        String str2 = str + "_" + z + "_" + (b());
        long j = d().getLong(str2, 0L);
        Logger.i("LVBA.AliveModule.HwSelfStartProvider", "key is " + str2 + " lastSuccessTime is " + j + " deviceBootTime " + currentTimeMillis);
        return j != 0 && currentTimeMillis > j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(ContentValues contentValues, boolean z, String str) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(d("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/databases/clouds_permission.db")));
        if (a2 == null) {
            return false;
        }
        if (a2.getDatabase() == null) {
            a2.close();
            return false;
        } else if (a(a2.getDatabase(), z, str)) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "selfStart is already add!");
            a2.close();
            return true;
        } else {
            boolean z2 = false;
            if (a(a2.getDatabase(), contentValues, str)) {
                z2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
            }
            a2.close();
            if (z2) {
                long currentTimeMillis = System.currentTimeMillis();
                d().clear();
                d().putLong(str + "_" + z + "_" + (b()), currentTimeMillis);
                Logger.i("LVBA.AliveModule.HwSelfStartProvider", "record timestamp when succeed.");
            }
            return z2;
        }
    }

    private static IMMKV d() {
        if (c != null) {
            return c;
        }
        c = MMKVCompat.module("hw_self_start", false);
        return c;
    }

    private static boolean b(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursor = null;
        try {
            try {
                Cursor query = sQLiteDatabase.query("smartAppsControlCloudTable", null, "packageName = ?", new String[]{str}, null, null, null);
                if (query == null) {
                    Logger.i("LVBA.AliveModule.HwSelfStartProvider", "query is null, no need to clear");
                    if (query != null) {
                        query.close();
                    }
                    return true;
                } else if (query.getCount() == 0) {
                    Logger.i("LVBA.AliveModule.HwSelfStartProvider", "query num is 0, no need to clear");
                    if (query != null) {
                        query.close();
                    }
                    return true;
                } else if (query == null) {
                    return false;
                } else {
                    query.close();
                    return false;
                }
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.HwSelfStartProvider", "rw db error " + e);
                if (0 == 0) {
                    return false;
                }
                cursor.close();
                return false;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        if (!c("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/shared_prefs/power_settings.xml")) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "write em failed!");
            return false;
        } else if (c("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/shared_prefs/app_control_settings.xml")) {
            return true;
        } else {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "write hm failed!");
            return false;
        }
    }

    private static boolean c(boolean z, String str) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(d("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/databases/clouds_permission.db")));
        if (a2 == null) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "dbHandle is null");
            return false;
        } else if (a2.getDatabase() == null) {
            a2.close();
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "db is null");
            return false;
        } else {
            boolean a3 = a(a2.getDatabase(), z, str);
            a2.close();
            return a3;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b(boolean z, String str) {
        if (!c(z, str)) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "db not set completed!");
            return false;
        } else if (!c()) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "sp not set completed!");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "ss is setCompleted!");
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("ab_hw_fp_ability_ss_background_58100", "false"));
    }

    private static void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.delete("smartAppsControlCloudTable", "packageName = ?", new String[]{str});
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "del error" + e);
        }
    }

    private static boolean c() {
        if (!b("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/shared_prefs/power_settings.xml")) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "em sp switch close!");
            return false;
        } else if (!b("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/shared_prefs/app_control_settings.xml")) {
            Logger.e("LVBA.AliveModule.HwSelfStartProvider", "hm sp switch close!");
            return false;
        } else {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "sp is set!");
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(d("content://com.android.settings.files/my_root/data/user_de/%d/com.huawei.systemmanager/databases/clouds_permission.db")));
        if (a2 == null) {
            return false;
        }
        if (a2.getDatabase() == null) {
            a2.close();
            return false;
        } else if (b(a2.getDatabase(), str)) {
            Logger.i("LVBA.AliveModule.HwSelfStartProvider", "query is empty, no need to clear!");
            a2.close();
            return true;
        } else {
            a(a2.getDatabase(), str);
            boolean a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
            if (a3) {
                d().clear();
                Logger.i("LVBA.AliveModule.HwSelfStartProvider", "success clear ss wl");
            }
            a2.close();
            return a3;
        }
    }
}