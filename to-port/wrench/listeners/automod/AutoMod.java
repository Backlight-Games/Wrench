package games.backlight.wrench.listeners.automod;

import games.backlight.wrench.util.BotUtil;
import net.dv8tion.jda.api.entities.Message;

public class AutoMod {
	protected final Message message;
	public AutoMod(Message message) {
		this.message = message;
		if (check()) {
			punish();
		}
	}

	public boolean check() {
		return !BotUtil.isAdmin(message.getMember());
	}

	public void punish() {
		System.out.println("BAD!");
	}
}
