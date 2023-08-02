package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.varus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.PhoneUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.ScreenUtils;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.IntentRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.StatusResult;
import com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.JumpBoxInfo;
import org.json.JSONObject;

/* loaded from: a.class */
public class a extends com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.a {
    static final String a = null;
    private static final String b = null;

    protected String b() {
        return "com.iflytek.inputmethod.miui/.FlyIME";
    }

    // 判断当前设备的默认输入方法是否为特定的方法
    @Override // com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.a
    protected boolean a(JumpBoxInfo jumpBoxInfo) {
        try {
            if (!ScreenUtils.instance().isScreenOn()) { // 判断屏幕是否亮着
                Logger.i("LVUA.Buildin.VarusAbility", "screen off");
                return false;
            }
            String b2 = b();
            ContentResolver contentResolver = StrategyFramework.getFrameworkContext().getContentResolver();
            String str = jumpBoxInfo.extra;
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("default_input_method")) { // str中是否有default_input_method字符串
                    String string = jSONObject.getString("default_input_method");
                    Logger.i("LVUA.Buildin.VarusAbility", "parse input method is " + string);
                    if (!TextUtils.isEmpty(string)) {
                        b2 = string;
                    }
                }
            }
            String string2 = PhoneUtils.instance().getString(contentResolver, "default_input_method");
            Logger.i("LVUA.Buildin.VarusAbility", "value:" + string2 + ",inputMethodName:" + b2);
            if (!b2.equals(string2)) { // 比较当前设备是否有default_input_method
                return false;
            }
            Logger.i("LVUA.Buildin.VarusAbility", b2 + " is default input method");
            return true;
        } catch (Throwable th) {
            Logger.i("LVUA.Buildin.VarusAbility", th);
            return false;
        }
    }

    @Override // com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.a
    protected String a() {
        return "pinduoduo_Android.build_in_varus_start_ability_key_63000";
    }

    // 发送广播
    @Override // com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.jumpbox.a
    protected StatusResult a(IntentRequest intentRequest, JumpBoxInfo jumpBoxInfo) {
        Uri data = intentRequest.getIntent().getData();
        if (data == null) {
            return new StatusResult(false, "target uri is null");
        }
        if (!com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.utils.b.a(intentRequest.getIntent())) {
            return new StatusResult(false, "exist more activity");
        }
        Context frameworkContext = StrategyFramework.getFrameworkContext();
        Intent intent = new Intent("launch_from_notice");
        intent.setPackage(jumpBoxInfo.jumpBoxPkgName);
        intent.putExtra("action_id", 3042);
        intent.putExtra("pkg_name", frameworkContext.getPackageName());
        intent.putExtra("url", data.toString());
        Logger.i("LVUA.Buildin.VarusAbility", "send broadcast");
        frameworkContext.sendBroadcast(intent);
        return new StatusResult(true, "");
    }
}
