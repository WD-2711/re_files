package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.oppoLockPull;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.Xml;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.j;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: b.class */
class b {
    private static final String a = null;
    private static int b;
    private static boolean c = false;
    private static int d = 1;
    private static boolean e = false;
    private static boolean f = true;
    private static final String g = null;
    private static final String h = null;
    private static final String i = null;
    private static final String j = null;

    private static Map a(XmlPullParser xmlPullParser) {
        int next;
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        HashSet hashSet4 = new HashSet();
        HashMap hashMap = new HashMap();
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                if ("base".equals(name)) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "version");
                    String attributeValue2 = xmlPullParser.getAttributeValue(null, "support");
                    String attributeValue3 = xmlPullParser.getAttributeValue(null, "supportBlack");
                    String attributeValue4 = xmlPullParser.getAttributeValue(null, "policy");
                    String attributeValue5 = xmlPullParser.getAttributeValue(null, "skipaudio");
                    if (attributeValue != null) {
                        try {
                            b = Integer.parseInt(attributeValue);
                        } catch (NumberFormatException e2) {
                            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "setVersion exception version = %s", new Object[]{attributeValue});
                        }
                    }
                    if (attributeValue2 != null) {
                        try {
                            c = Boolean.parseBoolean(attributeValue2);
                        } catch (Exception e3) {
                            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "setSupport exception support = %s", new Object[]{attributeValue2});
                        }
                    }
                    if (attributeValue3 != null) {
                        try {
                            e = Boolean.parseBoolean(attributeValue3);
                        } catch (Exception e4) {
                            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "setSupportBlack exception supportBlack = %s", new Object[]{attributeValue3});
                        }
                    }
                    if (attributeValue4 != null) {
                        try {
                            d = Integer.parseInt(attributeValue4);
                        } catch (NumberFormatException e5) {
                            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "setPolicy exception policy = %s", new Object[]{attributeValue4});
                        }
                    }
                    if (attributeValue5 != null) {
                        try {
                            f = Boolean.parseBoolean(attributeValue5);
                        } catch (Exception e6) {
                            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "setSkipAudio exception skipAudio = %s", new Object[]{attributeValue5});
                        }
                    }
                    Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "version: %s, support: %s, supportBlack: %s, policy: %s, skipaudio: %s", new Object[]{Integer.valueOf(b), Boolean.valueOf(c), Boolean.valueOf(e), Integer.valueOf(d), Boolean.valueOf(f)});
                } else if ("skip-list".equals(name)) {
                    int depth = xmlPullParser.getDepth();
                    while (true) {
                        int next2 = xmlPullParser.next();
                        if (next2 == 1 || (next2 == 3 && xmlPullParser.getDepth() <= depth)) {
                            break;
                        } else if (next2 != 3 && next2 != 4) {
                            String name2 = xmlPullParser.getName();
                            if ("pkg".equals(name2) || "cpn".equals(name2)) {
                                String attributeValue6 = xmlPullParser.getAttributeValue(null, "name");
                                if ("pkg".equals(name2)) {
                                    hashSet.add(attributeValue6);
                                } else {
                                    hashSet2.add(attributeValue6);
                                }
                            } else {
                                Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "Unknown tag parsing ColorRuleInfo : %s", new Object[]{name2});
                                b(xmlPullParser);
                            }
                        }
                    }
                } else if ("black-list".equals(name)) {
                    int depth2 = xmlPullParser.getDepth();
                    while (true) {
                        int next3 = xmlPullParser.next();
                        if (next3 == 1 || (next3 == 3 && xmlPullParser.getDepth() <= depth2)) {
                            break;
                        } else if (next3 != 3 && next3 != 4) {
                            String name3 = xmlPullParser.getName();
                            if ("pkg".equals(name3) || "cpn".equals(name3)) {
                                String attributeValue7 = xmlPullParser.getAttributeValue(null, "name");
                                if ("pkg".equals(name3)) {
                                    hashSet3.add(attributeValue7);
                                } else {
                                    hashSet4.add(attributeValue7);
                                }
                            } else {
                                Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "Unknown tag parsing ColorRuleInfo : %s", new Object[]{name3});
                                b(xmlPullParser);
                            }
                        }
                    }
                } else if (!"crackdownconfig".equals(name) && !"overlay-policy-list".equals(name)) {
                    Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "Unknown tag parsing ColorRuleInfo : %s", new Object[]{name});
                    a("unknown_tag", name);
                    b(xmlPullParser);
                }
            }
        } while (next != 1);
        Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "size: %s; blackPkg: %s", new Object[]{Integer.valueOf(hashSet3.size()), Arrays.toString(hashSet3.toArray())});
        Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "size: %s; blackCpn: %s", new Object[]{Integer.valueOf(hashSet4.size()), Arrays.toString(hashSet4.toArray())});
        Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "size: %s; whitePkg: %s", new Object[]{Integer.valueOf(hashSet.size()), Arrays.toString(hashSet.toArray())});
        Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "size: %s; whiteCpn: %s", new Object[]{Integer.valueOf(hashSet2.size()), Arrays.toString(hashSet2.toArray())});
        hashMap.put("blackPkg", hashSet3);
        hashMap.put("blackCpn", hashSet4);
        hashMap.put("whitePkg", hashSet);
        hashMap.put("whiteCpn", hashSet2);
        return hashMap;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Boolean a(ComponentName componentName) {
        k.a b2 = k.b(Uri.parse(a("content://com.coloros.phonemanager.files/clear_share/data/user/%s/com.daemon.shelper/shared_prefs/lockscreen_restrict_config.xml")));
        if (b2 == null) {
            b("get_file_failed");
            return null;
        }
        Boolean a2 = a(b2, componentName);
        b2.close();
        return a2;
    }

    static void a(String str, String str2) {
        if (Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.track_oppo_lock_pull_59200", "true"))) {
            HashMap hashMap = new HashMap();
            hashMap.put("action", str);
            hashMap.put("versionName", a());
            if (!TextUtils.isEmpty(str2)) {
                hashMap.put("tag", str2);
            }
            j.a("oppo_lock_pull", "OppoLockPull", (Map) hashMap, true, false);
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [java.nio.charset.Charset, java.lang.String] */
    static Map a(File file) {
        if (file == null) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "file is null");
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                Map a2 = a(newPullParser);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                        Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "close fileInputStream failed", e2);
                    }
                }
                return a2;
            } catch (Exception e3) {
                Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "init list failed", e3);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e4) {
                        Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "close fileInputStream failed", e4);
                    }
                }
                return null;
            }
        } catch (Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e5) {
                    Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "close fileInputStream failed", e5);
                }
            }
            throw th;
        }
    }

    static void b(String str) {
        a(str, "");
    }

    private static String a() {
        String str = "";
        try {
            str = StrategyFramework.getFrameworkContext().getPackageManager().getPackageInfo("com.daemon.shelper", 16384).versionName;
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "version name : %s", new Object[]{str});
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "get version name error", e2);
        }
        return str;
    }

    private static Boolean a(k.a aVar, ComponentName componentName) {
        Map a2;
        try {
            if (aVar.getTempStreamFilePath() != null && (a2 = a(new File(aVar.getTempStreamFilePath()))) != null) {
                return a(componentName, a2);
            }
            return null;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "canLockPullFromXml failed", e2);
            j.a("oppo_lock_pull", "canLockPullFromXml", e2);
            return null;
        }
    }

    private static Boolean a(ComponentName componentName, Map map) {
        String packageName = componentName.getPackageName();
        if (!e) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "skip black list");
        } else if (((Set) map.get("blackPkg")).contains(packageName)) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "black-list contains : %s", new Object[]{packageName});
            b("in_black_list");
            return false;
        } else if (((Set) map.get("blackCpn")).contains(componentName.flattenToString()) || ((Set) map.get("blackCpn")).contains(componentName.flattenToShortString()) || ((Set) map.get("blackCpn")).contains(componentName.getClassName())) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "black-list contains : %s", new Object[]{componentName});
            b("in_black_list");
            return false;
        }
        if (!c) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "skip white list, can start when lock");
            b("unsupport_skip_list");
            return true;
        } else if (((Set) map.get("whitePkg")).contains(packageName)) {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "skip-list contains : %s", new Object[]{packageName});
            b("in_skip_list");
            return true;
        } else if (!((Set) map.get("whiteCpn")).contains(componentName.flattenToString()) && !((Set) map.get("whiteCpn")).contains(componentName.flattenToShortString()) && !((Set) map.get("whiteCpn")).contains(componentName.getClassName())) {
            return false;
        } else {
            Logger.i("LVBA.AliveModule.Provider.oppoLockPull.LockPullUtils", "skip-list contains : %s", new Object[]{componentName});
            b("in_skip_list");
            return true;
        }
    }

    b() {
    }

    private static void b(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
        }
    }

    static String a(String str) {
        return String.format(str, Integer.valueOf(Process.myUserHandle().hashCode()));
    }
}
