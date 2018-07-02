var child = require('child_process').spawn(
  'java', ['-jar', 'Octane.jar']
);

child.stdout.on('data', function (data) {
    console.log('stdout: ' + data);
});

child.stderr.on('data', function (data) {
    console.log('stderr: ' + data);
});