// conf.js
exports.config = {
    seleniumAddress: 'http://localhost:4444/wd/hub',
    specs: ['./e2e/LoginE2E.js',
            './e2e/WatchListTestE2E.js'
    ],
    baseUrl: 'http://localhost:9091'
}