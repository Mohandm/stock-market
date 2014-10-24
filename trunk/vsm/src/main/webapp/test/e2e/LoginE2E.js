// spec.js
describe('Misys - Stock Market League', function() {

    beforeEach(function() {
        browser.get('http://localhost:9091/stockmarket/game#/form/login');
    });

    it('should have a title', function() {
        expect(browser.getTitle()).toEqual('Misys - Stock Market League');
    });

    it('should login', function() {
        var password = element(by.css('.password-field')),
            email = element(by.css('.email-field'));

        var loginButton = element(by.id('login-button-id'));
        email.sendKeys('gurudath.reddy@misys.com');
        password.sendKeys('password');
        loginButton.click();

        expect(browser.getTitle()).toEqual('Misys - Stock Market League');
    });


});