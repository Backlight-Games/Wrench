package dev.encode42.wrench.command.utility;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import dev.encode42.wrench.Wrench;
import dev.encode42.wrench.util.CommonImports;

import java.math.BigDecimal;
import java.math.RoundingMode;

@CommandController
public class RandomCommand extends CommonImports {
    @Command(value = {"random", "rand", "rng"},
            name = "Random",
            usage = "{prefix}random <min> <max>",
            category = "Utility")
    public void randomCommand(CommandEvent event, BigDecimal min, BigDecimal max) {
        // Find the max decimal count
        int decimals = Math.max(min.scale(), max.scale());

        // Generate the random number
        BigDecimal number = min.add(BigDecimal.valueOf(Wrench.getRandom().nextDouble()).multiply(max.subtract(min)));
        BigDecimal scaled = number.setScale(decimals, RoundingMode.HALF_UP);

        // Reply
        event.reply(lang.read("commands.random", scaled));
    }
}
