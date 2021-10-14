package games.backlight.wrench.command.utility;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Concat;
import com.github.kaktushose.jda.commands.entities.CommandEvent;
import games.backlight.wrench.util.CommonImports;

@CommandController
public class CountCommand extends CommonImports {
    @Command(value = {"count", "characters", "chars"},
            name = "Count",
            usage = "{prefix}count <message>",
            desc = "Count the characters in a message",
            category = "Utility")
    public void countCommand(CommandEvent event, @Concat String message) {
        // Reply
        event.reply(lang.read("commands.count", message.length()));
    }
}