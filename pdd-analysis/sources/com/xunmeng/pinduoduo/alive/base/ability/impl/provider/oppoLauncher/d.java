package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLauncher;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import com.android.launcher3.proxy.StartActivityParams;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.g;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.j;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IntentWrapper;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.LayoutProps;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.OppoIconInfo;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: d.class */
public class d {
    private static final String a = null;
    private static String b = null;

    public static String d() {
        try {
            Bundle call = StrategyFramework.getFrameworkContext().getContentResolver().call(Uri.parse(g()), "getLauncherModeSettings", (String) null, (Bundle) null);
            String str = "";
            if (call != null) {
                str = call.getString("launcher_table", "");
            }
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "get launcher table name : %s ", new Object[]{str});
            return !TextUtils.isEmpty(str) ? str : "singledesktopitems";
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "obtain table launcher name failed : ", e);
            j.a("oppo_launcher_update", "getLauncherTable", e);
            return "singledesktopitems";
        }
    }

    public static String h() {
        return f() + b("/data/user_de/%d/%s/shared_prefs/%s_unique_shared_prefs.xml");
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, int i) {
        try {
            String d = d();
            int delete = sQLiteDatabase.delete(d, "_id=?", new String[]{String.valueOf(i)});
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "remove icon updatedRows: %d, tableName: %s", new Object[]{Integer.valueOf(delete), d});
            return delete == 1;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "remove icon failed : ", e);
            j.a("oppo_launcher_update", "removeIconInner", e);
            return false;
        }
    }

    public static List a() {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(e())));
        if (a2 == null) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "open file failed when get icons !");
            return null;
        }
        List a3 = a(a2.getDatabase());
        a2.close();
        Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "get launcher icon list size : %s ", new Object[]{Integer.valueOf(a3.size())});
        return a3;
    }

    public static IntentWrapper j() {
        return k() ? new IntentWrapper("oppo_launcher_update", "content://com.android.launcher.file_provider/root#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.android.launcher.file_provider/root", false, false, 0, "", "") : new IntentWrapper("oppo_launcher_update", "content://com.oppo.launcher.file_provider/root#Intent;component=com.xunmeng.pinduoduo/com.xunmeng.pinduoduo.alive.impl.provider.component.HFPActivity;end", "content://com.oppo.launcher.file_provider/root", false, false, 0, "", "");
    }

    public static boolean a(OppoIconInfo oppoIconInfo) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(e())));
        if (a2 == null) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "open file failed when add icon !");
            return false;
        }
        boolean a3 = a(a2.getDatabase(), oppoIconInfo);
        if (a3) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "add icon success ? %s", new Object[]{Boolean.valueOf(a3)});
        return a3;
    }

    public static String g() {
        return k() ? "content://com.android.launcher.settings" : "content://com.oppo.launcher.settings";
    }

    static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()), i());
    }

    static String b(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()), i(), i());
    }

    public static String f() {
        return String.format("content://%s.file_provider/root", i());
    }

    public static IntentWrapper a(IntentWrapper intentWrapper) {
        String i = i();
        String targetIntent = intentWrapper.getTargetIntent();
        String targetUri = intentWrapper.getTargetUri();
        if (!TextUtils.isEmpty(intentWrapper.getTargetIntent())) {
            targetIntent = String.format(intentWrapper.getTargetIntent(), i);
        }
        if (!TextUtils.isEmpty(intentWrapper.getTargetUri())) {
            targetUri = String.format(intentWrapper.getTargetUri(), i);
        }
        return new IntentWrapper(intentWrapper.getScene(), targetIntent, targetUri, intentWrapper.isNeedSpecialFlags(), intentWrapper.isNeedTransit(), intentWrapper.getFlags(), intentWrapper.getTargetUri(), intentWrapper.getTransitIntentKey());
    }

    public static Intent a(Intent intent) {
        Intent intent2 = new Intent();
        intent2.addFlags(-2122317824);
        intent2.putExtra("start-activity-params", (Parcelable) new StartActivityParams(intent, 0));
        String i = i();
        Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "current launcher pkg name : %s ", new Object[]{i});
        if (TextUtils.isEmpty(i)) {
            i = "com.android.launcher";
        }
        intent2.setComponent(new ComponentName(i, "com.android.launcher3.proxy.ProxyActivityStarter"));
        return intent2;
    }

    static int[] c() {
        SharedPreferences a2;
        k.a aVar = null;
        try {
            aVar = k.a(Uri.parse(h()));
            String tempStreamFilePath = aVar.getTempStreamFilePath();
            if (tempStreamFilePath == null || (a2 = g.a(StrategyFramework.getFrameworkContext(), new File(tempStreamFilePath))) == null) {
                if (aVar == null) {
                    return null;
                }
                aVar.close();
                return null;
            }
            int i = a2.getInt("layout_cellcount_x", -1);
            int i2 = a2.getInt("layout_cellcount_y", -1);
            int i3 = a2.getInt("launcher_mode", -1);
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "get layout props from sp ,x : %d, y : %d, launcher mode : %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
            if (i == -1 || i2 == -1 || i3 == -1) {
                if (aVar != null) {
                    aVar.close();
                }
                return null;
            }
            int[] iArr = {i, i2, i3};
            if (aVar != null) {
                aVar.close();
            }
            return iArr;
        } catch (Throwable th) {
            try {
                Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "get layout from Sp failed !", th);
            } finally {
                if (aVar != null) {
                    aVar.close();
                }
            }
        }
    }

    public static String e() {
        return f() + a("/data/user_de/%d/%s/databases/launcher.db");
    }

    public static boolean a(int i) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(e())));
        if (a2 == null) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "open file failed when add icon !");
            return false;
        }
        boolean a3 = a(a2.getDatabase(), i);
        if (a3) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "add icon success ? %s", new Object[]{Boolean.valueOf(a3)});
        return a3;
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, OppoIconInfo oppoIconInfo) {
        if (!b(oppoIconInfo)) {
            return false;
        }
        try {
            Cursor query = sQLiteDatabase.query(d(), null, "appWidgetProvider = ?", new String[]{oppoIconInfo.getAppWidgetProvider()}, null, null, null);
            if (query != null) {
                int count = query.getCount();
                query.close();
                if (count > 0) {
                    Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "%s already be added in db !", new Object[]{oppoIconInfo});
                    return true;
                }
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", oppoIconInfo.getTitle());
            contentValues.put("container", Integer.valueOf(oppoIconInfo.getContainerId()));
            contentValues.put("screen", Integer.valueOf(oppoIconInfo.getScreen()));
            contentValues.put("cellX", Integer.valueOf(oppoIconInfo.getCellX()));
            contentValues.put("cellY", Integer.valueOf(oppoIconInfo.getCellY()));
            contentValues.put("spanX", Integer.valueOf(oppoIconInfo.getSpanX()));
            contentValues.put("spanY", Integer.valueOf(oppoIconInfo.getSpanY()));
            contentValues.put("itemType", Integer.valueOf(oppoIconInfo.getItemType()));
            contentValues.put("appWidgetId", Integer.valueOf(oppoIconInfo.getAppWidgetId()));
            contentValues.put("appWidgetProvider", oppoIconInfo.getAppWidgetProvider());
            contentValues.put("modified", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("restored", Integer.valueOf(oppoIconInfo.getRestored()));
            contentValues.put("profileId", Long.valueOf(oppoIconInfo.getProfileId()));
            contentValues.put("rank", Integer.valueOf(oppoIconInfo.getScreenRank()));
            contentValues.put("options", Integer.valueOf(oppoIconInfo.getOptions()));
            contentValues.put("user_id", Integer.valueOf(oppoIconInfo.getUserId()));
            contentValues.put("intent", oppoIconInfo.getIntent());
            long insert = sQLiteDatabase.insert(d(), null, contentValues);
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "addIcon newIconId: %d in tableName: %s", new Object[]{Long.valueOf(insert), d()});
            return insert != 0;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "add widget failed : ", e);
            j.a("oppo_launcher_update", "addIconInner", e);
            return false;
        }
    }

    private static List a(SQLiteDatabase sQLiteDatabase) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.query(d(), b.C, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    OppoIconInfo oppoIconInfo = new OppoIconInfo();
                    oppoIconInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    oppoIconInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    oppoIconInfo.setContainerId(cursor.getInt(cursor.getColumnIndex("container")));
                    oppoIconInfo.setScreen(cursor.getInt(cursor.getColumnIndex("screen")));
                    oppoIconInfo.setCellX(cursor.getInt(cursor.getColumnIndex("cellX")));
                    oppoIconInfo.setCellY(cursor.getInt(cursor.getColumnIndex("cellY")));
                    oppoIconInfo.setSpanX(cursor.getInt(cursor.getColumnIndex("spanX")));
                    oppoIconInfo.setSpanY(cursor.getInt(cursor.getColumnIndex("spanY")));
                    oppoIconInfo.setItemType(cursor.getInt(cursor.getColumnIndex("itemType")));
                    oppoIconInfo.setAppWidgetId(cursor.getInt(cursor.getColumnIndex("appWidgetId")));
                    oppoIconInfo.setAppWidgetProvider(cursor.getString(cursor.getColumnIndex("appWidgetProvider")));
                    oppoIconInfo.setModified(cursor.getLong(cursor.getColumnIndex("modified")));
                    oppoIconInfo.setRestored(cursor.getInt(cursor.getColumnIndex("restored")));
                    oppoIconInfo.setProfileId(cursor.getInt(cursor.getColumnIndex("profileId")));
                    oppoIconInfo.setScreenRank(cursor.getInt(cursor.getColumnIndex("rank")));
                    oppoIconInfo.setOptions(cursor.getInt(cursor.getColumnIndex("options")));
                    oppoIconInfo.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                    oppoIconInfo.setIntent(cursor.getString(cursor.getColumnIndex("intent")));
                    arrayList.add(oppoIconInfo);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "get launcher icons failed : ", e);
                j.a("oppo_launcher_update", "getLauncherIconsInner", e);
                if (cursor != null) {
                    cursor.close();
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static LayoutProps b() {
        int[] c = c();
        if (c == null || c.length != 3) {
            return null;
        }
        int i = c[0];
        return new LayoutProps(c[2], c[1], i, -6.8518E-41f, -6.8518E-41f);
    }

    private static boolean b(OppoIconInfo oppoIconInfo) {
        if (oppoIconInfo.getRestored() != 1) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "widget restored is not valid !");
            return false;
        } else if (oppoIconInfo.getItemType() != 5) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "it is not oppo widget item type !");
            return false;
        } else if (!TextUtils.isEmpty(oppoIconInfo.getAppWidgetProvider())) {
            return true;
        } else {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "it is not a valid AppWidgetProvider !");
            return false;
        }
    }

    public static String i() {
        if (TextUtils.isEmpty(b)) {
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                ResolveInfo resolveActivity = StrategyFramework.getFrameworkContext().getPackageManager().resolveActivity(intent, 16777216);
                if (resolveActivity != null && resolveActivity.activityInfo != null) {
                    b = resolveActivity.activityInfo.packageName;
                }
            } catch (Exception e) {
                Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "Get launcher pkg name failed !", e);
                j.a("oppo_launcher_update", "getLauncherPkg", e);
            }
        }
        if (TextUtils.isEmpty(b)) {
            Logger.i("LVBA.AliveModule.OppoLPImpl.Utils", "Get launcher pkg name by intent failed !");
            if (k()) {
                b = "com.android.launcher";
            } else {
                b = "com.oppo.launcher";
            }
        }
        Logger.e("LVBA.AliveModule.OppoLPImpl.Utils", "Get launcher pkg name : %s", new Object[]{b});
        return b;
    }

    public static boolean k() {
        return !TextUtils.isEmpty(PddSystemProperties.instance().get("ro.build.version.oplusrom", ""));
    }
}
