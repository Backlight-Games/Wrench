package games.backlight.wrench.manager;

import com.github.kaktushose.jda.commands.entities.JDACommands;
import games.backlight.wrench.Wrench;
import games.backlight.wrench.holder.RoleHolder;
import games.backlight.wrench.util.CommonImports;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.util.HashSet;
import java.util.Set;

public class PermissionManager extends CommonImports {
    private static final JDACommands commands = Wrench.getCommands();
    private static final Set<Long> managers = new HashSet<>();
    private static final Set<Long> admins = new HashSet<>();

    // todo: Make this dynamic? (redo this entire class)

    /**
     * Load the permission managers.
     */
    public static void init() {
        Set<Long> managerHolder = commands.getDefaultSettings().getPermissionHolders("manager");
        Set<Long> adminHolder = commands.getDefaultSettings().getPermissionHolders("admin");

        managerHolder.clear();
        adminHolder.clear();

        // Get managers
        for (Object member : config.getList("bot.owners")) {
            managers.add(Long.parseLong((String) member));
        }

        // Get support members
        Guild supportGuild = jda.getGuildById(config.getString("guild.id"));
        if (supportGuild != null) {
            RoleHolder.setSupport(supportGuild.getRoleById(config.getString("guild.roles.admin")));

            for (Member member : supportGuild.getMembersWithRoles(RoleHolder.getSupport())) {
                admins.add(member.getIdLong());
            }

            // Add managers as well
            admins.addAll(managers);
        }

        managerHolder.addAll(managers);
        adminHolder.addAll(admins);
    }
}
