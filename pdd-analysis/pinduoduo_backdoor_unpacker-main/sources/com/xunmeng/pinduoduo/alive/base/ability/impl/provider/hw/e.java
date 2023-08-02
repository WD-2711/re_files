package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Xml;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.LayoutProps;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.JSONFormatUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: e.class */
class e {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final String g = null;
    private static final String h = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: e$a.class */
    public class a {
        int a;
        String b;
        int c;
        int d;
        String e;

        public a(int i, String str, int i2, int i3, String str2) {
            this.a = i;
            this.b = str;
            this.c = i2;
            this.d = i3;
            this.e = str2;
        }

        public String toString() {
            return "LayoutBaseInfo{launcherType=" + this.a + ", layoutString='" + this.b + "', rows=" + this.c + ", columns=" + this.d + ", tableName='" + this.e + "'}";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static LayoutProps a() {
        Map b2 = b();
        try {
            a a2 = a(b2);
            if (a2 == null) {
                Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "invalid layoutBaseInfo");
                return null;
            }
            String str = (String) b2.get("WIDGET_CELL_WIDTH");
            String str2 = (String) b2.get("WIDGET_CELL_HEIGHT");
            if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "invalid layoutParamMap: %s", new Object[]{Arrays.toString(new String[]{str2, str})});
                return null;
            }
            if (RemoteConfig.instance().getBoolean("ab_hw_launcher_get_progress_value_61900", false)) {
                String str3 = (String) b2.get("progress_value");
                if (!TextUtils.isEmpty(str3)) {
                    LayoutProps layoutProps = new LayoutProps(a2.a, a2.c, a2.d, Float.parseFloat(str2), Float.parseFloat(str), Integer.parseInt(str3));
                    Logger.i("LVBA.AliveModule.Provider.LauncherLayoutUtils", "layoutProps result: %s", new Object[]{layoutProps.toString()});
                    return layoutProps;
                }
            }
            LayoutProps layoutProps2 = new LayoutProps(a2.a, a2.c, a2.d, Float.parseFloat(str2), Float.parseFloat(str));
            Logger.i("LVBA.AliveModule.Provider.LauncherLayoutUtils", "layoutProps result: %s", new Object[]{layoutProps2.toString()});
            return layoutProps2;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to build layoutProps: " + JSONFormatUtils.instance().toJson(b2), e2);
            h.a("getLayoutProps", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a d() {
        Map b2 = b();
        if (b2 == null) {
            return null;
        }
        return a(b2);
    }

    private static String b(int i) {
        return i == 4 ? "DEFAULT_DESKTOP_LAYOUT_DRAWER_INDEX" : i == 5 ? "DEFAULT_DESKTOP_LAYOUT_NEW_SIMPLE_INDEX" : "DEFAULT_DESKTOP_LAYOUT_INDEX";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String e() {
        a a2 = a(b());
        if (a2 == null) {
            return null;
        }
        return a2.e;
    }

    public static int c() {
        int i = 1;
        Configuration configuration = new Configuration();
        try {
            configuration.updateFrom((Configuration) Class.forName("com.huawei.android.app.ActivityManagerEx").getDeclaredMethod("getConfiguration", new Class[0]).invoke(null, new Object[0]));
            Class<?> cls = Class.forName("com.huawei.android.content.res.ConfigurationEx");
            i = ((Integer) cls.getDeclaredMethod("getSimpleuiMode", new Class[0]).invoke(cls.getConstructor(Configuration.class).newInstance(configuration), new Object[0])).intValue();
            if (i == 0) {
                i = f();
                Logger.i("LVBA.AliveModule.Provider.LauncherLayoutUtils", "getLauncherType: invalid launcher type 0, reset it as " + i);
            }
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "getLauncherType exception", e2);
            h.a("getLauncherType", e2);
        } catch (NoClassDefFoundError e3) {
            Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "getLauncherType NoClassDefFoundError:", e3);
            h.a("getLauncherType", e3);
        } catch (NoSuchFieldError e4) {
            Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "getLauncherType NoSuchFieldError:", e4);
            h.a("getLauncherType", e4);
        }
        return i;
    }

