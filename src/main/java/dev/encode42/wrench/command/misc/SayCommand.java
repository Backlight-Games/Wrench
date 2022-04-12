package dev.encode42.wrench.command.misc;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Concat;
import com.github.kaktushose.jda.commands.annotations.Permission;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;

@CommandController
public class SayCommand {
    @Command(value = {"say", "parrot"},
            name = "Say",
            usage = "{prefix}say <message>",
            category = "Utility")
    @Permission("admin")
    public void sayCommand(CommandEvent event, @Concat String message) {
        // Delete the message
        event.getMessage().delete().queue();

        // Parrot
        event.reply(message);
    }
}
