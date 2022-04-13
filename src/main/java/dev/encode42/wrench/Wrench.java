package dev.encode42.wrench;

import com.github.kaktushose.jda.commands.JDACommands;
import dev.encode42.copper.config.Config;
import dev.encode42.copper.config.Language;
import dev.encode42.copper.helper.JDAListenerHelper;
import dev.encode42.copper.logger.SLFLogger;
import dev.encode42.copper.util.BotUtil;
import dev.encode42.wrench.holder.ColorHolder;
import dev.encode42.wrench.listener.Listener;
import dev.encode42.wrench.task.NicknameTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Wrench {
	private static Wrench instance;
	private static JDA jda;
	private static JDACommands commands;
	private static Config config;
	private static Language lang;
	private static SplittableRandom random;

	public Wrench() throws InterruptedException {
		instance = this;
		SLFLogger.setPrimary(LoggerFactory.getLogger(Wrench.class));

		// Create the config files
		config = new Config("config/config.yml");
		lang = new Language("config/language/en.yml");
		lang.setGlobals(Map.of(
			"bot.name", config.getString("bot.name"),
			"bot.version", config.getString("version")
		));

		// Start the JDA instance
		lang.info("logger.bot.starting");
		try {
			jda = JDABuilder.createLight(new Config("config/token.yml").getString("token"))
					.setChunkingFilter(ChunkingFilter.ALL)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.build();
		} catch (LoginException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Register commands
		//commands = JDACommands.start(jda, this.getClass());

		jda.awaitReady();

		// Register support listeners
		new JDAListenerHelper(
			"dev.encode42.wrench.listener",
			Listener.class,
			jda,
			true
		);

		// Load Wrench
		load();

		random = new SplittableRandom();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new NicknameTask(), 1000, 1000 * 60 * 60 * 24);

		lang.info("logger.bot.started");
	}

	public void reload() {
			lang.info("logger.config.reloading");

			// Reload the config files
			config.load();
			lang.load();

			// Load everything again
			load();

			lang.info("logger.config.reloaded");
	}

	private void load() {
		// Utilities
		BotUtil.setJda(jda);

		// Set colors
		ColorHolder.setPrimary(Color.decode(config.getString("colors.primary")));
		ColorHolder.setSecondary(Color.decode(config.getString("colors.secondary")));
		ColorHolder.setDestructive(Color.decode(config.getString("colors.destructive")));
		ColorHolder.setSuccess(Color.decode(config.getString("colors.success")));
	}

	/**
	 * Get the running Wrench instance.
	 * @return Primary Wrench instance
	 */
	public static Wrench getInstance() {
		return instance;
	}

	/**
	 * Get the primary JDA instance.
	 * @return Primary JDA instance
	 */
	public static JDA getJDA() {
		return jda;
	}

	/**
	 * Get JDA's jda-commands instance.
	 * @return JDACommands instance
	 */
	public static JDACommands getCommands() {
		return commands;
	}

	/**
	 * Get the main configuration file.
	 * @return Configuration file
	 */
	public static Config getConfig() {
		return config;
	}

	/**
	 * Get the main language file.
	 * @return Language file
	 */
	public static Language getLang() {
		return lang;
	}

	/**
	 * @return The bot's random instance.
	 */
	public static SplittableRandom getRandom() {
		return random;
	}
}
