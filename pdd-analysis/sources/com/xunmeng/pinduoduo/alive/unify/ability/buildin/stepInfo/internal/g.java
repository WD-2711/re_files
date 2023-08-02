package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;
import java.util.TimeZone;

/* loaded from: g.class */
public class g extends e {
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public KarmaResult b() {
        String str = "";
        if (d() || e()) {
            str = "content://com.miui.providers.steps/item";
        } else if (f()) {
            str = "content://com.miui.providers.steps/item_latest";
        }
        if (TextUtils.isEmpty(str)) {
            com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("no_support_version");
            return new KarmaResult("invalid rom version");
        }
        Cursor cursor = null;
        try {
            try {
                Cursor query = StorageApi.instance().query(this.b.getContentResolver(), Uri.parse(str), new String[0], "", new String[0], "");
                if (query == null) {
                    Logger.i(a, "empty cursor");
                    com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("null_cursor");
                    KarmaResult karmaResult = new KarmaResult("file_not_found");
                    if (query != null) {
                        query.close();
                    }
                    return karmaResult;
                }
                int i = 0;
                long g2 = g();
                long g3 = g() + 0;
                while (query.moveToNext()) {
                    long j = query.getLong(query.getColumnIndex("_begin_time"));
                    long j2 = query.getLong(query.getColumnIndex("_end_time"));
                    if (j >= g2 && j2 >= g2 && j < g3 && j2 < g3) {
                        i += query.getInt(query.getColumnIndex("_steps"));
                    }
                }
                Logger.i(a, "xm step is : %d", new Object[]{Integer.valueOf(i)});
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("get_success");
                KarmaResult karmaResult2 = new KarmaResult(i, TimeStamp.instance().getRealLocalTimeV2());
                if (query != null) {
                    query.close();
                }
                return karmaResult2;
            } catch (Exception e2) {
                Logger.e(a, "read from file error", e2);
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("query_error");
                KarmaResult karmaResult3 = new KarmaResult("read_from_file_error");
                if (0 != 0) {
                    cursor.close();
                }
                return karmaResult3;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public boolean a() {
        return d() || e() || f();
    }

    private static String c() {
        String version = RomOsUtil.instance().getVersion();
        return (version == null || TextUtils.isEmpty(version)) ? "" : version.toLowerCase();
    }

    private static boolean e() {
        return TextUtils.equals("v125", c());
    }

    private static boolean d() {
        return TextUtils.equals("v12", c());
    }

    private static boolean f() {
        return TextUtils.equals("v130", c());
    }

    private long g() {
        long realLocalTimeV2 = TimeStamp.instance().getRealLocalTimeV2();
        return (realLocalTimeV2 - (realLocalTimeV2 % 0)) - TimeZone.getDefault().getRawOffset();
    }
}
