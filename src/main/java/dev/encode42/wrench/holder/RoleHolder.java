package dev.encode42.wrench.holder;

import net.dv8tion.jda.api.entities.Role;

public class RoleHolder {
    private static Role admins;

    public static Role getAdmins() {
        return admins;
    }

    public static void setAdmins(Role admins) {
        RoleHolder.admins = admins;
    }
}
