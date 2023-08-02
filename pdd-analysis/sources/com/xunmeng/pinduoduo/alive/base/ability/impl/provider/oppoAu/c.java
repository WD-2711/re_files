package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.j;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import java.util.HashMap;
import java.util.Map;

/* loaded from: c.class */
class c {
    private static final String a = null;

    public static Boolean a() {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a("content://com.coloros.phonemanager.files/clear_share/data/user_de/%s/com.coloros.appmanager/databases/rom_update.db")));
        if (a2 == null) {
            return null;
        }
        Boolean a3 = a(a2.getDatabase());
        a2.close();
        return a3;
    }

    private static boolean b(String str) {
        return str != null && str.contains("<filter-name>sys_wms_intercept_window</filter-name>");
    }

    private static Pair c(String str) {
        int lastIndexOf;
        StringBuilder sb = new StringBuilder(str);
        int lastIndexOf2 = sb.lastIndexOf("<skip att=\"");
        if (lastIndexOf2 > 0 && (lastIndexOf = sb.substring(0, lastIndexOf2).lastIndexOf("\"/>")) > 0) {
            return new Pair(sb.substring(lastIndexOf + "\"/>".length(), lastIndexOf2), Integer.valueOf(lastIndexOf));
        }
        return null;
    }

    private static String b(String str, String str2) {
        String d = d(str2);
        Pair c = c(str);
        return str.replace((c == null ? "" : (String) c.first) + d, "");
    }

    private static String d(String str) {
        return "<skip att=\"" + str + "\"/>";
    }

    private static Boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            String b = b(sQLiteDatabase);
            if (TextUtils.isEmpty(b)) {
                return null;
            }
            Boolean valueOf = Boolean.valueOf(a(b, "com.xunmeng.pinduoduo"));
            Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "isLockDisplayAbleFromDb result: %s", new Object[]{valueOf});
            return valueOf;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "isLockDisplayAbleFromDb failed: ", e);
            j.a("provider_oppo_ld", "isLockDisplayAbleFromDb", e);
            return null;
        }
    }

    static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()));
    }

    public static boolean a(boolean z) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a("content://com.coloros.phonemanager.files/clear_share/data/user_de/%s/com.coloros.appmanager/databases/rom_update.db")));
        if (a2 == null) {
            return false;
        }
        boolean a3 = a(a2.getDatabase(), z);
        if (a3) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "setLockDisplay success: %s, enableLockDisplay: %s", new Object[]{Boolean.valueOf(a3), Boolean.valueOf(z)});
        return a3;
    }

    private static boolean a(String str, String str2) {
        return str.contains(d(str2));
    }

    private static String b(SQLiteDatabase sQLiteDatabase) {
        try {
            String str = null;
            Cursor query = sQLiteDatabase.query("update_list", g.f, "filterName=?", new String[]{"sys_wms_intercept_window"}, null, null, null);
            if (query.moveToNext()) {
                str = query.getString(query.getColumnIndex("xml"));
            } else {
                Logger.e("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "getLockDisplayPkgXml: query no record");
            }
            query.close();
            if (TextUtils.isEmpty(str)) {
                Logger.e("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "getLockDisplayPkgXml: empty encoded xml");
                j.a("provider_oppo_ld", "emptyXml", (Map) new HashMap(), false, true);
                return null;
            }
            String f = f(str);
            Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "getLockDisplayPkgXml: decoded xml: \n%s", new Object[]{f});
            if (b(f)) {
                return f;
            }
            Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "getLockDisplayPkgXml: invalid xml");
            HashMap hashMap = new HashMap();
            hashMap.put("xmlContent", f);
            j.a("provider_oppo_ld", "invalidXml", (Map) hashMap, false, true);
            return null;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "getLockDisplayPkgXml failed: ", e);
            j.a("provider_oppo_ld", "getLockDisplayPkgXml", e);
            return null;
        }
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, boolean z) {
        try {
            String b = b(sQLiteDatabase);
            if (TextUtils.isEmpty(b)) {
                return false;
            }
            boolean a2 = a(b, "com.xunmeng.pinduoduo");
            if (a2 == z) {
                Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "setLockDisplayToDb: skip update db since isLockDisplayAble in db: %s", new Object[]{Boolean.valueOf(a2)});
                return true;
            }
            String c = z ? c(b, "com.xunmeng.pinduoduo") : b(b, "com.xunmeng.pinduoduo");
            Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "setLockDisplayToDb: updatedXml:\n%s", new Object[]{c});
            if (TextUtils.isEmpty(c)) {
                HashMap hashMap = new HashMap();
                hashMap.put("xmlContent", b);
                j.a("provider_oppo_ld", "invalidUpdatedXml", (Map) hashMap, false, true);
                return false;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("xml", e(c));
            int update = sQLiteDatabase.update("update_list", contentValues, "filterName=?", new String[]{"sys_wms_intercept_window"});
            Logger.i("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "setLockDisplayToDb: updateRows: %d", new Object[]{Integer.valueOf(update)});
            return update == 1;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoLD.LockDisplayUtils", "setLockDisplayToDb failed: ", e);
            j.a("provider_oppo_ld", "setLockDisplayToDb", e);
            return false;
        }
    }

    private static String e(String str) {
        return new a(Build.PRODUCT).a(str);
    }

    private static String f(String str) {
        return new a(Build.PRODUCT).b(str);
    }

    c() {
    }

    private static String c(String str, String str2) {
        Pair c = c(str);
        if (c == null) {
            return null;
        }
        return new StringBuilder(str).insert(((Integer) c.second).intValue() + "\"/>".length(), ((String) c.first) + d(str2)).toString();
    }
}
