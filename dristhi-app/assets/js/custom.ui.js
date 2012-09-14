Handlebars.registerHelper('ifNotZero', function(context, options) {
    if (context != 0) {
        return options.fn(this);
    }
});

Handlebars.registerHelper('ifequal', function (val1, val2, options) {
    if (val1 === val2) {
        return options.fn(this);
    }
    else {
        return options.inverse(this);
    }
});

var capitalize = function(text) { return text.slice(0, 1).toUpperCase() + text.slice(1); };

Handlebars.registerHelper('capitalize', capitalize);

Handlebars.registerHelper('camelCaseAndConvertToListItems', function(textWithSpacesAndUnderscores) {
    return new Handlebars.SafeString($(textWithSpacesAndUnderscores.trim().split(" ")).map(function(index, element) { if (element.trim() !== "") { return "<li>" + capitalize(element.trim()).replace(/_/g, " ") + "</li>"; }}).get().join(" "));
});

Handlebars.registerHelper('formatDate', function (unformattedDate) {
    var parsedDate = $.datepicker.parseDate('yy-mm-dd', unformattedDate);
    return $.datepicker.formatDate('dd-mm-yy', parsedDate);
});
