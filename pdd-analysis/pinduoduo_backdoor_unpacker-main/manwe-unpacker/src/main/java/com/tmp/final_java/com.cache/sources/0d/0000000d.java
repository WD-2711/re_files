package com.xunmeng.pinduoduo.alive.base.ability.impl.debugCheck;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.debugCheck.IDebugDetectTransmission;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: e.class */
public class e implements a.AnonymousClass1 {
    static final String a = null;
    private final String b;
    private final String c;
    private final ContentResolver d;
    private final IDebugDetectTransmission e;

    public e(String str, String str2, IDebugDetectTransmission iDebugDetectTransmission, ContentResolver contentResolver) {
        this.b = str2;
        this.c = str;
        this.e = iDebugDetectTransmission;
        this.d = contentResolver;
    }

    public void onChange(boolean z) {
    }

    public void onChange(boolean z, Uri uri, int i) {
    }

    public void onChange(boolean z, Uri uri) {
        if (!TextUtils.equals(uri.getLastPathSegment(), this.c)) {
            Logger.d("SettingsContentObserver", "get settings name not match : %s !", new Object[]{uri.getLastPathSegment()});
            return;
        }
        String string = Settings.System.getString(this.d, this.c);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        Object[] objArr = new Object[1];
        objArr[0] = TextUtils.equals(string, this.b) ? "start" : "stop";
        Logger.d("SettingsContentObserver", "update log status : %s", objArr);
        this.e.send(this.c, TextUtils.equals(string, this.b));
        DebugCheckUtils.logDebugDetectEvent(this.c, string);
    }
}