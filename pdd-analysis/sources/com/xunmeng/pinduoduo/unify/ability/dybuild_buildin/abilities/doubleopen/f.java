package com.xunmeng.pinduoduo.unify.ability.dybuild_buildin.abilities.doubleopen;

import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.comp.janus.a;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.RemoteConfig;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.execute.BaseAbility;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.JsonRequest;
import com.xunmeng.pinduoduo.alive.unify.ability.framework_buildin.schema.common.JsonResult;
import org.json.JSONObject;

/* loaded from: f.class */
public class f extends BaseAbility implements a.AnonymousClass1 {
    public static final String a = null;
    private static final String b = null;

    private boolean a() {
        return RomOsUtil.instance().isOppoManufacture() || RomOsUtil.instance().isXiaomiManufacture() || RomOsUtil.instance().isEmui() || RomOsUtil.instance().isHonerManufacture();
    }

    /* renamed from: a */
    public JsonResult execute(JsonRequest jsonRequest) {
        Boolean a2;
        try {
            if (jsonRequest == null) {
                return new JsonResult(false, "request is null");
            }
            JSONObject json = jsonRequest.json();
            if (json == null) {
                return new JsonResult(false, "json parse error");
            }
            String optString = json.optString("packageName");
            Logger.i("LVUA.Dybuild.SystemDoubleOpen", "start check open double app:" + optString);
            if (TextUtils.isEmpty(optString)) {
                return new JsonResult(false, "packageName is null");
            }
            if (RomOsUtil.instance().isXiaomiManufacture()) {
                a2 = d.a(optString);
            } else if (RomOsUtil.instance().isOppoManufacture()) {
                a2 = e.a(optString);
            } else if (!RomOsUtil.instance().isEmui() && !RomOsUtil.instance().isHonerManufacture()) {
                return new JsonResult(false, "Manufacture is support");
            } else {
                a2 = c.a(optString);
            }
            JSONObject jSONObject = new JSONObject();
            if (a2 != null) {
                jSONObject.put("isDoubleOpen", a2);
                return new JsonResult(true, "success", jSONObject.toString());
            }
            jSONObject.put("isDoubleOpen", false);
            return new JsonResult(false, "execute exception");
        } catch (Throwable th) {
            Logger.e("LVUA.Dybuild.SystemDoubleOpen", th);
            return new JsonResult(false, th.getMessage());
        }
    }

    public boolean isSupport() {
        if (!RemoteConfig.instance().getBoolean("pinduoduo_Android.build_support_double_open_62000", true)) {
            Logger.i("LVUA.Dybuild.SystemDoubleOpen", "DoubleOpen isSupport hit ab ");
            return false;
        } else if (a()) {
            return true;
        } else {
            Logger.i("LVUA.Dybuild.SystemDoubleOpen", "Manufacture is error");
            return false;
        }
    }

    public boolean shouldFilterByFramework() {
        return false;
    }
}
