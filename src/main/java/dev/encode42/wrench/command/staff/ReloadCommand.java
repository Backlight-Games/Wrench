package dev.encode42.wrench.command.staff;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Permission;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import dev.encode42.wrench.Wrench;
import dev.encode42.wrench.util.CommonImports;

@CommandController
public class ReloadCommand extends CommonImports {
    @Command(value = "reload",
            name = "Reload",
            usage = "{prefix}reload",
            category = "Staff")
    @Permission("manager")
    public void reloadCommand(CommandEvent event) {
        // Reload
        Wrench.getInstance().reload();

        // Reply
        event.reply(lang.read("commands.reload"));
    }
}
