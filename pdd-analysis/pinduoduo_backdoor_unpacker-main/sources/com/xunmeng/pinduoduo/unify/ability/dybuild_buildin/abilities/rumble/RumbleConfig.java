package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rumble;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: RumbleConfig.class */
public class RumbleConfig {
    Map transactCodeMap;
    String pkgName = "com.vivo.globalsearch";
    String serviceName = "com.vivo.globalsearch.presenter.service.SearchService";
    String serviceAction = "com.vivo.globalsearch.service";
    String descriptor = "com.vivo.globalsearch.presenter.service.ISearchService";
    public String blackSceneId = "3075";
    public boolean mscUseSyncApi = true;
    public long refreshTTLMills = 0;
    public long invalidTTLMills = 0;
    public boolean isIgnoreNoneBlack = true;
    List banKeyExtras = Arrays.asList("source_id", "come_from");
    int delayTime = 0;
}
