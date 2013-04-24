basePath = '../';

files = [
    ANGULAR_SCENARIO,
    ANGULAR_SCENARIO_ADAPTER,
    '*.js'
];

autoWatch = true;

proxies = {
    '/': 'http://localhost:8080/'
};

junitReporter = {
    outputFile: 'test_out/e2e.xml',
    suite: 'e2e'
};

urlRoot = '/tests/';
