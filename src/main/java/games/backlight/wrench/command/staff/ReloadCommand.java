package games.backlight.wrench.command.staff;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Permission;
import com.github.kaktushose.jda.commands.entities.CommandEvent;
import games.backlight.wrench.Wrench;
import games.backlight.wrench.util.CommonImports;

@CommandController
public class ReloadCommand extends CommonImports {
    @Command(value = "reload",
            name = "Reload",
            usage = "{prefix}reload",
            desc = "Reloads the configuration files",
            category = "Staff")
    @Permission("manager")
    public void reloadCommand(CommandEvent event) {
        // Reload
        Wrench.getInstance().reload();

        // Reply
        event.reply(lang.read("commands.reload"));
    }
}
