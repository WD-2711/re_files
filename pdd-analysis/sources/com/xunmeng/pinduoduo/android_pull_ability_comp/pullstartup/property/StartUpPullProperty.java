package com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property;

import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PluginJSONFormatUtils;
import com.xunmeng.pinduoduo.alive_adapter_sdk.BotBaseApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: StartUpPullProperty.class */
public class StartUpPullProperty {
    private static volatile StartUpPullProperty sInstance;
    private static final Lock syscLock = new ReentrantLock();
    private static final String CLASS_NAME = null;
    private static final String CONFIG_KEY = null;
    private String clsName = "com.xunmeng.pinduoduo.lifecycle.service.PDDAuthService";
    private String typeName = "com.xunmeng.pinduoduo.account_type";
    private long backStartDelayTime = 0;
    private String firstWayVersions = "v12,v125";
    private String whitePkgListScreenLock = "com.coloros.onekeylockscreen";
    private long clearLogDelayTime = 0;
    private String blackSceneId = "1142";
    private long refreshTTLMills = 0;
    private long validTTLMills = 0;
    private boolean mscUseSyncApi = false;
    private boolean configNullDefaultBlack = true;

    public String getClsName() {
        return this.clsName;
    }

    public long getValidTTLMills() {
        return this.validTTLMills;
    }

    public List getFirstWayVersions() {
        String[] splitStr = splitStr(this.firstWayVersions);
        return 0 == splitStr.length ? new ArrayList() : Arrays.asList(splitStr);
    }

    public String getTypeName() {
        return this.typeName;
    }

    private static StartUpPullProperty init() {
        String configValue = RemoteConfig.instance().getConfigValue("start_pull_activity_config_comp_6020", "");
        Logger.i("SpecialPullAbility.Comp", "active property: %s", new Object[]{configValue});
        StartUpPullProperty startUpPullProperty = null;
        try {
            startUpPullProperty = (StartUpPullProperty) PluginJSONFormatUtils.getInstance(BotBaseApplication.getApplication(), "alive_base_ability_plugin").fromJson(configValue, "com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.property.StartUpPullProperty");
        } catch (Exception e) {
            Logger.e("SpecialPullAbility.Comp", e);
        }
        if (null == startUpPullProperty) {
            startUpPullProperty = new StartUpPullProperty();
        }
        return startUpPullProperty;
    }

    public String getBlackSceneId() {
        return this.blackSceneId;
    }

    public List getCanStartWhitePkgListWhenLock() {
        String[] splitStr = splitStr(this.whitePkgListScreenLock);
        return 0 == splitStr.length ? new ArrayList() : Arrays.asList(splitStr);
    }

    private String[] splitStr(String str) {
        return null == str ? new String[0] : str.split(",").length > 0 ? str.split(",") : new String[]{str};
    }

    public boolean isMscUseSyncApi() {
        return this.mscUseSyncApi;
    }

    public static StartUpPullProperty getInstance() {
        if (sInstance == null) {
            syscLock.lock();
            try {
                if (sInstance == null) {
                    sInstance = init();
                }
            } finally {
                syscLock.unlock();
            }
        }
        return sInstance;
    }

    public long getClearLogDelayTime() {
        return this.clearLogDelayTime;
    }

    public long getBackStartDelayTime() {
        return this.backStartDelayTime;
    }

    public long getRefreshTTLMills() {
        return this.refreshTTLMills;
    }

    private StartUpPullProperty() {
    }

    public boolean isConfigNullDefaultBlack() {
        return this.configNullDefaultBlack;
    }
}
