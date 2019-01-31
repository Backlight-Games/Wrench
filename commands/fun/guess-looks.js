const { Command } = require('discord.js-commando');
const { oneLine } = require('common-tags');
const Random = require('random-js');

const genders = ['male', 'female'];
const { eyeColors, hairColors, hairStyles, extras } = require('../commandfiles/guess-looks');

module.exports = class GuessLooksCommand extends Command {
	constructor(client) {
		super(client, {
			name: 'guess-looks',
			aliases: ['guess-my-looks', 'style'],
			group: 'fun',
			memberName: 'guess-looks',
			description: 'Guess what a user looks like.',
			examples: ['guess-looks', 'guess-looks @user'],
			args: [
				{
					key: 'user',
					prompt: 'Who do you want me to guess the looks of?',
					type: 'user',
					default: msg => msg.author
				}
			]
		});
	}
	run(msg, { user }) {
		if (user.id === this.client.user.id) return msg.reply('Just look at my profile picture!');
		const authorUser = user.id === msg.author.id;
		const random = new Random(Random.engines.mt19937().seed(user.id));
		const gender = genders[random.integer(0, genders.length - 1)];
		const eyeColor = eyeColors[random.integer(0, eyeColors.length - 1)];
		const hairColor = hairColors[random.integer(0, hairColors.length - 1)];
		const hairStyle = hairStyles[random.integer(0, hairStyles.length - 1)];
		const age = random.integer(10, 100);
		const feet = random.integer(3, 7);
		const inches = random.integer(0, 11);
		const weight = random.integer(50, 300);
		const extra = extras[random.integer(0, extras.length - 1)];
		if (user.id == 272466470510788608) return msg.reply(`I think ${authorUser ? 'you are' : `${user.username} is`} at least a year old male with blue eyes and short dirty blonde hair. ${authorUser ? 'You are' : `${gender === 'male' ? 'He' : 'He'} is`} 5' 6" and weigh${authorUser ? '' : 's'} 124 pounds. Don't forget the emmense knowledge of tech!`);
		return msg.reply(oneLine`
			I think ${authorUser ? 'you are' : `${user.username} is`} a ${age} year old ${gender} with ${eyeColor} eyes
			and ${hairStyle} ${hairColor} hair. ${authorUser ? 'You are' : `${gender === 'male' ? 'He' : 'She'} is`}
			${feet}'${inches}" and weigh${authorUser ? '' : 's'} ${weight} pounds. Don't forget the ${extra}!
		`);
	}
};
