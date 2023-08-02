package com.xunmeng.pinduoduo.alive.strategy.comp.common;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.Logger;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.StrategyFramework;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.DeprecatedAb;
import com.xunmeng.pinduoduo.alive.strategy.interfaces.adapter.proxy.utils.RomOsUtil;

/* loaded from: b.class */
public class b {
    public static String a = "BaseAccountImpl";

    public static void a(String str, String str2, String str3) {
        Logger.i(a, "startSync: %s", new Object[]{str});
        Bundle bundle = new Bundle();
        bundle.putBoolean("force", true);
        ContentResolver.requestSync(new Account(str, str2), str3, bundle);
        Logger.i(a, "requestSync");
    }

    public static void a(String str, String str2) {
        try {
            AccountManager accountManager = AccountManager.get(StrategyFramework.getFrameworkContext());
            Account[] accountsByType = accountManager.getAccountsByType(str2);
            if (accountsByType == null) {
                return;
            }
            for (Account account : accountsByType) {
                if (TextUtils.equals(account.name, str) && TextUtils.equals(account.type, str2)) {
                    if (Build.VERSION.SDK_INT >= 22) {
                        accountManager.removeAccountExplicitly(account);
                    } else {
                        accountManager.removeAccount(account, null, null);
                    }
                    Logger.i(a, "remove account " + account);
                }
            }
        } catch (Exception e) {
            Logger.e(a, e);
        }
    }

    private static boolean a(AccountManager accountManager, String str, String str2) {
        Account[] accountsByType;
        try {
            for (Account account : accountManager.getAccountsByType(str2)) {
                if (TextUtils.equals(account.name, str) && TextUtils.equals(account.type, str2)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            Logger.e(a, th);
            return false;
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        Logger.i(a, "initAccount: %s", new Object[]{str});
        try {
            Account account = new Account(str, str2);
            AccountManager accountManager = AccountManager.get(context);
            if (a(accountManager, str, str2)) {
                return;
            }
            accountManager.addAccountExplicitly(account, null, Bundle.EMPTY);
            ContentResolver.setIsSyncable(account, str3, 1);
            ContentResolver.setSyncAutomatically(account, str3, true);
            boolean isFlowControl = DeprecatedAb.instance().isFlowControl("enable_master_sync_auto_5361", !RomOsUtil.instance().isVivoManufacture());
            Logger.i(a, "setMasterSyncAutomatically " + isFlowControl);
            if (!isFlowControl) {
                return;
            }
            ContentResolver.setMasterSyncAutomatically(true);
        } catch (Throwable th) {
            Logger.e(a, th);
        }
    }
}
