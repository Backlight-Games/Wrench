package games.backlight.wrench.util;

import dev.encode42.copper.config.Config;
import dev.encode42.copper.config.Language;
import games.backlight.wrench.Wrench;
import net.dv8tion.jda.api.JDA;

public abstract class CommonImports {
    protected static final Config config = Wrench.getConfig();
    protected static final Language lang = Wrench.getLang();
    protected static final JDA jda = Wrench.getJDA();
}
