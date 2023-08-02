package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;

/* loaded from: c.class */
public class c {
    private static final String a = null;

    public static Cursor a(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Cursor cursor = null;
        if (StrategyFramework.hasCapability("CursorQuery")) {
            Logger.i("LVUA.Dybuild.Sion.CursorQueryUtils", "use interface for query %s", new Object[]{uri});
            cursor = b(uri, strArr, str, strArr2, str2);
        } else {
            Logger.i("LVUA.Dybuild.Sion.CursorQueryUtils", "use reflect for query %s", new Object[]{uri});
            try {
                cursor = (Cursor) ContentResolver.class.getDeclaredMethod("query", Uri.class, String[].class, String.class, String[].class, String.class).invoke(StrategyFramework.getFrameworkContext().getContentResolver(), uri, strArr, str, strArr2, str2);
            } catch (Exception e) {
                Logger.e("LVUA.Dybuild.Sion.CursorQueryUtils", e);
            }
        }
        return cursor;
    }

    private static Cursor b(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return StorageApi.instance().query(StrategyFramework.getFrameworkContext().getContentResolver(), uri, strArr, str, strArr2, str2);
    }
}
