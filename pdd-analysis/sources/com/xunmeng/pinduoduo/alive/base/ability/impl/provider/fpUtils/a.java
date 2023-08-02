package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.fpUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.b;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.g;
import com.xunmeng.pinduoduo.alive.base.ability.impl.provider.k;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IDBHandle;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.provider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import java.io.File;

/* loaded from: a.class */
public class a implements a.AnonymousClass1 {
    public boolean writeStreamLocked(IStreamHandle iStreamHandle) {
        return k.a(iStreamHandle);
    }

    public boolean writeStreamWithRecheck(IStreamHandle iStreamHandle) {
        return k.b(iStreamHandle);
    }

    public IDBHandle openDB(Uri uri) {
        return b.a(uri);
    }

    public SharedPreferences getSharedPreferencesByFile(Context context, File file) {
        return g.a(context, file);
    }

    public IStreamHandle openStream(Uri uri) {
        return k.b(uri);
    }

    public IStreamHandle openStream(Uri uri, boolean z, boolean z2) {
        return k.a(uri, z, z2);
    }

    public IStreamHandle openStreamWithException(Uri uri) {
        return k.a(uri);
    }

    public boolean writeDBLocked(IDBHandle iDBHandle) {
        return b.a(iDBHandle);
    }
}
