package dev.encode42.wrench.holder;

import java.awt.*;

public class ColorHolder {
    private static Color primary;
    private static Color secondary;
    private static Color success;
    private static Color destructive;

    public static Color getPrimary() {
        return primary;
    }

    public static Color getSecondary() {
        return secondary;
    }

    public static Color getDestructive() {
        return destructive;
    }

    public static Color getSuccess() {
        return success;
    }

    public static void setPrimary(Color primary) {
        ColorHolder.primary = primary;
    }

    public static void setSecondary(Color secondary) {
        ColorHolder.secondary = secondary;
    }

    public static void setDestructive(Color destructive) {
        ColorHolder.destructive = destructive;
    }

    public static void setSuccess(Color success) {
        ColorHolder.success = success;
    }
}
