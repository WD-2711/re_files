package com.xunmeng.pinduoduo.alive.base.ability.impl.provider.vivo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.UriPermission;
import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.base.ability.interfaces.impl.EmptyFileProviderImpl;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: a.class */
public class a extends EmptyFileProviderImpl implements a.AnonymousClass1 {
    public static final Uri a = Uri.parse("content://com.android.settings.fileprovider/root_files");
    public static final String b = null;
    private static final String c = null;
    private final AtomicBoolean d = new AtomicBoolean(false);

    public void startGrantPermission(String str) {
        a(StrategyFramework.getFrameworkContext(), str);
        Logger.i("LVBA.AliveModule.VProviderImpl", "Start grant permission !");
    }

    private boolean a(List list) {
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(a.toString(), ((UriPermission) list.get(i)).getUri().toString())) {
                Logger.i("LVBA.AliveModule.VProviderImpl", "obtain persisted perm: %s", new Object[]{((UriPermission) list.get(i)).getUri().getPath()});
                return true;
            }
        }
        return false;
    }

    public boolean hasAbility(String str) {
        if (TextUtils.equals("startGrantPermission", str)) {
            return true;
        }
        return super.hasAbility(str);
    }

    private void a(Context context, String str) {
    }

    public boolean hasPermission() {
        if (this.d.get()) {
            Logger.i("LVBA.AliveModule.VProviderImpl", "Already obtain permission !");
            return true;
        }
        try {
            ContentResolver contentResolver = StrategyFramework.getFrameworkContext().getContentResolver();
            boolean a2 = a(contentResolver.getPersistedUriPermissions());
            if (!a2) {
                try {
                    contentResolver.takePersistableUriPermission(a, 3);
                } catch (Exception e) {
                    Logger.e("LVBA.AliveModule.VProviderImpl", "take Persistable Uri Permission failed !", e);
                }
                a2 = a(contentResolver.getPersistedUriPermissions());
            }
            this.d.set(a2);
        } catch (Exception e2) {
            Logger.e("LVBA.AliveModule.VProviderImpl", "get persisted perm failed !", e2);
        }
        return this.d.get();
    }
}
