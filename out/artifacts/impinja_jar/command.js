#!/usr/bin/env node
const childClass = require('child_process');
const program = require('commander');
const readline = require('readline').emitKeypressEvents(process.stdin);
const chalk = require('chalk');

var child;
var ip_address;

//set cli version, description and start command
program
  .version('0.0.1')
  .description('inpinja setter');

program
	.command('start <ip>')
	.alias('s')
	.description('Start reader')
	.action((ip) => {
		console.log(chalk.blue('\nStarting child process\n'))
		console.log('  >> java -jar Octane.jar ' + ip)
		ip_address = ip;
		child = spawnChild(ip);
	});

program.parse(process.argv);

//create node child process and start the jar inside
function spawnChild(ip) {
	return childClass.spawn(
	  	'java', ['-jar', 'Octane.jar', ip]
	);
}

//on java stdout event, log data 
child.stdout.on('data', function (data) {
	data = data.toString();
	if(data.length > 2) {
		console.log('	jar stdout: ' + stdoutStringFormat(data));
	}
});

//prevent tags being bunched on to one line in the console output, 
//injects \n char every 29 characters if str length is longer than 29
function stdoutStringFormat(str) {
	if(str.length > 29) {
		for(var i = 1;i < Math.floor(str.length / 29);i++) {
			str = str.split('').splice(i*29, 0, '\n').join('');
		}
	} 
	return str
}


child.stderr.on('data', function (data) {
    console.log(chalk.red('	jar stderr: ' + data));
});

//sends user input to jar's stdin, currently set to stop on any input
process.stdin.pipe(child.stdin)

//node keypress scanner, any input will be sent to jar first then close node process
process.stdin.setRawMode(true);
process.stdin.on('keypress', (str, key) => {
	console.log(chalk.blue('Closing jar...\nTerminating node process...\nDone. To restart run:\n  node command.js start '+ ip_address))
    process.exit();
});

