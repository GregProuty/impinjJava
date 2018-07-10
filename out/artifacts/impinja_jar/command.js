#!/usr/bin/env node
const childClass = require('child_process');
const program = require('commander');
const readline = require('readline').emitKeypressEvents(process.stdin);
var child;

program
  .version('0.0.1')
  .description('Contact management system');

program
	.command('start <ip>')
	.alias('s')
	.description('Start reader')
	.action((ip) => {
		child = spawnChild(ip);
	});

program.parse(process.argv);

function spawnChild(ip) {
	return childClass.spawn(
	  	'java', ['-jar', 'Octane.jar', ip]
	);
}

child.stdout.on('data', function (data) {
    console.log('stdout: ' + data);
});

child.stderr.on('data', function (data) {
    console.log('stderr: ' + data);
});

process.stdin.pipe(child.stdin)
process.stdin.setRawMode(true);
process.stdin.on('keypress', (str, key) => {
	console.log('Shutting down jar and exiting...')
    process.exit();
});

