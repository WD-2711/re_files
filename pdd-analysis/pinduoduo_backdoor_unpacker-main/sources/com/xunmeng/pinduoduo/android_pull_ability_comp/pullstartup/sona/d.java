package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona;

import android.content.Intent;
import android.os.Bundle;
import com.xunmeng.pinduoduo.alive.sona.ability.SonaRequest;

/* loaded from: d.class */
public class d {
    public static SonaRequest a(Bundle bundle) {
        return new SonaRequest((Intent) bundle.getParcelable("intent"), bundle.getString("caller"), bundle.getString("requestId"), bundle.getBundle("extra"));
    }

    public static Bundle a(SonaRequest sonaRequest) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", sonaRequest.getIntent());
        bundle.putString("caller", sonaRequest.getCaller());
        bundle.putString("requestId", sonaRequest.getRequestId());
        bundle.putBundle("extra", sonaRequest.getExtra());
        return bundle;
    }

    public static String a(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split.length <= 2 ? str : split[0] + "." + split[1];
    }
}
