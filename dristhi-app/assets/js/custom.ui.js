Handlebars.registerHelper('ifNotZero', function (context, options) {
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

Handlebars.registerHelper('ifFalse', function (val, options) {
    if (typeof val === "undefined") {
        return options.inverse(this);
    } else if (val === false) {
        return options.inverse(this);
    } else if (val.toString().toUpperCase() === "no".toUpperCase()) {
        return options.inverse(this);
    } else if (val === "") {
        return options.inverse(this);
    } else if (val === "0") {
        return options.inverse(this);
    }
    else {
        return options.fn(this);
    }
});

var capitalize = function (text) {
    return text.slice(0, 1).toUpperCase() + text.slice(1);
};

Handlebars.registerHelper('capitalize', capitalize);

Handlebars.registerHelper('camelCaseAndConvertToListItems', function (textWithSpacesAndUnderscores) {
    if (typeof textWithSpacesAndUnderscores === "undefined") {
        return "";
    }
    return new Handlebars.SafeString($(textWithSpacesAndUnderscores.trim().split(" ")).map(function (index, element) {
        if (element.trim() !== "") {
            return "<li>" + capitalize(element.trim()).replace(/_/g, " ") + "</li>";
        }
    }).get().join(" "));
});

Handlebars.registerHelper('formatText', function (unformattedText) {
    if (typeof unformattedText === "undefined" || unformattedText === null) {
        return "";
    }
    return capitalize(unformattedText.trim()).replace(/_/g, " ");
});

Handlebars.registerHelper('formatDate', function (unformattedDate) {
    if (typeof unformattedDate === "undefined") {
        return "";
    }
    var parsedDate = $.datepicker.parseDate('yy-mm-dd', unformattedDate);
    return $.datepicker.formatDate('dd-mm-yy', parsedDate);
});
