describe('Smart Helpers', function () {

    var smartHelper;

    beforeEach(module("smartRegistry.services"));
    beforeEach(inject(function (SmartHelper) {
        smartHelper = SmartHelper;
    }));

    describe("Date difference function", function(){
        it("should calculate the difference between an earlier date and a later date as positive", function(){
            expect(
                smartHelper.daysBetween(
                    new Date(Date.parse("2012-02-29")), new Date(Date.parse("2012-03-01")))).toEqual(1);
        });

        it("should calculate the difference between a later date and an earlier date as negative", function(){
            expect(
                smartHelper.daysBetween(
                    new Date(Date.parse("2012-03-01")), new Date(Date.parse("2012-02-29")))).toEqual(-1);
        });
    });
});