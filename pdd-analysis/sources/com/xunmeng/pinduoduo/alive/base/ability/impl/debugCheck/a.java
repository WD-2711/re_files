package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.debugCheck.IDebugDetectTransmission;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.threadpool.ThreadBiz;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.threadpool.ThreadPool;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PddSystemProperties;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMContentObserver;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.VMDynamicReceiver;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    private static final String a = null;
    private static final String b = null;
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;
    private static final String f = null;
    private static final Map g = new ConcurrentHashMap();
    private static volatile VMDynamicReceiver h;
    private static volatile VMContentObserver i;

    public boolean startDetectLogRecord() {
        String configValue = RemoteConfig.instance().getConfigValue("pinduoduo_Android.detect_debug_key_58900", "");
        String configValue2 = RemoteConfig.instance().getConfigValue("pinduoduo_Android.detect_debug_log_status_broadcast_key_60000", "");
        String expValue = RemoteConfig.instance().getExpValue("pinduoduo_Android.detect_debug_log_status_settings_key_60700", "");
        if (h != null || i != null) {
            Logger.d("LVBA.AliveModule", "already start detect !");
            return false;
        }
        a.AnonymousClass1 anonymousClass1 = new a.AnonymousClass1() { // from class: com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck.a.1
            public void send(String str, boolean z) {
                if (str != null) {
                    a.this.a(str, z);
                }
            }

            public boolean isDebugging() {
                Boolean isSysDebugging = a.this.isSysDebugging();
                return isSysDebugging != null && isSysDebugging.booleanValue();
            }
        };
        if (!TextUtils.isEmpty(expValue) && StrategyFramework.hasCapability("VMContentObserver")) {
            Logger.d("LVBA.AliveModule", "register log status from settings contentResolver ! ");
            return a(expValue, (IDebugDetectTransmission) anonymousClass1);
        } else if (!TextUtils.isEmpty(configValue2)) {
            Logger.d("LVBA.AliveModule", "register log status receiver ! ");
            return b(configValue2, anonymousClass1);
        } else if (!TextUtils.isEmpty(configValue)) {
            Logger.d("LVBA.AliveModule", "register secret code receiver !");
            return c(configValue, anonymousClass1);
        } else {
            Logger.d("LVBA.AliveModule", "no need to register debug receiver since empty config !");
            return false;
        }
    }

    private boolean i() {
        return TextUtils.equals(Build.MANUFACTURER, "OnePlus");
    }

    private Boolean a() {
        Boolean a2 = a(RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_key_property_58900", "persist.sys.vivolog.state"), "on");
        if (a2 != null && a2.booleanValue()) {
            f.b("APLog", "start");
        }
        return a2;
    }

    private Boolean a(String str, String str2) {
        String str3 = PddSystemProperties.instance().get(str, "");
        Logger.d("LVBA.AliveModule", "obtain debug state :%s !", new Object[]{str3});
        if (TextUtils.isEmpty(str3)) {
            return null;
        }
        return Boolean.valueOf(TextUtils.equals(str3, str2));
    }

    private boolean c(String str, IDebugDetectTransmission iDebugDetectTransmission) {
        Logger.d("LVBA.AliveModule", "obtain secret code config : %s ", new Object[]{str});
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SECRET_CODE");
        intentFilter.addDataScheme("android_secret_code");
        h = new VMDynamicReceiver(new d(str, iDebugDetectTransmission), "SecretCodeReceiver");
        boolean a2 = a((BroadcastReceiver) h, intentFilter);
        if (!a2) {
            h = null;
        }
        return a2;
    }

    private Boolean b() {
        Boolean a2 = a(RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_key_property_58900", "persist.sys.assert.panic"), "true");
        if (a2 != null && a2.booleanValue()) {
            f.b("APLog", "start");
        }
        return a2;
    }

    private Boolean c() {
        Boolean a2 = a(RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_key_property_58900", "persist.sys.huawei.debug.on"), "1");
        if (a2 != null && a2.booleanValue()) {
            f.b("APLog", "start");
        }
        return a2;
    }

    private boolean a(String str, IDebugDetectTransmission iDebugDetectTransmission) {
        Logger.d("LVBA.AliveModule", "obtain log status config : %s ", new Object[]{str});
        Map a2 = b.a(str);
        if (null == a2) {
            Logger.d("LVBA.AliveModule", "log status config invalid ! ");
            return false;
        }
        String str2 = (String) a2.get("settingsName");
        String str3 = (String) a2.get("openStatus");
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            Logger.d("LVBA.AliveModule", "log status config invalid for no settings name or openStatus!");
            return false;
        }
        i = new VMContentObserver(ThreadPool.instance().getWorkerHandler2(ThreadBiz.CS), new e(str2, str3, iDebugDetectTransmission, StrategyFramework.getFrameworkContext().getContentResolver()), "SettingsContentObserver");
        boolean a3 = a((ContentObserver) i, (String) a2.get("settingsName"));
        if (!a3) {
            i = null;
        }
        Logger.d("LVBA.AliveModule", "finish register content observer : %s", new Object[]{Boolean.valueOf(a3)});
        return a3;
    }

    private boolean a(ContentObserver contentObserver, String str) {
        Uri uriFor = Settings.System.getUriFor(str);
        if (uriFor == null) {
            Logger.d("LVBA.AliveModule", "target Uri is null !");
            return false;
        }
        try {
            StrategyFramework.getFrameworkContext().getContentResolver().registerContentObserver(uriFor, true, contentObserver);
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", "register debug detect failed ", th);
            return false;
        }
    }

    public void a(String str, boolean z) {
        if (!z) {
            g.remove(str);
        } else {
            g.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    private boolean e() {
        return TextUtils.equals(PddSystemProperties.instance().get(Build.VERSION.SDK_INT > 28 ? RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_key_property_58900", "init.svc.dumpstatez") : RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_key_property_58900", "init.svc.dumpstate"), ""), "running");
    }

    private boolean d() {
        if (e()) {
            Logger.d("LVBA.AliveModule", "xm bug report recording !");
            return true;
        } else if (f()) {
            Logger.d("LVBA.AliveModule", "xm full log recording !");
            return true;
        } else {
            Logger.d("LVBA.AliveModule", "xm no debugging !");
            return false;
        }
    }

    public Boolean isSysDebugging() {
        if (!g()) {
            Logger.d("LVBA.AliveModule", "debug check is not enable ! ");
            return null;
        } else if (RomOsUtil.instance().isVivo() || RomOsUtil.instance().isVivoManufacture()) {
            return a();
        } else {
            if (RomOsUtil.instance().isMiui()) {
                return Boolean.valueOf(d());
            }
            if (RomOsUtil.instance().isOppo() || RomOsUtil.instance().isOppoManufacture() || i()) {
                return b();
            }
            if (RomOsUtil.instance().isNewHuaweiManufacture() || RomOsUtil.instance().isHonerManufacture()) {
                return c();
            }
            Logger.d("LVBA.AliveModule", "not support debug check !");
            return null;
        }
    }

    private boolean f() {
        if (h() && g.keySet().size() != 0) {
            Iterator it = g.entrySet().iterator();
            while (it.hasNext()) {
                Logger.i("LVBA.AliveModule", "%s log is recording !", new Object[]{((Map.Entry) it.next()).getKey()});
            }
            return true;
        }
        return false;
    }

    private boolean g() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_enable_58900", "true"));
    }

    private boolean b(String str, IDebugDetectTransmission iDebugDetectTransmission) {
        Logger.d("LVBA.AliveModule", "obtain log status config : %s ", new Object[]{str});
        Map a2 = b.a(str);
        if (null == a2) {
            Logger.d("LVBA.AliveModule", "log status config invalid ! ");
            return false;
        } else if (TextUtils.isEmpty((CharSequence) a2.get("action")) || TextUtils.isEmpty((CharSequence) a2.get("openStatus")) || TextUtils.isEmpty((CharSequence) a2.get("statusKey"))) {
            Logger.d("LVBA.AliveModule", "log status config invalid for no action or openStatus! ");
            return false;
        } else {
            h = new VMDynamicReceiver(new c(a2, iDebugDetectTransmission), "LogStatusReceiver");
            boolean a3 = a((BroadcastReceiver) h, new IntentFilter((String) a2.get("action")));
            if (!a3) {
                h = null;
            }
            return a3;
        }
    }

    private boolean h() {
        return Boolean.parseBoolean(RemoteConfig.instance().getConfigValue("pinduoduo_Android.check_debug_full_log_check_xm_58900", "true"));
    }

    private boolean a(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        try {
            StrategyFramework.getFrameworkContext().registerReceiver(broadcastReceiver, intentFilter);
            Logger.d("LVBA.AliveModule", "finish register debug detect!");
            return true;
        } catch (Throwable th) {
            Logger.e("LVBA.AliveModule", "register debug detect failed ", th);
            return false;
        }
    }
}
