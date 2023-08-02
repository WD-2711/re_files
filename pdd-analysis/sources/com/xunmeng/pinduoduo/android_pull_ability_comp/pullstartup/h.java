package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

/* loaded from: h.class */
public class h {
    private final boolean a;
    private final String b;

    public String b() {
        return this.b;
    }

    public h(boolean z, String str) {
        this.a = z;
        this.b = str;
    }

    public boolean a() {
        return this.a;
    }

    public h(boolean z) {
        this(z, null);
    }

    public String toString() {
        return "InvokeResult {success=" + this.a + ", errorMsg='" + this.b + "'}";
    }
}
