Handlebars.registerHelper('ifNotZero', function(context, options) {
    if (context != 0) {
        return options.fn(this);
    }
});
