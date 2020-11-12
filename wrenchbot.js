/*
              _______
             /       \
            /   /####-
           /   /
        __/   /####
       /         \
      /         ./
     /          /   __    __ _______
     \_____   //   |  |__|  |   __  \
     /@@@//  //    |  |  |  |  |__\  |
    /  /_/  //     |        |  |__/  |
    \_/ /  //      |________|_______/
      _/__//
     /   / /            WrenchBot
    /   / /    Fun and Helpful Discord Bot
   /   / /
  /   / /              Version 2.X
 /   / /           https://encode42.dev
/  O  /
\____/
*/

// Define and require modules
const { CommandoClient }  = require("discord.js-commando");
const { stripIndents }    = require("common-tags");
const options             = require("./config");
const readline            = require("readline");
const moment              = require("moment");
const path                = require("path");
const fs                  = require("fs");

// Console input
const input = readline.createInterface({
	"input": process.stdin,
	"output": process.stdout
});

input.on("line", i => {
	if (i === "exit") process.exit(0);
});

// Check if the auth file exists
if (!fs.existsSync("./auth.json")) {
	console.error(stripIndents`
		The file \"auth.json\" does not exist! This is required in order to login to your bot.
		Please refer to README.md for steps on how to get your token.
	`);

	process.exit(1);
}

// Create private folders
function createFolder(...dirs) {
	dirs.forEach(dir => {
		if (!fs.existsSync(dir)) fs.mkdirSync(dir);
	});
}
createFolder("./data/private", "./data/private/database", "./data/private/logs");

// Register + create command instance
const Config = require("./data/js/config");
const client = new CommandoClient({
	"owner":                   options.owners,
	"invite":                  options.support,
	"commandPrefix":           options.prefix.commands,
	"commandEditableDuration": 1,
	"partials":                ["MESSAGE", "CHANNEL", "REACTION", "USER", "GUILD_MEMBER"]
});
client.registry
	.registerDefaultTypes()
	.registerDefaultGroups()
	.registerGroups([
		["search",     "Search"],
		["image",      "Image"],
		["fun",        "Fun"],
		["moderation", "Moderation"],
		["misc",       "Misc"]
	])
	.registerCommandsIn(path.join(__dirname, "commands"))
	.registerDefaultCommands({ "unknownCommand": false });

// Create voice channel join/leave event
client.on("voiceStateUpdate", (from, to) => {
	if (from.channelID !== to.channelID) client.emit("voiceJoinLeave", from, to);
})

// Create message edit event
.on("messageUpdate", async (oldMessage, newMessage) => {
	oldMessage = await client.getMessage(oldMessage);
	newMessage = await client.getMessage(newMessage);

	// Make sure the edit is really an edit
	if (oldMessage.content === newMessage.content) return;
	client.emit("messageEdit", oldMessage, newMessage);
});

// Get database
const totals = require("./data/js/config").totals;

// Logger
const { log, logger } = require("./data/js/logger");
logger(client, totals);

if (fs.existsSync("./data/private/enmap")) require("./data/js/config").migrateFromEnmap(log);

// Get utilities
listen(["./data/js/util.js"]);

function listen(files) {
	files.forEach(f => {
		require(f).run(client);
		fs.watchFile(f, () => {
			log.info(`${f} was changed! Updating...`);
			delete require.cache[require.resolve(f)];

			try {require(f).run(client)}
			catch (e) {log.error(`There was an error updating the file! ${e}`)}
		});
	});
}

// Start modules
const automod   = require("./data/js/automod");
const reactions = require("./data/js/reactions");

client.on("ready", async () => {
	log.ok("------------------------------------------");
	log.ok(` WrenchBot START ON: ${moment().format("MM/DD/YY hh:mm:ss A")}`);
	log.ok("------------------------------------------");
	log.info(`Name: ${client.user.tag} | ID: ${client.user.id}`);
	log.info(`${await totals.get("commands")} commands used | ${await totals.get("messages")} messages read | ${client.guilds.cache.size} servers`);

	// Set the bots status
	if (options.status.enabled) {status(); setInterval(status, options.status.timeout)};

	async function status() {
		// Get the status
		let status = await getStatus();

		// Re-roll if repeat
		if (client.user.presence.activities[0])
			while (status.name === client.user.presence.activities[0].name) status = await getStatus();

		// Set the status
		client.user.setActivity(status.name, { "type": status.type, "url": status.url });
	}

	// Get a random status
	async function getStatus() {
		const status = options.status.types[Math.floor(Math.random() * options.status.types.length)];
		status.name = await client.placeholders(status.name);
		return status;
	}
})

.on("message", async message => {
	if (message.author.bot) return;
	message = await client.getMessage(message);

	// Run events
	reactions(message);
	guildEvents(message);

	// totals.inc("messages");
})

// Run automod and reactions on edited messages
.on("messageEdit", async (oldMessage, message) => {
	if (message.author.bot) return;

	// Run events
	reactions(message);
	guildEvents(message);
})

// Voice chat text channel role
.on("voiceJoinLeave", async (from, to) => {
	const config = new Config("guild", to.guild);
	const guildConfig = await config.get();
	if (guildConfig === "breaking") return;

	if (!guildConfig.misc.voicechat.enabled) return;

	// Get each channel/role ID
	guildConfig.misc.voicechat.ID.forEach(e => {
		const ids = e.split(",");

		// Add/remove role
		if (to.channelID === ids[0]) to.member.roles.add(ids[1]);
		else if (to.member.roles.cache.has(ids[1])) to.member.roles.remove(ids[1]);
	});
});

// Guild-only events
async function guildEvents(message) {
	if (message.guild) {
		const config = new Config("guild", message.guild);
		const guildConfig = await config.get();
		if (guildConfig === "breaking") return;

		// Run automod and reactions
		if (guildConfig.automod.enabled && !client.checkRole(message, guildConfig.automod.adminID)) automod(message);

		// Tag command
		if (message.content.indexOf(options.prefix.tags) === 0 && message.content.includes(" ")) {
			const tagCommand = client.registry.commands.find(c => c.name === "tag");
			tagCommand.run(message, { "action": message.content.slice(1) });
		}
	}
}

// Finally, log it in
client.login(require("./auth").token);