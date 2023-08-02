package com.xunmeng.pinduoduo.alive.base.ability.comp;

import com.xunmeng.pinduoduo.alive.base.ability.interfaces.IAliveBaseAbility;
import com.xunmeng.pinduoduo.alive.sona.ability.ISonaAbility;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.base.IStrategy;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IActivity;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IReceiver;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.IService;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.plugin.ObjectCreator;
import com.xunmeng.pinduoduo.alive.strategy.pa.interfaces.adapter.intf.IPluginPAStrategyUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.interfaces.IAbility;
import com.xunmeng.pinduoduo.android_pull_ability_impl_interface.interf.IAlivePullStartUp;
import com.xunmeng.pinduoduo.launcher_detect_comp.impl.a;
import com.xunmeng.pinduoduo.launcher_detect_comp_interf.interf.IVivoBindServiceComp;
import java.util.Map;
import java.util.Set;

/* loaded from: Main.class */
public class Main {
    private static final String TAG = null;

    // 创建Strategy代理，根据名字获取Strategy
    public static IStrategy createStrategyProxy(String str) {
        Logger.i("LVBA.Plugin.Main", "createStrategyProxy: " + str);
        return (IStrategy) getComponent.mainFunction(str);
    }

    // 创建Receiver代理，组件化虚拟接口
    public static IReceiver createReceiverProxy(String str) {
        Logger.i("LVBA.Plugin.Main", "createReceiverProxy: " + str);
        return (IReceiver) getComponent.mainFunction(str);
    }

    // 获取launcher以检测 Vivo 绑定服务，Vivo的某个组件泄露漏洞利⽤
    public static IVivoBindServiceComp getLauncherDetectVivoBindService() {
        Logger.i("LVBA.Plugin.Main", "getLauncherDetectVivoBindService");
        return a.a();
    }

    // 创建Activity代理，组件化虚拟接口
    public static IActivity createActivityProxy(String str) {
        Logger.i("LVBA.Plugin.Main", "createActivityProxy: " + str);
        return (IActivity) getComponent.mainFunction(str);
    }

    // 获得Sona能力
    public static ISonaAbility getSonaAbility() {
        Logger.i("LVBA.Plugin.Main", "getSonaAbility");
        return new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.sona.a();
    }

    // 创建Service代理，组件化虚拟接口
    public static IService createServiceProxy(String str) {
        Logger.i("LVBA.Plugin.Main", "createServiceProxy: " + str);
        return (IService) getComponent.mainFunction(str);
    }

    // 获得Component名称
    public static Set getComponentNames() {
        Logger.i("LVBA.Plugin.Main", "getComponentNames");
        return getComponent.a();
    }

    // 启动getAlivePull
    public static IAlivePullStartUp getAlivePullStartUp() {
        Logger.i("LVBA.Plugin.Main", "getAlivePullStartUp");
        return new com.xunmeng.pinduoduo.android_pull_ability_comp.pullstartup.a();
    }

    // 获得保活基本能力
    public static IAliveBaseAbility getAliveBaseAbility() {
        Logger.i("LVBA.Plugin.Main", "getAliveBaseAbilityInstance");
        return new com.xunmeng.pinduoduo.alive.base.ability.impl.a();
    }

    // 创建保活能力
    public static IAbility createAliveAbility(String str) {
        Logger.i("LVBA.Plugin.Main", "createAliveAbility： %s", new Object[]{str});
        Map a = com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.a();
        a.putAll(com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.a());
        ObjectCreator objectCreator = (ObjectCreator) a.get(str);
        if (objectCreator == null) {
            Logger.i("LVBA.Plugin.Main", "not found ability： %s", new Object[]{str});
            return null;
        }
        return (IAbility) objectCreator.createObject();
    }

    // 获得保活能力
    public static Map getAliveUnifyAbilities() {
        Logger.i("LVBA.Plugin.Main", "getAliveUnifyAbility");
        Map a = com.xunmeng.pinduoduo.alive.unify.ability.buildin.b.a();
        a.putAll(com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.b.a());
        Logger.i("LVBA.Plugin.Main", "ability names: %s", new Object[]{a.keySet()});
        return a;
    }

    public static Boolean hasComponent(String str) {
        Logger.i("LVBA.Plugin.Main", "hasComponent: " + str);
        return Boolean.valueOf(getComponent.mainFunction(str) != null);
    }

    // 获得插件策略的工具组件
    public static IPluginPAStrategyUtil getIPluginPAStrategyUtil() {
        Logger.i("LVBA.Plugin.Main", "getIPluginPAStrategyUtil");
        return new com.xunmeng.pinduoduo.alive.strategy.impl.a();
    }
}