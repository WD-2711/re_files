package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.permQuery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Process;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: c.class */
public class c {
    static void a(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public static boolean a(int i, SQLiteDatabase sQLiteDatabase, String str, String str2, String[] strArr, String str3) {
        if (sQLiteDatabase != null) {
            try {
                try {
                    Cursor query = sQLiteDatabase.query(str, null, str2, strArr, null, null, null);
                    if (query == null || query.getCount() == 0) {
                        a(query);
                        return false;
                    } else if (!query.moveToNext()) {
                        a(query);
                        return false;
                    } else {
                        int columnIndex = query.getColumnIndex(str3);
                        if (columnIndex < 0) {
                            Logger.i("LVBA.AliveModule.PermQuery", "invalid column name !");
                            a(query);
                            return false;
                        }
                        int i2 = query.getInt(columnIndex);
                        Logger.i("LVBA.AliveModule.PermQuery", "current value in %s / %s column is : %s", new Object[]{str3, str, Integer.valueOf(i2)});
                        boolean z = i2 == i;
                        a(query);
                        return z;
                    }
                } catch (Exception e) {
                    Logger.e("LVBA.AliveModule.PermQuery", "check target valid failed !", e);
                    a((Cursor) null);
                    return false;
                }
            } catch (Throwable th) {
                a((Cursor) null);
                throw th;
            }
        }
        return false;
    }

    public static void a(b.a aVar) {
        if (aVar != null) {
            aVar.close();
        }
    }

    public static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()));
    }
}