    static a a(Map map) {
        try {
            int c2 = c();
            String str = (String) map.get(b(c2));
            String str2 = ((String) map.get("DESKTOP_LAYOUT_CELLS")) + ",3x3,3x4";
            if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "invalid layoutParamMap: %s", new Object[]{Arrays.toString(new String[]{str2, str})});
                return null;
            }
            String str3 = str2.split(",")[Integer.parseInt(str)];
            if (TextUtils.isEmpty(str3)) {
                Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "empty layoutString: " + JSONFormatUtils.instance().toJson(map));
            }
            String[] split = str3.split("x");
            return new a(c2, str3, Integer.parseInt(split[1]), Integer.parseInt(split[0]), a(c2) + str3);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to build layoutString: " + JSONFormatUtils.instance().toJson(map), e2);
            h.a("getLayoutBaseInfo", e2);
            return null;
        }
    }

    e() {
    }

    private static int f() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.setPackage("com.huawei.android.launcher");
        List<ResolveInfo> a2 = com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a(intent, 128);
        if (a2 == null || a2.isEmpty()) {
            Logger.i("LVBA.AliveModule.Provider.LauncherLayoutUtils", "getLauncherTypeByComponentState: no launcher home activity enabled, default launcher is not huawei launcher.");
            return 1;
        }
        int i = 1;
        for (ResolveInfo resolveInfo : a2) {
            if (resolveInfo != null && resolveInfo.activityInfo != null && resolveInfo.activityInfo.name != null) {
                String str = resolveInfo.activityInfo.name;
                Logger.i("LVBA.AliveModule.Provider.LauncherLayoutUtils", String.format(Locale.ENGLISH, "getLauncherTypeByComponentState: current enable launcher component:%s", str));
                if (!"com.huawei.android.launcher.unihome.UniHomeLauncher".equals(str)) {
                    if ("com.huawei.android.launcher.drawer.DrawerLauncher".equals(str)) {
                        i = 4;
                    } else if ("com.huawei.android.launcher.newsimpleui.NewSimpleLauncher".equals(str)) {
                        i = 5;
                    } else if ("com.huawei.android.launcher.simpleui.SimpleUILauncher".equals(str)) {
                        i = 2;
                    }
                }
            }
        }
        return i;
    }

    private static String a(int i) {
        String str = "favorites";
        if (i == 4) {
            str = "drawer_" + str;
        } else if (i == 5) {
            str = "simple_" + str;
        }
        return str;
    }

    static Map b() {
        HashMap hashMap = new HashMap();
        InputStream inputStream = null;
        try {
            try {
                inputStream = StorageApi.instance().openInputStream(StrategyFramework.getFrameworkContext().getContentResolver(), Uri.parse(f.a(com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hw.a.d)));
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(inputStream, "UTF-8");
                for (int i = 0; i != 1 && i != 2; i = newPullParser.next()) {
                }
                while (true) {
                    int next = newPullParser.next();
                    if (next == 1) {
                        break;
                    } else if (next != 3 && next != 4) {
                        String attributeValue = newPullParser.getAttributeValue(null, "name");
                        String attributeValue2 = newPullParser.getAttributeValue(null, "value");
                        if (TextUtils.equals("DESKTOP_LAYOUT_CELLS", attributeValue)) {
                            attributeValue2 = newPullParser.nextText();
                        }
                        hashMap.put(attributeValue, attributeValue2);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to close getLayoutParam stream", e2);
                    }
                }
                return hashMap;
            } catch (Exception e3) {
                Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to getLayoutParam", e3);
                h.a("getLayoutParam", e3);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to close getLayoutParam stream", e4);
                    }
                }
                return hashMap;
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e5) {
                    Logger.e("LVBA.AliveModule.Provider.LauncherLayoutUtils", "fail to close getLayoutParam stream", e5);
                }
            }
            throw th;
        }
    }
}
