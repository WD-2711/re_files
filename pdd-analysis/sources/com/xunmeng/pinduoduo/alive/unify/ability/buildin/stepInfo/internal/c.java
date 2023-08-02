package com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal;

import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.fileProvider.fpUtils.IStreamHandle;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.intf.fileProvider.purge.Request;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.FileProviderV2;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.TimeStamp;
import com.xunmeng.pinduoduo.alive.unify.ability.interfaces.schema.karma.KarmaResult;
import java.io.File;

/* loaded from: c.class */
public class c extends e {
    private static final String c = null;
    private static final String d = null;
    private static final String e = null;

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public KarmaResult b() {
        if (!FileProviderV2.instance().hasPermission("provider_hw_health")) {
            Log.i(a, "no fp permission");
            FileProviderV2.instance().requestGrantPermission(new Request("provider_hw_health", "StepInfoGainer"));
            return new KarmaResult("no_fp_permission");
        }
        IStreamHandle iStreamHandle = null;
        try {
            try {
                IStreamHandle openStream = FileProviderV2.instance().fileProviderUtils().openStream(Uri.parse("content://com.huawei.health.fastapp.engine.fileProvider/root_path/data/data/com.huawei.health/shared_prefs/huawei_hihealth.xml"));
                if (openStream == null || openStream.getTempStreamFilePath() == null) {
                    Logger.i(a, "illegal stream");
                    com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("null_stream");
                    KarmaResult karmaResult = new KarmaResult("file_not_found");
                    if (openStream != null) {
                        openStream.close();
                    }
                    return karmaResult;
                }
                File file = new File(openStream.getTempStreamFilePath());
                if (!file.exists()) {
                    Logger.i(a, "copy file failed");
                    com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("null_stream");
                    KarmaResult karmaResult2 = new KarmaResult("file_not_found");
                    if (openStream != null) {
                        openStream.close();
                    }
                    return karmaResult2;
                }
                SharedPreferences sharedPreferencesByFile = FileProviderV2.instance().fileProviderUtils().getSharedPreferencesByFile(this.b, file);
                if (sharedPreferencesByFile == null) {
                    Logger.i(a, "null sp");
                    com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("null_sp");
                    KarmaResult karmaResult3 = new KarmaResult("file_not_found");
                    if (openStream != null) {
                        openStream.close();
                    }
                    return karmaResult3;
                }
                String string = sharedPreferencesByFile.getString("bi_oldHuid", "");
                if (string == null || TextUtils.isEmpty(string)) {
                    Logger.i(a, "illegal result , return ");
                    com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("illegal result");
                    KarmaResult karmaResult4 = new KarmaResult("no_step_info");
                    if (openStream != null) {
                        openStream.close();
                    }
                    return karmaResult4;
                }
                int i = sharedPreferencesByFile.getInt(string + "step_sum_diff_value_acc", -1);
                Logger.i(a, "hw step is %d", new Object[]{Integer.valueOf(i)});
                com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.b.a("get_success");
                KarmaResult karmaResult5 = i != -1 ? new KarmaResult(i, TimeStamp.instance().getRealLocalTimeV2()) : new KarmaResult("no_step_info");
                if (openStream != null) {
                    openStream.close();
                }
                return karmaResult5;
            } catch (Exception e2) {
                Log.e(a, "read from file error", e2);
                KarmaResult karmaResult6 = new KarmaResult("read_from_file_error");
                if (0 != 0) {
                    iStreamHandle.close();
                }
                return karmaResult6;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                iStreamHandle.close();
            }
            throw th;
        }
    }

    @Override // com.xunmeng.pinduoduo.alive.unify.ability.buildin.stepInfo.internal.e
    public boolean a() {
        return a("com.huawei.health");
    }
}
