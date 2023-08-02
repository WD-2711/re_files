package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotContentResolverUtils;
import java.io.InputStream;

/* loaded from: c.class */
public class c {
    private static void a(Uri uri, String str, String str2) {
        PermissionAndUriLogger.logUriUsage(uri, str, str2);
    }

    public static int a(ContentResolver contentResolver, Uri uri, ContentValues contentValues, String str, String[] strArr, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, contentValues, str, strArr, str2);
        }
        a(uri, "update", str2);
        return contentResolver.update(uri, contentValues, str, strArr);
    }

    public static Cursor a(ContentResolver contentResolver, Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, strArr, bundle, cancellationSignal, str);
        }
        a(uri, "query", str);
        return contentResolver.query(uri, strArr, bundle, cancellationSignal);
    }

    public static Uri a(ContentResolver contentResolver, Uri uri, ContentValues contentValues, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, contentValues, str);
        }
        a(uri, "insert", str);
        return contentResolver.insert(uri, contentValues);
    }

    private static Cursor b(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, String str3) {
        return BotContentResolverUtils.query(contentResolver, uri, strArr, str, strArr2, str2, str3);
    }

    private static Cursor b(ContentResolver contentResolver, Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal, String str) {
        return BotContentResolverUtils.query(contentResolver, uri, strArr, bundle, cancellationSignal, str);
    }

    public static ContentResolver a(Context context, String str) {
        return BotAppBuildInfo.getRealVersionCode() >= 15990920 ? b(context, str) : context.getContentResolver();
    }

    public static int a(ContentResolver contentResolver, Uri uri, String str, String[] strArr, String str2) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, str, strArr, str2);
        }
        a(uri, "delete", str2);
        return contentResolver.delete(uri, str, strArr);
    }

    private static int b(ContentResolver contentResolver, Uri uri, ContentValues contentValues, String str, String[] strArr, String str2) {
        return BotContentResolverUtils.update(contentResolver, uri, contentValues, str, strArr, str2);
    }

    private static int b(ContentResolver contentResolver, Uri uri, String str, String[] strArr, String str2) {
        return BotContentResolverUtils.delete(contentResolver, uri, str, strArr, str2);
    }

    private static ContentResolver b(Context context, String str) {
        return BotContentResolverUtils.getContentResolver(context, str);
    }

    private static InputStream b(ContentResolver contentResolver, Uri uri, String str) {
        return BotContentResolverUtils.openInputStream(contentResolver, uri, str);
    }

    public static Cursor a(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, String str3) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, strArr, str, strArr2, str2, str3);
        }
        a(uri, "query", str3);
        return contentResolver.query(uri, strArr, str, strArr2, str2);
    }

    private static Cursor b(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal, String str3) {
        return BotContentResolverUtils.query(contentResolver, uri, strArr, str, strArr2, str2, cancellationSignal, str3);
    }

    private static Uri b(ContentResolver contentResolver, Uri uri, ContentValues contentValues, String str) {
        return BotContentResolverUtils.insert(contentResolver, uri, contentValues, str);
    }

    public static InputStream a(ContentResolver contentResolver, Uri uri, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, str);
        }
        a(uri, "openInputStream", str);
        return contentResolver.openInputStream(uri);
    }

    public static Cursor a(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal, String str3) {
        if (BotAppBuildInfo.getRealVersionCode() >= 15073300) {
            return b(contentResolver, uri, strArr, str, strArr2, str2, cancellationSignal, str3);
        }
        a(uri, "query", str3);
        return contentResolver.query(uri, strArr, str, strArr2, str2, cancellationSignal);
    }
}