package games.backlight.wrench;

import com.github.kaktushose.jda.commands.entities.CommandSettings;
import com.github.kaktushose.jda.commands.entities.JDACommands;
import com.github.kaktushose.jda.commands.entities.JDACommandsBuilder;
import games.backlight.wrench.util.BotUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import games.backlight.wrench.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Wrench {
	private static final Logger logger = LoggerFactory.getLogger(Wrench.class);
	private static Wrench instance;
	private static JDA jda;
	private static JDACommands commands;
	private static Config config;
	private static Language lang;


	public Wrench() throws LoginException {
		instance = this;

		// Create the config files
		config = new Config("config/config.yml");
		lang = new Language("config/language/en.yml");
		Config token = new Config("config/token.yml", "token.yml");

		// Start the JDA instance
		logger.info(lang.read("logger.bot.starting"));
		jda = JDABuilder.createLight(token.getString("token")).build();

		// Register commands
		commands = new JDACommandsBuilder(jda)
			.setCommandPackage("games.backlight.wrench.commands")
			.setDefaultSettings(
				new CommandSettings(
					config.getString("bot.prefix.commands"),
					true,
					true,
					config.getBoolean("bot.prefix.mention")
				)
			)
			.build();

		BotUtil.registerListeners();

		logger.info(lang.read("logger.bot.started"));
	}

	public void reload() {
		logger.info(lang.read("logger.config.reloading"));

		config.load();
		lang.load();

		logger.info(lang.read("logger.config.reloaded"));
	}

	public static Wrench getInstance() {
		return instance;
	}

	public static JDA getJDA() {
		return jda;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Config getConfig() {
		return config;
	}

	public static Language getLang() {
		return lang;
	}
}
