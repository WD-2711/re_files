package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: SionConfig.class */
public class SionConfig {
    List subAbilitySeq = Arrays.asList("DirectSubAbility", "RumbleSubAbility", "FloatSubAbility", "RyzeSubAbility", "NotificationSubAbility", "AlarmSubAbility");
    public int delayRemoveLifecycleTime = 3000;
    public Set callerWhiteList = new HashSet();
    public List checkDOPkgNames = Arrays.asList(StrategyFramework.getFrameworkContext().getPackageName());
    public Map checkDOPkgNamesByAbility = new HashMap() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.SionConfig.1
        {
            SionConfig.this = this;
            String packageName = StrategyFramework.getFrameworkContext().getPackageName();
            put("RumbleSubAbility", packageName);
            put("NotificationSubAbility", packageName);
            put("RivanSmartSubAbility", packageName);
            put("RivanSubAbility", packageName);
            put("VarusCommonSubAbility", packageName);
            put("VarusMiuiSubAbility", packageName);
            put("FloatSubAbility", packageName);
            put("RyzeSubAbility", packageName);
        }
    };
    public Map secPatchMaxVersion = new HashMap() { // from class: com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.sion.SionConfig.2
    };

    SionConfig() {
    }
}
