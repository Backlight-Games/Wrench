// Define and require modules
const configHandler	= require("../../data/js/configHandler");
const config		= require("../../config");

module.exports = async (message) => {
	let guildConfig;
	if (message.guild) guildConfig = await configHandler.getConfig(message.guild.id);
	const content = message.content;

	if (!message.guild || guildConfig.misc.reactions.greetings === "true") checkGreetings();
	async function checkGreetings() {
		let found = false;
		const greeting = ["hello", "hi", "hey", "hiya", "heyo", "howdy", "sup", "yo", "hola", "hallo", "bonjour", "salut", "ciao", "konnichiwa"];
		const farewell = ["goodbye", "bye", "cya", "gtg"];

		if (greeting.includes(message.content)) {
			await message.channel.fetchMessages({ "limit": 4 }).then(messages => {
				messages.delete(message.id);
				messages.some(msg => {if (greeting.includes(msg.content)) found = true;});
			});

			if (!found) message.react("👋").then(async () => {await message.react("🇭"); message.react("🇮")});
		}

		if (farewell.includes(message.content)) {
			await message.channel.fetchMessages({ "limit": 4 }).then(messages => {
				messages.delete(message.id);
				messages.some(msg => {if (farewell.includes(msg.content)) found = true;});
			});

			if (!found) message.react("👋").then(async () => {await message.react("🇧"); await message.react("🇾"); message.react("🇪")});
		}
	}
	if (config.reactions.enabled && (!message.guild || guildConfig.misc.reactions.emotes === "true")) checkReactions();
	function checkReactions() {
		config.reactions.types.forEach(e => {
			if (!message.content.match(new RegExp(e.regex))) return;
			if (e.message) message.channel.send(e.message);
			if (e.emote)   message.react(e.emote);
		});
	}
};