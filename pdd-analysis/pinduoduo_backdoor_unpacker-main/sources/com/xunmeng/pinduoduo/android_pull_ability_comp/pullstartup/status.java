package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup;

/* renamed from: com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.h */
/* loaded from: h.class */
public class status {

    /* renamed from: a */
    private final boolean boolValue;

    /* renamed from: b */
    private final String strValue;

    public String b() {
        return this.strValue;
    }

    public status(boolean z, String str) {
        this.boolValue = z;
        this.strValue = str;
    }

    /* renamed from: a */
    public boolean getBoolean() {
        return this.boolValue;
    }

    public status(boolean z) {
        this(z, null);
    }

    public String toString() {
        return "InvokeResult {success=" + this.boolValue + ", errorMsg='" + this.strValue + "'}";
    }
}
