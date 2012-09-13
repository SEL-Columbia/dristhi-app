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

Handlebars.registerHelper('capitalize', function(text) {
  return text.slice(0, 1).toUpperCase() + text.slice(1);
});

Handlebars.registerHelper('formatDate', function (unformattedDate) {
    var parsedDate = $.datepicker.parseDate('yy-mm-dd', unformattedDate);
    return $.datepicker.formatDate('dd-mm-yy', parsedDate);
});
