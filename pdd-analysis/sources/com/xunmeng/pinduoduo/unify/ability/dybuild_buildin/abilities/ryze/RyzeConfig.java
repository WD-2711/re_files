package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.ryze;

import java.util.List;

/* loaded from: RyzeConfig.class */
class RyzeConfig {
    public long maxSupportVersion;
    String extra;
    List banVersions;
    public long minSupportVersion;
    public String type = "broadcast";
    public String blackSceneId = "3075";
    public boolean mscUseSyncApi = true;
    public long refreshTTLMills = 0;
    public long invalidTTLMills = 0;
    public boolean isIgnoreNoneBlack = true;
    public String aliveUri = "content://com.xiaomi.ad.LockScreenAdProvider";

    RyzeConfig() {
    }
}
