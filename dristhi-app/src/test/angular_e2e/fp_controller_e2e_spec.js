describe("FP E2E", function(){
    beforeEach(function(){
        browser().navigateTo('../assets/www/smart_registry/fp_register.html');
    });

    it("should just pass", function(){
        expect(element("#fp-client-list").count()).toEqual(1);
    });
});