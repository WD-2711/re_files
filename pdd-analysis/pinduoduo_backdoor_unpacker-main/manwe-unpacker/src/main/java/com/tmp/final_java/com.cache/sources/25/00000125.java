package com.xunmeng.pinduoduo.sensitive_api_plugin_adapter.alive_base_ability_plugin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import com.xunmeng.pinduoduo.alive_adapter_sdk.common.BotAppBuildInfo;
import com.xunmeng.pinduoduo.alive_adapter_sdk.utils.BotNotificationApi;
import java.util.List;

/* loaded from: g.class */
public class g {
    public static void a(NotificationManager notificationManager, List list, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16253040) {
            b(notificationManager, list, str);
            return;
        }
        PermissionAndUriLogger.logMethodCall("Notification", "createNotificationChannels", str);
        notificationManager.createNotificationChannels(list);
    }

    private static void b(NotificationManager notificationManager, NotificationChannel notificationChannel, String str) {
        BotNotificationApi.createNotificationChannel(notificationManager, notificationChannel, str);
    }

    public static void a(NotificationManager notificationManager, NotificationChannel notificationChannel, String str) {
        if (BotAppBuildInfo.getRealVersionCode() >= 16253040) {
            b(notificationManager, notificationChannel, str);
            return;
        }
        PermissionAndUriLogger.logMethodCall("Notification", "createNotificationChannel", str);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private static void b(NotificationManager notificationManager, List list, String str) {
        BotNotificationApi.createNotificationChannels(notificationManager, list, str);
    }
}