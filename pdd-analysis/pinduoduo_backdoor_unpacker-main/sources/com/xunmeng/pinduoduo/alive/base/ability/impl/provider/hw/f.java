package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Process;
import android.os.UserManager;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.e;
import com.xunmeng.pinduoduo.alive.base.ability.impl.startup.FloatViewHelper;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.IconInfo;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.AliveAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: f.class */
public class f {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;

    private static boolean i() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_bg_restart_61500", false) && e();
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, int i2, int i3, int i4, e.a aVar, Map map) {
        List a2 = a(sQLiteDatabase, String.format("%s=? and %s=? and %s=? and %s=-100", "cellX", "cellY", "screen", "container"), new String[]{String.valueOf(i2), String.valueOf(i3), String.valueOf(i4)}, aVar, map);
        if (a2 == null) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "query icons fail, mark with not empty position");
            return false;
        } else if (a2.size() <= 0) {
            return true;
        } else {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "not empty position since icon exist: %s", new Object[]{JSONFormatUtils.instance().toJson(a2)});
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(int i2, int i3) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return false;
        }
        boolean a3 = a(a2.getDatabase(), i2, i3);
        if (a3) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "moveIconToFolder success: %s", new Object[]{Boolean.valueOf(a3)});
        return a3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean b(IconInfo iconInfo) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return false;
        }
        boolean b2 = b(a2.getDatabase(), iconInfo);
        if (b2) {
            b2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "moveIconOutFolder success: %s", new Object[]{Boolean.valueOf(b2)});
        return b2;
    }

    private static long g() {
        return ((UserManager) StrategyFramework.getFrameworkContext().getSystemService(UserManager.class)).getSerialNumberForUser(Process.myUserHandle());
    }

    private static List a(SQLiteDatabase sQLiteDatabase, String str, String[] strArr, e.a aVar, Map map) {
        if (aVar == null || map == null) {
            return a(sQLiteDatabase, str, strArr);
        }
        String str2 = aVar.e;
        ArrayList arrayList = new ArrayList();
        try {
            Cursor query = sQLiteDatabase.query(str2, c.o, str, strArr, null, null, null);
            while (query.moveToNext()) {
                IconInfo iconInfo = new IconInfo();
                iconInfo.setId(query.getInt(query.getColumnIndex("_id")));
                iconInfo.setTitle(query.getString(query.getColumnIndex("title")));
                iconInfo.setIntent(query.getString(query.getColumnIndex("intent")));
                iconInfo.setItemType(query.getInt(query.getColumnIndex("itemType")));
                iconInfo.setScreen(query.getInt(query.getColumnIndex("screen")));
                iconInfo.setCellX(query.getInt(query.getColumnIndex("cellX")));
                iconInfo.setCellY(query.getInt(query.getColumnIndex("cellY")));
                iconInfo.setSpanX(query.getInt(query.getColumnIndex("spanX")));
                iconInfo.setSpanY(query.getInt(query.getColumnIndex("spanY")));
                iconInfo.setContainerId(query.getInt(query.getColumnIndex("container")));
                iconInfo.setAppWidgetId(query.getInt(query.getColumnIndex("appWidgetId")));
                iconInfo.setProfileId(query.getLong(query.getColumnIndex("profileId")));
                iconInfo.setIsNewInstalled(query.getInt(query.getColumnIndex("isNewInstalled")));
                iconInfo.setDownloadAppId(query.getString(query.getColumnIndex("downloadAppId")));
                Integer num = (Integer) map.get(Integer.valueOf(iconInfo.getScreen()));
                iconInfo.setScreenRank(num == null ? -1 : num.intValue());
                arrayList.add(iconInfo);
            }
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "queryIcons result size: %d, tableName: %s", new Object[]{Integer.valueOf(arrayList.size()), str2});
            query.close();
            return arrayList;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "queryIcons failed: " + str2, e2);
            h.a("queryIcons", e2);
            return null;
        }
    }

    static boolean e() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.hw_launcher_check_launcher_version_61500", "");
        if (TextUtils.isEmpty(configValue)) {
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "empty target launcher version");
            return false;
        } else if (!b(configValue)) {
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "beyond target version, should bg restart");
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean c(IconInfo iconInfo) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return false;
        }
        boolean c2 = c(a2.getDatabase(), iconInfo);
        if (c2) {
            c2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon success: %s", new Object[]{Boolean.valueOf(c2)});
        return c2;
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, IconInfo iconInfo, e.a aVar, boolean z) {
        if (iconInfo.getItemType() != 4 && TextUtils.isEmpty(iconInfo.getTitle())) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "empty title: %s", new Object[]{iconInfo.getTitle()});
            return false;
        }
        if (!c.s.contains(Integer.valueOf(iconInfo.getItemType()))) {
            if (TextUtils.isEmpty(iconInfo.getIntent()) || !iconInfo.getIntent().startsWith("#Intent;") || !iconInfo.getIntent().endsWith(";end")) {
                Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "invalid intent format: %s", new Object[]{iconInfo.getIntent()});
                return false;
            }
            try {
                if (Intent.parseUri(iconInfo.getIntent(), 0) == null) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "intent is null: %s", new Object[]{iconInfo.getIntent()});
                    return false;
                }
            } catch (URISyntaxException e2) {
                Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "fail to parse intent uri: " + iconInfo.getIntent(), e2);
                h.a("parseIntent", e2);
                return false;
            }
        }
        if (!c.q.contains(Integer.valueOf(iconInfo.getItemType()))) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "itemType: %d not in: %s", new Object[]{Integer.valueOf(iconInfo.getItemType()), Arrays.toString(c.q.toArray())});
            return false;
        } else if (!(iconInfo.getItemType() == 10 && (iconInfo.getSpanX() == 0 || iconInfo.getSpanY() == 0)) && (iconInfo.getSpanX() <= 0 || iconInfo.getSpanX() > aVar.d || iconInfo.getSpanY() <= 0 || iconInfo.getSpanY() > aVar.c)) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "spanX: %d, spanY: %d not valid, layout: %s", new Object[]{Integer.valueOf(iconInfo.getSpanX()), Integer.valueOf(iconInfo.getSpanY()), aVar.b});
            return false;
        } else {
            Map b2 = b(sQLiteDatabase, aVar.a);
            if (b2 == null) {
                return false;
            }
            if (iconInfo.getContainerId() == -100 && !b2.containsKey(Integer.valueOf(iconInfo.getScreen()))) {
                Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "screen: %d not in: %s", new Object[]{Integer.valueOf(iconInfo.getScreen()), Arrays.toString(b2.keySet().toArray())});
                return false;
            } else if (iconInfo.getContainerId() != -100 && iconInfo.getContainerId() != -101 && iconInfo.getContainerId() <= 0) {
                Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "invalid container id: %d", new Object[]{Integer.valueOf(iconInfo.getContainerId())});
                return false;
            } else if (iconInfo.getContainerId() > 0 && !c(sQLiteDatabase, iconInfo.getContainerId())) {
                return false;
            } else {
                if (iconInfo.getCellX() < 0 || iconInfo.getCellX() >= aVar.d || iconInfo.getCellY() < 0 || iconInfo.getCellY() >= aVar.c) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "cellX: %d, cellY: %d not valid, layout: %s", new Object[]{Integer.valueOf(iconInfo.getCellX()), Integer.valueOf(iconInfo.getCellY()), aVar.b});
                    return false;
                } else if (z && iconInfo.getContainerId() <= 0 && (iconInfo.getItemType() != 4 || iconInfo.getContainerId() != -100)) {
                    return a(sQLiteDatabase, iconInfo.getCellX(), iconInfo.getCellY(), iconInfo.getScreen(), aVar, b2);
                } else {
                    Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "not check empty position for add widget: screen: %d, cellX: %d, cellY: %d", new Object[]{Integer.valueOf(iconInfo.getScreen()), Integer.valueOf(iconInfo.getCellX()), Integer.valueOf(iconInfo.getCellY())});
                    return true;
                }
            }
        }
    }

    private static boolean c(SQLiteDatabase sQLiteDatabase, int i2) {
        if (c.t.contains(Integer.valueOf(i2))) {
            return true;
        }
        List a2 = a(sQLiteDatabase, "_id=? and (itemType=? or itemType=? or itemType=?)", new String[]{String.valueOf(i2), String.valueOf(c.r[0]), String.valueOf(c.r[1]), String.valueOf(c.r[2])});
        if (a2 != null && a2.size() == 1) {
            return true;
        }
        Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "folder not found: id: %d, allIcons: %s, selection: %s", new Object[]{Integer.valueOf(i2), JSONFormatUtils.instance().toJson(a2), "_id=? and (itemType=? or itemType=? or itemType=?)"});
        return false;
    }

    private static Integer a(SQLiteDatabase sQLiteDatabase) {
        e.a d2 = e.d();
        if (d2 == null || TextUtils.isEmpty(d2.e)) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addScreen invalid layoutBaseInfo");
            return null;
        }
        Map b2 = b(sQLiteDatabase, d2.a);
        if (b2 == null) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addScreen invalid screenMap");
            return null;
        }
        try {
            int i2 = -1;
            int i3 = -1;
            for (Map.Entry entry : b2.entrySet()) {
                i2 = Math.max(i2, ((Integer) entry.getKey()).intValue() + 1);
                i3 = Math.max(i3, ((Integer) entry.getValue()).intValue() + 1);
            }
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "addScreen newScreenId: %d, newScreenRank: %d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)});
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", Integer.valueOf(i2));
            contentValues.put("launcherType", Integer.valueOf(d2.a));
            contentValues.put("screenRank", Integer.valueOf(i3));
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "addScreen record id: %d, tableName: %s", new Object[]{Long.valueOf(sQLiteDatabase.insert("workspace_screens", "", contentValues)), "workspace_screens"});
            return Integer.valueOf(i2);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addScreen failed: workspace_screens", e2);
            h.a("addScreen", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(boolean z) {
        try {
            String a2 = a();
            Intent intent = new Intent();
            intent.setPackage(a2);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            intent.addFlags(8392704);
            if (!z) {
                StrategyFramework.getFrameworkContext().startActivity(intent);
            } else if (RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_bg_start_by_view_61400", false)) {
                Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "start bg activity by float view");
                FloatViewHelper.startBgActivityWithFloatView(intent);
            } else {
                com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.startBackgroundActivity(intent);
            }
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "restart launcher finish, pkg: %s, bg start: %s", new Object[]{a2, Boolean.valueOf(z)});
            return true;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "fail to restartLauncher", e2);
            h.a("restartLauncher", e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Integer c() {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return null;
        }
        Integer a3 = a(a2.getDatabase());
        if (a3 != null) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2) ? a3 : null;
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "addScreen success, newScreenId: %d", new Object[]{a3});
        return a3;
    }

    private static Map b(SQLiteDatabase sQLiteDatabase, int i2) {
        HashMap hashMap = new HashMap();
        try {
            Cursor query = sQLiteDatabase.query("workspace_screens", i.e, "launcherType=?", new String[]{String.valueOf(i2)}, null, null, null);
            while (query.moveToNext()) {
                hashMap.put(Integer.valueOf(query.getInt(query.getColumnIndex("_id"))), Integer.valueOf(query.getInt(query.getColumnIndex("screenRank"))));
            }
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "queryScreenMap result: %s, launcherType: %d", new Object[]{JSONFormatUtils.instance().toJson(hashMap), Integer.valueOf(i2)});
            query.close();
            return hashMap;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "queryScreenMap failed: " + i2, e2);
            h.a("queryScreenMap", e2);
            return null;
        }
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        String e2 = e.e();
        if (!TextUtils.isEmpty(e2) && c(sQLiteDatabase, i3)) {
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("container", Integer.valueOf(i3));
                int update = sQLiteDatabase.update(e2, contentValues, "_id=?", new String[]{String.valueOf(i2)});
                Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "moveToFolder updatedRows: %d, tableName: %s", new Object[]{Integer.valueOf(update), e2});
                return update == 1;
            } catch (Exception e3) {
                Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "moveToFolder failed: " + e2, e3);
                h.a("moveToFolder", e3);
                return false;
            }
        }
        return false;
    }

    static boolean b(String str) {
        String c2 = g.c();
        if (TextUtils.isEmpty(c2)) {
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "invalid launcher version, isBeyondTargetVersion=false");
            return false;
        }
        return g.a(c2, str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a() {
        String str = "com.huawei.android.launcher";
        if (RomOsUtil.instance().isHonerManufacture()) {
            str = "com.hihonor.android.launcher";
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List b() {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return null;
        }
        List a3 = a(a2.getDatabase(), (String) null, (String[]) null);
        a2.close();
        return a3;
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, IconInfo iconInfo) {
        e.a d2 = e.d();
        if (d2 != null) {
            String str = d2.e;
            if (!TextUtils.isEmpty(str)) {
                if (!a(sQLiteDatabase, iconInfo, d2, true)) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addIcon invalid iconInfo");
                    return false;
                }
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title", iconInfo.getTitle());
                    contentValues.put("intent", iconInfo.getIntent());
                    contentValues.put("itemType", Integer.valueOf(iconInfo.getItemType()));
                    contentValues.put("screen", Integer.valueOf(iconInfo.getScreen()));
                    contentValues.put("cellX", Integer.valueOf(iconInfo.getCellX()));
                    contentValues.put("cellY", Integer.valueOf(iconInfo.getCellY()));
                    contentValues.put("spanX", Integer.valueOf(iconInfo.getSpanX()));
                    contentValues.put("spanY", Integer.valueOf(iconInfo.getSpanY()));
                    contentValues.put("container", Integer.valueOf(iconInfo.getContainerId()));
                    contentValues.put("profileId", Long.valueOf(g()));
                    Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "addIcon newIconId: %d, tableName: %s", new Object[]{Long.valueOf(sQLiteDatabase.insert(str, "", contentValues)), str});
                    return true;
                } catch (Exception e2) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addIcon failed: " + str, e2);
                    h.a("addIcon", e2);
                    return false;
                }
            }
        }
        Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "addIcon invalid layoutBaseInfo");
        return false;
    }

    private static IconInfo a(SQLiteDatabase sQLiteDatabase, int i2) {
        List a2 = a(sQLiteDatabase, "_id=?", new String[]{String.valueOf(i2)});
        if (a2 == null || a2.size() != 1) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "icon not found: id: %d, allIcons: %s, selection: %s", new Object[]{Integer.valueOf(i2), JSONFormatUtils.instance().toJson(a2), "_id=?"});
            return null;
        }
        return (IconInfo) a2.get(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()), a());
    }

    private static boolean b(SQLiteDatabase sQLiteDatabase, IconInfo iconInfo) {
        String e2 = e.e();
        if (TextUtils.isEmpty(e2)) {
            return false;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("screen", Integer.valueOf(iconInfo.getScreen()));
            contentValues.put("cellX", Integer.valueOf(iconInfo.getCellX()));
            contentValues.put("cellY", Integer.valueOf(iconInfo.getCellY()));
            contentValues.put("container", (Integer) (-100));
            int update = sQLiteDatabase.update(e2, contentValues, "_id=?", new String[]{String.valueOf(iconInfo.getId())});
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "moveOutFolder updatedRows: %d, tableName: %s", new Object[]{Integer.valueOf(update), e2});
            return update == 1;
        } catch (Exception e3) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "moveOutFolder failed: " + e2, e3);
            h.a("moveOutFolder", e3);
            return false;
        }
    }

    private static boolean c(SQLiteDatabase sQLiteDatabase, IconInfo iconInfo) {
        e.a d2 = e.d();
        if (d2 != null) {
            String str = d2.e;
            if (!TextUtils.isEmpty(str)) {
                IconInfo a2 = a(sQLiteDatabase, iconInfo.getId());
                if (a2 == null) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon icon: %d not exist", new Object[]{Integer.valueOf(iconInfo.getId())});
                    return false;
                }
                boolean z = (iconInfo.getScreen() == a2.getScreen() && iconInfo.getCellX() == a2.getCellX() && iconInfo.getCellY() == a2.getCellY()) ? false : true;
                Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon: positionChanged: %s, originIcon: %s", new Object[]{Boolean.valueOf(z), a2.toString()});
                if (!a(sQLiteDatabase, iconInfo, d2, z)) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon invalid iconInfo");
                    return false;
                }
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("title", iconInfo.getTitle());
                    contentValues.put("intent", iconInfo.getIntent());
                    contentValues.put("itemType", Integer.valueOf(iconInfo.getItemType()));
                    contentValues.put("screen", Integer.valueOf(iconInfo.getScreen()));
                    contentValues.put("cellX", Integer.valueOf(iconInfo.getCellX()));
                    contentValues.put("cellY", Integer.valueOf(iconInfo.getCellY()));
                    contentValues.put("spanX", Integer.valueOf(iconInfo.getSpanX()));
                    contentValues.put("spanY", Integer.valueOf(iconInfo.getSpanY()));
                    contentValues.put("container", Integer.valueOf(iconInfo.getContainerId()));
                    int update = sQLiteDatabase.update(str, contentValues, "_id=?", new String[]{String.valueOf(iconInfo.getId())});
                    Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon updatedRows: %d, tableName: %s", new Object[]{Integer.valueOf(update), str});
                    return update == 1;
                } catch (Exception e2) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon failed: " + str, e2);
                    h.a("updateIcon", e2);
                    return false;
                }
            }
        }
        Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "updateIcon invalid layoutBaseInfo");
        return false;
    }

    private static List a(SQLiteDatabase sQLiteDatabase, String str, String[] strArr) {
        e.a d2 = e.d();
        if (d2 == null || TextUtils.isEmpty(d2.e)) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "queryIcons invalid layoutBaseInfo");
            return null;
        }
        Map b2 = b(sQLiteDatabase, d2.a);
        if (b2 != null) {
            return a(sQLiteDatabase, str, strArr, d2, b2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(int i2) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return false;
        }
        boolean d2 = d(a2.getDatabase(), i2);
        if (d2) {
            d2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "removeIcon success: %s", new Object[]{Boolean.valueOf(d2)});
        return d2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(IconInfo iconInfo) {
        b.a a2 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a(Uri.parse(a(a.e)));
        if (a2 == null) {
            return false;
        }
        boolean a3 = a(a2.getDatabase(), iconInfo);
        if (a3) {
            a3 = com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b.a((IDBHandle) a2);
        }
        a2.close();
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "addIcon success: %s", new Object[]{Boolean.valueOf(a3)});
        return a3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean d() {
        if (i()) {
            if (AliveAbility.instance().isAbilityDisabled()) {
                Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "can't bg restart launcher since alive ability disabled");
                return false;
            }
            boolean canStartBackgroundActivity = com.xunmeng.pinduoduo.alive.base.ability.impl.startup.b.canStartBackgroundActivity();
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "can restart launcher by bg start: %s", new Object[]{Boolean.valueOf(canStartBackgroundActivity)});
            return canStartBackgroundActivity;
        }
        return true;
    }

    f() {
    }

    private static boolean d(SQLiteDatabase sQLiteDatabase, int i2) {
        String e2 = e.e();
        if (TextUtils.isEmpty(e2)) {
            return false;
        }
        try {
            int delete = sQLiteDatabase.delete(e2, "_id=?", new String[]{String.valueOf(i2)});
            Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "removeIcon updatedRows: %d, tableName: %s", new Object[]{Integer.valueOf(delete), e2});
            return delete == 1;
        } catch (Exception e3) {
            Logger.e("LVBA.AliveModule.Provider.LauncherUtils", "removeIcon failed: " + e2, e3);
            h.a("removeIcon", e3);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.f$1, java.lang.Runnable] */
    public static boolean f() {
        boolean z;
        boolean h2 = h();
        final boolean i2 = i();
        boolean z2 = RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_monitor_launcher_death_61500", false) && h2;
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "restartLauncher, parallel: %s, bg: %s, monitorLauncherDeath: %s", new Object[]{Boolean.valueOf(h2), Boolean.valueOf(i2), Boolean.valueOf(z2)});
        boolean z3 = false;
        if (z2) {
            z3 = d.a(a());
        }
        if (!h2) {
            z = b(i2);
        } else {
            int i3 = RemoteConfig.instance().getInt("pinduoduo_Android.hw_launcher_restart_parallel_cnt_61300", 3);
            for (int i4 = 0; i4 < i3; i4++) {
                final int i5 = i4 + 1;
                ThreadPool.instance().computeTask(ThreadBiz.CS, "LauncherUtils#restartLauncher" + i5, (Runnable) new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.f.1
                    @Override // com.xunmeng.pinduoduo.alive.strategy.comp.janus.a.AnonymousClass1
                    public void run() {
                        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "parallel restart launcher %d", new Object[]{Integer.valueOf(i5)});
                        f.b(i2);
                    }
                });
            }
            z = true;
        }
        if (z2 && z3) {
            if (z) {
                z = d.b();
                Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "launcher death monitor result: %s", new Object[]{Boolean.valueOf(z)});
            } else {
                d.a();
            }
        }
        Logger.i("LVBA.AliveModule.Provider.LauncherUtils", "final launcher restart result: %s", new Object[]{Boolean.valueOf(z)});
        return z;
    }

    private static boolean h() {
        return RemoteConfig.instance().getBoolean("pinduoduo_Android.hw_fp_parallel_restart_61500", false);
    }
}
