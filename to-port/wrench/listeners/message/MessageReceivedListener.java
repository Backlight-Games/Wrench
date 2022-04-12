package games.backlight.wrench.listeners.message;

import games.backlight.wrench.listeners.Listener;
import games.backlight.wrench.listeners.automod.AutoMod;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.jodah.expiringmap.ExpiringMap;
import games.backlight.wrench.Wrench;
import games.backlight.wrench.util.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Listener
public class MessageReceivedListener extends ListenerAdapter {
	private final Config config = Wrench.getConfig();
	private Map<String, Message> cache = null;

	@Override
	public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
		Message message = event.getMessage();

		// Cache the message
		cache(message);

		new AutoMod(message);
	}

	private void cache(Message message) {
		User author = message.getAuthor();

		// Build the message cache
		if (cache == null) {
			cache = ExpiringMap.builder()
				.expiration(config.getInt("bot.cache.messages"), TimeUnit.HOURS)
				.build();
		}

		// Don't process bot messages
		if (author.isBot() || author.isSystem() || message.isWebhookMessage()) {
			return;
		}

		// Add to the cache
		cache.put(message.getId(), message);
	}
}
