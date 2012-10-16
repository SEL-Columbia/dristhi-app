Handlebars.registerHelper('imageBasedOnYesOrNo', function (val, options) {
    if (val.toString().toUpperCase() === "yes".toUpperCase())
        return '<img class="yes" />';
    else
        return '<img class="no" />';
});

Handlebars.registerHelper('formatBooleanToYesOrNo', function (val, options) {
    if (val.toString().toUpperCase() === "yes".toUpperCase())
        return "Yes";
    else
        return "No";
});


Handlebars.registerHelper('imageForDeliveryFacility', function (val, options) {
    const SUB_DISTRICT_HOSPITAL = "sdh";
    const DISTRICT_HOSPITAL = "dh";
    const HOME = "home";

    if (val.isHighRisk && !(val.deliveryFacilityType === SUB_DISTRICT_HOSPITAL || val.deliveryFacilityType === DISTRICT_HOSPITAL))
        return '<img class="no" />';
    else if (val.deliveryFacilityType === HOME) {
        return '<img class="no" />';
    }
    else return '<img class="yes" />';
});