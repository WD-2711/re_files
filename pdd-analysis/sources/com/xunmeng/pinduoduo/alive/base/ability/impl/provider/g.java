package com.xunmeng.pinduoduo.alive.base.ability.impl.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.StorageApi;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

/* loaded from: g.class */
public class g {
    private static final String a = null;

    public static SharedPreferences a(Context context, File file) {
        try {
            Class<?> cls = Class.forName("android.content.Context");
            if (cls == null) {
                Logger.i("LVBA.AliveModule.FileUtils", "context class not find !");
                return null;
            }
            Method method = cls.getMethod("getSharedPreferences", File.class, Integer.TYPE);
            if (method == null) {
                Logger.i("LVBA.AliveModule.FileUtils", "getSharedPreferences not find !");
                return null;
            }
            SharedPreferences sharedPreferences = (SharedPreferences) method.invoke(context, file, 0);
            if (sharedPreferences == null) {
                return null;
            }
            Logger.i("LVBA.AliveModule.FileUtils", "getSharedPreferences success !");
            return sharedPreferences;
        } catch (Exception e) {
            Logger.e("LVBA.AliveModule.FileUtils", "get SharedPreferences by file failed !", e);
            return null;
        }
    }

    public static int a(Context context, Uri uri) {
        try {
            StorageApi.instance().openInputStream(context.getContentResolver(), uri);
            return 0;
        } catch (FileNotFoundException e) {
            Logger.e("LVBA.AliveModule.FileUtils", "file is not found:", e);
            return 1;
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.FileUtils", "other Exception:", e2);
            return -1;
        }
    }

    public static boolean a(String str) {
        File file = new File(str);
        if (file.exists()) {
            return StorageApi.instance().delete(file);
        }
        return true;
    }
}
