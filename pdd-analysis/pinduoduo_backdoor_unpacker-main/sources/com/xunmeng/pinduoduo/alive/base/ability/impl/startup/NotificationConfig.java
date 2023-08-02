package com.xunmeng.pinduoduo.alive.base.ability.impl.startup;

/* loaded from: NotificationConfig.class */
public class NotificationConfig {
    private int importance;
    private String sound;
    private Boolean shouldShowLights;
    private Boolean shouldVibrate;
    static final String CLASS_NAME = null;

    public String getSound() {
        return this.sound;
    }

    public Boolean isShouldVibrate() {
        return this.shouldVibrate;
    }

    public int getImportance() {
        return this.importance;
    }

    public Boolean isShouldShowLights() {
        return this.shouldShowLights;
    }

    public String toString() {
        return "NotificationConfig{sound='" + this.sound + "', shouldVibrate=" + this.shouldVibrate + ", shouldShowLights=" + this.shouldShowLights + ", importance=" + this.importance + '}';
    }

    public NotificationConfig(String str, Boolean bool, Boolean bool2, int i) {
        this.sound = str;
        this.shouldVibrate = bool;
        this.shouldShowLights = bool2;
        this.importance = i;
    }
}
