package dev.encode42.wrench.command.utility;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Concat;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import dev.encode42.wrench.util.CommonImports;

@CommandController
public class CountCommand extends CommonImports {
    @Command(value = {"count", "characters", "chars"},
            name = "Count",
            usage = "{prefix}count <message>",
            category = "Utility")
    public void countCommand(CommandEvent event, @Concat String message) {
        // Reply
        event.reply(lang.read("commands.count", message.length()));
    }
}
