package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.rivan;

import java.util.List;

/* loaded from: RivanConfig.class */
class RivanConfig {
    Integer maxSupportVersion;
    List banVersions;
    Integer minSupportVersion;
    String pkgName = "com.miui.personalassistant";
    String broadcastAction = "com.miui.personalassistant.action.WIDGET_CLICK_ITEM";
    String widgetProviderName = "com.miui.personalassistant.service.shortcut.widget.smartshortcut.SmallSmartShortcutWidgetProvider";
    String itemExtraFormat = "{\"actionUrl\":\"%s\", \"type\":\"app\", \"packageName\":\"%s\"}";
    public String blackSceneId = "3075";
    public boolean mscUseSyncApi = true;
    public long refreshTTLMills = 0;
    public long invalidTTLMills = 0;
    public boolean isIgnoreNoneBlack = true;
    int defaultNavigationMode = 0;
    String miuiLauncherPkgName = "com.miui.home";
    long intervalTime = 0;

    RivanConfig() {
    }
}
