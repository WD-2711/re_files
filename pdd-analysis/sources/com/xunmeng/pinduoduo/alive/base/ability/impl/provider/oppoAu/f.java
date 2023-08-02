package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoAu;

import android.content.ContentValues;
import android.content.Intent;
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
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.HashMap;
import java.util.Map;

/* loaded from: f.class */
class f {
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
        return str != null && str.contains("<filter-name>apps_launcher_cannot_uninstall_pkgs</filter-name>") && str.contains("<string-array name=\"local_forbidden_uninstall_pkg\">");
    }

    private static Pair c(String str) {
        int lastIndexOf;
        StringBuilder sb = new StringBuilder(str);
        int lastIndexOf2 = sb.lastIndexOf("<item>");
        if (lastIndexOf2 > 0 && (lastIndexOf = sb.substring(0, lastIndexOf2).lastIndexOf("</item>")) > 0) {
            return new Pair(sb.substring(lastIndexOf + "</item>".length(), lastIndexOf2), Integer.valueOf(lastIndexOf));
        }
        return null;
    }

    private static String b(String str, String str2) {
        String d = d(str2);
        Pair c = c(str);
        return str.replace((c == null ? "" : (String) c.first) + d, "");
    }

    private static String d(String str) {
        return "<item>" + str + "</item>";
    }

    private static Boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            String b = b(sQLiteDatabase);
            if (TextUtils.isEmpty(b)) {
                return null;
            }
            boolean z = !a(b, "com.xunmeng.pinduoduo");
            Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "isUninstallableFromDb result: %s", new Object[]{Boolean.valueOf(z)});
            return Boolean.valueOf(z);
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "isUninstallableFromDb failed: ", e);
            j.a("provider_oppo_au", "isUninstallableFromDb", e);
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
        Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "setUninstall success: %s, enableUninstall: %s", new Object[]{Boolean.valueOf(a3), Boolean.valueOf(z)});
        return a3;
    }

    private static boolean a(String str, String str2) {
        return str.contains(d(str2));
    }

    private static String b(SQLiteDatabase sQLiteDatabase) {
        try {
            String str = null;
            Cursor query = sQLiteDatabase.query("update_list", g.f, "filterName=?", new String[]{"apps_launcher_cannot_uninstall_pkgs"}, null, null, null);
            if (query.moveToNext()) {
                str = query.getString(query.getColumnIndex("xml"));
            } else {
                Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "getUninstallPkgXml: query no record");
            }
            query.close();
            if (TextUtils.isEmpty(str)) {
                Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "getUninstallPkgXml: empty encoded xml");
                j.a("provider_oppo_au", "emptyXml", (Map) new HashMap(), false, true);
                return null;
            }
            String f = f(str);
            Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "getUninstallPkgXml: decoded xml: \n%s", new Object[]{f});
            if (b(f)) {
                return f;
            }
            Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "getUninstallPkgXml: invalid xml");
            HashMap hashMap = new HashMap();
            hashMap.put("xmlContent", f);
            j.a("provider_oppo_au", "invalidXml", (Map) hashMap, false, true);
            return null;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "getUninstallPkgXml failed: ", e);
            j.a("provider_oppo_au", "getUninstallPkgXml", e);
            return null;
        }
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, boolean z) {
        try {
            String b = b(sQLiteDatabase);
            if (TextUtils.isEmpty(b)) {
                return false;
            }
            boolean z2 = !a(b, "com.xunmeng.pinduoduo");
            if (z2 == z) {
                Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "setUninstallToDb: skip update db since isUnInstallable in db: %s", new Object[]{Boolean.valueOf(z2)});
                return true;
            }
            String b2 = z ? b(b, "com.xunmeng.pinduoduo") : c(b, "com.xunmeng.pinduoduo");
            Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "setUninstallToDb: updatedXml: \n%s", new Object[]{b2});
            if (TextUtils.isEmpty(b2)) {
                HashMap hashMap = new HashMap();
                hashMap.put("xmlContent", b);
                j.a("provider_oppo_au", "invalidUpdatedXml", (Map) hashMap, false, true);
                return false;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("xml", e(b2));
            int update = sQLiteDatabase.update("update_list", contentValues, "filterName=?", new String[]{"apps_launcher_cannot_uninstall_pkgs"});
            Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "setUninstallToDb: updatedRows: %d", new Object[]{Integer.valueOf(update)});
            return update == 1;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "setUninstallToDb failed: ", e);
            j.a("provider_oppo_au", "setUninstallToDb", e);
            return false;
        }
    }

    private static String e(String str) {
        return new a(Build.PRODUCT).a(str);
    }

    private static String f(String str) {
        return new a(Build.PRODUCT).b(str);
    }

    public static boolean b() {
        Logger.i("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "restartLauncher");
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.addFlags(8392704);
            StrategyFramework.getFrameworkContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.Provider.oppoAu.UninstallUtils", "fail to restartLauncher", e);
            j.a("provider_oppo_au", "restartLauncher", e);
            return false;
        }
    }

    f() {
    }

    private static String c(String str, String str2) {
        Pair c = c(str);
        if (c == null) {
            return null;
        }
        return new StringBuilder(str).insert(((Integer) c.second).intValue() + "</item>".length(), ((String) c.first) + d(str2)).toString();
    }
}
