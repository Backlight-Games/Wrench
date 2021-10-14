package games.backlight.wrench.listener;

import dev.encode42.copper.config.Config;
import dev.encode42.copper.config.Language;
import dev.encode42.copper.util.Util;
import games.backlight.wrench.Wrench;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Listener
public class CommonListener extends ListenerAdapter {
    protected final Config config = Wrench.getConfig();
    protected final Language lang = Wrench.getLang();
    protected Guild guild;
    protected Member member;
    protected Message message;
    protected boolean valid;

    @Override
    public void onButtonClick(ButtonClickEvent event) {
        guild = event.getGuild();
        member = event.getMember();
        message = event.getMessage();

        valid = !Util.isEqualSome(null, guild, member, message);
    }
}
