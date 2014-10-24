// spec.js
describe('angularjs homepage', function() {
    it('should have a title', function() {
        browser.get('http://localhost:9091/stockmarket/game#/watchList');

        expect(browser.getTitle()).toEqual('Misys - Stock Market League');
    });
});