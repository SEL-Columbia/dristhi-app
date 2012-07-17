function EC(ecBridge) {
    return {
        populateInto: function(cssIdentifierOfRootElement) {
            $(cssIdentifierOfRootElement).html(Handlebars.templates.ec(ecBridge.getCurrentEC()));
        }
    };
}

function ECBridge() {
    return {
        getCurrentEC: function() {
            return JSON.parse(context.get());
        }
    };
}
