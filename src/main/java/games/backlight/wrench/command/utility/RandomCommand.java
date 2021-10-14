package games.backlight.wrench.command.utility;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.entities.CommandEvent;
import dev.encode42.copper.config.Language;
import games.backlight.wrench.Wrench;
import games.backlight.wrench.util.CommonImports;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.SplittableRandom;

@CommandController
public class RandomCommand extends CommonImports {
    @Command(value = {"random", "rand", "rng"},
            name = "Random",
            usage = "{prefix}random <min> <max>",
            desc = "Get a random number in a range.\n" +
                    "Supports decimals!",
            category = "Utility")
    public void randomCommand(CommandEvent event, BigDecimal min, BigDecimal max) {
        // Find the max decimal count
        int decimals = Math.max(min.scale(), max.scale());

        // Generate the random number
        SplittableRandom random = new SplittableRandom();
        BigDecimal number = min.add(BigDecimal.valueOf(random.nextDouble()).multiply(max.subtract(min)));
        BigDecimal scaled = number.setScale(decimals, RoundingMode.HALF_UP);

        // Reply
        event.reply(lang.read("commands.random", scaled));
    }
}
