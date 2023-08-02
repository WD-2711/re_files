package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.parcel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;

/* loaded from: d.class */
public class d {
    public static String a(com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.e eVar) {
        Logger.i("SpecialPullAbility.Comp", "check validate");
        Intent intent = new Intent("android.intent.action.SEND");
        try {
            Bundle a = eVar.a(intent);
            if (a == null) {
                return "null_make_bundle";
            }
            try {
                Bundle bundle = new Bundle();
                bundle.putAll(a);
                Parcel obtain = Parcel.obtain();
                bundle.writeToParcel(obtain, 0);
                obtain.setDataPosition(0);
                Bundle readBundle = obtain.readBundle();
                com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.utils.b.a();
                if (readBundle == null) {
                    return "null_read_bundle";
                }
                Intent intent2 = (Intent) readBundle.getParcelable("intent");
                if (intent2 != null) {
                    if (TextUtils.equals(intent.getAction(), intent2.getAction())) {
                        return "";
                    }
                }
                return "action_not_equal";
            } catch (Throwable th) {
                Logger.e("SpecialPullAbility.Comp", "check intent failed and check next");
                String th2 = th.toString();
                return TextUtils.isEmpty(th2) ? "unknown_exception" : th2;
            }
        } catch (Throwable th3) {
            Logger.e("SpecialPullAbility.Comp", "make bundle fail");
            return th3.toString();
        }
    }

    public static boolean a(Bundle bundle) {
        Parcel parcel = null;
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putAll(bundle);
            Parcel obtain = Parcel.obtain();
            obtain.writeParcelable(bundle2, 0);
            obtain.setDataPosition(0);
            Bundle bundle3 = (Bundle) obtain.readParcelable(null);
            if (bundle3 == null) {
                Logger.i("SpecialPullAbility.Comp", "bundle is null, pass");
                if (obtain != null) {
                    obtain.recycle();
                }
                return true;
            } else if (((Intent) bundle3.getParcelable("intent")) == null) {
                Logger.i("SpecialPullAbility.Comp", "second intent is null, pass");
                if (obtain != null) {
                    obtain.recycle();
                }
                return true;
            } else if (bundle2.getParcelable("intent") != null) {
                if (obtain != null) {
                    obtain.recycle();
                }
                return true;
            } else {
                Logger.i("SpecialPullAbility.Comp", "first intent is null, has report, no pass");
                if (obtain != null) {
                    obtain.recycle();
                }
                return false;
            }
        } catch (Throwable th) {
            try {
                Logger.i("SpecialPullAbility.Comp", "report check exception");
            } finally {
                if (0 != 0) {
                    parcel.recycle();
                }
            }
        }
    }
}
