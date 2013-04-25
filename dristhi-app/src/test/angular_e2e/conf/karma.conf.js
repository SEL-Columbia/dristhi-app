basePath = '../';

files = [
    ANGULAR_SCENARIO,
    ANGULAR_SCENARIO_ADAPTER,
    '*.js'
];

autoWatch = true;

browsers = ['PhantomJS'];

proxies = {
    '/': 'http://localhost:8888/'
};

junitReporter = {
    outputFile: 'test_out/e2e.xml',
    suite: 'e2e'
};

urlRoot = '/tests/';
