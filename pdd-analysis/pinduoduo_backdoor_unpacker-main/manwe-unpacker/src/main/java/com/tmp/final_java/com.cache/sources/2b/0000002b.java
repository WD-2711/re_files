package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hss;

import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Xml;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.hss.PersistentFileParameter;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;
    private File c;
    private k.setValue d;
    private boolean e;
    private String f = null;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private Map b = new HashMap(20);

    private void a(String str) {
        Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----clearPackageServicesInLocalData-----");
        if (TextUtils.isEmpty(str)) {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "package is null, return");
            return;
        }
        Iterator it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            PersistentFileParameter persistentFileParameter = (PersistentFileParameter) ((Map.Entry) it.next()).getKey();
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "PackageName = " + persistentFileParameter.getPackageName());
            if (TextUtils.equals(persistentFileParameter.getPackageName(), str)) {
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "find and remove service");
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "per.getServiceName() = " + persistentFileParameter.getServiceName());
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "per.getPackageName() = " + persistentFileParameter.getPackageName());
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "per.getActionName() = " + persistentFileParameter.getActionName());
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "per.getDataSchemeName() = " + (persistentFileParameter.getDataSchemeName() == null ? "null" : persistentFileParameter.getDataSchemeName()));
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "mNonPresetNonResidentData = " + this.b.size());
                it.remove();
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "mNonResidentServiceData = " + this.b.size());
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "mNonResidentServiceFile = " + (this.d != null ? this.d.getTempStreamFilePath() : "null"));
            }
        }
    }

    /* renamed from: c */
    private boolean saveData(Map map) {
        Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----saveData-----");
        boolean z = true;
        OutputStream outputStream = null;
        try {
            try {
                if (this.d == null || this.d.getTempStreamFilePath() == null || map == null) {
                    Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid parameter when writeData");
                    if (0 != 0) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when close outputStream", e);
                            z = false;
                        }
                    }
                    return z;
                }
                File file = new File(this.d.getTempStreamFilePath());
                if (!file.exists()) {
                    Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "file not exits");
                    if (0 != 0) {
                        try {
                            outputStream.close();
                        } catch (IOException e2) {
                            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when close outputStream", e2);
                            z = false;
                        }
                    }
                    return z;
                }
                XmlSerializer newSerializer = Xml.newSerializer();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                newSerializer.setOutput(fileOutputStream, "UTF-8");
                newSerializer.startDocument("UTF-8", true);
                a(newSerializer, newSerializer.getDepth());
                newSerializer.startTag(null, "services");
                writeMapToXml(newSerializer, map);
                a(newSerializer, newSerializer.getDepth() - 1);
                newSerializer.endTag(null, "services");
                newSerializer.endDocument();
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when close outputStream", e3);
                        z = false;
                    }
                }
                return z;
            } catch (IOException e4) {
                Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur FileNotFoundException when saveData", e4);
                boolean z2 = false;
                if (0 != 0) {
                    try {
                        outputStream.close();
                    } catch (IOException e5) {
                        Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when close outputStream", e5);
                        z2 = false;
                    }
                }
                return z2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    outputStream.close();
                } catch (IOException e6) {
                    Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when close outputStream", e6);
                    z = false;
                }
            }
            return z;
        }
    }

    private boolean b(String str, String str2, String str3, String str4) {
        boolean z = false;
        Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "---into isPersistentFileParameterExist----");
        if (str == null || str2 == null || str3 == null) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "parameter is null when isContinuousFileParameterExist");
            return false;
        } else if (str.isEmpty() || str2.isEmpty() || str3.isEmpty()) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "parameter value is empty when isContinuousFileParameterExist");
            return false;
        } else {
            for (Map.Entry entry : this.b.entrySet()) {
                if (str4 == null || str4.equals(((PersistentFileParameter) entry.getKey()).getDataSchemeName())) {
                    if (str.equals(((PersistentFileParameter) entry.getKey()).getServiceName()) && str2.equals(((PersistentFileParameter) entry.getKey()).getPackageName()) && str3.equals(((PersistentFileParameter) entry.getKey()).getActionName())) {
                        z = true;
                    }
                }
            }
            return z;
        }
    }

    public int a(String str, String str2, String str3, String str4) {
        Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----addNonResidentServiceData---");
        if (str == null || str2 == null || str3 == null || !str2.equals(StrategyFramework.getFrameworkContext().getPackageName())) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid params");
            return -1;
        } else if (str.isEmpty() || str2.isEmpty() || str3.isEmpty()) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "parameter value is empty");
            return -1;
        } else if (b(str, str2, str3, str4)) {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "----PersistentFileParameter exists-------");
            return 0;
        } else {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "----PersistentFileParameter not exists-------");
            PersistentFileParameter persistentFileParameter = new PersistentFileParameter();
            persistentFileParameter.setServiceName(str);
            persistentFileParameter.setPackageName(str2);
            persistentFileParameter.setActionName(str3);
            persistentFileParameter.setDataSchemeName(str4);
            this.b.put(persistentFileParameter, this.h);
            return 1;
        }
    }

    /* renamed from: a */
    private void writeMapToXml(XmlSerializer xmlSerializer, Map map) {
        if (xmlSerializer == null || map == null) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid parameter when writeMapToXml");
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            try {
                a(xmlSerializer, xmlSerializer.getDepth());
                xmlSerializer.startTag(null, "service");
                a(xmlSerializer, xmlSerializer.getDepth());
                xmlSerializer.startTag(null, "package_name");
                xmlSerializer.text(((PersistentFileParameter) entry.getKey()).getPackageName());
                xmlSerializer.endTag(null, "package_name");
                a(xmlSerializer, xmlSerializer.getDepth());
                xmlSerializer.startTag(null, "service_name");
                xmlSerializer.text(((PersistentFileParameter) entry.getKey()).getServiceName());
                xmlSerializer.endTag(null, "service_name");
                a(xmlSerializer, xmlSerializer.getDepth());
                if (((PersistentFileParameter) entry.getKey()).getActionName() == null) {
                    xmlSerializer.startTag(null, "flag");
                    xmlSerializer.text((String) entry.getValue());
                    xmlSerializer.endTag(null, "flag");
                } else {
                    xmlSerializer.startTag(null, "action_name");
                    xmlSerializer.text(((PersistentFileParameter) entry.getKey()).getActionName());
                    xmlSerializer.endTag(null, "action_name");
                }
                if (((PersistentFileParameter) entry.getKey()).getDataSchemeName() != null) {
                    xmlSerializer.startTag(null, "data_scheme_name");
                    xmlSerializer.text(((PersistentFileParameter) entry.getKey()).getDataSchemeName());
                    xmlSerializer.endTag(null, "data_scheme_name");
                }
                a(xmlSerializer, xmlSerializer.getDepth() - 1);
                xmlSerializer.endTag(null, "service");
            } catch (IOException e) {
                Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when writeMapToXml", e);
            }
        }
    }

    /* renamed from: a */
    private void getStartTagData(XmlPullParser xmlPullParser) {
        if (xmlPullParser == null) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid parameter when getStartTagData");
            return;
        }
        try {
            if ("package_name".equals(xmlPullParser.getName())) {
                this.i = xmlPullParser.nextText();
            } else if ("service_name".equals(xmlPullParser.getName())) {
                this.j = xmlPullParser.nextText();
            } else if ("flag".equals(xmlPullParser.getName())) {
                this.h = xmlPullParser.nextText();
            } else if ("action_name".equals(xmlPullParser.getName())) {
                this.f = xmlPullParser.nextText();
            } else if ("data_scheme_name".equals(xmlPullParser.getName())) {
                this.g = xmlPullParser.nextText();
            } else {
                Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "other condition");
            }
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur XmlPullParserException when getStartTagData", e);
        }
    }

    /*  JADX ERROR: Failed to set jump: 0x00b2 -> 0x0101
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.InsnNode.addAttr(jadx.api.plugins.input.data.attributes.IJadxAttrType, Object)" because "insnByOffset[target]" is null
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.addJump(ProcessInstructionsVisitor.java:144)
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.initJumps(ProcessInstructionsVisitor.java:57)
        	at jadx.core.dex.visitors.ProcessInstructionsVisitor.visit(ProcessInstructionsVisitor.java:40)
        */
    private void a(java.util.Map r5) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xunmeng.pinduoduo.alive.base.ability.impl.provider.hss.a.a(java.util.Map):void");
    }

    /* renamed from: a */
    private void getEndTagData(XmlPullParser xmlPullParser, Map map) {
        if (xmlPullParser == null || map == null) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid parameter when getEndTagData");
        } else if (!"service".equals(xmlPullParser.getName())) {
        } else {
            PersistentFileParameter persistentFileParameter = new PersistentFileParameter();
            persistentFileParameter.setPackageName(this.i);
            persistentFileParameter.setServiceName(this.j);
            persistentFileParameter.setActionName(this.f);
            persistentFileParameter.setDataSchemeName(this.g);
            map.put(persistentFileParameter, this.h);
            this.i = null;
            this.j = null;
            this.f = null;
            this.g = null;
            this.h = null;
        }
    }

    public boolean clearPackageServices() {
        try {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----clearPackageServices-----");
            if (!b()) {
                return false;
            }
            boolean clearPackageServicesInner = clearPackageServicesInner();
            if (this.d != null) {
                this.d.close();
            }
            return clearPackageServicesInner;
        } finally {
            if (this.d != null) {
                this.d.close();
            }
        }
    }

    private void a(XmlSerializer xmlSerializer, int i) {
        if (xmlSerializer == null || i < 0) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "invalid parameter when formatXml");
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                xmlSerializer.text(System.lineSeparator());
            }
            for (int i2 = 0; i2 < i; i2++) {
                xmlSerializer.text("    ");
            }
        } catch (IOException e) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "occur IOException when FormateXml", e);
        }
    }

    /* renamed from: a */
    private boolean clearPackageServicesInner() {
        Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----clearPackageServicesInner-----");
        a(StrategyFramework.getFrameworkContext().getPackageName());
        return b(this.b);
    }

    public Map getNonResidentServices() {
        try {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----getNonResidentServices-----");
            if (!b()) {
                return null;
            }
            Map map = this.b;
            if (this.d != null) {
                this.d.close();
            }
            return map;
        } finally {
            if (this.d != null) {
                this.d.close();
            }
        }
    }

    private boolean b() {
        if (!this.e) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "HssLocalManager init local data fail");
            return false;
        }
        return true;
    }

    public a() {
        if (Build.VERSION.SDK_INT >= 17) {
            this.c = new File(String.format("/data/user_de/%d/com.huawei.securityserver/files/", Integer.valueOf(Process.myUserHandle().hashCode())), "BroadcastServiceConfig.xml");
        }
        this.d = k.a(Uri.parse("content://com.android.settings.files/my_root").buildUpon().appendPath(this.c.getAbsolutePath()).build(), true, true);
        this.e = false;
        a(this.b);
    }

    public boolean addNonResidentServiceData(Map map) {
        try {
            Logger.i("LVBA.AliveModule.HssLocalDataManagerImpl", "-----addNonResidentServiceData-----");
            if (!b()) {
                return false;
            }
            a(StrategyFramework.getFrameworkContext().getPackageName());
            for (Map.Entry entry : map.entrySet()) {
                a(((PersistentFileParameter) entry.getKey()).getServiceName(), ((PersistentFileParameter) entry.getKey()).getPackageName(), ((PersistentFileParameter) entry.getKey()).getActionName(), ((PersistentFileParameter) entry.getKey()).getDataSchemeName());
            }
            boolean b = b(this.b);
            if (this.d != null) {
                this.d.close();
            }
            return b;
        } finally {
            if (this.d != null) {
                this.d.close();
            }
        }
    }

    private boolean b(Map map) {
        if (!saveData(map)) {
            Logger.e("LVBA.AliveModule.HssLocalDataManagerImpl", "write saveFile data fail");
            return false;
        }
        return k.b((IStreamHandle) this.d);
    }
}